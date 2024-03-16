package dev.pollito.springbootstartertemplate.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import moe.jikan.models.Error;

@RequiredArgsConstructor
@Getter
public class JikanException extends RuntimeException {
  private final transient Error error;
}
