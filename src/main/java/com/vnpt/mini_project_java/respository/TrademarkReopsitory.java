package com.vnpt.mini_project_java.respository;

import com.vnpt.mini_project_java.entity.Product;
import com.vnpt.mini_project_java.entity.Trademark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrademarkReopsitory extends JpaRepository<Trademark, Long> {
}
