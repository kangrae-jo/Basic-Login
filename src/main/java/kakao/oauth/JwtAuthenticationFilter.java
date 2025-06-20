package kakao.oauth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kakao.member.entity.Member;
import kakao.member.repository.MemberRepository;
import kakao.oauth.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // 1. Authorization 헤더 확인
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // "Bearer " 이후 토큰만 추출

            try {
                // 2. 토큰에서 사용자 이름 추출
                String name = jwtUtil.getMemberNameFromToken(token);

                // 3. 사용자 정보 조회
                Member member = memberRepository.findById(name).orElse(null);
                if (member != null) {
                    // 4. 인증 객체 생성
                    UserPrincipal principal = UserPrincipal.create(member);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            principal, // principal에 UserPrincipal 객체 저장
                            null,             // 비밀번호 생략
                            principal.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // 유효하지 않은 토큰이면 무시하고 다음 필터로 넘어감
                System.out.println(e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

}
