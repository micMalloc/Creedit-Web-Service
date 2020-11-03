package kr.creedit.domain.rds.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EqualsAndHashCode(of = "id")
public class Member implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "email", unique = true, length = 50)
    private String email;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "name", length = 50)
    private String userName;

    @Column(name = "auth")
    private String auth;

    @Builder
    public Member(String email, String password, String userName, String auth) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.auth = auth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setAuthAsUser() {
        this.auth = "USER";
    }
}
