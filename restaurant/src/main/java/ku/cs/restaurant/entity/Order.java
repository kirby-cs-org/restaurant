package ku.cs.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import ku.cs.restaurant.entity.OrderStatus;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity 
@Data
public class Order {
    @Id 
    @GeneratedValue 
    @JdbcTypeCode(SqlTypes.VARCHAR) 
    @Column(name = "o_id")
    private UUID id;

    @Column(name = "o_qty")
    private int qty;

    @Column(name = "o_status")
    private OrderStatus status;

    @Column(name = "o_timestamp")
    private LocalDateTime timestamp;
}
