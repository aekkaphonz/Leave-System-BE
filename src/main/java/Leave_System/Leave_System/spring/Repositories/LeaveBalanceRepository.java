package Leave_System.Leave_System.spring.Repositories;

import Leave_System.Leave_System.spring.Entities.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveBalanceRepository extends JpaRepository<BalanceEntity, Integer> {
}
