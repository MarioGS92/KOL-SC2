package es.pilo.kolsc2.service;

import es.pilo.kolsc2.model.oauth.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Servicio de autenticación
 */
@Slf4j
@Service
public class AuthenticationService {


    /**
     * ID de cliente
     */
    @Value("${kol.oauth.clientId}")
    private String clientId;

    /**
     * Secret de cliente
     */
    @Value("${kol.oauth.clientSecret}")
    private String clientSecret;

    /**
     * URL de token
     */
    @Value("${kol.oauth.tokenUri}")
    private String tokenUri;

    /**
     * Token actual
     */
    private Token token;

    /**
     * Template rest
     */
    private final WebClient webClient;

    /**
     * Servicio de autenticación
     *
     * @param webClient cliente web
     */
    @Autowired
    public AuthenticationService(WebClient webClient) {
        this.webClient = webClient;
    }


    /**
     * Obtiene el token si existe el autorization code
     *
     * @return token
     */
    public Mono<Token> getAuthToken() {
        if (token != null && !token.hasExpired()) {
            return Mono.just(token);
        }

        // Hacer petición a blizzard para obtener el token
        return webClient
                .post()
                .uri(tokenUri + "?" +
                        "grant_type=client_credentials" +
                        "")
                .headers(headers -> headers.setBasicAuth(clientId, clientSecret))
                .retrieve()
                .bodyToMono(Token.class)
                .map(token -> {
                    log.info("Authentication token updated.");
                    this.token = token;

                    return token;
                })
                .doOnError(e -> log.error("Error getting Blizzard authentication token.", e));
    }
}
