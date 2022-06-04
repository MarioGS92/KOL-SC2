package es.pilo.kolsc2.model.starcraft.summary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Sumario de lader
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LadderSummary {

    /**
     * Entradas de ladder
     */
    private List<LadderMembership> allLadderMemberships;
}
