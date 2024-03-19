package dev.pollito.springbootstartertemplate.controller.advice;

import static dev.pollito.springbootstartertemplate.util.ErrorResponseBuilder.buildErrorResponse;

import io.swagger.petstore.models.Error;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Order()
@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public static ResponseEntity<Error> handle(MissingServletRequestParameterException e) {
    return buildErrorResponse(HttpStatus.BAD_REQUEST, e, e.getBody().getDetail());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public static ResponseEntity<Error> handle(ConstraintViolationException e) {
    return buildErrorResponse(
        HttpStatus.BAD_REQUEST, e, constraintViolationExceptionMessageFormatter(e));
  }

  private static String constraintViolationExceptionMessageFormatter(
      ConstraintViolationException e) {
    String formattedMessage = "";

    if (!e.getConstraintViolations().isEmpty()) {
      ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
      String propertyPath = violation.getPropertyPath().toString();
      String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
      String message = violation.getMessage();
      formattedMessage = fieldName + ": " + message;
    }

    return formattedMessage;
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public static ResponseEntity<Error> handle(MethodArgumentTypeMismatchException e) {
    return buildErrorResponse(HttpStatus.BAD_REQUEST, e, e.getCause().getCause().getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public static ResponseEntity<Error> handle(MethodArgumentNotValidException e) {
    return buildErrorResponse(
        HttpStatus.BAD_REQUEST, e, methodArgumentNotValidExceptionMessageFormatter(e));
  }

  @NotNull
  private static String methodArgumentNotValidExceptionMessageFormatter(
      MethodArgumentNotValidException e) {
    return Objects.requireNonNull(e.getDetailMessageArguments())[1]
        .toString()
        .replace("[", "")
        .replace("]", "");
  }

  @ExceptionHandler(Exception.class)
  public static ResponseEntity<Error> handle(Exception e) {
    log.error(
        "A generic error is about to be returned. This may be caused by an unhandled exception", e);
    return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e, "An unexpected error occurred.");
  }
}
