package com.osr.order.infrastructure.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity implements Persistable<UUID> {

	@Id
	private UUID id;
	private String status;
	private String paymentMethod;
	private Date date;
	private Date modified;
	private String address;
	private String city;
	private String zipCode;

	@OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL)
	private List<OrderItemEntity> orderItems;

	@Transient
	private boolean isNew = true;

	@Override
	public boolean isNew() {
		return isNew;
	}

}
