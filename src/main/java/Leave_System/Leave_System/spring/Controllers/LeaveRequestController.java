package Leave_System.Leave_System.spring.Controllers;

import Leave_System.Leave_System.spring.Entities.RequestEntity;
import Leave_System.Leave_System.spring.Repositories.LeaveTypeRepository;
import Leave_System.Leave_System.spring.Repositories.UserRepository;
import Leave_System.Leave_System.spring.Services.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;
    private final RequestEntity requestEntity;



    @Autowired
    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
        this.requestEntity = new RequestEntity();
    }
     private UserRepository userRepository;
     private LeaveTypeRepository leaveTypeRepository;

    @PostMapping("/leave-requests")
    public RequestEntity createRequest(@RequestBody RequestEntity requestEntity) {
        return leaveRequestService.createRequest(requestEntity);
    }

    @GetMapping("/leave-requests")
    public List<RequestEntity> findAll() {
        return leaveRequestService.findAll();
    }

    @GetMapping("/leave-requests/{id}")
    public ResponseEntity<RequestEntity> findByRequestId(@PathVariable("id") int id) {
        RequestEntity request = leaveRequestService.findById(id);
        return ResponseEntity.ok(request);
    }




    @PutMapping("/leave-requests/{id}")
    public ResponseEntity<RequestEntity> updateStatus(@PathVariable int id, @RequestBody Map<String, String> request) {
        String status = request.get("status"); // ดึงค่าจาก JSON

        if (status == null) {
            return ResponseEntity.badRequest().body(null);
        }

        RequestEntity updatedRequest = leaveRequestService.updateLeaveStatus(id, status);
        return ResponseEntity.ok(updatedRequest);
    }


}
