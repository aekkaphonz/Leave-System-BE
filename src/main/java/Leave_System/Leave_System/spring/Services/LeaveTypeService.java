package Leave_System.Leave_System.spring.Services;

import Leave_System.Leave_System.spring.Entities.LeaveTypeEntity;
import Leave_System.Leave_System.spring.Repositories.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveTypeService {

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    public LeaveTypeEntity createLeave(LeaveTypeEntity leaveTypeEntity) {
        return leaveTypeRepository.save(leaveTypeEntity);
    }
}
