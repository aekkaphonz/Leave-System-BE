package Leave_System.Leave_System.spring.Controllers;

import Leave_System.Leave_System.spring.Entities.TypeEntity;
import Leave_System.Leave_System.spring.Repositories.LeaveTypeRepository;
import Leave_System.Leave_System.spring.Services.LeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;
    private final LeaveTypeRepository leaveTypeRepository;

    @Autowired
    public LeaveTypeController(LeaveTypeService leaveTypeService, LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeService = leaveTypeService;
        this.leaveTypeRepository = leaveTypeRepository;
    }


    @PostMapping("/leave-tpye")
    public TypeEntity addLeaveType(@RequestBody TypeEntity typeEntity) {
        return leaveTypeRepository.save(typeEntity);
    }

    @GetMapping("leave-type")
    public List<TypeEntity> findAll() {
        return (List<TypeEntity>) leaveTypeRepository.findAll();
    }
}
