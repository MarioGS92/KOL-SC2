package es.pilo.kolsc2.model.starcraft;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Perfil separado del usuario
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDescription {

    /**
     * ID de la región
     */
    private Integer regionId;

    /**
     * ID del realm
     */
    private Integer realmId;

    /**
     * ID del perfil
     */
    private Integer profileId;

    /**
     * Crea la descripción de perfil con base en su URL
     *
     * @param profileUrl URL del perfil
     */
    public ProfileDescription(String profileUrl) {
        // Separar la URL
        String[] splitted = profileUrl.split("/");

        profileId = Integer.valueOf(splitted[splitted.length - 1]);
        realmId = Integer.valueOf(splitted[splitted.length - 2]);
        regionId = Integer.valueOf(splitted[splitted.length - 3]);
    }

    /**
     * String de la región
     *
     * @return String con la región
     */
    public String getRegionString() {
        return switch (regionId) {
            case 1 -> "us";
            case 2 -> "eu";
            case 3 -> "ko";
            default -> "cn";
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileDescription that = (ProfileDescription) o;
        return Objects.equals(regionId, that.regionId) && Objects.equals(realmId, that.realmId) && Objects.equals(profileId, that.profileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionId, realmId, profileId);
    }
}
