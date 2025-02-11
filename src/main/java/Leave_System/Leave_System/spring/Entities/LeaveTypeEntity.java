package Leave_System.Leave_System.spring.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "leave_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" ,nullable = false, updatable = false)
    private int id;

    @Column(name = "leave_type_name",length = 50)
    private String leaveTypeName;

    @Column(name = "description")
    private String description;

    @Column(name = "max_days")
    private int maxDays;
}
