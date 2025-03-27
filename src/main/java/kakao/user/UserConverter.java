package kakao.user;

public class UserConverter {

    public static UserResponseDTO.JoinResultDTO toJoinResultDTO(User user) {
        return UserResponseDTO.JoinResultDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

}
