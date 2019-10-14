package pets.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PetEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetEmailApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
	  return new RestTemplate();
	}
	
}
