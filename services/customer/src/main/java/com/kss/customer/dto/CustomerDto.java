package com.kss.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private AddressDto address;
}
