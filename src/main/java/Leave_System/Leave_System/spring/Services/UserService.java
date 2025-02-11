package Leave_System.Leave_System.spring.Services;

import Leave_System.Leave_System.spring.Entities.UserEntity;
import Leave_System.Leave_System.spring.Repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
        }


    }

