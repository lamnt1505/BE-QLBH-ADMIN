package com.vnpt.mini_project_java.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "order_info")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderID;

    @Column(name = "order_import")
    private LocalDate orderDateImport;

    @Column(name = "status", columnDefinition = "nvarchar(50)")
    private String status;

    @Column(name = "order_total")
    private double orderTotal;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @JsonManagedReference
    @OneToMany(mappedBy ="order" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails = new HashSet<>();

    public Account getVendor(){
        return account;
    }

    public void setVendor(Account account){
        this.account = account;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", orderDateImport=" + orderDateImport +
                ", orderTotal=" + orderTotal +
                ", account=" + account +
                ", orderDetails=" + orderDetails +
                '}';
    }

    public enum UpdateStatus{
        SUCCESS,
        ORDERID_NOT_FOUND,
        INVALID_ORDERID
    }

}
