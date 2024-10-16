package com.vnpt.mini_project_java.restcontroller;

import com.vnpt.mini_project_java.projections.StatisticalForMonthProjections;
import com.vnpt.mini_project_java.projections.StatisticalForYearProjections;
import com.vnpt.mini_project_java.projections.StatisticalProductProjections;
import com.vnpt.mini_project_java.service.statistical.StatisticalService;
import com.vnpt.mini_project_java.util.ExcelUtil;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/export")
public class StatisticalRestController {

    private final StatisticalService statisticsService;

    public StatisticalRestController(StatisticalService statisticsService) {
        this.statisticsService = statisticsService;
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
}
