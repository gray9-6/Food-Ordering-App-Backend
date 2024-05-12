package com.ajay.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    User customer;

    Long total;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    List<CartItem> cartItems = new ArrayList<>();


}
