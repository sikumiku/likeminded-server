package com.dev.sigrid.likemindedserver.dto;

import com.dev.sigrid.likemindedserver.domain.Address;
import com.dev.sigrid.likemindedserver.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDTO {

    private String addressLine;
    private String city;
    private String postcode;
    private String country;

    public static AddressDTO domainToDto(Address address) {
        return new AddressDTO(
                address.getAddressLine(),
                address.getCity(),
                address.getPostcode(),
                address.getCountrycode()
        );
    }

    public static Address dtoToDomain(AddressDTO address, User user) {
        return new Address(
                address.getAddressLine(),
                address.getCity(),
                address.getPostcode(),
                address.getCountry(),
                user
        );
    }
}
