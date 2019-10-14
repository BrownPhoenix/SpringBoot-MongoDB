package pets.web.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import pets.Specie;
import pets.Order;
import pets.PaymentMethod;
import pets.Pet;
import pets.User;
import pets.data.SpecieRepository;
import pets.data.PaymentMethodRepository;
import pets.data.UserRepository;
import pets.web.api.EmailOrder.EmailPet;
import reactor.core.publisher.Mono;

@Service
public class EmailOrderService {

  private UserRepository userRepo;
  private SpecieRepository specieRepo;
  private PaymentMethodRepository paymentMethodRepo;

  public EmailOrderService(UserRepository userRepo, SpecieRepository specieRepo,
      PaymentMethodRepository paymentMethodRepo) {
    this.userRepo = userRepo;
    this.specieRepo = specieRepo;
    this.paymentMethodRepo = paymentMethodRepo;
  }
  
  public Mono<Order> convertEmailOrderToDomainOrder(Mono<EmailOrder> emailOrder) {
    // TODO: Probably should handle unhappy case where email address doesn't match a given user or
    //       where the user doesn't have at least one payment method.
    
    return emailOrder.flatMap(eOrder -> {
      Mono<User> userMono = userRepo.findByEmail(eOrder.getEmail());
      
      Mono<PaymentMethod> paymentMono = userMono.flatMap(user -> {
        return paymentMethodRepo.findByUserId(user.getId());
      });
      return Mono.zip(userMono, paymentMono)
          .flatMap(tuple -> {
            User user = tuple.getT1();
            PaymentMethod paymentMethod = tuple.getT2();
            Order order = new Order();
            order.setUser(user);
            order.setCcNumber(paymentMethod.getCcNumber());
            order.setCcCVV(paymentMethod.getCcCVV());
            order.setCcExpiration(paymentMethod.getCcExpiration());
            order.setDeliveryName(user.getFullname());
            order.setDeliveryStreet(user.getStreet());
            order.setDeliveryCity(user.getCity());
            order.setDeliveryState(user.getState());
            order.setDeliveryZip(user.getZip());
            order.setPlacedAt(new Date());
            
            return emailOrder.map(eOrd -> {
              List<EmailPet> emailPets = eOrd.getPets();
              for (EmailPet emailPet : emailPets) {
                List<String> specieIds = emailPet.getSpecies();
                List<Specie> species = new ArrayList<>();
                for (String specieId : specieIds) {
                  Mono<Specie> specieMono = specieRepo.findById(specieId);
                  specieMono.subscribe(specie -> 
                      species.add(specie));
                }
                Pet pet = new Pet();
                pet.setName(emailPet.getName());
                pet.setSpecies(species);
                order.addDesign(pet);
              }
              return order;
            });
          });
    });
  }
  
}
