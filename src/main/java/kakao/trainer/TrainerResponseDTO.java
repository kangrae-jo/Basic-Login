package kakao.trainer;

import lombok.Getter;

@Getter
public class TrainerResponseDTO {

    private String email;
    private String name;

    public TrainerResponseDTO(String email, String name) {
        this.email = email;
        this.name = name;
    }

}
