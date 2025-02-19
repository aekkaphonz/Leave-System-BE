package Leave_System.Leave_System.spring.Controllers;

import Leave_System.Leave_System.spring.Entities.RequestEntity;
import Leave_System.Leave_System.spring.Entities.UserEntity;
import Leave_System.Leave_System.spring.Services.LeaveRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.junit.jupiter.api.Assertions.*;

class LeaveRequestControllerTest {

    @Mock
    private LeaveRequestService leaveRequestService;

    private LeaveRequestController leaveRequestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        leaveRequestController = new LeaveRequestController(leaveRequestService);
    }

    @Test
    void createRequest_WithValidData_ShouldReturnSuccess() {
        // Arrange
        RequestEntity requestEntity = new RequestEntity();
        UserEntity user = new UserEntity();
        requestEntity.setUser(user);

        when(leaveRequestService.createRequest(any(RequestEntity.class))).thenReturn(requestEntity);

        // Act
        ResponseEntity<?> response = leaveRequestController.createRequest(requestEntity);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(200, responseBody.get("responseStatus"));
        assertEquals("ส่งข้อมูลแบบฟอร์มขอลางานเรียบร้อย", responseBody.get("responseMessage"));
    }

    @Test
    void createRequest_WithNullRequest_ShouldReturnBadRequest() {
        // Act
        ResponseEntity<?> response = leaveRequestController.createRequest(null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(400, responseBody.get("responseStatus"));
        assertEquals("แบบฟอร์มขอลางานไม่สามารถเป็นค่าว่างได้", responseBody.get("responseMessage"));
    }

    @Test
    void createRequest_WhenServiceThrowsException_ShouldReturnInternalServerError() {
        // Arrange
        RequestEntity requestEntity = new RequestEntity();
        UserEntity user = new UserEntity();
        requestEntity.setUser(user);

        doThrow(new RuntimeException("Database error")).when(leaveRequestService).createRequest(any(RequestEntity.class));

        // Act
        ResponseEntity<?> response = leaveRequestController.createRequest(requestEntity);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(500, responseBody.get("responseStatus"));
        assertEquals("เกิดข้อผิดพลาดในระบบ", responseBody.get("responseMessage"));
    }

    @Test
    void findAll_ShouldReturnAllRequests() {
        // Arrange
        List<RequestEntity> requests = Arrays.asList(
            new RequestEntity(),
            new RequestEntity()
        );
        when(leaveRequestService.findAll()).thenReturn(requests);

        // Act
        List<RequestEntity> result = leaveRequestController.findAll();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void findByRequestId_WithValidId_ShouldReturnRequest() {
        // Arrange
        RequestEntity request = new RequestEntity();
        when(leaveRequestService.findById(1)).thenReturn(request);

        // Act
        ResponseEntity<RequestEntity> response = leaveRequestController.findByRequestId(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void updateStatus_WithValidData_ShouldReturnUpdatedRequest() {
        // Arrange
        RequestEntity updatedRequest = new RequestEntity();
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("status", "APPROVED");
        
        when(leaveRequestService.updateLeaveStatus(eq(1), eq("APPROVED"))).thenReturn(updatedRequest);

        // Act
        ResponseEntity<RequestEntity> response = leaveRequestController.updateStatus(1, requestBody);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void updateStatus_WithNullStatus_ShouldReturnBadRequest() {
        // Arrange
        Map<String, String> requestBody = new HashMap<>();
        
        // Act
        ResponseEntity<RequestEntity> response = leaveRequestController.updateStatus(1, requestBody);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
