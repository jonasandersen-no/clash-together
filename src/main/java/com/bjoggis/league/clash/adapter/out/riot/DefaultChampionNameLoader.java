package com.bjoggis.league.clash.adapter.out.riot;

import com.bjoggis.league.clash.application.port.ChampionNameLoader;
import com.bjoggis.league.clash.domain.ChampionName;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
class DefaultChampionNameLoader implements ChampionNameLoader {

  @Override
  public List<ChampionName> fetchAllChampionNames() {
    RestClient restClient = RestClient.create(
        "https://ddragon.leagueoflegends.com");

    ResponseEntity<ChampionEndpointResponse> entity = restClient.get()
        .uri("/cdn/14.12.1/data/en_US/champion.json")
        .retrieve()
        .toEntity(ChampionEndpointResponse.class);

    ChampionEndpointResponse body = entity.getBody();
    if (body != null) {
      return body.data().keySet()
          .stream()
          .map(ChampionName::new)
          .toList();
    }
    return List.of();
  }
}
