package es.pilo.kolsc2.model.starcraft.summary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Ladder donde el jugador está
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LadderMembership {

    /**
     * ID de ladder
     */
    private String ladderId;

    /**
     * Nombre de liga
     */
    private String localizedGameMode;

    /**
     * Rango
     */
    private Integer rank;

    /**
     * Índice del nivel del gamemode
     *
     * @return int
     */
    public int getLocalizedGameModeIndex() {
        if (localizedGameMode == null) {
            return -1;
        }

        String level = localizedGameMode.split(" ")[1];
        return switch (level) {
            case "Bronce" -> 5;
            case "Silver" -> 4;
            case "Platinum" -> 3;
            case "Diamond" -> 2;
            case "Master" -> 1;
            default -> 0;
        };
    }

}
