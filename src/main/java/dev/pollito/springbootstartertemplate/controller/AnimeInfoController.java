package dev.pollito.springbootstartertemplate.controller;

import dev.pollito.springbootstartertemplate.api.AnimeApi;
import dev.pollito.springbootstartertemplate.models.AnimeInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnimeInfoController implements AnimeApi {
  @Override
  public ResponseEntity<AnimeInfo> getAnimeInfo(Integer id) {
    return AnimeApi.super.getAnimeInfo(id);
  }
}
