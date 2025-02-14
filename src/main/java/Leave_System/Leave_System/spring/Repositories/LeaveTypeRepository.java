package Leave_System.Leave_System.spring.Repositories;


import Leave_System.Leave_System.spring.Entities.LeaveTypesEnum;
import Leave_System.Leave_System.spring.Entities.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource(exported = false)
public interface LeaveTypeRepository extends JpaRepository<TypeEntity,Integer> {

}
