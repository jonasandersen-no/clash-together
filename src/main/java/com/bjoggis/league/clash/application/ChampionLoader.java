package com.bjoggis.league.clash.application;

import com.bjoggis.league.clash.domain.ChampionImage;
import com.bjoggis.league.clash.domain.ChampionName;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChampionLoader {

  private static final Logger log = LoggerFactory.getLogger(ChampionLoader.class);
  private final LoadChampionNames loadChampionNames;
  private final ImageCache imageCache;

  public ChampionLoader(LoadChampionNames loadChampionNames, ImageCache imageCache) {
    this.loadChampionNames = loadChampionNames;
    this.imageCache = imageCache;
  }

  public List<ChampionImage> loadAllChampions() {
    List<ChampionName> championNames = loadChampionNames.championNames();

    List<ChampionImage> imagesAsBase64 = new ArrayList<ChampionImage>(championNames.size());
    try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
      for (ChampionName championName : championNames) {
        executorService.submit(() -> {
          try {
            log.debug("Loading image for champion {}", championName);
            imagesAsBase64.add(new ChampionImage(championName, imageCache.image(championName)));
          } catch (IOException e) {
            log.error("Failed to load image for champion {}", championName, e);
          }
        });
      }
    }
    return imagesAsBase64;
  }
}