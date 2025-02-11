package Leave_System.Leave_System.spring.Repositories;


import Leave_System.Leave_System.spring.Entities.LeaveTypeEntity;
import org.springframework.data.repository.CrudRepository;

public interface LeaveTypeRepository extends CrudRepository<LeaveTypeEntity,Integer> {
}
