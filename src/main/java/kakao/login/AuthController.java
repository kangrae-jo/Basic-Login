package kakao.login;

import jakarta.servlet.http.HttpServletResponse;
import kakao.trainer.Trainer;
import kakao.trainer.TrainerRequestDTO;
import kakao.trainer.TrainerResponseDTO;
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
    public ResponseEntity<?> join(@RequestBody TrainerRequestDTO.LoginRequestDTO loginRequestDTO) {
        return null;
    }

    // 카카오 로그인 처리
    @GetMapping("/auth/login/kakao")
    public BaseResponse<TrainerResponseDTO> kakaoLogin(@RequestParam("code") String accessCode,
                                                       HttpServletResponse httpServletResponse) {
        Trainer trainer = authService.oAuthLogin(accessCode, httpServletResponse);
        System.out.println("success!");

        return BaseResponse.onSuccess(new TrainerResponseDTO(trainer.getEmail(), trainer.getName()));
    }

}
