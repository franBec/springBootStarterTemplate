package dev.pollito.springbootstartertemplate.controller;

import dev.pollito.springbootstartertemplate.api.AnimeApi;
import dev.pollito.springbootstartertemplate.models.AnimeInfo;
import dev.pollito.springbootstartertemplate.service.AnimeInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnimeInfoController implements AnimeApi {
  private final AnimeInfoService animeInfoService;

  @Override
  public ResponseEntity<AnimeInfo> getAnimeInfo(Integer id) {
    return ResponseEntity.ok(animeInfoService.getAnimeInfo(id));
  }
}
