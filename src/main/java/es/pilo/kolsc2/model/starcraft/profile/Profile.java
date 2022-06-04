package es.pilo.kolsc2.model.starcraft.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Perfil
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    /**
     * Sumario del perfil
     */
    private ProfileSummary summary;
}
