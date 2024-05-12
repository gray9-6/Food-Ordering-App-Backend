package com.ajay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    User customer;

    @JsonIgnore  // whenever we fetch the order object at that time i don't want the restaurant object inside the order object
    @ManyToOne   // one restaurant can have many orders, but order will belongs to ane address only
    Restaurant restaurant;

    Long totalAmount;

    String orderStatus;

    Date createdAt;

    @ManyToOne  // one address can have many orders, but one order belongs to  one address only
    Address deliveryAddress;

    @OneToMany
    List<OrderItem> orderItems;

//    Payment payment;

    int totalItems;

    int totalPrice;
}
