package com.vnpt.mini_project_java.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vnpt.mini_project_java.entity.Storage;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage,Long> {
}
