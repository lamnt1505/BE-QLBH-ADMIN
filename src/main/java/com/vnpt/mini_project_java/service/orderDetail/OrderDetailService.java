package com.vnpt.mini_project_java.service.orderDetail;

import com.vnpt.mini_project_java.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> findDetailByInvoiceId(long orderDetailID);

    <S extends OrderDetail> S save(S entity);
}
