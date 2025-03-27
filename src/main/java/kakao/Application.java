package kakao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

//https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=75f04676990b0bf0deebd59d284221ae&redirect_uri=http://localhost:8080/auth/login/kakao