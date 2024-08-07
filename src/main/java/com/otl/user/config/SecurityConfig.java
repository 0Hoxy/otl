package com.otl.user.config;

import com.otl.items.config.CustomAuthenticationEntryPoint;
import com.otl.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        // 공개 페이지에 대한 접근 설정
                        .requestMatchers("/", "/user/**","/item/**").permitAll()
                        //여기에 비회원들도 입장가능한 페이지 추가
                        .requestMatchers("/h2-console/**").permitAll()
                        //ADMIN만 접근 가능
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/")
                        //loadUserByUsername에서 넘겨받은 User.builder().build()의 파라미터에서 username을 "email"로 지정한다.(로그인 시 사용할 파라미터 이름으로 email을 지정한다)
                        .usernameParameter("email")
                        .failureUrl("/user/login/error")
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .logoutSuccessUrl("/")
                )
                .userDetailsService(userService)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") // H2 콘솔 경로에 대해 CSRF 비활성화
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                )
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //비밀번호를 데이터베이스에 그대로 저장했을 경우, 데이터베이스가 해킹당하면 고객의 회원 정보가 그대로 노출 되므로, 이를 해결하기 위해 BcryptPasswordEncoder를 @Bean으로 등록하여 사용한다.
        return new BCryptPasswordEncoder();
    }

    @Bean
    //AuthenticationConfiguration을 통해 자동 구성된 AuthenticationManager를 가져옵니다.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring()
                .requestMatchers(
                        new AntPathRequestMatcher("/js/**"),
                        new AntPathRequestMatcher("/css/**"),
                        new AntPathRequestMatcher("/images/**"),
                        new AntPathRequestMatcher("/webjars/**")
                ));
    }

}
