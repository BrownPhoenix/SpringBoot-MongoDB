package pets.email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * <p>Handles email content as pet orders where...</p>
 *  <li> The order's email is the sender's email</li>
 *  <li> The email subject line *must* be "TACO ORDER" or else it will be ignored</li>
 *  <li> Each line of the email starts with the name of a pet design, followed by a colon,
 *    followed by one or more specie names in a comma-separated list.</li>
 *    
 * <p>The specie names are matched against a known set of species using a LevenshteinDistance
 * algorithm. As an example "beef" will match "GROUND BEEF" and be mapped to "GRBF"; "corn" will
 * match "Corn Tortilla" and be mapped to "COTO".</p>
 * 
 * <p>An example email body might look like this:</p>
 * 
 * <code>
 * Corn Carnitas: corn, carnitas, lettuce, tomatoes, cheddar<br/>
 * Veggielicious: flour, tomatoes, lettuce, salsa
 * </code>
 * 
 * <p>This will result in an order with two pets where the names are "Corn Carnitas" and "Veggielicious".
 * The species will be {COTO, CARN, LETC, TMTO, CHED} and {FLTO,TMTO,LETC,SLSA}.</p>
 */
@Component
public class EmailToOrderTransformer
     extends AbstractMailMessageTransformer<Order> {
  
  private static final String SUBJECT_KEYWORDS = "TACO ORDER";

  @Override
  protected AbstractIntegrationMessageBuilder<Order> 
                doTransform(Message mailMessage) throws Exception {
    Order petOrder = processPayload(mailMessage);
    return MessageBuilder.withPayload(petOrder);
  }
  
  private Order processPayload(Message mailMessage) {
    try {
      String subject = mailMessage.getSubject();
      if (subject.toUpperCase().contains(SUBJECT_KEYWORDS)) {
        String email = 
              ((InternetAddress) mailMessage.getFrom()[0]).getAddress();
        String content = mailMessage.getContent().toString();
        return parseEmailToOrder(email, content);
      }
    } catch (MessagingException e) {
    } catch (IOException e) {}
    return null;
  }

  private Order parseEmailToOrder(String email, String content) {
    Order order = new Order(email);
    String[] lines = content.split("\\r?\\n");
    for (String line : lines) {
      if (line.trim().length() > 0 && line.contains(":")) {
        String[] lineSplit = line.split(":");
        String petName = lineSplit[0].trim();
        String species = lineSplit[1].trim();
        String[] speciesSplit = species.split(",");
        List<String> specieCodes = new ArrayList<>();
        for (String specieName : speciesSplit) {
          String code = lookupSpecieCode(specieName.trim());
          if (code != null) {
            specieCodes.add(code);
          }
        }
        
        Pet pet = new Pet(petName);
        pet.setSpecies(specieCodes);
        order.addPet(pet);
      }
    }
    return order;
  }
  
  private String lookupSpecieCode(String specieName) {
    for (Specie specie : ALL_SPECIES) {
      String ucSpecieName = specieName.toUpperCase();
      if (LevenshteinDistance.getDefaultInstance().apply(ucSpecieName, specie.getName()) < 3 ||
          ucSpecieName.contains(specie.getName()) ||
          specie.getName().contains(ucSpecieName)) {
        return specie.getCode();
      }
    }
    return null;
  }
  
  private static Specie[] ALL_SPECIES = new Specie[] {
      new Specie("DOG", "Dog"),
      new Specie("CAT", "Cat"),
      new Specie("LIZ", "Lizard"),
      new Specie("SNK", "Snake"),
      new Specie("FISH", "Fish"),
      new Specie("SPDR", "Spider"),
      new Specie("RAT", "Rodent"),
      new Specie("MALE", "Male"),
      new Specie("FEMALE", "Female"),
      new Specie("LB", "Light Brown"),
      new Specie("DB", "Dark Brown"),
      new Specie("RED", "Red/Ginger/Orange"),
      new Specie("BLCK", "Black"),
      new Specie("GREY", "Grey"),
      new Specie("WHTE", "White"),
      new Specie("YELO", "Yellow"),
      new Specie("STRI", "Striped pattern"),
      new Specie("DOTS", "Dotted/Spotted pattern"),
      new Specie("TIPP", "Tipped Paw"),
      new Specie("ST", "Short Tail"),
      new Specie("LT", "Long Tail"),
      new Specie("BRAV", "Brave"),
      new Specie("SHY", "Shy"),
      new Specie("FRI", "Friendly"),
      new Specie("AGRE", "Aggresive"),
      new Specie("ADV", "Adventurous"),
      new Specie("FUN", "Funny")
     
  };
}
  