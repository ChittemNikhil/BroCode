package org.brocode.orderservice.repository;

import org.brocode.orderservice.model.Order;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {


    List<Order> findAllByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
