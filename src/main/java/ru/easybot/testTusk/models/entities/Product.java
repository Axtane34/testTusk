package ru.easybot.testTusk.models.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull(message = "product should contain serial number")
    @Column(name = "serialNumber")
    private String serialNumber;
    @NotNull(message = "product should contain vendor")
    @Column(name = "vendor")
    private String vendor;
    @Positive(message = "price should be greater then 0")
    @Digits(message = "field contains only digits with fraction 2", integer = 20, fraction = 2)
    @Column(name = "price")
    private BigDecimal price;
    @Positive(message = "quantity should be greater then 0")
    @Column(name = "quantity")
    private Long quantity;
}