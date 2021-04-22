package dev.leonardolemos.backendchallenge;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Backend Challenge",
                version = "1.0",
                description = "Um teste da TOTVS CRM (antiga Wealth Systems)"
        )
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
