package kr.creedit.api.front.config;

import kr.creedit.api.front.member.service.CustomOAuth2OAuthMemberService;
import kr.creedit.api.front.member.service.MemberService;
import kr.creedit.domain.rds.member.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
// 내가 웹 설정을 다 하겠다는 Annotation EnableWebSecurity, Configuration
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;

    private final CustomOAuth2OAuthMemberService customOAuth2OAuthMemberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                        .antMatchers("/login", "/sign-up", "/user").permitAll() // 누구나 접근 허용
                        .antMatchers("/").hasRole("USER") // USER, ADMIN만 접근 가능
                        .antMatchers("/admin").hasRole("ADMIN") // ADMIN만 접근 가능
                        .anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
                .and()
                    .formLogin() // 8
                        .loginPage("/login") // 로그인 페이지 링크
                        .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 주소
                .and()
                    .logout() // 9
                        .logoutSuccessUrl("/login") // 로그아웃 성공시 리다이렉트 주소
                        .invalidateHttpSession(true) // 세션 날리기
                .and()
                    .oauth2Login()  //OAuth2 로그인 설정을 시작한다.
                                    // 내가 application-oauth.yml 에 설정한 정보는 OAuth2ClientProperties를 통해 읽어진다.
                                    // 해당 정보는 OAuth2ClientRegistrationRepositoryConfiguration 을 통해 들어있으며
                                    // 해당 설정에서 적용되어 사용된다.
                        .userInfoEndpoint() // OAuth2 로그인 이후 사용자 정보를 가져올 때의 설정을 시작한다.
                            .userService(customOAuth2OAuthMemberService); // 소셜 로그인 성공 이후 후속 조치를 진행할 userService 인터페이스의 구현체를 등록한다.
                                                                          // 소셜 서비스에서 사용자 정보를 가져온 상태에서 추가로 진행할 기능을 명시할 수 있다.
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
