package org.usermicroservice.enums;

public enum CustomerEmailMessage {
    PROFILE_SAVED_SUCCESSFULLY("Your profile has been saved successfully."),
    PROFILE_UPDATED_SUCCESSFULLY("Your profile has been updated successfully."),
    RESET_PASSWORD_SUBJECT("Reinitialise your Password ");

    private final String msg;
    CustomerEmailMessage(String msg){
        this.msg = msg;
    }
    public String getMessage(){
        return this.msg;
    }
}
