package edu.icet.clothify_web_backend.exception;

import edu.icet.clothify_web_backend.exception.impl.NotFoundException;
import edu.icet.clothify_web_backend.model.ErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlboalHandleException{

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorException> notFountExecption(NotFoundException ex){
        return ResponseEntity.ok().body(ErrorException.builder().status("Failed").message(ex.getMessage()).build());
    }

}
