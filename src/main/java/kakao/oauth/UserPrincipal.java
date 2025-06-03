package kakao.oauth;

import java.util.Collection;
import java.util.List;
import kakao.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserPrincipal implements OAuth2User, UserDetails {

    private final String name;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String name, Collection<? extends GrantedAuthority> authorities) {
        this.name = name;
        this.authorities = authorities;
    }

    public static UserPrincipal create(Member member) {
        return new UserPrincipal(
                member.getName(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Override
    public String getName() {
        return name;
    }

    // UserDetails 필수 메서드
    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public String getPassword() {
        return null; // 비밀번호 없이 JWT 기반 인증이므로 null 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public java.util.Map<String, Object> getAttributes() {
        return java.util.Collections.emptyMap();
    }

}
