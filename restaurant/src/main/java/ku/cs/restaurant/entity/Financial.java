package ku.cs.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Financial {
    @Id
    private Date date;

    private double income;
    private double expense;
    private double total;
}
