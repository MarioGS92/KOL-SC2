package es.pilo.kolsc2.api;

import es.pilo.kolsc2.model.competition.Player;
import es.pilo.kolsc2.model.starcraft.ProfileDescription;
import es.pilo.kolsc2.model.starcraft.profile.Profile;
import es.pilo.kolsc2.model.starcraft.summary.LadderSummary;
import es.pilo.kolsc2.model.starcraft.teams.LadderTeams;
import es.pilo.kolsc2.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST para profile
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    /**
     * Servicio de perfil
     */
    private final ProfileService profileService;

    /**
     * Crea el controlador de perfil
     *
     * @param profileService servicio de perfil
     */
    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Obtiene un perfil desde su URL
     *
     * @param url URL del perfil
     * @return perfil
     */
    @PostMapping
    public Mono<Profile> getProfile(@RequestBody String url) {
        return profileService.getProfile(new ProfileDescription(url));
    }

    /**
     * Obtiene un ladder summary desde su URL
     *
     * @param url URL del perfil
     * @return ladder summary
     */
    @PostMapping("/ladder/summary")
    public Mono<LadderSummary> getLadderSummary(@RequestBody String url) {
        return profileService.getLadderSummary(new ProfileDescription(url));
    }

    /**
     * Obtiene un ladder summary desde su URL
     *
     * @param url URL del perfil
     * @param id  ID de ladder
     * @return ladder summary
     */
    @PostMapping("/ladder/{id}")
    public Mono<LadderTeams> getLadderSummary(@RequestBody String url, @PathVariable Long id) {
        return profileService.getLadder(new ProfileDescription(url), id);
    }

    /**
     * Obtiene la información resumida de un player
     *
     * @param url URL del perfil
     * @return ladder summary
     */
    @PostMapping("/grouped")
    public Mono<Player> getFullInfoOfPlayer(@RequestBody String url) {
        return profileService.getPlayerCompetitionInfo(new ProfileDescription(url));
    }

    /**
     * Obtiene la información resumida de un player
     *
     * @param url URL del perfil
     * @return ladder summary
     */
    @PostMapping("/grouped/list")
    public Flux<Player> getFullInfoOfPlayer(@RequestBody List<String> url) {
        return profileService.getPlayerCompetitionInfo(url.stream()
                .map(ProfileDescription::new)
                .collect(Collectors.toList())
        );
    }

}
