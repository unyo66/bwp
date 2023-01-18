package com.bwp.app.serucity;

import com.bwp.app.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
public record BoardPrincipal(
        Long userId,
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String nickname,
        String address,
        String phone,
        String notice
) implements UserDetails {
    @Override public String getUsername() { return username; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        /* 매개변수로 하나 넣어주고, null 을 authorities 로 변경하기 */
        //return null;
        return authorities;
    }


    /* 얘네는 안중요 */
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }


    /* 생성자 만들기 - 팩토리 메서드 of() 로 만들기 */
    public static BoardPrincipal of(Long userId, String username, String password, String nickname, String address, String phone, String notice) {
        /* 권한정보를 컬렉션 데이터로 저장되기때문에 중복은 없다. 그래서 자료구조는 Set 이 되야한다.*/
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new BoardPrincipal(
                userId,
                username,
                password,

                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
                /** roleTypes 해설: 권환에 관련된 정보를 넣어줄거임.
                 그냥 두면 roleTypes 의 타입은 RoleType 타입이다. (위에 Set<BoardPrincipal.RoleType> 때문임)
                 그런데 맨 위에 매개변수 부분 보면 'Collection<? extends GrantedAuthority>'가 있다. 그거에 관련된 제네릭을 써야 하는데 RoleType 타입이라 에러난다.
                 그래서 타입을 변환을 하기 위해서 stream() 을 이용할거다.
                 .map(BoardPrincipal.RoleType::getName): 맵을 이용해서 RoleType 에서 name을 가져올거고
                 SimpleGrantedAuthority 로 생성자를 만들거다. (Ctrl + B 해보면 거기에 GrantedAuthority 로 구현하고 있음.)
                 collect(Collectors.toUnmodifiableSet()): 그걸 모아서(collect) toUnmodifiableSet 을 이용해서 바꾼다.

                 이렇게 하면 최초에 BoardPrincipal 이 생성될때 인증정보를 만들게 되는데 new 가 돌면서 roleType이 만들어질거다.
                 */
                ,
                nickname,
                address,
                phone,
                notice
        );
    }

    /* 위에서 Set<BoardPrincipal.RoleType> 부분에서 쓰임*/
    public enum RoleType {
        USER("ROLE_USER"); /* 스프링시큐리티에서 권한 표현을 하는 문자열에는 무조건 앞에 ROLE_ 로 시작해야 한다.*/

        @Getter
        private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }

    /* UserAccountDto 로부터 BoardPrincipal 을 역으로 만들어야 하는 경우도 있을거다. */
    public static BoardPrincipal from(UserAccountDto dto) {
        return BoardPrincipal.of(
                dto.id(),
                dto.email(),
                dto.pw(),
                dto.nickname(),
                dto.address(),
                dto.phone(),
                dto.notice()
        );
    }
    /* Principal 을 받아서 dto 로 변환하는 작업 하기*/
    public UserAccountDto toDto() {
        return UserAccountDto.of(
                userId,
                username,
                password,
                nickname,
                address,
                phone,
                notice
        );
    }
}
