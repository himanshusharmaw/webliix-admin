package com.webliix.service;

import com.webliix.model.Client;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MailService {
    private final JavaMailSender mailSender;
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private final String FROM = "contact@webliix.in";  

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendWelcomeEmailAsync(Client client) {
        if (client == null || client.getEmail() == null || client.getEmail().isBlank()) {
            log.warn("⚠️ No client or client.email is empty, skipping welcome email");
            return;
        }

        try {
            log.info("📨 Sending welcome email to {}", client.getEmail());

            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, "utf-8");

            String subject = "Welcome to Webliix " +
                    (client.getCompanyName() != null ? " — " + client.getCompanyName() : "");

            String html = buildWelcomeHtml(client);

            helper.setText(html, true);
            helper.setTo(client.getEmail());
            helper.setFrom(FROM, "Webliix");
            helper.setReplyTo(FROM);
            helper.setSubject(subject);

            mailSender.send(msg);
            log.info("✅ Welcome email sent to {}", client.getEmail());
        } catch (MessagingException ex) {
            log.error("❌ Failed to send welcome email to {}: {}", client.getEmail(), ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("❌ Unexpected error sending welcome email: {}", ex.getMessage(), ex);
        }
    }

    private String buildWelcomeHtml(Client client) {
        String name = client.getContactPerson() != null && !client.getContactPerson().isBlank()
                ? client.getContactPerson()
                : (client.getCompanyName() != null ? client.getCompanyName() : "Valued Partner");

        return """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; color:#333;">
                    <table align="center" cellpadding="0" cellspacing="0" width="600" 
                           style="background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 6px rgba(0,0,0,0.1);">
                        <tr>
                            <td style="background-color: #2c3e50; padding: 20px; text-align: center; color: #ffffff;">
                                <h1 style="margin:0; font-size: 24px;">Welcome to Webliix</h1>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 30px;">
                                <h2 style="color:#2c3e50; margin-top:0;">Hi %s,</h2>
                                <p>We are thrilled to have you on board with <strong>Webliix</strong>! 
                                   Thank you for choosing us as your trusted partner for your business needs.</p>
                                <p style="margin: 15px 0;">Our team is committed to providing you with the best possible experience. 
                                   If you have any questions, feel free to reach out anytime.</p>
                                <p><strong>Your Contact:</strong> %s</p>
                                <p><strong>Email:</strong> <a href="mailto:contact@webliix.in" style="color:#2c3e50;">contact@webliix.in</a><br/>
                                   <strong>Phone:</strong> <a href="tel:+919310181569" style="color:#2c3e50;">+91 9310181569</a><br/>
                                   <strong>Website:</strong> <a href="https://webliix.in" style="color:#2c3e50;">www.webliix.in</a></p>
                                <br/>
                                <p style="margin:0;">Best Regards,</p>
                                <p style="font-weight:bold; color:#2c3e50;">The Webliix Team</p>
                            </td>
                        </tr>
                        <tr>
                            <td style="background-color: #f1f1f1; padding: 15px; text-align:center; font-size: 12px; color:#777;">
                                © 2025 Webliix. All Rights Reserved.
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """.formatted(escapeHtml(name), escapeHtml(client.getPhone() != null ? client.getPhone() : "N/A"));
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
