package com.qf.datasources_demo.service;

import com.qf.datasources_demo.dao.StuMapper;
import com.qf.datasources_demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/23 0:29
 */
@Service
public class StuServiceImpl implements IStuService {

    @Autowired
    private StuMapper stuMapper;

    @Override
    public List<Student> queryAll() {
        return stuMapper.queryAll();
    }
}
