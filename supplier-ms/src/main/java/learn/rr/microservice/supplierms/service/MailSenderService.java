package learn.rr.microservice.supplierms.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MailSenderService {
    private  JavaMailSender mailSender;

    /**
     *
     * @param to comma saperated string for recipient email
     * @param cc comma saperated string for recipient email to be in cc
     * @param subject Subject line of E-Mail
     * @param message E-Mail body
     */
    public void sendSampleMail(String to,String cc,String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to.split(","));
        if(!cc.isEmpty()) {
            mailMessage.setCc(cc.split(","));
        }
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

}
