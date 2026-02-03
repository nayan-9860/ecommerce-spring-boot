package com.nayan.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

        private String fullName;
        private String phone;

        private String line1;
        private String line2;

        private String city;
        private String state;
        private String pinCode;

        private String country;
}
