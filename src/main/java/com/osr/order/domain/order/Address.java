package com.osr.order.domain.order;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Address {

	private String address;
	private String city;
	private String zipCode;

	public Address(String address, String city, String zipCode) {
		if(addressIsEmpty(address)) {
			throw new IllegalArgumentException("Address must be informed");
		}
		
		if(cityIsEmpty(city)) {
			throw new IllegalArgumentException("City must be informed");
		}
		
		this.address = address;
		this.city = city;
		this.zipCode = zipCode;
	}
	
	private boolean addressIsEmpty(String address) {
		return address.isEmpty();
	}
	
	private boolean cityIsEmpty(String city) {
		return city.isEmpty();
	}
	
	public Address(Address address) {
		this(address.address, address.city, address.city);
	}
	
}
