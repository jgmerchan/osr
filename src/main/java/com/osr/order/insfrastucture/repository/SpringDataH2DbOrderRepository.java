package com.osr.order.insfrastucture.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataH2DbOrderRepository extends JpaRepository<OrderEntity, UUID> {

}
