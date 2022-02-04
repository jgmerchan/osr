package com.osr.order.insfrastucture.repository;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
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
@Table(name = "order_items")
public class OrderItemEntity implements Persistable<UUID> {

	@Id
	private UUID id;
	private UUID orderId;
	private UUID productId;
	private String description;
	private BigDecimal unitPriceValue;
	private String unitPriceCurrencyCode;
	private Integer quantity;

	@Transient
	private boolean isNew = true;

	@Override
	public boolean isNew() {
		return isNew;
	}

	@PostLoad
	@PrePersist
	void trackNotNew() {
		this.isNew = false;
	}

}
