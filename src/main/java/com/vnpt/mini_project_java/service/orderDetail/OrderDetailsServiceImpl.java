package com.vnpt.mini_project_java.service.orderDetail;

import com.vnpt.mini_project_java.entity.OrderDetail;
import com.vnpt.mini_project_java.respository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailService{
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> findDetailByInvoiceId(long orderDetailID) {
        return orderDetailRepository.findDetailByInvoiceId(orderDetailID);
    }

    @Override
    public <S extends OrderDetail> S save(S entity) {
        return orderDetailRepository.save(entity);
    }
}
