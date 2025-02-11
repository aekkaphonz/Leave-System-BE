package Leave_System.Leave_System.spring.Services;

import Leave_System.Leave_System.spring.Entities.TypeEntity;
import Leave_System.Leave_System.spring.Repositories.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveTypeService {

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    public TypeEntity createLeave(TypeEntity typeEntity) {
        return leaveTypeRepository.save(typeEntity);
    }
}
