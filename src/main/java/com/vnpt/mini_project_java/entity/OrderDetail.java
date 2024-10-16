package com.vnpt.mini_project_java.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailID;
    
    private int amount;
    
    private Double price;

    @OneToOne
    @JoinColumn(name = "productID", insertable = true, updatable = true)
    private Product product;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;
    
    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailID=" + orderDetailID +
                ", amount=" + amount +
                ", price=" + price +
                ", product=" + product +
                ", order=" + order +
                '}';
    }
}
