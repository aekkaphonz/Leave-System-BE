package Leave_System.Leave_System.spring.Controllers;

import Leave_System.Leave_System.spring.Entities.BalanceEntity;


import Leave_System.Leave_System.spring.Repositories.LeaveBalanceRepository;
import Leave_System.Leave_System.spring.Services.LeaveBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/leave-balance")
    public ResponseEntity<?> createBalance(@RequestBody BalanceEntity balanceEntity) {
        if (balanceEntity == null || balanceEntity.getUser() ==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "responseStatus", 400,
                    "responseMessage","ยอดวันลาคงเหลือไม่สามารถเป็นค่าว่างได้"
            ));
        }

        try{
            leaveBalanceService.createBalance(balanceEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "responseStatus",200
                    ,"responseMessage","สร้างยอดวันลาคงเหลือสำเร็จ"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "responseStatus",500,
                    "responseMessage","เกิดข้อผิดพลาดในระบบ"
            ));
        }
    }


    @GetMapping("/leave-balance")
    public List<BalanceEntity> findAll() {
        return (List<BalanceEntity>)leaveBalanceRepository.findAll();
    }
}


