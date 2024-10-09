package ku.cs.restaurant.dto.financial;

import lombok.Data;

import java.util.Date;

@Data
public class CreateFinancialRequest {
    private int income;
    private int expense;
}
