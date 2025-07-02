package com.vnpt.mini_project_java.service.order;

import com.vnpt.mini_project_java.entity.Order;
import com.vnpt.mini_project_java.projections.StatisticalProductProjections;
//import com.vnpt.mini_project_java.models.StatisticalForProductProjections;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void deleteById(Long orderId);

    List<Order> listInvoiceByAccount(long orderID);

    <S extends Order> S save(S entity);

    List<Order> listOrder();

    Order findById(Long orderId);

    void delete(Order entity);

    Optional<Order> findByOrderCode(String code);
}
