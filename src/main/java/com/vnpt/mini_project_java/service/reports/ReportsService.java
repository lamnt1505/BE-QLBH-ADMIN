package com.vnpt.mini_project_java.service.reports;

import com.vnpt.mini_project_java.projections.StatisticalProductProjections;
import net.sf.jasperreports.engine.JRException;

import java.util.List;

public interface ReportsService {

    byte[] generateProductStatisticsReport(List<StatisticalProductProjections> statisticsList)throws JRException;
}
