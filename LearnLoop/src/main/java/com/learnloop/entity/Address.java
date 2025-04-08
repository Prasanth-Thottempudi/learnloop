package com.learnloop.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "address_data")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addId;

    @Column(nullable = false)
    @NotBlank(message = "Street cannot be blank")
    @Size(max = 100, message = "Street can be at most 100 characters")
    private String street;

    @Column(nullable = false)
    @NotBlank(message = "City cannot be blank")
    @Size(max = 50, message = "City can be at most 50 characters")
    private String city;

    @Column(nullable = false)
    @NotBlank(message = "State cannot be blank")
    @Size(max = 50, message = "State can be at most 50 characters")
    private String state;

    @Column(nullable = false, name = "postal_code")
    @NotBlank(message = "Postal code cannot be blank")
    @Pattern(regexp = "^[0-9]{5,6}$", message = "Postal code must be 5 or 6 digits")
    private String postalCode;

    @Column(nullable = false)
    @NotBlank(message = "Country cannot be blank")
    @Size(max = 50, message = "Country can be at most 50 characters")
    private String country;
}
