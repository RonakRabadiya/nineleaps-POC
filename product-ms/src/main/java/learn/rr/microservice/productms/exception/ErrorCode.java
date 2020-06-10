package learn.rr.microservice.productms.exception;

public interface ErrorCode {
    String ERROR_CODE_PRODUCT_NOT_FOUND = "PRODUCT_NOT_FOUND";
    String ERROR_CODE_PRODUCT_ALREADY_EXISTS = "PRODUCT_ALREADY_EXISTS";
    String ERROR_CODE_SUPPLIER_ALREAD_REGISTERED = "SUPPLIER_ALREAD_REGISTERED";
    String BUSINESS_ERROR = "BUSINESS_ERROR";

    enum Code {
        PRODUCT_NOT_FOUND(404),
        PRODUCT_ALREADY_EXISTS(400),
        SUPPLIER_ALREAD_REGISTERED(400),
        BUSINESS_ERROR(400);

        private int value;

        Code(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static int getCode(String error) {
            return Code.valueOf(error).value;
        }

    }
}
