package Leave_System.Leave_System.spring.Repositories;


import Leave_System.Leave_System.spring.Entities.LeaveTypesEnum;
import Leave_System.Leave_System.spring.Entities.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LeaveTypeRepository extends JpaRepository<TypeEntity,Integer> {

}
