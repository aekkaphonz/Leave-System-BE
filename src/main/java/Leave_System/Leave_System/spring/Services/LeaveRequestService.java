package Leave_System.Leave_System.spring.Services;


import Leave_System.Leave_System.spring.Entities.RequestEntity;
import Leave_System.Leave_System.spring.Entities.RequestStatus;
import Leave_System.Leave_System.spring.Repositories.LeaveRequestRepository;
import Leave_System.Leave_System.spring.Repositories.LeaveTypeRepository;
import Leave_System.Leave_System.spring.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;

import java.util.List;


@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    private UserRepository userRepository;
    private LeaveTypeRepository leaveTypeRepository;


    public RequestEntity createRequest(RequestEntity requestEntity) {
        try {
            if (requestEntity == null || requestEntity.getUser() == null) {
                throw new IllegalArgumentException("Request or User cannot be null");
            }
            if (requestEntity.getLeaveType() == null) {
                throw new IllegalArgumentException("Request or LeaveType cannot be null");
            }
            if (requestEntity.getStartDate() == null || requestEntity.getEndDate() == null) {
                throw new IllegalArgumentException("StartDate and EndDate cannot be null");
            }
            LocalDate startDate = requestEntity.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = requestEntity.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (endDate.isBefore(startDate)) {
                throw new IllegalArgumentException("EndDate cannot be before StartDate");
            }

            if (requestEntity.getStatus() == null) {
                requestEntity.setStatus(RequestStatus.PENDING);
            }

            return leaveRequestRepository.save(requestEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving request entity", e);
        }
    }

    public List<RequestEntity> findAll() {
        return leaveRequestRepository.findAll();
    }

    public RequestEntity findById(int id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
    }


    public RequestEntity updateLeaveStatus(int id, String status) {
        RequestEntity leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found with id: " + id));

        try {
            RequestStatus newStatus = RequestStatus.valueOf(status.toUpperCase());
            leaveRequest.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }

        return leaveRequestRepository.save(leaveRequest);
    }

}
