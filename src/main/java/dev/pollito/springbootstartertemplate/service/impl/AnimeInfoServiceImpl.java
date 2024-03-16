package dev.pollito.springbootstartertemplate.service.impl;

import dev.pollito.springbootstartertemplate.mapper.AnimeInfoMapper;
import dev.pollito.springbootstartertemplate.models.AnimeInfo;
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
  public AnimeInfo getAnimeInfo(Integer id) {
    return animeInfoMapper.map(animeApi.getAnimeFullById(id));
  }
}
