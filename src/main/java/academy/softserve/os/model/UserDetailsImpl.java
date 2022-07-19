package academy.softserve.os.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final Long id;
    private Long workerId;
    private final String username;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl from(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(roleAssignment -> new SimpleGrantedAuthority(roleAssignment.getRole().name()))
                .collect(Collectors.toList());
        if (user.getWorker() != null){
            return new UserDetailsImpl(
                    user.getId(),
                    user.getWorker().getId(),
                    user.getLogin(),
                    user.getPasswordHash(),
                    authorities);
        }
        return new UserDetailsImpl(
                user.getId(),
                user.getLogin(),
                user.getPasswordHash(),
                authorities);
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
