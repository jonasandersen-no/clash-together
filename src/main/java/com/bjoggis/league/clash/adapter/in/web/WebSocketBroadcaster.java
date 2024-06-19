package com.bjoggis.league.clash.adapter.in.web;

import com.bjoggis.league.clash.domain.ChampionImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketBroadcaster extends TextWebSocketHandler {

  private static final Logger log = LoggerFactory.getLogger(WebSocketBroadcaster.class);

  private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

  private final List<ChampionImage> championImages = new ArrayList<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    log.info("New session established: {}", session.getId());
    sessionMap.put(session.getId(), session);
    StringBuilder concat = new StringBuilder();
    for (ChampionImage championImage : championImages) {
      String part = toHtml(championImage);
      concat.append(part);
    }

    TextMessage textMessage = new TextMessage(concat.toString());
    session.sendMessage(textMessage);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    try (WebSocketSession _ = sessionMap.remove(session.getId())) {
      log.info("Session removed: {}", session.getId());
    }
  }

  public void addChampion(ChampionImage championImage) {
    sessionMap.values().forEach(session -> {
      try {
        championImages.add(championImage);
        TextMessage textMessage = new TextMessage(toHtml(championImage));
        session.sendMessage(textMessage);
      } catch (Exception e) {
        log.error("Failed to send message to session {}", session.getId(), e);
      }
    });
  }

  //language=HTML
  private String toHtml(ChampionImage championImage) {
    return """
        <div id="selected-champion" hx-swap-oob="beforeend">
          <div class="col">
            <img src="data:image/jpeg;charset=utf-8;base64,%s" width="100" height="100" alt="%s">
            <p>%s</p>
          </div>
        </div>
        """.formatted(championImage.base64Image(), championImage.name(), championImage.name());
  }
}
