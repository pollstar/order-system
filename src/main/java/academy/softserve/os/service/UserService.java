package academy.softserve.os.service;

import academy.softserve.os.model.User;
import academy.softserve.os.service.command.CreateUserCommand;

public interface UserService {

    User createUser(CreateUserCommand command);
}
