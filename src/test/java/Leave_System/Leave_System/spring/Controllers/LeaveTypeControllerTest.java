package Leave_System.Leave_System.spring.Controllers;

import Leave_System.Leave_System.spring.Entities.TypeEntity;
import Leave_System.Leave_System.spring.Entities.LeaveTypesEnum;
import Leave_System.Leave_System.spring.Services.LeaveTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LeaveTypeControllerTest {

    @Mock
    private LeaveTypeService leaveTypeService;

    @InjectMocks
    private LeaveTypeController leaveTypeController;

    private TypeEntity sampleType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleType = new TypeEntity();
        sampleType.setId(1);
        sampleType.setLeaveTypeName(LeaveTypesEnum.SICK_LEAVE);
        sampleType.setDescription("Leave for medical reasons");
        sampleType.setMaxDays(30);
    }

    @Test
    void findAll_ShouldReturnAllLeaveTypes() {

        List<TypeEntity> expectedTypes = Arrays.asList(sampleType);
        when(leaveTypeService.findAll()).thenReturn(expectedTypes);

        ResponseEntity<?> response = leaveTypeController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("responseStatus"));
        assertEquals("ดึงข้อมูลประเภทการลาสำเร็จ", body.get("responseMessage"));
        @SuppressWarnings("unchecked")
        List<TypeEntity> actualTypes = (List<TypeEntity>) body.get("data");
        assertNotNull(actualTypes);
        assertEquals(expectedTypes.size(), actualTypes.size());
        assertEquals(expectedTypes.get(0).getId(), actualTypes.get(0).getId());
        assertEquals(expectedTypes.get(0).getLeaveTypeName(), actualTypes.get(0).getLeaveTypeName());
        verify(leaveTypeService).findAll();
    }

    @Test
    void findAll_WhenServiceThrowsException_ShouldReturnInternalServerError() {

        when(leaveTypeService.findAll()).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<?> response = leaveTypeController.findAll();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("responseStatus"));
        assertEquals("เกิดข้อผิดพลาดในการดึงข้อมูลประเภทการลา", body.get("responseMessage"));
        verify(leaveTypeService).findAll();
    }

    @Test
    void createLeaveType_WithValidData_ShouldReturnCreated() {

        when(leaveTypeService.createLeave(any(TypeEntity.class))).thenReturn(sampleType);

        ResponseEntity<?> response = leaveTypeController.createLeaveType(sampleType);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("responseStatus"));
        assertEquals("สร้างประเภทการลาสำเร็จ", body.get("responseMessage"));
        verify(leaveTypeService).createLeave(any(TypeEntity.class));
    }

    @Test
    void createLeaveType_WithNullType_ShouldReturnBadRequest() {

        ResponseEntity<?> response = leaveTypeController.createLeaveType(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(400, body.get("responseStatus"));
        assertEquals("ข้อมูลประเภทการลาไม่ถูกต้อง", body.get("responseMessage"));
        verify(leaveTypeService, never()).createLeave(any(TypeEntity.class));
    }

    @Test
    void createLeaveType_WithNullLeaveTypeName_ShouldReturnBadRequest() {

        TypeEntity typeWithNullName = new TypeEntity();
        typeWithNullName.setDescription("Test Description");

        ResponseEntity<?> response = leaveTypeController.createLeaveType(typeWithNullName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(400, body.get("responseStatus"));
        assertEquals("กรุณากรอกประเภทการลา", body.get("responseMessage"));
        verify(leaveTypeService, never()).createLeave(any(TypeEntity.class));
    }

    @Test
    void createLeaveType_WhenServiceThrowsException_ShouldReturnInternalServerError() {

        when(leaveTypeService.createLeave(any(TypeEntity.class))).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<?> response = leaveTypeController.createLeaveType(sampleType);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("responseStatus"));
        assertEquals("เกิดข้อผิดพลาดในการสร้างประเภทการลา", body.get("responseMessage"));
        verify(leaveTypeService).createLeave(any(TypeEntity.class));
    }
}
