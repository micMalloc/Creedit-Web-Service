package kr.creedit.api.front.member;

import kr.creedit.domain.rds.member.OauthMember;
import kr.creedit.domain.rds.member.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;


/**
 *  구글 로그인 이후 가져온 사용자의 이름 / 이메일 / 사진을 가져올 수 있는 DTO
 *
 *  참고
 *      - https://developers.google.com/identity/protocols/oauth2/web-server#httprest_1
 *      - 해당 페이지에 Request / Response Format을 볼 수 있음
 *
 *      - Google OAuth2.0의 endpoint
 *          - https://accounts.google.com/o/oauth2/v2/auth
 *
 *      - Request Format
 *      - Required
 *          - client_id : 어플리케이션 아이디
 *          - redirect_uri : 유저가 authorization을 마친 이후 리다이렉트 하는 것을 명시해 줘야한다.
 *              - 스프링 security 2.0 이상부터는 redirection endpoint가 /login/oauth2/code/* 이므로 구글이나 naver 설정에서 redirection url에 아래와 같이 설정
 *                  - http://localhost:8080/login/oauth2/code/google 이와 같이 설정함
 *              - 리다이렉션 URL을 위처럼 지정하면 스프링부트가 자동으로 리다이렉트에 대한 대응을 지원하기 때문에 또다른 설정은 필요 없음
 *              - 커스텀 하고 싶다면 아래의 링크를 참조
 *                  - https://docs.spring.io/spring-security/site/docs/5.0.7.RELEASE/reference/html/oauth2login-advanced.html#oauth2login-advanced-redirection-endpoint
 *          - response_type
 *              - 구글 OAuth2.0 endPoint에서 authorization code를 리턴할 지 안할지 결정하는 것
 *                  - Web Application이라면 해당 파라미터를 code로 set하도록 하자.
 *          - scopes
 *              - 어느정도의 resource(name / picture / email.. 등 등)을 요청할 것인지에 대한 설정
 *
 *      - Response
 *          - 성공 ex) : https://oauth2.example.com/auth?code=4/P7q7W91a-oMsCeLvIaQm6bTrgtp7
 *          - 실패 ex) : https://oauth2.example.com/auth?error=access_denied
 *
 *      - 더 필요한 설정은 위의 링크에서 확인 (https://accounts.google.com/o/oauth2/v2/auth)
 */

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String name,
                           String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public OauthMember toEntity() {
        return OauthMember.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }

}
