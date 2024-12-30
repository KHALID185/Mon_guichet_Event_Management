package org.usermicroservice.emails;

import jakarta.mail.MessagingException;
import org.usermicroservice.dtos.UserDTO;
import org.usermicroservice.entities.User;

public interface IMailService {

   // void sendResetPasswordMail(String to, String message, UserDTO userDTO) throws MessagingException;
    void sendConfirmationEmail(User user, String senderEmail) throws MessagingException;
   // void sendResetPasswordMail(String to, String subject, UserDTO userDTO) throws MessagingException;

    void sendResetPasswordMail(String to, String message, UserDTO userDTO) throws MessagingException;
}
