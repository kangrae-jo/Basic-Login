
### Basic-Login

<img width="871" alt="image" src="https://github.com/user-attachments/assets/7d849d3a-e9b5-4de8-968b-3e12dfd9ac22" />

[1] 프론트
  |
  |  (사용자가 카카오 로그인 버튼 클릭)
  |  → 요청: GET /oauth2/authorization/kakao

[2] 백엔드 (Spring Security)
  |
  |  📍 authorization-uri
  |  → 카카오 로그인 페이지로 리다이렉트

[3] 카카오 로그인 페이지
  |
  |  사용자가 로그인 (이메일/비번)
  |  → 성공하면 카카오가 "인가 코드" 발급

[4] 카카오 → 백엔드
  |
  |  전달: 인증 코드 (code)

[5] 백엔드 (Spring Security)
  |
  |  📍 token-uri
  |  → 카카오에 Access Token 요청

[6] 카카오
  |
  |  Access Token + 사용자 정보 응답


[7] 백엔드
  |
  |  📍 user-info-uri
  |  → 사용자 정보 받아옴
  |
  |  👉 CustomOAuth2UserService
  |      - User 엔티티 저장 or 조회
  |
  |  👉 OAuth2SuccessHandler
  |      - JWT 생성
 

[8] 백엔드 → 프론트
  |
  |  리다이렉트: http://{CLIENT_URL}/oauth2/success?token=JWT

[9] 프론트
  |
  |  JWT 저장 (localStorage 등)
  |
  |  이후 요청 시 → Authorization: Bearer {JWT}

[10] 백엔드
  |
  |  JwtAuthenticationFilter
  |    - JWT 유효성 검증
  |    - 인증된 사용자로 등록 (SecurityContext)
  |
  |  → 컨트롤러에서 사용자 정보 사용 가능!
