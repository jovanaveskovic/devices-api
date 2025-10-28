package challenge.devices.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI devicesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Devices API")
                        .description("REST API for managing devices")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Device Team")
                                .email("support@device-service.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

}
