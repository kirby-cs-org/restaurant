package ku.cs.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "t_id")
    private UUID id;

    @Column(name = "t_total")
    private double total;

    @Column(name = "qr_image")
    private String img_src;

    @Column(name = "created_at")
    private LocalDateTime timestamp = LocalDateTime.now();
}