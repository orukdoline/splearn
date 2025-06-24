package tobyspring.splearn.application.required;

import tobyspring.splearn.domain.Email;

/**
 * 이메일을 발송하는 기능을 제공한다.
 */

public interface EmailSender {
    void send(Email email, String subject, String body);
}
