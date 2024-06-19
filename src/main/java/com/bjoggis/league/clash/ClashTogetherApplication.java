package com.bjoggis.league.clash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ClashTogetherApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClashTogetherApplication.class, args);
  }

}
