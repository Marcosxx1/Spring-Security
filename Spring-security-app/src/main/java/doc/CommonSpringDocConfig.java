package doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class CommonSpringDocConfig {

    @Value("${spring.application.name}")
    private String title;

    @Value("${spring.application.version}")
    private String version;

    @Bean
    OpenAPI openApiDefinition() {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title(title)
                        .description("This is the " + title + " swagger documentation page")
                        .version(version)
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licences/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation().description("Terms").url("/service.html"));
        openAPI.setComponents(new Components());
        this.loadSwaggerExamplesFromResourceFolder(openAPI);
        return openAPI;
    }

    private void loadSwaggerExamplesFromResourceFolder(OpenAPI openAPI) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            String swaggerExampleFolder = "swagger.examples";
            Resource[] resources = resolver.getResources("classpath*:" + swaggerExampleFolder + "/**/*.*");
            for (Resource resource : resources) {
                openAPI.getComponents().addExamples(resource.getFilename(), this.resourceToSwaggerExample(resource));
            }
        } catch (IOException e) {
            log.error("Error loading swagger examples: ", e);
        }
    }

    private Example resourceToSwaggerExample(Resource resource) throws IOException {
        String fileAsString = resource.getContentAsString(StandardCharsets.UTF_8);
        Example example = new Example();
        example.setValue(fileAsString);
        return example;
    }
}
