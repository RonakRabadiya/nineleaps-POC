package learn.rr.microservice.productms.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfiguration implements WebMvcConfigurer {

    public void  addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestloggingInterceptor());
    }
}
