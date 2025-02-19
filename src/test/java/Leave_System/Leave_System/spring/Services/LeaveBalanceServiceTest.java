package Leave_System.Leave_System.spring.Services;

import Leave_System.Leave_System.spring.Entities.BalanceEntity;
import Leave_System.Leave_System.spring.Entities.TypeEntity;
import Leave_System.Leave_System.spring.Entities.UserEntity;
import Leave_System.Leave_System.spring.Repositories.LeaveBalanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveBalanceServiceTest {

    @Mock
    private LeaveBalanceRepository leaveBalanceRepository;

    @InjectMocks
    private LeaveBalanceService leaveBalanceService;

    @Test
    void createBalance_ValidBalance_ShouldCreateSuccessfully() {

        BalanceEntity balance = createValidBalance();
        when(leaveBalanceRepository.save(any(BalanceEntity.class))).thenReturn(balance);

        BalanceEntity result = leaveBalanceService.createBalance(balance);

        assertNotNull(result);
        assertEquals(10, result.getRemainingDays());
        assertEquals(2025, result.getLeaveYear());
        verify(leaveBalanceRepository).save(any(BalanceEntity.class));
    }

    @Test
    void createBalance_NullBalance_ShouldThrowException() {

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> leaveBalanceService.createBalance(null));
        assertEquals("Balance cannot be null", exception.getMessage());
        verify(leaveBalanceRepository, never()).save(any());
    }

    @Test
    void createBalance_NullUser_ShouldThrowException() {

        BalanceEntity balance = createValidBalance();
        balance.setUser(null);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> leaveBalanceService.createBalance(balance));
        assertEquals("User cannot be null", exception.getMessage());
        verify(leaveBalanceRepository, never()).save(any());
    }

    @Test
    void createBalance_NullLeaveType_ShouldThrowException() {

        BalanceEntity balance = createValidBalance();
        balance.setLeaveType(null);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> leaveBalanceService.createBalance(balance));
        assertEquals("LeaveType cannot be null", exception.getMessage());
        verify(leaveBalanceRepository, never()).save(any());
    }

    @Test
    void createBalance_ZeroLeaveYear_ShouldThrowException() {

        BalanceEntity balance = createValidBalance();
        balance.setLeaveYear(0);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> leaveBalanceService.createBalance(balance));
        assertEquals("LeaveYear cannot be zero", exception.getMessage());
        verify(leaveBalanceRepository, never()).save(any());
    }

    @Test
    void createBalance_ZeroRemainingDays_ShouldThrowException() {

        BalanceEntity balance = createValidBalance();
        balance.setRemainingDays(0);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> leaveBalanceService.createBalance(balance));
        assertEquals("RemainingDays cannot be zero", exception.getMessage());
        verify(leaveBalanceRepository, never()).save(any());
    }

    @Test
    void createBalance_RepositoryException_ShouldThrowRuntimeException() {

        BalanceEntity balance = createValidBalance();
        when(leaveBalanceRepository.save(any())).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class,
                () -> leaveBalanceService.createBalance(balance));
        assertTrue(exception.getMessage().contains("Error creating balance"));
        verify(leaveBalanceRepository).save(any());
    }

    private BalanceEntity createValidBalance() {
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setUsername("testuser");

        TypeEntity leaveType = new TypeEntity();
        leaveType.setId(1);

        BalanceEntity balance = new BalanceEntity();
        balance.setUser(user);
        balance.setLeaveType(leaveType);
        balance.setLeaveYear(2025);
        balance.setRemainingDays(10);
        return balance;
    }
}
