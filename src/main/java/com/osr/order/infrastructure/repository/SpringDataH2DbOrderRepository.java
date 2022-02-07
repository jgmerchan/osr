package com.osr.order.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osr.order.infrastructure.repository.entities.OrderEntity;

@Repository
public interface SpringDataH2DbOrderRepository extends JpaRepository<OrderEntity, UUID> {

}
