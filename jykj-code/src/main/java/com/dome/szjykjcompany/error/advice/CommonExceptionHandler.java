package com.dome.szjykjcompany.error.advice;




import com.dome.szjykjcompany.error.LyException.LyException;
import com.dome.szjykjcompany.error.pojo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(LyException.class)
    public ResponseEntity<ExceptionResult> handleException(LyException e) {
        return ResponseEntity.status(e.getExceptionEnum().getCode()).body(new
                ExceptionResult(e.getExceptionEnum()));
    }
}
