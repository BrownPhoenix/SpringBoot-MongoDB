package pets;

import lombok.Data;

@Data
public class Specie {

  private final String name;
  private final Type type;
  
  public static enum Type {
	  ANIMAL,GENDER, COLOUR, UNIQUE_DETAIL,PERSONALITY
  }
  
}
