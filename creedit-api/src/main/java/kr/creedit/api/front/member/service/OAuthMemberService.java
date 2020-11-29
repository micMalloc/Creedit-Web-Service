package kr.creedit.api.front.member.service;

import kr.creedit.api.front.member.SessionMember;
import kr.creedit.api.front.member.dto.OAuthMemberDto;
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

@RequiredArgsConstructor
@Service
public class OAuthMemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final OauthMemberRepository oauthMemberRepository;
    private final HttpSession httpSession;

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

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
                .map(entity -> entity.update(attributes.getName()))
                .orElse(attributes.toEntity());

        return oauthMemberRepository.save(oauthMember);
    }


}
