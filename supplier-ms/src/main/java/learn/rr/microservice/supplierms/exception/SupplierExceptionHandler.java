package learn.rr.microservice.supplierms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class SupplierExceptionHandler {

//    @ExceptionHandler(SupplierNotFoudException.class)
//    protected ResponseEntity<Object> handleSupplierNotFoundException(SupplierNotFoudException ex , WebRequest request){
//        Error error = new Error(new Date(),ex.getMessage(),request.getDescription(false));
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(SupplierAlreadyExistsException.class)
//    protected ResponseEntity<Object> handleSupplierAlreadyExistsException(SupplierAlreadyExistsException ex , WebRequest request){
//        Error error = new Error(new Date(),ex.getMessage(),request.getDescription(false));
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }


    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request){
        Error error = new Error(new Date(),ex.getErrorCode()+"|"+ex.getErrorMessage(),request.getDescription(false));
        return new ResponseEntity<>(error,HttpStatus.valueOf(ErrorCode.Code.value(ex.getErrorCode())));
    }
}
