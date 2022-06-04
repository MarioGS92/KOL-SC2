package es.pilo.kolsc2.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import es.pilo.kolsc2.model.competition.Player;
import es.pilo.kolsc2.model.starcraft.ProfileDescription;
import es.pilo.kolsc2.model.starcraft.profile.Profile;
import es.pilo.kolsc2.model.starcraft.summary.LadderMembership;
import es.pilo.kolsc2.model.starcraft.summary.LadderSummary;
import es.pilo.kolsc2.model.starcraft.teams.LadderTeam;
import es.pilo.kolsc2.model.starcraft.teams.LadderTeams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Servicio de perfil
 */
@Slf4j
@Service
public class ProfileService {

    /**
     * URL de profile
     */
    public static final String PROFILE_URL = "https://#regionString.api.blizzard.com/sc2/profile/#regionId/#realmId/#profileId?locale=en_US&access_token=#token";

    /**
     * URL del sumario de ladder
     */
    public static final String LADDER_SUMMARY_URL = "https://#regionString.api.blizzard.com/sc2/profile/#regionId/#realmId/#profileId/ladder/summary?locale=en_US&access_token=#token";

    /**
     * URL del ladder teams
     */
    public static final String LADDER_TEAMS_URL = "https://#regionString.api.blizzard.com/sc2/profile/#regionId/#realmId/#profileId/ladder/#ladderId?locale=en_US&access_token=#token";

    /**
     * Servicio de autenticación
     */
    private final AuthenticationService authenticationService;

    /**
     * Template rest
     */
    private final WebClient webClient;

    /**
     * Cache de profiles
     */
    private final Cache<ProfileDescription, Profile> profileCache = CacheBuilder.newBuilder()
            .maximumSize(200)
            .expireAfterAccess(7, TimeUnit.DAYS)
            .build();

    /**
     * Cache de ladder summary
     */
    private final Cache<ProfileDescription, LadderSummary> ladderSummaryCache = CacheBuilder.newBuilder()
            .maximumSize(200)
            .expireAfterAccess(7, TimeUnit.DAYS)
            .build();

    /**
     * Cache de ladder teams
     */
    private final Cache<Long, LadderTeams> ladderTeamsCache = CacheBuilder.newBuilder()
            .maximumSize(200)
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build();

    /**
     * Crea el servicio de perfil
     *
     * @param authenticationService servicio de autenticación
     * @param webClient             cliente web
     */
    @Autowired
    public ProfileService(AuthenticationService authenticationService, WebClient webClient) {
        this.authenticationService = authenticationService;
        this.webClient = webClient;
    }

    /**
     * Obtiene el perfil
     *
     * @param profileDescription descripción del perfil
     * @return perfil
     */
    public Mono<Profile> getProfile(ProfileDescription profileDescription) {
        Profile currentProfile = profileCache.getIfPresent(profileDescription);
        if (currentProfile != null) {
            return Mono.just(currentProfile);
        }

        // Obtiene el profile desde bnet
        return authenticationService.getAuthToken()
                .flatMap(token -> {
                    String url = PROFILE_URL
                            .replace("#token", token.getAccessToken())
                            .replace("#regionString", profileDescription.getRegionString())
                            .replace("#regionId", profileDescription.getRegionId().toString())
                            .replace("#realmId", profileDescription.getRealmId().toString())
                            .replace("#profileId", profileDescription.getProfileId().toString());

                    return webClient
                            .get()
                            .uri(url)
                            .retrieve()
                            .bodyToMono(Profile.class)
                            .doOnError(e -> log.error("Error getting profile {}.", profileDescription.getProfileId(), e));
                })
                .map(profile -> {
                    log.info("Profile get of {}", profileDescription.getProfileId());
                    profileCache.put(profileDescription, profile);

                    return profile;
                });
    }

    /**
     * Obtiene el perfil
     *
     * @param profileDescription descripción del perfil
     * @return perfil
     */
    public Mono<LadderSummary> getLadderSummary(ProfileDescription profileDescription) {
        LadderSummary currentLS = ladderSummaryCache.getIfPresent(profileDescription);
        if (currentLS != null) {
            return Mono.just(currentLS);
        }

        // Obtiene el profile desde bnet
        return authenticationService.getAuthToken()
                .flatMap(token -> {
                    String url = LADDER_SUMMARY_URL
                            .replace("#token", token.getAccessToken())
                            .replace("#regionString", profileDescription.getRegionString())
                            .replace("#regionId", profileDescription.getRegionId().toString())
                            .replace("#realmId", profileDescription.getRealmId().toString())
                            .replace("#profileId", profileDescription.getProfileId().toString());

                    return webClient
                            .get()
                            .uri(url)
                            .retrieve()
                            .bodyToMono(LadderSummary.class)
                            .doOnError(e -> log.error("Error getting ladder summary of {}.", profileDescription.getProfileId(), e));
                })
                .map(ls -> {
                    log.info("Ladder Summary get of {}", profileDescription.getProfileId());
                    ladderSummaryCache.put(profileDescription, ls);

                    return ls;
                });
    }

