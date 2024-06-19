package com.bjoggis.league.clash.application;

import com.bjoggis.league.clash.application.port.ChampionImageLoader;
import com.bjoggis.league.clash.domain.Base64Image;
import com.bjoggis.league.clash.domain.ChampionName;
import java.io.IOException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ImageCache {

  private final ChampionImageLoader championImageLoader;

  public ImageCache(ChampionImageLoader championImageLoader) {
    this.championImageLoader = championImageLoader;
  }

  @Cacheable("championImages")
  public Base64Image image(ChampionName championName) throws IOException {
    return championImageLoader.loadImageForChampion(championName);
  }
}
