package tobyspring.splearn.domain;

import lombok.Getter;
import lombok.ToString;

import static java.util.Objects.*;
import static org.springframework.util.Assert.state;

@Getter
@ToString
public class Member {
    private String email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;

    // 생성자를 사용하지 못하도록 private으로 변경.
    private Member() {
    }

    // 생성자를 사용하지 않고 record를 사용하여 member에 새로운 속성이 추가되더라도 파라미터 길이가 길어지지 않음.
    // 코드 리뷰할 때 빠르고 직관적으로 체크가 가능함.
    public static Member create(MemberCreateRequest createRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.email = requireNonNull(createRequest.email());
        member.nickname = requireNonNull(createRequest.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(createRequest.password()));

        member.status = MemberStatus.PENDING;

        return member;
    }

    public void activate() {
        state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

        this.status = MemberStatus.ACTIVE;
    }

    public void deactivate() {
        state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = MemberStatus.DEACTIVATED;
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = requireNonNull(nickname);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }

    public boolean isActive() {
        return this.status == MemberStatus.ACTIVE;
    }
}
