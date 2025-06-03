package kakao.member.controller;

import kakao.member.service.MemberService;
import kakao.oauth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    
    @GetMapping("/info")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal UserPrincipal user) {
        if (user == null) {
            return ResponseEntity.status(401).body("[ERROR] 로그인이 필요합니다.");
        }
        return ResponseEntity.ok(memberService.getUserInfo(user));
    }

}
