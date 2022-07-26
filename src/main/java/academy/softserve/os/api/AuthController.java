package academy.softserve.os.api;

import academy.softserve.os.api.config.jwt.JwtUtils;
import academy.softserve.os.api.dto.JwtResponseDTO;
import academy.softserve.os.api.dto.command.LoginCommandDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponseDTO> authUser(@RequestBody LoginCommandDTO loginCommandDTO) {
        var authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginCommandDTO.getLogin(),
                                loginCommandDTO.getPassword()));

        var jwt = jwtUtils.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponseDTO(jwt));
    }
}
