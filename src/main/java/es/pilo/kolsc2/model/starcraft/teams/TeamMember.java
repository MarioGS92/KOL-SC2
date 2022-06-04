package es.pilo.kolsc2.model.starcraft.teams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Miembro de un equipo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember {

    /**
     * ID del miembro de equipo
     */
    private String id;

    /**
     * Realm al que pertenece
     */
    private Integer realm;

    /**
     * Región a la que pertenece
     */
    private Integer region;

    /**
     * Nombre del miembro
     */
    private String displayName;

    /**
     * Tag del miembro
     */
    private String clanTag;

    /**
     * Raza favorita
     */
    private String favoriteRace;

}
