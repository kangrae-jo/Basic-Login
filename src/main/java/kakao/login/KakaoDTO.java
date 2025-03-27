package kakao.login;

import lombok.Getter;

public class KakaoDTO {

    @Getter
    public static class OAuthToken {
        private String access_token;
        private String token_type;
        private String refresh_token;
        private int expires_in;
        private String scope;
        private int refresh_token_expires_in;
    }

    @Getter
    public static class KakaoProfile {
        private Long id;
        private String connected_at;
        private Properties properties;
        private KakaoAccount kakao_account;

        @Getter
        public class Properties {
            private String nickname;
            private String profile_image;
            private String thumbnail_image;
        }

        @Getter
        public class KakaoAccount {
            private Boolean profile_nickname_needs_agreement;
            private Boolean profile_image_needs_agreement;
            private Profile profile;
            private Boolean has_email;
            private Boolean email_needs_agreement;
            private Boolean is_email_valid;
            private Boolean is_email_verified;
            private String email;

            @Getter
            public class Profile {
                private String nickname;
                private String thumb_image_url;
                private String profile_image_url;
                private Boolean is_default_image;
                private Boolean is_default_nickname;
            }
        }
    }

}
