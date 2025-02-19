package Leave_System.Leave_System.spring.Services;

import Leave_System.Leave_System.spring.Entities.UserEntity;
import Leave_System.Leave_System.spring.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ValidUser_ShouldCreateSuccessfully() {

        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setDepartment("IT");

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        UserEntity result = userService.createUser(user);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("IT", result.getDepartment());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void createUser_NullUser_ShouldThrowException() {

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(null);
        });
    }

    @Test
    void createUser_EmptyUsername_ShouldThrowException() {

        UserEntity user = new UserEntity();
        user.setUsername("");
        user.setEmail("test@example.com");
        user.setDepartment("IT");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(user);
        });
    }

    @Test
    void createUser_EmptyEmail_ShouldThrowException() {

        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setEmail("");
        user.setDepartment("IT");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(user);
        });
    }

    @Test
    void createUser_EmptyDepartment_ShouldThrowException() {

        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setDepartment("");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(user);
        });
    }

    @Test
    void createUser_RepositoryException_ShouldThrowRuntimeException() {

        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setDepartment("IT");

        when(userRepository.save(any(UserEntity.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            userService.createUser(user);
        });
    }
}
