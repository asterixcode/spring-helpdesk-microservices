package com.asterixcode.orderserviceapi.repository;

import com.asterixcode.orderserviceapi.entity.Order;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository
    extends ListCrudRepository<Order, Long>, ListPagingAndSortingRepository<Order, Long> {}
