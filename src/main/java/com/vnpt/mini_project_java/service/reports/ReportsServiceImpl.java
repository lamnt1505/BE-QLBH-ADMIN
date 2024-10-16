package com.vnpt.mini_project_java.service.reports;

import com.vnpt.mini_project_java.projections.StatisticalProductProjections;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ReportsServiceImpl implements ReportsService{
    @Override
    public byte[] generateProductStatisticsReport(List<StatisticalProductProjections> statisticsList)
            throws JRException{

        InputStream reportTemplate = getClass().getResourceAsStream("classpath:/resources/reports/productReports.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplate);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(statisticsList);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,dataSource);

        byte[] reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        return reportBytes;
    }
}
