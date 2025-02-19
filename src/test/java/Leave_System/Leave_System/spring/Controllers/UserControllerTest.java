package Leave_System.Leave_System.spring.Controllers;

import Leave_System.Leave_System.spring.Entities.UserEntity;
import Leave_System.Leave_System.spring.Repositories.UserRepository;
import Leave_System.Leave_System.spring.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService, userRepository);
    }

    @Test
    void createUser_WithValidData_ShouldReturnCreated() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setEmail("test@example.com");

        when(userService.createUser(any(UserEntity.class))).thenReturn(userEntity);

        // Act
        ResponseEntity<?> response = userController.createUser(userEntity);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(200, responseBody.get("responseStatus"));
        assertEquals("สมัครสมาชิกสำเร็จ", responseBody.get("responseMessage"));
    }

    @Test
    void createUser_WithNullUser_ShouldReturnBadRequest() {
        // Act
        ResponseEntity<?> response = userController.createUser(null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(400, responseBody.get("responseStatus"));
        assertEquals("ข้อมูลผู้ใช้ไม่ถูกต้อง", responseBody.get("responseMessage"));
    }

    @Test
    void createUser_WithNullUsername_ShouldReturnBadRequest() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");

        // Act
        ResponseEntity<?> response = userController.createUser(userEntity);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(400, responseBody.get("responseStatus"));
        assertEquals("กรุณาใส่ชื่อของท่าน", responseBody.get("responseMessage"));
    }

    @Test
    void createUser_WithNullEmail_ShouldReturnBadRequest() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");

        // Act
        ResponseEntity<?> response = userController.createUser(userEntity);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(400, responseBody.get("responseStatus"));
        assertEquals("กรุณากรอกอีเมล", responseBody.get("responseMessage"));
    }

    @Test
    void createUser_WhenServiceThrowsException_ShouldReturnInternalServerError() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setEmail("test@example.com");

        doThrow(new RuntimeException("Database error")).when(userService).createUser(any(UserEntity.class));

        // Act
        ResponseEntity<?> response = userController.createUser(userEntity);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(500, responseBody.get("responseStatus"));
        assertEquals("เกิดข้อผิดพลาดในระบบ", responseBody.get("responseMessage"));
    }
}
