package ku.cs.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity 
@Data
public class Orders {
    @Id 
    @GeneratedValue 
    @JdbcTypeCode(SqlTypes.VARCHAR) 
    @Column(name = "o_id")
    private UUID id;

    @Column(name = "o_qty")
    private int qty;

    @Column(name = "o_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "o_timestamp")
    private LocalDateTime timestamp;
}
