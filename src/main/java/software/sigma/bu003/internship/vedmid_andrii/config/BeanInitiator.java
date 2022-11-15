package software.sigma.bu003.internship.vedmid_andrii.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanInitiator {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
