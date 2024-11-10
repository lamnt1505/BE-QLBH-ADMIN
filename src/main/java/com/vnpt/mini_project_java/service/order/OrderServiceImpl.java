package com.vnpt.mini_project_java.service.order;

import com.vnpt.mini_project_java.entity.Order;
import com.vnpt.mini_project_java.entity.Storage;
import com.vnpt.mini_project_java.respository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    public final OrderRepository orderRepository;

    @Override
    public void deleteById(Long orderId) {
        orderRepository.deleteById(orderId);
    }
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> listInvoiceByAccount(long orderID) {
        return orderRepository.findOrderByAccount(orderID);
    }

    @Override
    public <S extends Order> S save(S entity) {
        return orderRepository.save(entity);
    }
    @Override
    public List<Order> listOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long orderID) {
        return orderRepository.findById(orderID).orElse(null);
    }

    @Override
    public void delete(Order entity) {
        orderRepository.delete(entity);
    }

}
