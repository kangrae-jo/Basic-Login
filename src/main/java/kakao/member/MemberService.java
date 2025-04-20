package kakao.member;

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

    public MemberDTO getUserInfo(String name) {
        Member member = memberRepository.findById(name)
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 해당 사용자가 존재하지 않습니다: " + name));
        return new MemberDTO(member.getName());
    }

}
