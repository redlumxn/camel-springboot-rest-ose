package org.example;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.cdi.ContextName;

@Component
@ContextName("rest-dsl")
public class APIRoute extends RouteBuilder {

    @Bean
    ServletRegistrationBean camelServlet() {
        ServletRegistrationBean mapping = new ServletRegistrationBean();
        mapping.setName("CamelServlet");
        mapping.setLoadOnStartup(1);
        // CamelHttpTransportServlet is the name of the Camel servlet to use
        mapping.setServlet(new CamelHttpTransportServlet());
        mapping.addUrlMappings("/api/*");
        return mapping;
    }

    @Override
    public void configure() throws Exception {        
        // configure swagger doc
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json)            
            // and output using pretty print
            .dataFormatProperty("prettyPrint", "true")
            // add swagger api-doc out of the box
            .apiContextPath("/api-doc")
            .apiProperty("api.title", "Helloservice API").apiProperty("api.version", "1.0.0")
            // and enable CORS
            .apiProperty("cors", "true")
            // and return right api doco host
            .apiProperty("base.path", "/api")
            // set host on swagger doc
            .apiProperty("host", (System.getenv("SWAGGERUI_HOST") != null? System.getenv("SWAGGERUI_HOST") : "localhost:8080"));

        // rest service
        rest()
            .get("/hello/{personId}")
            .to("direct:getPersonId");
    }
}