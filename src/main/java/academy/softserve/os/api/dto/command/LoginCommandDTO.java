package academy.softserve.os.api.dto.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginCommandDTO {
	
	private String login;
	private String password;
}
