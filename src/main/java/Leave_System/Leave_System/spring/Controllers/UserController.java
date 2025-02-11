package Leave_System.Leave_System.spring.Controllers;

import Leave_System.Leave_System.spring.Entities.UserEntity;
import Leave_System.Leave_System.spring.Repositories.UserRepository;
import Leave_System.Leave_System.spring.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;



    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;

    }

//users
    @PostMapping("/create_user")
    public UserEntity createUser(@RequestBody UserEntity userEntity) {
        return userService.createUser(userEntity);
    }


}
