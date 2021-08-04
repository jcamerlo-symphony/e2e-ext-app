package com.symphony.e2eextensionapp;

import configuration.SymConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class E2eExtApiTestApp {
    private static SymConfig config;

    public static String hostname;
    public static String namespace;
    public static String ip;
    public static int port;

    public static void main(String [] args) {
        SpringApplication.run(E2eExtApiTestApp.class, args);
    }

    public SymConfig generateConfig(String hostname, String namespace, int port) {
        SymConfig c = new SymConfig();

        c.setAgentHost( hostname );
        c.setKeyAuthHost( hostname );
        c.setPodHost( hostname );
        c.setSessionAuthHost( hostname );

        c.setAgentPort( port );
        c.setKeyAuthPort( port );
        c.setPodPort( port );
        c.setSessionAuthPort( port );

        c.setAppPrivateKeyPath("rsa/");
        c.setAppPrivateKeyName("private.pem");

        c.setAppId("e2e-ext-app");

        return c;
    }

    public E2eExtApiTestApp(@Value("${target.hostname}") String hostname, @Value("${target.namespace}") String namespace, @Value("${self.ip}") String ip, @Value("${target.port}") int port) {
        E2eExtApiTestApp.hostname = hostname;
        E2eExtApiTestApp.namespace = namespace;
        E2eExtApiTestApp.ip = ip;
        E2eExtApiTestApp.port = port;

        config = this.generateConfig(hostname, namespace, port);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/bundle.json").allowedOrigins("*");
            }
        };
    }

    public static SymConfig getConfig() {
        return config;
    }
}
