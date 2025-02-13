package Leave_System.Leave_System.spring.Repositories;


import Leave_System.Leave_System.spring.Entities.RequestEntity;

import org.springframework.data.jpa.repository.JpaRepository;


public interface LeaveRequestRepository extends JpaRepository<RequestEntity,Integer> {

}
