package academy.softserve.os.service.impl;

import academy.softserve.os.model.User;
import academy.softserve.os.model.UserDetailsImpl;
import academy.softserve.os.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private UserServiceImpl userService;

    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository = mock(UserRepository.class);
        var passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void givenUserLogin_loadUserByUsername_shouldReturnUser() {
        //given
        var login = "login";
        var user = User.builder()
                .login("login")
                .passwordHash("123")
                .roles(List.of())
                .build();
        var userDetails = UserDetailsImpl.from(user);

        //when
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));
        var result = userService.loadUserByUsername(login);

        //then
        assertThat(result.getUsername()).isEqualTo(userDetails.getUsername());
        assertThat(result.getPassword()).isEqualTo(userDetails.getPassword());
        assertThat(result.getAuthorities()).isEqualTo(userDetails.getAuthorities());
    }

    @Test
    void givenUserLogin_loadUserByUsername_shouldThrowException() {
        //given
        var login = "login";
        //when
        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> userService.loadUserByUsername(any()))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}