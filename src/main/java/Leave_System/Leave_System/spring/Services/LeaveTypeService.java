package Leave_System.Leave_System.spring.Services;

import Leave_System.Leave_System.spring.Entities.TypeEntity;
import Leave_System.Leave_System.spring.Entities.UserEntity;
import Leave_System.Leave_System.spring.Repositories.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveTypeService {

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    public TypeEntity createLeave(TypeEntity typeEntity) {
        try {
            if (typeEntity == null) {
                throw new IllegalArgumentException("Type entity cannot be null");
            }
            if (typeEntity.getLeaveTypeName() == null) {
                throw new IllegalArgumentException("Leave type name cannot be null or empty");
            }
            return leaveTypeRepository.save(typeEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving user entity", e);
        }
    }

}
