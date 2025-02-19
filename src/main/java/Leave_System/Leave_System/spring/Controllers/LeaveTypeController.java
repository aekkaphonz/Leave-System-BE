package Leave_System.Leave_System.spring.Controllers;

import Leave_System.Leave_System.spring.Entities.TypeEntity;
import Leave_System.Leave_System.spring.Repositories.LeaveTypeRepository;
import Leave_System.Leave_System.spring.Services.LeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/leave-type")
    public ResponseEntity<?> createLeaveType(@RequestBody TypeEntity typeEntity) {
        if (typeEntity == null || typeEntity.getLeaveTypeName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "responseStatus", 400,
                    "responseMessage", typeEntity == null ? "ข้อมูลประเภทการลาไม่ถูกต้อง" : "กรุณากรอกประเภทการลา"
            ));
        }
        try{
            leaveTypeService.createLeave(typeEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "responseStatus",200,
                    "responseMessage","สร้างประเภทการลาสำเร็จ"
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "responseStatus",500,
                    "responseMessage","เกิดข้อผิดพลาดในระบบ"
            ));
        }
    }

    @GetMapping("/leave-type")
    public List<TypeEntity> findAll() {
        return leaveTypeService.findAll();
    }
}
