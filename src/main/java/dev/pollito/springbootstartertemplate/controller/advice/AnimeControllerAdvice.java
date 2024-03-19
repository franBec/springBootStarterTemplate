package dev.pollito.springbootstartertemplate.controller.advice;

import dev.pollito.springbootstartertemplate.controller.AnimeController;
import dev.pollito.springbootstartertemplate.exception.JikanException;
import dev.pollito.springbootstartertemplate.models.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static dev.pollito.springbootstartertemplate.util.ErrorResponseBuilder.buildErrorResponse;

@RestControllerAdvice(assignableTypes = AnimeController.class)
public class AnimeControllerAdvice {

    @ExceptionHandler(JikanException.class)
    public static ResponseEntity<Error> handle(JikanException e) {
        if (isNotFound(e)) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, e, e.getError().getMessage());
        }

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getError().getMessage());
    }

    private static boolean isNotFound(JikanException e) {
        return Objects.nonNull(e.getError().getStatus())
                && e.getError().getStatus() == HttpStatus.NOT_FOUND.value();
    }
}
