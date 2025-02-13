package Leave_System.Leave_System.spring.Controllers;

import Leave_System.Leave_System.spring.Entities.BalanceEntity;


import Leave_System.Leave_System.spring.Repositories.LeaveBalanceRepository;
import Leave_System.Leave_System.spring.Services.LeaveBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LeaveBalanceController {

    private LeaveBalanceService leaveBalanceService;
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    public LeaveBalanceController(LeaveBalanceService leaveBalanceService, LeaveBalanceRepository leaveBalanceRepository) {
        this.leaveBalanceService = leaveBalanceService;
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    @PostMapping("leave-balance")
    public BalanceEntity addBalance(@RequestBody BalanceEntity balanceEntity) {
        return leaveBalanceService.addBalance(balanceEntity);
    }

    @GetMapping("leave-balance")
    public List<BalanceEntity> findAll() {
        return (List<BalanceEntity>)leaveBalanceRepository.findAll();
    }
}
