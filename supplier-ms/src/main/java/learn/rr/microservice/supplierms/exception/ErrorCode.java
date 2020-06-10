package learn.rr.microservice.supplierms.exception;

public interface ErrorCode {
    String ERROR_CODE_SUPPLIER_NOT_FOUND = "SUPPLIER_NOT_FOUND";
    String ERROR_CODE_SUPPLIER_ALREADY_EXISTS = "SUPPLIER_ALREADY_EXISTS";
    String BUSINESS_ERROR = "BUSINESS_ERROR";

     enum Code{
        SUPPLIER_NOT_FOUND(404),
        SUPPLIER_ALREADY_EXISTS(400),
        BUSINESS_ERROR(400);

        private int code ;

        Code(int code) {
            this.code = code;
        }

        private int getValue(){
            return this.code;
        }


        public static int value(String error){
            return Code.valueOf(error).getValue();
        }
    }
}
