package kakao.oauth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // 실패 로그 출력
        System.out.println("[ERROR] OAuth2 로그인 실패: " + exception.getMessage());

        // 실패 시 원하는 URL로 리다이렉트 (예: 로그인 페이지로)
        response.sendRedirect("/login?error=true");
    }

}
