package pets;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@Document
public class Specie {
  
  @Id
  private final String id;
  private final String name;
  private final Type type;
  
  public static enum Type {
	  ANIMAL,GENDER, COLOUR, UNIQUE_DETAIL,PERSONALITY
  }

}
