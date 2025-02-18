package Leave_System.Leave_System.spring.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name =  "username" ,length = 50)
    private String username;

    @Column(name = "email" ,length = 50)
    private String email;

    @Column(name = "department")
    private String department;
}
