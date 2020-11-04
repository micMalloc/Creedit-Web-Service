package kr.creedit.api.front.member.dto;

import kr.creedit.api.front.member.mapper.MemberDtoMapper;
import kr.creedit.domain.rds.member.Member;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDto {

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Save {
        private String email;
        private String password;
        private String displayName;

        public Member toEntity() {
            return MemberDtoMapper.INSTANCE.toEntity(this);
        }

        public void encodePassword() {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            this.password = encoder.encode(password);
        }
    }
}
