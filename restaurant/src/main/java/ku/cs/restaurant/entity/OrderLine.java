package ku.cs.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderLine {

    @EmbeddedId
    private OrderLineKey id;

    @Column(name = "o_qty")
    private int o_qty;

    @Column(name = "total_each")
    private double total_each;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "o_id")
    private Orders order;

    @ManyToOne
    @MapsId("foodId")
    @JoinColumn(name = "f_id")
    private Food food;

}
