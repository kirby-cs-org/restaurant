package ku.cs.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "p_id")
    private UUID id;

    @Column(name = "p_name")
    private String name;

    @Column(name = "p_price")
    private double price;

    @Column(name = "p_qty")
    private int qty;

    @Enumerated(EnumType.STRING)
    @Column(name = "p_status")
    private ProductStatus status;
}

