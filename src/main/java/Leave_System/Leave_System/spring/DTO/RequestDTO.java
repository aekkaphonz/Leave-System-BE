package Leave_System.Leave_System.spring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    private int userId;
    private int leaveTypeId;
    private String startDate;
    private String endDate;
    private String reason;
}
