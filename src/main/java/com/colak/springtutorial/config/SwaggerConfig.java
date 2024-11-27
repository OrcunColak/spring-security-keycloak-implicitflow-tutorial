package com.colak.springtutorial.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

@Configuration

// This annotation includes security requirements for the OpenAPI documentation, specifying that the "bearer" token is
// required for accessing the endpoints.
@OpenAPIDefinition(security = { @SecurityRequirement(name = "bearer") })

// This scheme utilizes OAuth 2.0 for authentication, specifically employing the Implicit Flow, as indicated by
// @OAuthFlows(implicit = @OAuthFlow(authorizationUrl = "${security.authorization.url}"))
@SecuritySchemes(value = {
        @SecurityScheme(
                name = "bearer",
                type = SecuritySchemeType.OAUTH2,
                flows = @OAuthFlows(implicit = @OAuthFlow(authorizationUrl = "${security.authorization.url}"))) })
public class SwaggerConfig {

}
