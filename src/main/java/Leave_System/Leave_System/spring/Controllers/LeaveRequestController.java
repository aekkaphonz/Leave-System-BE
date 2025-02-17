package Leave_System.Leave_System.spring.Controllers;

import Leave_System.Leave_System.spring.DTO.RequestDTO;
import Leave_System.Leave_System.spring.Entities.RequestEntity;
import Leave_System.Leave_System.spring.Repositories.LeaveTypeRepository;
import Leave_System.Leave_System.spring.Repositories.UserRepository;
import Leave_System.Leave_System.spring.Services.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
     private  RequestDTO requestDTO;

//    @PostMapping("/leave-requests")
//    public ResponseEntity<?> createRequest(@RequestBody RequestEntity requestEntity) {
//        if (requestEntity == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
//                    "responseStatus", 400,
//                    "responseMessage", "Request cannot be null"
//            ));
//        }
//
//        try {
//            leaveRequestService.createRequest(requestEntity);
//            return ResponseEntity.ok(Map.of(
//                    "responseStatus", 200,
//                    "responseMessage", "ส่งข้อมูลแบบฟอร์มขอลางานเรียบร้อย"
//            ));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
//                    "responseStatus", 400,
//                    "responseMessage", e.getMessage()
//            ));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
//                    "responseStatus", 500,
//                    "responseMessage", "เกิดข้อผิดพลาดในระบบ"
//            ));
//        }
//    }



    @PostMapping("/leave-requests")
    public ResponseEntity<?> createRequest(@RequestBody RequestEntity requestEntity) {
        if (requestEntity == null || requestEntity.getUser() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "responseStatus", 400,
                    "responseMessage", "Request cannot be null"
            ));
        }

        try {
            leaveRequestService.createRequest(requestEntity);
            return ResponseEntity.ok(Map.of(
                    "responseStatus", 200,
                    "responseMessage", "ส่งข้อมูลแบบฟอร์มขอลางานเรียบร้อย"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "responseStatus", 500,
                    "responseMessage", "เกิดข้อผิดพลาดในระบบ"
            ));
        }
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
        String status = request.get("status");

        if (status == null) {
            return ResponseEntity.badRequest().body(null);
        }

        RequestEntity updatedRequest = leaveRequestService.updateLeaveStatus(id, status);
        return ResponseEntity.ok(updatedRequest);
    }


}
