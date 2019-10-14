package pets;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import pets.Specie;
import pets.PaymentMethod;
import pets.Pet;
import pets.User;
import pets.Specie.Type;
import pets.data.SpecieRepository;
import pets.data.PaymentMethodRepository;
import pets.data.PetRepository;
import pets.data.UserRepository;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {

  @Bean
  public CommandLineRunner dataLoader(SpecieRepository repo,
        UserRepository userRepo, PasswordEncoder encoder, PetRepository petRepo,
        PaymentMethodRepository paymentMethodRepo) { // user repo for ease of testing with a built-in user
    
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
        Specie dog = saveAnSpecie("DOG", "Dog", Type.ANIMAL);
        Specie cat = saveAnSpecie("CAT", "Cat", Type.ANIMAL);
        Specie lizard = saveAnSpecie("LIZ", "Lizard", Type.ANIMAL);
        Specie snake = saveAnSpecie("SNK", "Snake", Type.ANIMAL);
        Specie fish = saveAnSpecie("FISH", "Fish", Type.ANIMAL);
        Specie spider = saveAnSpecie("SPDR", "Spider", Type.ANIMAL);
        Specie rodent = saveAnSpecie("RAT", "Rodent", Type.ANIMAL);
       
        Specie male = saveAnSpecie("MALE", "Male", Type.GENDER);
        Specie female = saveAnSpecie("FEM", "Female", Type.GENDER);
        
        Specie lBrown = saveAnSpecie("LB", "Light Brown", Type.COLOUR);
        Specie dBrown = saveAnSpecie("DB", "Dark Brown", Type.COLOUR);
        Specie red = saveAnSpecie("RED", "Red/Ginger/Orange", Type.COLOUR);
        Specie black = saveAnSpecie("BLCK", "Black", Type.COLOUR);
        Specie grey = saveAnSpecie("GREY", "Grey", Type.COLOUR);
        Specie white = saveAnSpecie("WHTE", "White", Type.COLOUR);
        Specie yellow = saveAnSpecie("YELO", "Yellow", Type.COLOUR);
        
        Specie stripes = saveAnSpecie("STRI", "Striped pattern", Type.UNIQUE_DETAIL);
        Specie dots = saveAnSpecie("DOTS", "Dotted/Spotted pattern", Type.UNIQUE_DETAIL);
        Specie tippedPaws = saveAnSpecie("TIPP", "Tipped Paw", Type.UNIQUE_DETAIL);
        Specie shortTail = saveAnSpecie("ST", "Short Tail", Type.UNIQUE_DETAIL);
        Specie longTail = saveAnSpecie("LT", "Long Tail", Type.UNIQUE_DETAIL);
        
        
        Specie brave = saveAnSpecie("BRAV", "Brave", Type.PERSONALITY);
        Specie shy = saveAnSpecie("SHY", "Shy/skitish", Type.PERSONALITY);
        Specie friendly = saveAnSpecie("FRI", "Frienly", Type.PERSONALITY);
        Specie agressive = saveAnSpecie("AGRE", "Agressive", Type.PERSONALITY);
        Specie adventurous = saveAnSpecie("ADV", "Adventurous", Type.PERSONALITY);
        Specie funny = saveAnSpecie("FUN", "Funny", Type.PERSONALITY);
        
        
//        UserUDT u = new UserUDT(username, fullname, phoneNumber)
        
        userRepo.save(new User("habuma", encoder.encode("password"), 
              "Craig Walls", "123 North Street", "Cross Roads", "TX", 
              "76227", "123-123-1234", "craig@habuma.com"))
          .subscribe(user -> {
              paymentMethodRepo.save(new PaymentMethod(user, "4111111111111111", "321", "10/25")).subscribe();
          });        
        
//        Pet pet1 = new Pet();
//        pet1.setId("TACO1");
//        pet1.setName("Carnivore");
//        pet1.setSpecies(Arrays.asList(flourTortilla, groundBeef, carnitas, sourCream, salsa, cheddar));
//        petRepo.save(pet1).subscribe();
//
//        Pet pet2 = new Pet();
//        pet2.setId("TACO2");
//        pet2.setName("Bovine Bounty");
//        pet2.setSpecies(Arrays.asList(cornTortilla, groundBeef, cheddar, jack, sourCream));
//        petRepo.save(pet2).subscribe();
//
//        Pet pet3 = new Pet();
//        pet3.setId("TACO3");
//        pet3.setName("Veg-Out");
//        pet3.setSpecies(Arrays.asList(flourTortilla, cornTortilla, tomatoes, lettuce, salsa));
//        petRepo.save(pet3).subscribe();

      }

      private Specie saveAnSpecie(String id, String name, Type type) {
        Specie specie = new Specie(id, name, type);
        repo.save(specie).subscribe();
        return specie;
      }
    };
  }
  
}
