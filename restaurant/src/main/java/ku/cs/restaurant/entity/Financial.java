package ku.cs.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Financial {
    @Id
    @Temporal(TemporalType.DATE)
    private Date date;

    private double income;
    private double expense;
    private double total;
}
