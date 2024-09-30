package ku.cs.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Food {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "f_id")
    private UUID id;

    @Column(name = "f_image")
    private String imagePath;

    @Column(name = "f_name")
    private String name;

    @Column(name = "f_price")
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "f_status")
    private Status status;

    @OneToMany(mappedBy = "food")
    private List<Recipe> recipes = new ArrayList<>();
}

