package Leave_System.Leave_System.spring.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "leave_balances")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "leave_type_id")
    private TypeEntity leaveType;

    @Column(name = "leave_year")
    private int leaveYear;

    @Column(name = "remaining_days")
    private int remainingDays;
}
