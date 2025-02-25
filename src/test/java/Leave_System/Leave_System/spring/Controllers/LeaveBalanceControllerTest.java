package Leave_System.Leave_System.spring.Controllers;

import Leave_System.Leave_System.spring.Entities.BalanceEntity;
import Leave_System.Leave_System.spring.Entities.UserEntity;
import Leave_System.Leave_System.spring.Repositories.LeaveBalanceRepository;
import Leave_System.Leave_System.spring.Services.LeaveBalanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LeaveBalanceControllerTest {

    @Mock
    private LeaveBalanceService leaveBalanceService;

    @Mock
    private LeaveBalanceRepository leaveBalanceRepository;

    private LeaveBalanceController leaveBalanceController;

    private BalanceEntity sampleBalance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        leaveBalanceController = new LeaveBalanceController(leaveBalanceService, leaveBalanceRepository);

        sampleBalance = new BalanceEntity();
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setUsername("testuser");
        sampleBalance.setUser(user);
        sampleBalance.setRemainingDays(10);
    }

    @Test
    void findAll_ShouldReturnAllBalances() {

        List<BalanceEntity> expectedBalances = Arrays.asList(sampleBalance);
        when(leaveBalanceRepository.findAll()).thenReturn(expectedBalances);

        List<BalanceEntity> result = leaveBalanceController.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleBalance.getUser().getId(), result.get(0).getUser().getId());
        assertEquals(sampleBalance.getRemainingDays(), result.get(0).getRemainingDays());
        verify(leaveBalanceRepository).findAll();
    }

    @Test
    void createBalance_WithValidData_ShouldReturnCreated() {

        when(leaveBalanceService.createBalance(any(BalanceEntity.class))).thenReturn(sampleBalance);

        ResponseEntity<?> response = leaveBalanceController.createBalance(sampleBalance);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(200, responseBody.get("responseStatus"));
        assertEquals("สร้างยอดวันลาคงเหลือสำเร็จ", responseBody.get("responseMessage"));
    }

    @Test
    void createBalance_WithNullBalance_ShouldReturnBadRequest() {

        ResponseEntity<?> response = leaveBalanceController.createBalance(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(400, responseBody.get("responseStatus"));
        assertEquals("ยอดวันลาคงเหลือไม่สามารถเป็นค่าว่างได้", responseBody.get("responseMessage"));
    }

    @Test
    void createBalance_WithNullUser_ShouldReturnBadRequest() {

        BalanceEntity balanceEntity = new BalanceEntity();

        ResponseEntity<?> response = leaveBalanceController.createBalance(balanceEntity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(400, responseBody.get("responseStatus"));
        assertEquals("ยอดวันลาคงเหลือไม่สามารถเป็นค่าว่างได้", responseBody.get("responseMessage"));
    }

    @Test
    void createBalance_WhenServiceThrowsException_ShouldReturnInternalServerError() {

        doThrow(new RuntimeException("Database error")).when(leaveBalanceService)
                .createBalance(any(BalanceEntity.class));

        ResponseEntity<?> response = leaveBalanceController.createBalance(sampleBalance);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(500, responseBody.get("responseStatus"));
        assertEquals("เกิดข้อผิดพลาดในระบบ", responseBody.get("responseMessage"));
    }
}
