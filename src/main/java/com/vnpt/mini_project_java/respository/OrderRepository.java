package com.vnpt.mini_project_java.respository;

import com.vnpt.mini_project_java.entity.Order;
import com.vnpt.mini_project_java.projections.StatisticalForMonthProjections;
import com.vnpt.mini_project_java.projections.StatisticalForQuarterProjections;
import com.vnpt.mini_project_java.projections.StatisticalForYearProjections;
import com.vnpt.mini_project_java.projections.StatisticalProductProjections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    String STATICTICAL_FOR_PRODUCT_QUERY = "SELECT od.productid AS id," +
            " p.product_name AS name," +
            " SUM(od.amount) AS quantitysold," +
            " SUM(o.order_total) AS total" +
            " FROM order_info o " +
            " JOIN order_detail od ON o.order_id = od.order_id" +
            " JOIN product p ON p.product_id = od.productid" +
            " GROUP BY od.productid";

    String STATICTICAL_FOR_YEAR_QUERY = "SELECT YEAR(order_import) AS year," +
            " COUNT(order_info.order_id) AS orderCount," +
            " SUM(order_total) AS total," +
            " MIN(order_total) AS minTotal," +
            " MAX(order_total) AS maxTotal" +
            " FROM order_info " +
            " JOIN " +
                "order_detail ON order_info.order_id = order_detail.order_id" +
            " GROUP BY " +
                "YEAR(order_import)";

    String STATISCAL_FOR_MONTH_QUERY = "SELECT MONTH(order_import) AS month, YEAR(order_import) AS year," +
            " COUNT(order_info.order_id) AS orderCount," +
            " SUM(order_total) AS total," +
            " MIN(order_total) AS minTotal," +
            " MAX(order_total) AS maxTotal" +
                " FROM order_info " +
            " JOIN order_detail ON order_info.order_id = order_detail.order_id" +
            " GROUP BY " +
            "MONTH(order_import),YEAR(order_import)";

    String STATICTICAL_FOR_QUARTER_QUERY = "SELECT YEAR(order_import) AS year, " +
            "QUARTER(order_import) AS quarter, " +
            "COUNT(order_info.order_id) AS orderCount, " +
            "SUM(order_total) AS total, " +
            "MIN(order_total) AS minTotal, " +
            "MAX(order_total) AS maxTotal " +
            "FROM order_info " +
            "JOIN order_detail ON order_info.order_id = order_detail.order_id " +
            "GROUP BY YEAR(order_import), QUARTER(order_import)";

    @Query(value = STATICTICAL_FOR_PRODUCT_QUERY, nativeQuery = true)
    List<StatisticalProductProjections> statisticalForProduct();

    @Query(value = STATICTICAL_FOR_YEAR_QUERY, nativeQuery = true)
    List<StatisticalForYearProjections> statisticalForYear();

    @Query(value = STATISCAL_FOR_MONTH_QUERY, nativeQuery = true)
    List<StatisticalForMonthProjections> statisticalForMonth();

    @Query(value = STATICTICAL_FOR_QUARTER_QUERY, nativeQuery = true)
    List<StatisticalForQuarterProjections> statisticalForQuarter();

    @Query(value = "select * from order_info where account_id = ?", nativeQuery = true)
    List<Order> findOrderByAccount(long orderID);
}
