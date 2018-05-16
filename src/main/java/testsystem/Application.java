package testsystem;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


@SpringBootApplication
@EnableScheduling
@EnableWebSocketMessageBroker
public class Application extends AbstractWebSocketMessageBrokerConfigurer {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/color").withSockJS();
    }

}
//
//https://www.concretepage.com/spring-4/spring-4-security-thymeleaf-integration-custom-login-page-and-logout-example-with-csrf-token-using-javaconfig
