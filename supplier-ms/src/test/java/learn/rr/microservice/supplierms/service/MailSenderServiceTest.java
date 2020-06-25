package learn.rr.microservice.supplierms.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MailSenderServiceTest {
    @Mock
    private JavaMailSender javaMailSender ;
    @InjectMocks
    private MailSenderService mailSenderService ;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> messageArgumentCaptor ;


    @Test
    void sendMail(){
        String to = "tomail@test.com";
        String cc = "ccmail@test.com";
        String  subject = "mock mail subject";
        String  message = "mock mail message";
        doNothing().when(javaMailSender).send(messageArgumentCaptor.capture());
        mailSenderService.sendSampleMail(to,cc,subject,message);
        SimpleMailMessage request = messageArgumentCaptor.getValue();
        assertThat(request).isNotNull();
        assertThat(request.getCc()).isNotNull();
        assertThat(request.getCc()).isNotEmpty();
        assertThat(request.getTo()).isNotNull();
        assertThat(request.getTo()).isNotEmpty();
        assertThat(request.getCc()[0]).isEqualTo(cc);
        assertThat(request.getTo()[0]).isEqualTo(to);
        assertThat(request.getSubject()).isEqualTo(subject);
        assertThat(request.getText()).isEqualTo(message);
    }
}
