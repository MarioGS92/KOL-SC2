package es.pilo.kolsc2.model.starcraft.teams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Equipo de ladder
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LadderTeam {

    /**
     * Miembros del equipo
     */
    private List<TeamMember> teamMembers;

    /**
     * Rango anterior
     */
    private Integer previousRank;

    /**
     * Puntos
     */
    private Integer points;

    /**
     * Victoria
     */
    private Integer wins;

    /**
     * Perdidos
     */
    private Integer losses;

    /**
     * MMR del equipo
     */
    private Integer mmr;

    /**
     * Fecha de entrada en liga
     */
    private Long joinTimestamp;
}
