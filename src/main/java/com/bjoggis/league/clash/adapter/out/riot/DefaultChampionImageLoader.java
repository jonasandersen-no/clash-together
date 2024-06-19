package com.bjoggis.league.clash.adapter.out.riot;

import com.bjoggis.league.clash.application.port.ChampionImageLoader;
import com.bjoggis.league.clash.domain.Base64Image;
import com.bjoggis.league.clash.domain.ChampionName;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Service
public class DefaultChampionImageLoader implements ChampionImageLoader {

  private static final Logger log = LoggerFactory.getLogger(DefaultChampionImageLoader.class);

  @Override
  public Base64Image loadImageForChampion(ChampionName championName) throws IOException {
    URI uri = URI.create("https://ddragon.leagueoflegends.com/cdn/14.12.1/img/champion/%s.png".formatted(championName));
    log.info("Loading image from {}", uri);
    InputStream in = new BufferedInputStream(uri.toURL().openStream());
    byte[] bytes = FileCopyUtils.copyToByteArray(in);
    return new Base64Image(Base64.getEncoder().encodeToString(bytes));
  }
}
