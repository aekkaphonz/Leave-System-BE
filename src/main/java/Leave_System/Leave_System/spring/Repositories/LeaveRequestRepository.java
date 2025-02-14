package Leave_System.Leave_System.spring.Repositories;


import Leave_System.Leave_System.spring.Entities.RequestEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(exported = false)
public interface LeaveRequestRepository extends JpaRepository<RequestEntity,Integer> {

}
