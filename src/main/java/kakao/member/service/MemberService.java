package kakao.member.service;

import java.util.NoSuchElementException;
import kakao.member.entity.Member;
import kakao.member.paylod.MemberDTO;
import kakao.member.repository.MemberRepository;
import kakao.oauth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDTO getUserInfo(UserPrincipal user) {
        Member member = memberRepository.findById(user.getName())
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 해당 사용자가 존재하지 않습니다."));

        return new MemberDTO(member.getName());
    }

}
