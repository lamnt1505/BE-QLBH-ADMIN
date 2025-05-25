package com.vnpt.mini_project_java.respository;


import com.vnpt.mini_project_java.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    @Query(value = "select * from order_detail where order_id = ?", nativeQuery = true)
    List<OrderDetail> findDetailByInvoiceId(long orderDetailID);
}
