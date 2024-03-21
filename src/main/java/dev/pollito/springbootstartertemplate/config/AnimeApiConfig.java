package dev.pollito.springbootstartertemplate.config;

import dev.pollito.springbootstartertemplate.errordecoder.JikanErrorDecoder;
import dev.pollito.springbootstartertemplate.properties.JikanProperties;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import moe.jikan.api.AnimeApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans(
    value = {
      @ComponentScan(
          basePackages = {
            "moe.jikan.api",
          })
    })
@RequiredArgsConstructor
public class AnimeApiConfig {

  private final JikanProperties jikanProperties;

  @Bean
  public AnimeApi jikanApi() {
    return Feign.builder()
        .client(new OkHttpClient())
        .encoder(new GsonEncoder())
        .decoder(new GsonDecoder())
        .errorDecoder(new JikanErrorDecoder())
        .logger(new Slf4jLogger(AnimeApi.class))
        .logLevel(Logger.Level.FULL)
        .target(AnimeApi.class, jikanProperties.getBaseUrl());
  }
}
