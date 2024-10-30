package com.vnpt.mini_project_java.respository;

import com.vnpt.mini_project_java.entity.Trademark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrademarkReopsitory extends JpaRepository<Trademark, Long> {
}
