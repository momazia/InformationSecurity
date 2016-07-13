package com.otp.service;

import java.io.StringWriter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 * EMail service handling all the email related operations
 * 
 * @author Max
 *
 */
@Service
public class MailService {

    private static final String TEMPLATE_ADDRESS = "template/email/";
    private static final String GENERAL_MAIL_VM = "general_template.html";

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mailgun.from}")
    private String FROM;
    @Value("${mailgun.api.key}")
    private String MAILGUN_API_KEY;
    @Value("${mailgun.domain.name}")
    private String MAILGUN_DOMAIN_NAME;
    @Value("${mailgun.username}")
    private String USER_NAME;

    /**
     * Sends a plain email to the address given.
     * 
     * @param subject
     * @param emailAddress
     * @param body
     */
    public void sendPlainText(String subject, String emailAddress, String body) {
	SimpleMailMessage message = new SimpleMailMessage();
	message.setFrom(FROM);
	message.setTo(emailAddress);
	message.setSubject(subject);
	message.setText(body);
	mailSender.send(message);
    }

    /**
     * Sends HTML email to the given address.
     * 
     * @param subject
     * @param emailAddress
     * @param data
     * @param templateName
     */
    public void sendHtml(final String subject, final String emailAddress, final Object data) {
	mailSender.send(new MimeMessagePreparator() {
	    public void prepare(MimeMessage mimeMessage) throws MessagingException {
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		message.setFrom(FROM);
		message.setTo(emailAddress);
		message.setSubject(subject);
		message.setText(executeTemplate(GENERAL_MAIL_VM, data), true);
	    }
	});
    }

    /**
     * Executes the given template name with the data passed.
     * 
     * @param templateName
     * @param data
     * @return
     */
    public String executeTemplate(String templateName, Object data) {
	Template t = velocityEngine.getTemplate(TEMPLATE_ADDRESS + templateName);
	VelocityContext context = new VelocityContext();
	context.put("data", data);
	StringWriter writer = new StringWriter();
	t.merge(context, writer);
	return writer.toString();
    }
}
