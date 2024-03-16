package dev.pollito.springbootstartertemplate.service;

import dev.pollito.springbootstartertemplate.models.AnimeStatisticsViewers;

public interface AnimeInfoService {
  AnimeStatisticsViewers getAnimeStatisticsViewers(Integer id);
}
