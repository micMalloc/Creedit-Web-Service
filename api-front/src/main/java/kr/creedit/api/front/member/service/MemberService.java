package kr.creedit.api.front.member.service;

import kr.creedit.api.front.member.dto.MemberDto;
import kr.creedit.domain.rds.member.Member;
import kr.creedit.domain.rds.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * 회원 정보 저장 서비스
     *
     * @param requestDto 회원 정보가 담긴 DTO
     * @return 저장된 회원의 고유 ID
     */
    public Long saveMember(MemberDto.SignUp requestDto) {
        requestDto.encodePassword();

        Member member = requestDto.toEntity();
        member.setAuthAsUser();

        return memberRepository.save(member)
                .getId();
    }

    /**
     * Spring Security 필수 메서드 구현 - 사용자 정보 받아오는 메서드
     *
     * @param email 요청된 이메일 주소
     * @return UserDetails 사용자 정보
     * @throws UsernameNotFoundException 해당 email에 매핑된 사용자 정보가 없는 경우 발생
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
