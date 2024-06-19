package com.bjoggis.league.clash.application.port;

import com.bjoggis.league.clash.domain.ChampionName;
import java.util.List;

public interface ChampionNameLoader {

  List<ChampionName> fetchAllChampionNames();
}
