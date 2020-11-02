package kr.creedit.api.front.member.dto;

import kr.creedit.api.front.member.mapper.MemberDtoMapper;
import kr.creedit.domain.rds.member.Member;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDto {

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Save {
        private String email;
        private String password;
        private String userName;
        private String auth;

        public void setPassword(String password) {
            this.password = password;
        }

        public Member toEntity() {
            return MemberDtoMapper.INSTANCE.toEntity(this);
        }
    }
}
