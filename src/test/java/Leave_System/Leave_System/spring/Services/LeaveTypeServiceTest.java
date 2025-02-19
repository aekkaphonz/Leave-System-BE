package Leave_System.Leave_System.spring.Services;

import Leave_System.Leave_System.spring.Entities.TypeEntity;
import Leave_System.Leave_System.spring.Entities.LeaveTypesEnum;
import Leave_System.Leave_System.spring.Repositories.LeaveTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveTypeServiceTest {

    @Mock
    private LeaveTypeRepository leaveTypeRepository;

    @InjectMocks
    private LeaveTypeService leaveTypeService;

    @Test
    void createLeave_ValidType_ShouldCreateSuccessfully() {
       
        TypeEntity leaveType = createLeaveType(LeaveTypesEnum.ANNUAL_LEAVE, "Annual Leave Description", 10);
        when(leaveTypeRepository.save(any(TypeEntity.class))).thenReturn(leaveType);

      
        TypeEntity result = leaveTypeService.createLeave(leaveType);

        
        assertNotNull(result);
        assertEquals(LeaveTypesEnum.ANNUAL_LEAVE, result.getLeaveTypeName());
        assertEquals("Annual Leave Description", result.getDescription());
        assertEquals(10, result.getMaxDays());
        verify(leaveTypeRepository).save(any(TypeEntity.class));
    }

    @Test
    void createLeave_NullType_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> leaveTypeService.createLeave(null));
        verify(leaveTypeRepository, never()).save(any());
    }

    @Test
    void createLeave_NullTypeName_ShouldThrowException() {
        
        TypeEntity leaveType = new TypeEntity();
        leaveType.setLeaveTypeName(null);
        leaveType.setDescription("Test Description");
        leaveType.setMaxDays(10);

   
        assertThrows(IllegalArgumentException.class, () -> leaveTypeService.createLeave(leaveType));
        verify(leaveTypeRepository, never()).save(any());
    }

    @Test
    void createLeave_RepositoryException_ShouldThrowRuntimeException() {
        
        TypeEntity leaveType = createLeaveType(LeaveTypesEnum.ANNUAL_LEAVE, "Annual Leave Description", 10);
        when(leaveTypeRepository.save(any())).thenThrow(new RuntimeException("Database error"));

       
        Exception exception = assertThrows(RuntimeException.class, () -> leaveTypeService.createLeave(leaveType));
        assertTrue(exception.getMessage().contains("Error while saving leave type entity"));
        verify(leaveTypeRepository).save(any());
    }

    @Test
    void findAll_ShouldReturnAllLeaveTypes() {
     
        List<TypeEntity> expectedTypes = Arrays.asList(
            createLeaveType(LeaveTypesEnum.ANNUAL_LEAVE, "Annual Leave Description", 10),
            createLeaveType(LeaveTypesEnum.SICK_LEAVE, "Sick Leave Description", 30)
        );
        when(leaveTypeRepository.findAll()).thenReturn(expectedTypes);

      
        List<TypeEntity> result = leaveTypeService.findAll();

   
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(LeaveTypesEnum.ANNUAL_LEAVE, result.get(0).getLeaveTypeName());
        assertEquals(LeaveTypesEnum.SICK_LEAVE, result.get(1).getLeaveTypeName());
        verify(leaveTypeRepository).findAll();
    }

    @Test
    void findAll_RepositoryException_ShouldThrowRuntimeException() {
       
        when(leaveTypeRepository.findAll()).thenThrow(new RuntimeException("Database error"));

    
        Exception exception = assertThrows(RuntimeException.class, () -> leaveTypeService.findAll());
        assertTrue(exception.getMessage().contains("Error while fetching leave types"));
        verify(leaveTypeRepository).findAll();
    }

    private TypeEntity createLeaveType(LeaveTypesEnum type, String description, int maxDays) {
        TypeEntity entity = new TypeEntity();
        entity.setLeaveTypeName(type);
        entity.setDescription(description);
        entity.setMaxDays(maxDays);
        return entity;
    }
}
