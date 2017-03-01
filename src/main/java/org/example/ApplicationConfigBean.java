package org.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "helloservice")
public class ApplicationConfigBean {

    private static final String GREETING = "GREETING";
    
    private String greeting;

    public ApplicationConfigBean() {
    }

    public String getGreeting() {
        if (System.getenv(GREETING) != null && !System.getenv(GREETING).isEmpty())
            return System.getenv(GREETING);
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

}
