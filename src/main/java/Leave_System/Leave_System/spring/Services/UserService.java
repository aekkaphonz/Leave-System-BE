package Leave_System.Leave_System.spring.Services;

import Leave_System.Leave_System.spring.Entities.UserEntity;
import Leave_System.Leave_System.spring.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity createUser(UserEntity userEntity) {
        validateUser(userEntity);
        try {
            return userRepository.save(userEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving user entity", e);
        }
    }

    private void validateUser(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("User entity cannot be null");
        }
        if (userEntity.getUsername() == null || userEntity.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (userEntity.getEmail() == null || userEntity.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (userEntity.getDepartment() == null || userEntity.getDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be null or empty");
        }
    }
}
