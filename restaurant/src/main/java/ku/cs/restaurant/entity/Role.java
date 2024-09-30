package ku.cs.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 20)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}
