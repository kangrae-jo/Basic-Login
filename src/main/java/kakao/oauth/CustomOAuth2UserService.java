package kakao.oauth;

import java.util.Map;
import kakao.member.entity.Member;
import kakao.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        // 카카오에서 사용자 정보 받아오기
        /*
            super는 부모클래스의 메서드를 호출 -> 카카오 사용자 정보 받아오기 기본로직
            this class는 DB저장 등 후처리만을 따로 커스텀하는 구조
         */
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 사용자 정보 추출
        // 카카오 응답이 Map 구조기 때문에 바로 파싱 가능 (경고 무시)
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        // 이메일의 '@' 앞부분을 Trainer 이름으로 사용
        String email = (String) kakaoAccount.get("email");
        String name = email.split("@")[0];

        Member member = memberRepository.findById(name)
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .name(name)      // PK name
                        .build()));

        // 사용자 정보를 Spring Security에 넘김
        return new CustomOAuth2User(member.getName(), attributes);
    }

}
