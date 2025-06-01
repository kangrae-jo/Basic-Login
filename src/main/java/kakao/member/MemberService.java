package kakao.member;

import com.sun.security.auth.UserPrincipal;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDTO getUserInfo(UserPrincipal userPrincipal) {
        Member member = memberRepository.findById(userPrincipal.getName())
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 해당 사용자가 존재하지 않습니다."));

        return new MemberDTO(member.getName());
    }

}