    /**
     * Obtiene el perfil
     *
     * @param profileDescription descripción del perfil
     * @param ladderId           ladder ID
     * @return perfil
     */
    public Mono<LadderTeams> getLadder(ProfileDescription profileDescription, Long ladderId) {
        LadderTeams currentLT = ladderTeamsCache.getIfPresent(ladderId);
        if (currentLT != null) {
            return Mono.just(currentLT);
        }

        // Obtiene el profile desde bnet
        return authenticationService.getAuthToken()
                .flatMap(token -> {
                    String url = LADDER_TEAMS_URL
                            .replace("#token", token.getAccessToken())
                            .replace("#regionString", profileDescription.getRegionString())
                            .replace("#regionId", profileDescription.getRegionId().toString())
                            .replace("#realmId", profileDescription.getRealmId().toString())
                            .replace("#profileId", profileDescription.getProfileId().toString())
                            .replace("#ladderId", ladderId.toString());

                    return webClient
                            .get()
                            .uri(url)
                            .retrieve()
                            .bodyToMono(LadderTeams.class)
                            .doOnError(e -> log.error("Error getting ladder teams of {}.", profileDescription.getProfileId(), e));
                })
                .map(ls -> {
                    log.info("Ladder get of {}", profileDescription.getProfileId());
                    ladderTeamsCache.put(ladderId, ls);

                    return ls;
                });
    }

    /**
     * Obtiene la información de varios player de la competición
     *
     * @param profileDescription descripción del perfil
     * @return players
     */
    public Flux<Player> getPlayerCompetitionInfo(List<ProfileDescription> profileDescription) {
        return Flux.fromIterable(profileDescription).flatMap(this::getPlayerCompetitionInfo);
    }

    /**
     * Obtiene la información de un player de la competición
     *
     * @param profileDescription descripción del perfil
     * @return player
     */
    public Mono<Player> getPlayerCompetitionInfo(ProfileDescription profileDescription) {
        String profileId = String.valueOf(profileDescription.getProfileId());

        return getProfile(profileDescription)
                .flatMap(profile -> getLadderSummary(profileDescription)
                        .flatMap(summary -> {
                            // Filtrar por los memberships 1v1
                            Optional<LadderMembership> biggerMembership = summary.getAllLadderMemberships().stream()
                                    .filter(m -> m.getLocalizedGameMode().startsWith("1v1"))
                                    .min(Comparator.comparing(LadderMembership::getLocalizedGameModeIndex));

                            if (biggerMembership.isPresent()) {
                                LadderMembership bestLadder = biggerMembership.get();
                                return processPlayerBestLadder(profileDescription, profileId, profile, bestLadder);
                            } else {
                                // No se encontró ningún profile description 1v1. Limpiar por si vuelve
                                ladderSummaryCache.invalidate(profileDescription);
                                return Mono.empty();
                            }
                        }));
    }

    /**
     * Procesa el mejor ladder de un player
     *
     * @param profileDescription descripción
     * @param profileId          ID de perfil
     * @param profile            perfil
     * @param bestLadder         ladder a leer
     * @return player
     */
    private Mono<Player> processPlayerBestLadder(ProfileDescription profileDescription, String profileId, Profile profile, LadderMembership bestLadder) {
        return getLadder(profileDescription, Long.valueOf(bestLadder.getLadderId()))
                .flatMap(ladder -> {
                    // Buscar team
                    Optional<LadderTeam> oLadderTeam = ladder.getLadderTeams().stream()
                            .filter(lt ->
                                    lt.getTeamMembers().get(0).getId().equals(profileId))
                            .findFirst();

                    if (oLadderTeam.isPresent()) {
                        LadderTeam lt = oLadderTeam.get();

                        return Mono.just(new Player(
                                profileDescription.getProfileId(),
                                profile.getSummary().getDisplayName(),
                                profile.getSummary().getClanName(),
                                profile.getSummary().getClanTag(),
                                profile.getSummary().getPortrait(),
                                lt.getTeamMembers().get(0).getFavoriteRace(),
                                lt.getPreviousRank(),
                                lt.getPoints(),
                                lt.getWins(),
                                lt.getLosses(),
                                lt.getMmr(),
                                lt.getJoinTimestamp()
                        ));
                    } else {
                        return Mono.empty();
                    }
                });
    }

}
