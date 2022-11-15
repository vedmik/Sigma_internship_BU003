package software.sigma.bu003.internship.coparts.client.technomir.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tehnomir-api")
@Getter
@Setter
public class TehnomirClientConfig {
        @Value("url-to-api")
        private String urlToApi;
        @Value("api-token")
        private String apiToken;
}
