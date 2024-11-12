package com.llibron.projectplan.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Date startDate;

    private Date endDate;

}
