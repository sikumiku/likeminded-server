package com.dev.sigrid.likemindedserver.dto;

import com.dev.sigrid.likemindedserver.domain.Address;
import com.dev.sigrid.likemindedserver.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class AddressDTO {
    @Size(min = 3, max = 45)
    private String addressLine;
    @Size(min = 3, max = 45)
    private String city;
    @Size(min = 5, max = 10)
    private String postcode;
    @Size(min = 3, max = 3)
    private String countrycode;

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
                address.getCountrycode(),
                user
        );
    }
}
