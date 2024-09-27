package ku.cs.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@Entity
public class Recipe {
    @Id
    @GeneratedValue
    @Column(name = "r_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "i_id")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "p_id")
    private Product product;

    @Column(name = "qty")
    private int qty;
}
