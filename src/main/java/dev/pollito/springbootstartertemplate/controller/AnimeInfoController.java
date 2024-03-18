package dev.pollito.springbootstartertemplate.controller;

import dev.pollito.springbootstartertemplate.api.AnimeApi;
import dev.pollito.springbootstartertemplate.models.AnimeStatisticsViewers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnimeInfoController implements AnimeApi {
  @Override
  public ResponseEntity<AnimeStatisticsViewers> getAnimeStatisticsViewers(Integer id) {
    return AnimeApi.super.getAnimeStatisticsViewers(id);
  }
}
