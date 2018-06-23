package pl.sda.poznan.springmvcintro.aop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationErrorHandler {

  @ExceptionHandler(NumberFormatException.class)
  public ResponseEntity handleNumberFormatException() {
    return ResponseEntity.badRequest().body("Bad format");
  }
}
