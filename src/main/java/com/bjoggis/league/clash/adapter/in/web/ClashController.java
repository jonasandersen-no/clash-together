package com.bjoggis.league.clash.adapter.in.web;

import com.bjoggis.league.clash.application.ChampionLoader;
import com.bjoggis.league.clash.domain.ChampionImage;
import java.util.Comparator;
import java.util.List;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClashController {

  private final ChampionLoader championLoader;

  public ClashController(ChampionLoader championLoader) {
    this.championLoader = championLoader;
  }

  @GetMapping
  public String index(Model model) {

    List<ChampionImage> imagesAsBase64 = championLoader.loadAllChampions();

    imagesAsBase64.sort(Comparator.comparing(ChampionImage::name));

    model.addAttribute("champions", imagesAsBase64);
    return "index";
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationReady() {
    championLoader.loadAllChampions();
  }

}
