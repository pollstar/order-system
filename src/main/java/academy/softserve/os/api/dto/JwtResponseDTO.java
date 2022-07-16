package academy.softserve.os.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponseDTO {
	
	private String token;
	private String type = "Bearer";
	private Long id;
	private String login;
	private List<String> roles;
	
	public JwtResponseDTO(String token, Long id, String login, List<String> roles) {
		this.token = token;
		this.id = id;
		this.login = login;
		this.roles = roles;
	}
}
