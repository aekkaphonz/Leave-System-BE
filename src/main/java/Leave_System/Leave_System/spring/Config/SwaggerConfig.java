package Leave_System.Leave_System.spring.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Leave System API")
                        .version("1.0")
                        .description("API documentation for Leave System application."));
    }
}
