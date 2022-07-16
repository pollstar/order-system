package academy.softserve.os.api;

import academy.softserve.os.api.dto.JwtResponseDTO;
import academy.softserve.os.api.dto.command.CreateUserCommandDTO;
import academy.softserve.os.api.dto.command.LoginCommandDTO;
import academy.softserve.os.configs.jwt.JwtUtils;
import academy.softserve.os.mapper.UserMapper;
import academy.softserve.os.model.Role;
import academy.softserve.os.model.RoleAssignment;
import academy.softserve.os.model.UserDetailsImpl;
import academy.softserve.os.service.UserService;
import academy.softserve.os.service.exception.WrongRoleNameException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginCommandDTO loginCommandDTO) {

        var authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginCommandDTO.getLogin(),
                        loginCommandDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var jwt = jwtUtils.generateJwtToken(authentication);

        var userDetails = (UserDetailsImpl) authentication.getPrincipal();

        var roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponseDTO(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid CreateUserCommandDTO createUserCommandDTO) {
        throwIfRolesAreWrong(createUserCommandDTO.getRoles());
        userService.createUser(userMapper.toCreateUserCommand(createUserCommandDTO));
        return ResponseEntity.ok("User created");
    }

    private void throwIfRolesAreWrong(Set<String> roles) {
        try {
            roles.forEach(roleName -> new RoleAssignment(Role.valueOf(roleName)));

        } catch (IllegalArgumentException illegalArgumentException) {
            throw new WrongRoleNameException();
        }
    }
}
