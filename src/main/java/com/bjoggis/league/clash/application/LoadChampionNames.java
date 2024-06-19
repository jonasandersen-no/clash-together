package com.bjoggis.league.clash.application;

import com.bjoggis.league.clash.application.port.ChampionNameLoader;
import com.bjoggis.league.clash.domain.ChampionName;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LoadChampionNames {

  private final ChampionNameLoader championNameLoader;

  LoadChampionNames(ChampionNameLoader championNameLoader) {
    this.championNameLoader = championNameLoader;
  }

  List<ChampionName> championNames() {
    return championNameLoader.fetchAllChampionNames();
  }
}
