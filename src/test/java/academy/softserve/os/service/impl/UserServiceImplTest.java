package academy.softserve.os.service.impl;

import academy.softserve.os.model.User;
import academy.softserve.os.model.UserDetailsImpl;
import academy.softserve.os.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(value = {UserRepository.class})
class UserServiceImplTest {

    private UserServiceImpl userService;

    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void givenUserLogin_loadUserByUsername_shouldReturnUser() {
        //given
        var login = "login";
        var user = User.builder()
                .login("login")
                .passwordHash("123")
                .roles(Set.of())
                .build();
        var userDetails = UserDetailsImpl.from(user);

        //when
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));
        var result = userService.loadUserByUsername(login);

        //then
        assertThat(result).isEqualTo(userDetails);
    }

    @Test
    void givenUserLogin_loadUserByUsername_shouldThrowException() {
        //given
        var login = "login";
        var user = User.builder()
                .login("login")
                .passwordHash("123")
                .build();

        //when
        when(userRepository.findByLogin(login)).thenThrow(UsernameNotFoundException.class);

        //then
        assertThatThrownBy(() -> userService.loadUserByUsername(any()))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}