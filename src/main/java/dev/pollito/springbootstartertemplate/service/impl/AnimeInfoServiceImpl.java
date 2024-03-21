package dev.pollito.springbootstartertemplate.service.impl;

import dev.pollito.springbootstartertemplate.mapper.AnimeInfoMapper;
import dev.pollito.springbootstartertemplate.models.AnimeStatisticsViewers;
import dev.pollito.springbootstartertemplate.service.AnimeInfoService;
import lombok.RequiredArgsConstructor;
import moe.jikan.api.AnimeApi;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnimeInfoServiceImpl implements AnimeInfoService {
  private final AnimeApi animeApi;
  private final AnimeInfoMapper animeInfoMapper;

  @Override
  public AnimeStatisticsViewers getAnimeInfo(Integer id) {
    return animeInfoMapper.map(animeApi.getAnimeStatistics(id));
  }
}
