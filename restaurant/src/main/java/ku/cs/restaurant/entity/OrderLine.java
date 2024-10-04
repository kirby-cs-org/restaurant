package ku.cs.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class OrderLine {
    @EmbeddedId
    private OrderLineKey id;

    @Column(name = "o_qty")
    private int qty;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "o_id")
    private Orders order;

    @ManyToOne
    @MapsId("foodId")
    @JoinColumn(name = "f_id")
    private Food food;
}
