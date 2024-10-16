package com.vnpt.mini_project_java.service.statistical;

import com.vnpt.mini_project_java.projections.StatisticalForMonthProjections;
import com.vnpt.mini_project_java.projections.StatisticalForYearProjections;
import com.vnpt.mini_project_java.projections.StatisticalProductProjections;

import java.util.List;

public interface StatisticalService {
    List<StatisticalForYearProjections> statisticalForYear();

    List<StatisticalProductProjections> statisticalForProduct();

    List<StatisticalForMonthProjections> statisticalForMonth();
}
