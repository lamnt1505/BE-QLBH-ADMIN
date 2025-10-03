package com.vnpt.mini_project_java.restcontroller;

import com.vnpt.mini_project_java.dto.DailyRevenueStatusDTO;
import com.vnpt.mini_project_java.dto.RevenueDTO;
import com.vnpt.mini_project_java.projections.StatisticalForMonthProjections;
import com.vnpt.mini_project_java.projections.StatisticalForYearProjections;
import com.vnpt.mini_project_java.projections.StatisticalProductProjections;
import com.vnpt.mini_project_java.service.order.OrderService;
import com.vnpt.mini_project_java.service.statistical.StatisticalService;
import com.vnpt.mini_project_java.util.ExcelUtil;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/export")
public class StatisticalRestController {

    private final StatisticalService statisticsService;

    private final OrderService orderService;

    public StatisticalRestController(StatisticalService statisticsService, OrderService orderService) {
        this.statisticsService = statisticsService;
        this.orderService = orderService;
    }

    @GetMapping("/product")
    public ResponseEntity<ByteArrayResource> exportStatistics() {
        List<StatisticalProductProjections> statistics = statisticsService.statisticalForProduct();

        byte[] excelData = ExcelUtil.createStatisticalProductExcel(statistics);

        ByteArrayResource resource = new ByteArrayResource(excelData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statistical_product.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @GetMapping("/year")
    public ResponseEntity<ByteArrayResource> exportyear(){
        List<StatisticalForYearProjections> statistics = statisticsService.statisticalForYear();

        byte[] excelData = ExcelUtil.createStatisticalForYearExcel(statistics);

        ByteArrayResource resource = new ByteArrayResource(excelData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=statistical_year.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @GetMapping("/month")
    public ResponseEntity<ByteArrayResource> exportmonth(){
        List<StatisticalForMonthProjections> statistical = statisticsService.statisticalForMonth();

        byte[] excelData = ExcelUtil.createStatisticalForMonth(statistical);

        ByteArrayResource resource = new ByteArrayResource(excelData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=statistical_month.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @GetMapping("/revenue")
    public Map<String, Object> getRevenue() {
        return orderService.getRevenue();
    }

    @GetMapping("/revenue-by-month")
    public ResponseEntity<List<RevenueDTO>> getRevenueByMonth() {
        return ResponseEntity.ok(orderService.getRevenueByMonth());
    }

    @GetMapping("/revenue-by-day-status")
    public ResponseEntity<List<DailyRevenueStatusDTO>> getRevenueByDayAndStatus() {
        return ResponseEntity.ok(orderService.getRevenueByDayAndStatus());
    }
}
