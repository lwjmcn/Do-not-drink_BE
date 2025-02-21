package com.jorupmotte.donotdrink.provider;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailProvider {
    private final JavaMailSender javaMailSender;
    private final String SUBJECT = "[DoNotDrink] 회원이 되신 걸 환영합니다🥤";

    public boolean sendVerificationEmail (String emailTo, String verificationCode) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            String htmlContent = getVerificationContent(verificationCode);

            messageHelper.setTo(emailTo);
            messageHelper.setSubject(SUBJECT);
            messageHelper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    private String getVerificationContent(String verificationCode){
        return "<div style=\"width: 100%; max-width: 320px; margin: 0 auto; padding: 20px\">\n" +
                "  <div\n" +
                "    style=\"\n" +
                "      margin-bottom: 20px;\n" +
                "      padding: 20px;\n" +
                "      background-color: #ffffff;\n" +
                "      color: #333;\n" +
                "      border-radius: 10px;\n" +
                "      border: 1px solid #ff1b5c;\n" +
                "    \"\n" +
                "  >\n" +
                "    <p style=\"font-size: 40px\">\uD83E\uDD64</p>\n" +
                "    <h3>환영합니다 :-)</h3>\n" +
                "    <p>\n" +
                "      안녕하세요, 두낫드링크를 이용해주셔서 진심으로 감사드립니다. 아래\n" +
                "      <span style=\"color: #ff1b5c\">인증 코드</span>를 입력하여 회원가입을\n" +
                "      완료해주세요.\n" +
                "    </p>\n" +
                "    <h1\n" +
                "      style=\"\n" +
                "        letter-spacing: 8px;\n" +
                "        text-align: center;\n" +
                "        padding: 10px;\n" +
                "        color: #ff1b5c;\n" +
                "      \"\n" +
                "    >\n" +
                "      {" +
                verificationCode +
                "}\n" +
                "    </h1>\n" +
                "    <p>감사합니다.</p>\n" +
                "    <p style=\"font-size: 12px\">두낫드링크 드림</p>\n" +
                "  </div>\n" +
                "  <p style=\"font-size: 10px; color: gray\">\n" +
                "    * 이메일 인증을 요청하시지 않으셨다면 이 메일을 무시하셔도 됩니다.\n" +
                "  </p>\n" +
                "</div>\n";
    }
}
