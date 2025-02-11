package Leave_System.Leave_System.spring.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_requests")
public class LeaveRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" ,nullable = false, updatable = false)
    private int id;
}
