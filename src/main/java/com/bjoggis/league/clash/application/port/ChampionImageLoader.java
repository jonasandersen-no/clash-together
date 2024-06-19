package com.bjoggis.league.clash.application.port;

import com.bjoggis.league.clash.domain.Base64Image;
import com.bjoggis.league.clash.domain.ChampionName;
import java.io.IOException;

public interface ChampionImageLoader {

  Base64Image loadImageForChampion(ChampionName championName) throws IOException;
}
