package Leave_System.Leave_System.spring.Services;

import Leave_System.Leave_System.spring.Entities.RequestEntity;
import Leave_System.Leave_System.spring.Entities.RequestStatus;
import Leave_System.Leave_System.spring.Entities.UserEntity;
import Leave_System.Leave_System.spring.Entities.TypeEntity;
import Leave_System.Leave_System.spring.Repositories.LeaveRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveRequestServiceTest {

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @InjectMocks
    private LeaveRequestService leaveRequestService;

    @Test
    void createRequest_ValidRequest_ShouldCreateSuccessfully() {

        RequestEntity request = createValidRequest();
        when(leaveRequestRepository.save(any(RequestEntity.class))).thenReturn(request);

        RequestEntity result = leaveRequestService.createRequest(request);

        assertNotNull(result);
        assertEquals(RequestStatus.PENDING, result.getStatus());
        verify(leaveRequestRepository).save(any(RequestEntity.class));
    }

    @Test
    void createRequest_NullRequest_ShouldThrowException() {

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> leaveRequestService.createRequest(null));
        assertEquals("Request cannot be null", exception.getMessage());
        verify(leaveRequestRepository, never()).save(any());
    }

    @Test
    void createRequest_NullUser_ShouldThrowException() {

        RequestEntity request = createValidRequest();
        request.setUser(null);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> leaveRequestService.createRequest(request));
        assertEquals("User cannot be null", exception.getMessage());
        verify(leaveRequestRepository, never()).save(any());
    }

    @Test
    void createRequest_NullLeaveType_ShouldThrowException() {

        RequestEntity request = createValidRequest();
        request.setLeaveType(null);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> leaveRequestService.createRequest(request));
        assertEquals("LeaveType cannot be null", exception.getMessage());
        verify(leaveRequestRepository, never()).save(any());
    }

    @Test
    void createRequest_NullDates_ShouldThrowException() {

        RequestEntity request = createValidRequest();
        request.setStartDate(null);
        request.setEndDate(null);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> leaveRequestService.createRequest(request));
        assertEquals("StartDate and EndDate cannot be null", exception.getMessage());
        verify(leaveRequestRepository, never()).save(any());
    }

    @Test
    void createRequest_EndDateBeforeStartDate_ShouldThrowException() {

        RequestEntity request = createValidRequest();
        request.setStartDate(new Date(System.currentTimeMillis() + 86400000));
        request.setEndDate(new Date());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> leaveRequestService.createRequest(request));
        assertEquals("EndDate cannot be before StartDate", exception.getMessage());
        verify(leaveRequestRepository, never()).save(any());
    }

    @Test
    void createRequest_RepositoryException_ShouldThrowRuntimeException() {

        RequestEntity request = createValidRequest();
        when(leaveRequestRepository.save(any())).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class,
                () -> leaveRequestService.createRequest(request));
        assertTrue(exception.getMessage().contains("Error while creating leave request"));
        verify(leaveRequestRepository).save(any());
    }

    @Test
    void findAll_ShouldReturnAllRequests() {

        List<RequestEntity> expectedRequests = Arrays.asList(
                createValidRequest(),
                createValidRequest());
        when(leaveRequestRepository.findAll()).thenReturn(expectedRequests);

        List<RequestEntity> result = leaveRequestService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(leaveRequestRepository).findAll();
    }

    @Test
    void findAll_RepositoryException_ShouldThrowRuntimeException() {

        when(leaveRequestRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class,
                () -> leaveRequestService.findAll());
        assertTrue(exception.getMessage().contains("Error while finding all leave requests"));
        verify(leaveRequestRepository).findAll();
    }

    @Test
    void findById_ExistingId_ShouldReturnRequest() {

        RequestEntity request = createValidRequest();
        when(leaveRequestRepository.findById((int) 1)).thenReturn(Optional.of(request));

        RequestEntity result = leaveRequestService.findById((int) 1);

        assertNotNull(result);
        verify(leaveRequestRepository).findById((int) 1);
    }

    @Test
    void findById_NonExistingId_ShouldThrowException() {

        when(leaveRequestRepository.findById((int) 1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> leaveRequestService.findById((int) 1));
        assertEquals("Leave request not found", exception.getMessage());
        verify(leaveRequestRepository).findById((int) 1);
    }

    @Test
    void createRequest_WithNullStatus_ShouldSetStatusToPending() {

        RequestEntity request = createValidRequest();
        request.setStatus(null);
        when(leaveRequestRepository.save(any(RequestEntity.class))).thenReturn(request);

        RequestEntity result = leaveRequestService.createRequest(request);

        assertNotNull(result);
        assertEquals(RequestStatus.PENDING, result.getStatus());
        verify(leaveRequestRepository).save(request);
    }

    @Test
    void createRequest_WithNonNullStatus_ShouldKeepOriginalStatus() {

        RequestEntity request = createValidRequest();
        request.setStatus(RequestStatus.APPROVED);
        when(leaveRequestRepository.save(any(RequestEntity.class))).thenReturn(request);

        RequestEntity result = leaveRequestService.createRequest(request);

        assertNotNull(result);
        assertEquals(RequestStatus.APPROVED, result.getStatus());
        verify(leaveRequestRepository).save(request);
    }

    @Test
    void updateLeaveStatus_ValidStatus_ShouldUpdateSuccessfully() {

        RequestEntity request = createValidRequest();
        when(leaveRequestRepository.findById((int) 1)).thenReturn(Optional.of(request));
        when(leaveRequestRepository.save(any(RequestEntity.class))).thenReturn(request);

        RequestEntity result = leaveRequestService.updateLeaveStatus((int) 1, "APPROVED");

        assertNotNull(result);
        assertEquals(RequestStatus.APPROVED, result.getStatus());
        verify(leaveRequestRepository).findById((int) 1);
        verify(leaveRequestRepository).save(any(RequestEntity.class));
    }

    @Test
    void updateLeaveStatus_InvalidStatus_ShouldThrowException() {

        RequestEntity request = createValidRequest();
        when(leaveRequestRepository.findById((int) 1)).thenReturn(Optional.of(request));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> leaveRequestService.updateLeaveStatus((int) 1, "INVALID_STATUS"));
        assertTrue(exception.getMessage().contains("Invalid status"));
        verify(leaveRequestRepository).findById((int) 1);
        verify(leaveRequestRepository, never()).save(any());
    }

    @Test
    void updateLeaveStatus_FindByIdError_ShouldThrowRuntimeException() {

        when(leaveRequestRepository.findById(1)).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class,
                () -> leaveRequestService.updateLeaveStatus(1, "APPROVED"));
        assertEquals("Error while finding leave request", exception.getMessage());
        verify(leaveRequestRepository).findById(1);
        verify(leaveRequestRepository, never()).save(any());
    }

    @Test
    void updateLeaveStatus_SaveError_ShouldThrowRuntimeException() {

        RequestEntity request = createValidRequest();
        when(leaveRequestRepository.findById(1)).thenReturn(Optional.of(request));
        when(leaveRequestRepository.save(any())).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class,
                () -> leaveRequestService.updateLeaveStatus(1, "APPROVED"));
        assertEquals("Error while updating leave status", exception.getMessage());
        verify(leaveRequestRepository).findById(1);
        verify(leaveRequestRepository).save(any());
    }

    private RequestEntity createValidRequest() {
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setUsername("testuser");

        TypeEntity leaveType = new TypeEntity();
        leaveType.setId(1);

        RequestEntity request = new RequestEntity();
        request.setUser(user);
        request.setLeaveType(leaveType);
        request.setStartDate(new Date());
        request.setEndDate(new Date(System.currentTimeMillis() + 86400000));
        return request;
    }
}
