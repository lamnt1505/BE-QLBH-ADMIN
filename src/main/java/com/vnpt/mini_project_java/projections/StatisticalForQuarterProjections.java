package com.vnpt.mini_project_java.projections;

public interface StatisticalForQuarterProjections {
    int getYear();
    int getQuarter();

    int getOrderCount();
    double getTotal();
    double getMinTotal();
    double getMaxTotal();
}
