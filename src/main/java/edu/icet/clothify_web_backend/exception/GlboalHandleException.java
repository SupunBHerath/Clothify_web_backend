package edu.icet.clothify_web_backend.exception;

import edu.icet.clothify_web_backend.exception.impl.ErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlboalHandleException{

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<edu.icet.clothify_web_backend.model.ErrorException> notFountExecption(ErrorException ex){
        return ResponseEntity.ok().body(edu.icet.clothify_web_backend.model.ErrorException.builder().status("Failed").message(ex.getMessage()).build());
    }

}
