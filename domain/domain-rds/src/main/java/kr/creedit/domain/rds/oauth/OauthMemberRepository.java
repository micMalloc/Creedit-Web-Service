package kr.creedit.domain.rds.oauth;

import kr.creedit.domain.rds.oauth.OauthMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthMemberRepository extends JpaRepository<OauthMember, Long> {
    Optional<OauthMember> findByEmail(String email);
}
