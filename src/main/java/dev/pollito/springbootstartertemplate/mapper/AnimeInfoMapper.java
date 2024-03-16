package dev.pollito.springbootstartertemplate.mapper;

import dev.pollito.springbootstartertemplate.models.AnimeInfo;
import moe.jikan.models.GetAnimeFullById200Response;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnimeInfoMapper {

  @Mapping(source = "response.data.title", target = "title")
  @Mapping(source = "response.data.year", target = "year")
  AnimeInfo map(GetAnimeFullById200Response response);
}
