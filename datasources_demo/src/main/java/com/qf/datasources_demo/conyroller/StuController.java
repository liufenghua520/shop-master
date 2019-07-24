package com.qf.datasources_demo.conyroller;

import com.qf.datasources_demo.datasource.DataSourceSelector;
import com.qf.datasources_demo.datasource.DynamicDataSource;
import com.qf.datasources_demo.entity.Student;
import com.qf.datasources_demo.service.IStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/23 0:27
 */
@Controller
@RequestMapping("/stu")
public class StuController {

    @Autowired
    private IStuService stuService;

    @RequestMapping("/list")
    public String stuList(Model model){

        DataSourceSelector.setLocal("db2");

        List<Student> students = stuService.queryAll();
        model.addAttribute("students",students);
        return "stulist";
    }

}
