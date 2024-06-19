package com.bjoggis.league.clash.domain;

public record Base64Image(String value) {

  @Override
  public String toString() {
    return value;
  }
}