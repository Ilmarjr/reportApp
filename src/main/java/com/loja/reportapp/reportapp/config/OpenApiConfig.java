package com.loja.reportapp.reportapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Catalogo Reports A3")
                        .version("v1")
                        .description("API de Relatório de Vendas")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                        ));
    }
}
