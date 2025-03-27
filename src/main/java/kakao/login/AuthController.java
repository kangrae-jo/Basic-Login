package kakao.login;

import jakarta.servlet.http.HttpServletResponse;
import kakao.user.User;
import kakao.user.UserConverter;
import kakao.user.UserRequestDTO;
import kakao.user.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> join(@RequestBody UserRequestDTO.LoginRequestDTO loginRequestDTO) {
        return null;
    }

    // 카카오 로그인 처리
    @GetMapping("/auth/login/kakao")
    public BaseResponse<UserResponseDTO.JoinResultDTO> kakaoLogin(@RequestParam("code") String accessCode,
                                                                  HttpServletResponse httpServletResponse) {
        User user = authService.oAuthLogin(accessCode, httpServletResponse);
        return BaseResponse.onSuccess(UserConverter.toJoinResultDTO(user));
    }

}
