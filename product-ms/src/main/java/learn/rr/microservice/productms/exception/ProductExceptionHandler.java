package learn.rr.microservice.productms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ProductExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request){
        Error error = new Error(new Date(),ex.getErrorCode()+"|"+ex.getErrorMessage(),request.getDescription(false));
        return new ResponseEntity<Object>(error,HttpStatus.valueOf(ErrorCode.Code.getCode(ex.getErrorCode())));
    }
}
