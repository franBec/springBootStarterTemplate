package dev.pollito.springbootstartertemplate.util;

import static dev.pollito.springbootstartertemplate.util.Constants.SLF4J_MDC_SESSION_ID_KEY;

import dev.pollito.springbootstartertemplate.models.Error;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ControllerAdviceUtil {

  private ControllerAdviceUtil() {}

  public static ResponseEntity<Error> buildErrorResponse(
      HttpStatus status, Exception e, String errorMessage) {
    return ResponseEntity.status(status)
        .body(
            new Error()
                .error(e.getClass().getSimpleName())
                .message(errorMessage)
                .path(getCurrentRequestPath())
                .timestamp(OffsetDateTime.now())
                .session(UUID.fromString(MDC.get(SLF4J_MDC_SESSION_ID_KEY))));
  }

  private static String getCurrentRequestPath() {
    ServletRequestAttributes attr =
        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    return attr.getRequest().getRequestURI();
  }
}
