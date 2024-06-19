package com.bjoggis.league.clash.adapter.in.web;

import com.bjoggis.league.clash.application.ChampionLoader;
import com.bjoggis.league.clash.domain.ChampionImage;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HtmxController {

  private static final Logger log = LoggerFactory.getLogger(HtmxController.class);
  private final ChampionLoader championLoader;
  private final WebSocketBroadcaster webSocketBroadcaster;

  public HtmxController(ChampionLoader championLoader,
      WebSocketBroadcaster webSocketBroadcaster) {
    this.championLoader = championLoader;
    this.webSocketBroadcaster = webSocketBroadcaster;
  }

  @PostMapping(value = "/add", headers = "HX-Request")
  void add(@RequestParam String name) {
    log.info("Adding champion with name {}", name);
    List<ChampionImage> championImages = championLoader.loadAllChampions();
    championImages.stream()
        .filter(championImage -> championImage.name().isEqualTo(name))
        .findFirst()
        .ifPresent(webSocketBroadcaster::addChampion);
  }

  @PostMapping(value = "/champions", headers = "HX-Request")
  String champions(@RequestParam String name) throws IOException {
    List<ChampionImage> championImages = championLoader.loadAllChampions();

    log.info("Searching for champion images with name {}", name);

    // Search for the champion image by name. Name is only a part of the full string. Return all champions found
    return championImages.stream()
        .filter(championImage -> championImage.name().containsIgnoreCase(name))
        .peek(championImage -> log.info("Found champion image with name {}", championImage.name()))
        .map(this::toHtml)
        .reduce("", String::concat);

  }
//
//  <div th:each="champion : ${champions}" class="col">
//          <img th:src="${'data:image/jpeg;charset=utf-8;base64,' + champion.base64Image()}" width="100" height="100"
//  alt="">
//          <p th:text="${champion.name}"></p>
//        </div>

  private String toHtml(ChampionImage championImage) {
    return """
        <div class="col">
          <img src="data:image/jpeg;charset=utf-8;base64,%s" width="100" height="100" alt="%s">
          <p>%s</p>
        </div>
        """.formatted(championImage.base64Image(), championImage.name(), championImage.name());
  }
}
