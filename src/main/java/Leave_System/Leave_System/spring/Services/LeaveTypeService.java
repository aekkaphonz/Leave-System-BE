package Leave_System.Leave_System.spring.Services;

import Leave_System.Leave_System.spring.Entities.TypeEntity;
import Leave_System.Leave_System.spring.Repositories.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;

    @Autowired
    public LeaveTypeService(LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
    }

    public TypeEntity createLeave(TypeEntity typeEntity) {
        if (typeEntity == null) {
            throw new IllegalArgumentException("Type entity cannot be null");
        }
        if (typeEntity.getLeaveTypeName() == null) {
            throw new IllegalArgumentException("Leave type name cannot be null or empty");
        }
        try {
            return leaveTypeRepository.save(typeEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving leave type entity", e);
        }
    }

    public List<TypeEntity> findAll() {
        try {
            return leaveTypeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching leave types", e);
        }
    }
}
