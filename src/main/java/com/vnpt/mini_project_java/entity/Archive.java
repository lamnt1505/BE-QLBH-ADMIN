package com.vnpt.mini_project_java.entity;

import javax.persistence.*;

@Entity
@Table(name = "archive")
public class Archive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idarchive;


}
