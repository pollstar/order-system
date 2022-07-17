package academy.softserve.os.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponseDTO {

    private String accessToken;
    private String type = "Bearer";

    public JwtResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
