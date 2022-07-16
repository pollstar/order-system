package academy.softserve.os.api.dto;

import java.util.List;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
