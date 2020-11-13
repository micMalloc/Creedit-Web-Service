package kr.creedit.api.front.member.dto;

import kr.creedit.domain.rds.oauth.OauthMember;
import kr.creedit.domain.rds.role.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthMemberDto {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public OAuthMemberDto(Map<String, Object> attributes,
                          String nameAttributeKey, String name,
                          String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthMemberDto of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthMemberDto ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthMemberDto.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthMemberDto ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthMemberDto.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public OauthMember toEntity() {
        return OauthMember.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .build();
    }

}
