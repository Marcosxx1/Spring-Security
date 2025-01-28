package doc;

import com.tupperware.recruitment.platform.login.common.security.config.HttpSecurityConfigurer;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.Priority;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@ConditionalOnClass(SecurityAutoConfiguration.class)
@Configuration
public class CommonSpringDocSecurityConfig {

    private static final String[] ALLOWED_REQUEST_MATCHERS = {
        "/v3/api-docs", "/v3/api-docs/swagger-config", "/swagger-ui.html", "/swagger-ui/**"
    };

    private static final String SCHEME_NAME = "Bearer Authentication";
    private static final String BEARER_FORMAT = "JWT";
    private static final String SCHEME = "bearer";

    @Bean
    OpenApiCustomizer securityCustomizer() {
        return openApi -> openApi.addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
                .components(openApi.getComponents()
                        .addSecuritySchemes(
                                SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .bearerFormat(BEARER_FORMAT)
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme(SCHEME)));
    }

    @Priority(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public HttpSecurityConfigurer springDocSecurityHttpConfigurer() {
        return httpSecurity -> httpSecurity.authorizeHttpRequests(
                authorize -> authorize.requestMatchers(ALLOWED_REQUEST_MATCHERS).permitAll());
    }
}
