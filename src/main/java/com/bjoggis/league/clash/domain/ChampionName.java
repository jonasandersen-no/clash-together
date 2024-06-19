package com.bjoggis.league.clash.domain;

public record ChampionName(String value) implements Comparable<ChampionName> {

  @Override
  public String toString() {
    return value;
  }

  @Override
  public int compareTo(ChampionName o) {
    return value.compareTo(o.value);
  }

  public boolean isEqualTo(String name) {
    return value.equalsIgnoreCase(name);
  }

  public boolean containsIgnoreCase(String name) {
    return value.toLowerCase().contains(name.toLowerCase());
  }
}
