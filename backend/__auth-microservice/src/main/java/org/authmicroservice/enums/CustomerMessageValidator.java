package org.authmicroservice.enums;

public enum CustomerMessageValidator {
    SAVED_SUCCESSFULLY("Your Account saved successfully"),
    CHECK_EMAIL_FOR_VALIDATION_YOUR_MAIL("Check your email for validation your Email");

    private final String msg;
    CustomerMessageValidator(String msg){
        this.msg = msg;
    }
    public String getMessage(){
        return this.msg;
    }
}
