package com.bwp.app.config;

import com.bwp.app.dto.UserAccountDto;
import com.bwp.app.repository.UserAccountRepository;
import com.bwp.app.serucity.BoardPrincipal;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        /* 삭제 */
//        return http
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // HttpSecurity 의 authorizeHttpRequests 에서 모든 요청(anyRequest() 부분)이 인증을 허용(permitAll() 부분)하겠다
//                .formLogin() // 로그인 페이지를 만들고
//                .and().build();
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .antMatchers(
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/favicon.*"
                        ).permitAll()
                        .mvcMatchers(  /* mvcMatchers: 스프링에 패턴 매칭 기능이 들어간 메서드. 컨트롤러에서 맵핑할때 "/articles/** /form" 이런식으로 경로 설정할때도 있는데 그런 특정 경로를 지정해서 권한을 설정할 수도 있게 하는 메서드임. */
                                HttpMethod.GET, /* 특정 경로를 지정 하는 부분임. - GET 방식, 루트페이지, 게시판리스트 페이지 는  */
                                "/", "/articles/**", "/items/**"
                        ).permitAll()  /* 권한을 허용하고 */
                        .anyRequest().authenticated() /* 그 외의 경로로 접근할때는 어떤 요청이든 authentication(인가) 과정을 거치도록 한다 */
                )
                .formLogin().and() // formLogin() : 로그인 페이지를 만들어준다.
                .rememberMe().and()
                .logout()
                .logoutSuccessUrl("/") /* 로그아웃을 성공하면 "/" 이 경로로 이동해라 라는뜻*/
                .and()
                .build();
    }

    /* 사용자 정보 가져오는 부분 - 실제 인증 데이터를 가져오는 서비스 로직을 가져온다. */
    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) { /* DB에 있는 user 정보를 주입 받아야 하니까 매개변수로 userAccountRepository 를 받는다.  */
        return username -> userAccountRepository
                .findByEmail(username)
                .map(UserAccountDto::from)
                .map(BoardPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - username: " + username)); /* 혹시라도 인증된 사용자를 못찾을때에 대한 대안으로 UsernameNotFoundException 사용함.*/
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}