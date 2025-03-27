package kakao.login;

import jakarta.servlet.http.HttpServletResponse;
import kakao.trainer.Trainer;
import kakao.trainer.TrainerRepository;
import kakao.util.JwtUtil;
import kakao.util.KakaoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoUtil kakaoUtil;
    private final TrainerRepository trainerRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public Trainer oAuthLogin(String accessCode, HttpServletResponse httpServletResponse) {
        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(accessCode);
        TrainerDTO kakaoProfile = kakaoUtil.requestProfile(oAuthToken);

        String email = kakaoProfile.getEmail();
        Trainer trainer = trainerRepository.findByEmail(email).orElseGet(() -> createNewUser(kakaoProfile));

        String token = jwtUtil.createAccessToken(trainer.getEmail());
        httpServletResponse.setHeader("Authorization", token);

        return trainer;
    }

    private Trainer createNewUser(TrainerDTO kakaoProfile) {
        Trainer trainer = new Trainer(kakaoProfile.getId(), kakaoProfile.getEmail(), kakaoProfile.getName());
        return trainerRepository.save(trainer);
    }

}
