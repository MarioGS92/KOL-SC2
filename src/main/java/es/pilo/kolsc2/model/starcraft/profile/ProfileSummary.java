package es.pilo.kolsc2.model.starcraft.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Sumario del perfil de un usuario
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileSummary {

    /**
     * Nombre que se muestra del jugador
     */
    private String displayName;

    /**
     * Nombre de clan
     */
    private String clanName;

    /**
     * TAG del clan
     */
    private String clanTag;

    /**
     * URL del retrato
     */
    private String portrait;
}
