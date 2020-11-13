package kr.creedit.api.front.member;

import kr.creedit.domain.rds.oauth.OauthMember;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private String name;
    private String email;

    public SessionMember(OauthMember oauthMember) {
        this.name = oauthMember.getName();
        this.email = oauthMember.getEmail();
    }
}


