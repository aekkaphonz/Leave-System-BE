package Leave_System.Leave_System.spring.Controllers;

import Leave_System.Leave_System.spring.Entities.RequestEntity;
import Leave_System.Leave_System.spring.Services.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/leave-requests")
    public RequestEntity createRequest(@RequestBody RequestEntity requestEntity) {
        return leaveRequestService.createRequest(requestEntity);
    }

    @GetMapping("/leave-requests")
    public List<RequestEntity> findAll() {
        return leaveRequestService.findAll();
    }
}
