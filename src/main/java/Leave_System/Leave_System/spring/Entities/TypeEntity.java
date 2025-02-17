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
public class TypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type_name")
    private LeaveTypesEnum leaveTypeName;

    @Column(name = "description")
    private String description;

    @Column(name = "max_days")
    private int maxDays;
}
