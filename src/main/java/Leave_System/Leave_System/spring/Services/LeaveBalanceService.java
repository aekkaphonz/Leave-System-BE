package Leave_System.Leave_System.spring.Services;

import Leave_System.Leave_System.spring.Entities.BalanceEntity;
import Leave_System.Leave_System.spring.Repositories.LeaveBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveBalanceService {

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;


    public BalanceEntity createBalance(BalanceEntity balance) {
        try {
            if (balance == null) {
                throw new IllegalArgumentException("Balance cannot be null");
            }
            if (balance.getUser() == null ) {
                throw new IllegalArgumentException("User cannot be null");
            }
            if (balance.getLeaveType() == null ) {
                throw new IllegalArgumentException("LeaveType cannot be null");
            }
            if (balance.getLeaveYear() == 0 ){
                throw new IllegalArgumentException("LeaveYear cannot be zero");
            }
            if (balance.getRemainingDays()==0){
                throw new IllegalArgumentException("RemainingDays cannot be zero");
            }
            return leaveBalanceRepository.save(balance);
        }catch (Exception e){
            throw new RuntimeException("Error creating balance", e);
        }

    }
}
