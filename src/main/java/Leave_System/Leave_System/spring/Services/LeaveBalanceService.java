package Leave_System.Leave_System.spring.Services;

import Leave_System.Leave_System.spring.Entities.BalanceEntity;
import Leave_System.Leave_System.spring.Repositories.LeaveBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveBalanceService {

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;


    public BalanceEntity addBalance(BalanceEntity balance) {
        return leaveBalanceRepository.save(balance);
    }
}
