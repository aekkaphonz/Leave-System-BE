package Leave_System.Leave_System.spring.Repositories;

import Leave_System.Leave_System.spring.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository  extends JpaRepository<UserEntity, Integer> {
}
