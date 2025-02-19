package Leave_System.Leave_System.spring.Services;

import Leave_System.Leave_System.spring.Entities.RequestEntity;
import Leave_System.Leave_System.spring.Entities.RequestStatus;
import Leave_System.Leave_System.spring.Repositories.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;

    @Autowired
    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    public RequestEntity createRequest(RequestEntity requestEntity) {
        validateRequest(requestEntity);

        if (requestEntity.getStatus() == null) {
            requestEntity.setStatus(RequestStatus.PENDING);
        }

        try {
            return leaveRequestRepository.save(requestEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating leave request", e);
        }
    }

    private void validateRequest(RequestEntity requestEntity) {
        if (requestEntity == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        if (requestEntity.getUser() == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (requestEntity.getLeaveType() == null) {
            throw new IllegalArgumentException("LeaveType cannot be null");
        }
        if (requestEntity.getStartDate() == null || requestEntity.getEndDate() == null) {
            throw new IllegalArgumentException("StartDate and EndDate cannot be null");
        }

        LocalDate startDate = requestEntity.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = requestEntity.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("EndDate cannot be before StartDate");
        }
    }

    public List<RequestEntity> findAll() {
        try {
            return leaveRequestRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error while finding all leave requests", e);
        }
    }

    public RequestEntity findById(int id) {
        try {
            return leaveRequestRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Leave request not found"));
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Leave request not found")) {
                throw e;
            }
            throw new RuntimeException("Error while finding leave request", e);
        }
    }

    public RequestEntity updateLeaveStatus(int id, String status) {
        RequestEntity leaveRequest;
        try {
            leaveRequest = findById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while finding leave request", e);
        }

        RequestStatus newStatus;
        try {
            newStatus = RequestStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        leaveRequest.setStatus(newStatus);
        try {
            return leaveRequestRepository.save(leaveRequest);
        } catch (Exception e) {
            throw new RuntimeException("Error while updating leave status", e);
        }
    }
}