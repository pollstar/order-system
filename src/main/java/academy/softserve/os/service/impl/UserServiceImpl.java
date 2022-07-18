package academy.softserve.os.service.impl;

import academy.softserve.os.exception.LoginIsNotUniqueException;
import academy.softserve.os.model.Role;
import academy.softserve.os.model.RoleAssignment;
import academy.softserve.os.model.User;
import academy.softserve.os.model.UserDetailsImpl;
import academy.softserve.os.repository.UserRepository;
import academy.softserve.os.service.UserService;
import academy.softserve.os.service.command.CreateUserCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found with login: " + username));
        return UserDetailsImpl.from(user);
    }

    @Override
    @Transactional
    public User createUser(CreateUserCommand command) {
        if (userRepository.existsByLogin(command.getLogin())) {
            throw new LoginIsNotUniqueException();
        }
        var user = getUserFromCommand(command);
        return userRepository.save(user);
    }

    private User getUserFromCommand(CreateUserCommand command) {
        return User.builder()
                .login(command.getLogin())
                .passwordHash(passwordEncoder.encode(command.getPassword()))
                .roles(command.getRoles()
                        .stream()
                        .map(roleName -> new RoleAssignment(Role.valueOf(roleName)))
                        .collect(Collectors.toList()))
                .build();
    }


}
