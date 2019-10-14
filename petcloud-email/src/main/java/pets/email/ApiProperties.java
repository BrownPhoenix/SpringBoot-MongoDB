package pets.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "petcloud.api")
@Component
public class ApiProperties {
  private String url;
}