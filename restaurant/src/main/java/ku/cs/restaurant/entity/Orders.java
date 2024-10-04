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

    @Column(name = "o_total")
    private double total;

    @Column(name = "o_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "created_at")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "update_at")
    private LocalDateTime updateTimestamp = LocalDateTime.now();
}
