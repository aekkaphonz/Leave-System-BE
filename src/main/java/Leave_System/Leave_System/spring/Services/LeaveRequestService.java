package Leave_System.Leave_System.spring.Services;

import Leave_System.Leave_System.spring.Entities.RequestEntity;
import Leave_System.Leave_System.spring.Repositories.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public RequestEntity createRequest(RequestEntity requestEntity) {
        return leaveRequestRepository.save(requestEntity);
    }

    public List<RequestEntity> findAll() {
        return leaveRequestRepository.findAll();
    }

}
