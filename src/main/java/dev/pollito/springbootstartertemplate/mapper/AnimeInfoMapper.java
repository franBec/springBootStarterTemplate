package dev.pollito.springbootstartertemplate.mapper;

import dev.pollito.springbootstartertemplate.models.AnimeStatisticsViewers;
import moe.jikan.models.AnimeStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnimeInfoMapper {

  @Mapping(source = "response.data.completed", target = "viewers")
  AnimeStatisticsViewers map(AnimeStatistics response);
}
