package es.pilo.kolsc2.model.competition;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Jugador de la competición
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    /**
     * ID del jugador
     */
    private Integer id;

    /**
     * Nombre que se muestra
     */
    private String playerName;

    /**
     * Nombre del clan
     */
    private String clanName;

    /**
     * Tag del clan
     */
    private String clanTag;

    /**
     * Retrato
     */
    private String portrait;

    /**
     * Raza favorita
     */
    private String favoriteRace;

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
