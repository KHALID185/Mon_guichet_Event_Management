package org.usermicroservice.emails;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.usermicroservice.dtos.UserDTO;
import org.usermicroservice.entities.User;

@Service
@Slf4j
@AllArgsConstructor
public class MailService implements IMailService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendConfirmationEmail(User confirmationToken, String senderEmail) throws MessagingException {
        //MIME - HTML message
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(senderEmail);
        helper.setTo(confirmationToken.getEmail());
        helper.setSubject("Confirm Your Email - E-Commerce Application Registration");
        helper.setText("<html>" +
                        "<body>" +
                        "<h2>Hello "+ confirmationToken.getFirstname() + ",</h2>"
                        + "<br/> Welcome buddy, enjoy your journey with us " +
                        "Please click the link below to confirm your account."
                        + "<br/> "  + generateConfirmationLink(confirmationToken.getConfirmationToken())+" " +
                        "<br/>Best Regards,<br/>" +
                        "Max from the E-Commerce Registration Team" +
                        "</body>" +
                        "</html>"
                , true);
        javaMailSender.send(message);
        log.info("Confirmation email sent to {}", confirmationToken.getEmail());
    }

    private String generateConfirmationLink(String token){
        return "<a href=http://localhost:8080/auth/confirm-account?token="+token+">Confirm Email</a>";
    }


    @Override
    public void sendResetPasswordMail(String to, String message, UserDTO userDTO) throws MessagingException {
        String resetPasswordLink = generateResetPasswordLink(userDTO.getResetPasswordToken());

        MimeMessage messageMail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(messageMail, true);

        helper.setFrom("emailmicroservice1994@gmail.com");
        helper.setTo(to);
        helper.setSubject("Reset Your Email - E-Commerce Application Registration");
        helper.setText(
                "<html>" +
                        "<body>" +
                        "<h2>Hello this Alan from the security team " + userDTO.getFirstname() + ",</h2>" +
                        "<br/> We have sent you this email in response to your password reset request." +
                        "<br/> You can follow those steps to reset your password : " +
                        "<a href='" + resetPasswordLink + "'>Password Reset</a>" + // Make sure the link is clickable
                        "<br/> Regards, <br/>" +
                        "Registration Team" +
                        "</body>" +
                        "</html>",
                true
        );

        javaMailSender.send(messageMail);
        log.info("Reset password email sent to {}", to);
    }

    public String generateResetPasswordLink(String token){
        // Update this to your actual password reset page URL
        return "http://localhost:8080/reset-password.html?token=" + token;
    }


}
