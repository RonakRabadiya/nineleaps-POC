package learn.rr.microservice.supplierms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("learn.rr.microservice.supplierms"))
                .paths(PathSelectors.ant("/suppliers/**"))

                .build();
    }

    // Swagger Meta Data: http://localhost:8080/v2/api-docs
    // swager UI : http://localhost:8080/swagger-ui.html

    private ApiInfo getApiInfo() {
        // @formatter:off
		return new ApiInfoBuilder()
				.title("spplier microservice")
				.description("This Page list all api(s) for supplier microservice")
				.version("1.0")
				.contact(new Contact("Ronak Rabadiya", "https://github.com/RonakRabadiya"	, "ronak.rabadiya@nineleaps.com"))
				.build();
		}
}
