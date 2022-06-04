package es.pilo.kolsc2.model.starcraft.teams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Listado de teams
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LadderTeams {

    /**
     * Lista de ladder teams
     */
    private List<LadderTeam> ladderTeams;

}
