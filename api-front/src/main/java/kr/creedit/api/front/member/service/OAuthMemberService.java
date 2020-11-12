package kr.creedit.api.front.member.service;

import kr.creedit.api.front.member.dto.OAuthMemberDto;
import kr.creedit.api.front.member.SessionMember;
import kr.creedit.domain.rds.oauth.OauthMember;
import kr.creedit.domain.rds.oauth.OauthMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 *  loadUser
 *      - 해당 함수 파마리터인 userRequest에는 request가, OAuth2User에는 받아온 정보가 들어있다.
 *
 *      - 현재 코드 기준 application-oauth.yml에 설정되어 있는
 *          - client id
 *          - client secret
 *          - scope (profile, image 로 설정해 놓음)
 *
 *      - +기타 필요한 정보들이 들어있다.
 *          - redirectUriTemplate (따로 설정하지 않았기 때문에 {baseUrl}/{action}/oauth2/code/{registrationId} 이처럼 설정되어있음
 *              - 나는 구글에서 토큰 정보를 아래와 같이
 *              - http://localhost:8080/login/oauth2/code/google 하도록 했기에 코드에서 if 문을 통해서 naver / google을 비교할 수 있는 것.
 *
 *      - 출력해보고 싶다면 objectMapper 이용하여 출력 할 수 있음.
 */

@RequiredArgsConstructor
@Service
public class OAuthMemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final OauthMemberRepository oauthMemberRepository;
    private final HttpSession httpSession;

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();

        /*
            oAuth2User 내의 정보
                - authority는 ROLE_USER
                - sub정보
                - name
                - picture
                - email
                - locale 등 원하는 정보가 들어있게 된다.
         */
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // registrationId : 위에 설명해놓았음. 해당 코드에서 google이라는 정보가 들어 갈 것이다.
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        // userNameAttributeName : 구글 기준 sub / 네이버 기준 id
        // 각 서비스에서 primary key의 필드명으로 이해하면 쉽다.
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthMemberDto attributes = OAuthMemberDto
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        OauthMember oauthMember = saveOrUpdate(attributes);
        httpSession.setAttribute("oauthMember", new SessionMember(oauthMember));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(oauthMember.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private OauthMember saveOrUpdate(OAuthMemberDto attributes) {
        OauthMember oauthMember = oauthMemberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return oauthMemberRepository.save(oauthMember);
    }


}
