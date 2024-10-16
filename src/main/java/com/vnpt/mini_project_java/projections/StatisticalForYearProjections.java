package com.vnpt.mini_project_java.projections;

public interface StatisticalForYearProjections {
    int getYear();

    int getOrderCount();

    double getTotal();

    double getMinTotal();

    double getMaxTotal();
}
