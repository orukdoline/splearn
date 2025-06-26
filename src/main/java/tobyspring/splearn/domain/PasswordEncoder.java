package tobyspring.splearn.domain;

import org.springframework.stereotype.Component;

public interface PasswordEncoder {
    String encode(String password);
    boolean matches(String password, String passwordHash);
}
