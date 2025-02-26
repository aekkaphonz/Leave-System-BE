package Leave_System.Leave_System.spring.Controllers;

import Leave_System.Leave_System.spring.Entities.UserEntity;
import Leave_System.Leave_System.spring.Repositories.UserRepository;
import Leave_System.Leave_System.spring.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/create_user")
    public ResponseEntity<?> createUser(@RequestBody UserEntity userEntity) {
        if (userEntity == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "responseStatus", 400,
                    "responseMessage", "ข้อมูลผู้ใช้ไม่ถูกต้อง"));
        }
        if (userEntity.getUsername() == null || userEntity.getEmail() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "responseStatus", 400,
                    "responseMessage", userEntity.getUsername() == null ? "กรุณาใส่ชื่อของท่าน" : "กรุณากรอกอีเมล"));
        }
        try {
            userService.createUser(userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "responseStatus", 200,
                    "responseMessage", "สมัครสมาชิกสำเร็จ"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "responseStatus", 500,
                    "responseMessage", "เกิดข้อผิดพลาดในระบบ"));
        }
    }

    @GetMapping("/user")
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }


}
