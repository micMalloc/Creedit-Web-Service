package kr.creedit.api.front.member;

import kr.creedit.domain.rds.member.OauthMember;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionOauthMember implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionOauthMember(OauthMember oauthMember) {
        this.name = oauthMember.getName();
        this.email = oauthMember.getEmail();
        this.picture = oauthMember.getPicture();
    }
}


