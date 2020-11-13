package kr.creedit.api.front.config;

import kr.creedit.api.front.member.service.OAuthMemberService;
import kr.creedit.api.front.member.service.MemberService;
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
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;

    private final OAuthMemberService OAuthMemberService;

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
                        .antMatchers("/login", "/sign-up", "/user", "/has-role").permitAll()
                        .antMatchers("/").hasRole("USER")
                        .antMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                .and()
                    .formLogin() // 8
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                .and()
                    .logout() // 9
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(OAuthMemberService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
