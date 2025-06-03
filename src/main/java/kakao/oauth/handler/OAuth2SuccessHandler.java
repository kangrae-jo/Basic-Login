package kakao.oauth.handler;

import static kakao.oauth.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import kakao.oauth.CustomOAuth2User;
import kakao.oauth.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${client.redirect-url}") // 기본 리다이렉트 URL
    private String DEFAULT_REDIRECT_URL;
    private final JwtUtils jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 사용자 정보 가져오기
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String name = oAuth2User.getName();

        // JWT 생성
        String accessToken = jwtUtil.createToken(name);

        // 쿠키에서 redirect_uri 가져오기
        String targetUrl = getRedirectUriFromCookie(request).orElse(DEFAULT_REDIRECT_URL);

        // redirect_uri에 token 추가
        String redirectUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", accessToken)
                .build().toUriString();

        // 쿠키 제거 (보안)
        removeCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME);

        // 리다이렉트
        response.sendRedirect(redirectUrl);
    }

    private Optional<String> getRedirectUriFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> REDIRECT_URI_PARAM_COOKIE_NAME.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }

    private void removeCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // 즉시 삭제
        response.addCookie(cookie);
    }
}