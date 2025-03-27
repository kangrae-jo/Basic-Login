package kakao.user;

import lombok.Builder;
import lombok.Getter;

public class UserResponseDTO {

    @Getter
    @Builder
    public static class JoinResultDTO {
        private String email;
        private String name;
    }

}
