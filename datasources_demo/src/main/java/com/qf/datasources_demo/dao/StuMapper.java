package com.qf.datasources_demo.dao;

import com.qf.datasources_demo.entity.Student;

import java.util.List;

public interface StuMapper {
    List<Student> queryAll();
}
