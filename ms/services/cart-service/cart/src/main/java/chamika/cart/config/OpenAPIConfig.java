package chamika.cart.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${server.port}")
    private String serverPort;

    @Bean
    public OpenAPI myOpenAPI() {

        Server devServer = new Server();
        devServer.setUrl("http://localhost:" + serverPort);
        devServer.setDescription("Development environment");

        Contact contact = new Contact();
        contact.setName("Cart Service API");
        contact.setEmail("chamika.21@cse.mrt.ac.lk");


        Info info = new Info()
                .title("Cart Service API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints for managing carts of customers in e-commerce app.");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}