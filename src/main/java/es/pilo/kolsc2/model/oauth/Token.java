package es.pilo.kolsc2.model.oauth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Token
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    /**
     * Token
     */
    @JsonProperty(value = "access_token")
    private String accessToken;

    /**
     * Tipo de token
     */
    @JsonProperty(value = "token_type")
    private String tokenType;

    /**
     * En cuanto expira
     */
    @JsonProperty(value = "expires_in")
    private long expiresIn;

    /**
     * Scope
     */
    private String scope;

    /**
     * Fecha de recepción
     */
    @JsonIgnore
    private long receivedIn;

    /**
     * Comprueba si ha expirado
     *
     * @return true si ha expirado el token
     */
    public boolean hasExpired() {
        return (receivedIn + expiresIn) > System.currentTimeMillis();
    }

}
