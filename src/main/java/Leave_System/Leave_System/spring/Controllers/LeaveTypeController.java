package Leave_System.Leave_System.spring.Controllers;

import Leave_System.Leave_System.spring.Entities.LeaveTypeEntity;
import Leave_System.Leave_System.spring.Repositories.LeaveTypeRepository;
import Leave_System.Leave_System.spring.Services.LeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LeaveTypeController {

    private LeaveTypeService leaveTypeService;
    private LeaveTypeRepository leaveTypeRepository;

    @Autowired
    public LeaveTypeController(LeaveTypeService leaveTypeService, LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeService = leaveTypeService;
        this.leaveTypeRepository = leaveTypeRepository;
    }


    @PostMapping("/leave-requests")
    public LeaveTypeEntity addLeaveType(@RequestBody LeaveTypeEntity leaveTypeEntity) {
        return leaveTypeRepository.save(leaveTypeEntity);
    }

    @GetMapping("leave-requests")
    public List<LeaveTypeEntity> findAll() {
        return (List<LeaveTypeEntity>) leaveTypeRepository.findAll();
    }
}
