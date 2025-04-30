## 2025 Spring Security Study
스프링 부트 기반 스프링 시큐리티 스터디
### spring security study 실습환경
- Java: 17
- Spring Security
- Spring Data JPA
- MySQL
- Mustache (Template Engine)


### Spring Security 
스프링 시큐리티는 스프링 기반 어플리케이션의 보안을 담당하는 스프링 하위 프레임워크.
Spring Security 는 세션 기반 인증, 서버 상태 관리 - JWT는 토큰 기반 인증. 


### Spring Security가 적용된 로그인 동작 원리
![로그인/회원가입 동작 원리 구조도](https://github.com/user-attachments/assets/a85a04cd-99fe-48d0-a67f-062e3d6b915b)

- 로그인 
1. [사용자] 로그인 요청
	- 사용자가 /login 경로로 POST 요청으로 username / password 전송.

2. SecurityContextPersistenceFilter
	- 요청 시작 시점에 동작.
    - 세션에 기존 SecurityContext가 있는 경우 → SecurityContextHolder에 세팅해서 필터 체인에서 사용할 수 있게 준비.

    - 직접 인증하진 않지만, 컨텍스트 유지 관리가 핵심 역할.

3. UsernamePasswordAuthenticationFilter
	- 로그인 요청이면 이 필터가 동작함
	- 요청에서 username/password 추출
	- AuthenticationManager에게 인증 요청 (authenticate() 호출)

4. UserDetailsService + UserRepository
	- AuthenticationManager는 내부적으로 UserDetailsService 호출
	→ 여기서 UserRepository를 통해 사용자 정보(UserEntity) 조회
	- 조회된 정보를 UserDetails로 래핑해서 반환

5. 인증 검증
	- 입력한 비밀번호와 DB에 있는 암호화된 비밀번호 비교
	- 일치하면 Authentication 객체 생성
	→ SecurityContextHolder.getContext().setAuthentication(authentication)으로 저장

6. SecurityContextPersistenceFilter
	- 요청이 끝날 때, SecurityContextHolder에서 SecurityContext를 꺼내
	→ 세션(HttpSession)에 다시 저장 (SecurityContextRepository 사용)

7. 이후 요청들
	- 세션이 유지된다면 이후 요청 시 SecurityContextPersistenceFilter가 다시 세션에서 인증 정보를 꺼내 SecurityContextHolder에 넣어줌.
	→ 즉, 인증 유지됨 (로그인 유지)
