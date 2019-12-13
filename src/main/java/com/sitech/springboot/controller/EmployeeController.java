package com.sitech.springboot.controller;

import com.sitech.springboot.dao.DepartmentDao;
import com.sitech.springboot.dao.EmployeeDao;
import com.sitech.springboot.entities.Department;
import com.sitech.springboot.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
public class EmployeeController  {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;

    //查询所有员工返回列表页面
    @GetMapping("/emps")
    public String list(Model model){
        Collection<Employee> employees=employeeDao.getAll();

        //放到请求域中
        model.addAttribute("emps",employees);

        //thymleaf 默认拼串
        return "emp/list";
    }

    //来到员工添加页面,查出所有部门，在页面页面显示
    @GetMapping("/emp")
    public String toAddPage(Model model){
        //返回添加页面
        Collection<Department> departments=departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "emp/add";

    }
    //员工添加功能
    //springmvc自动将请求参数和入参对象的属性一一对应：
    //要求了请求参数的名字和javaBean入参的对象里面的属性名是一样的
    @PostMapping("/emp")
    public String addEmp(Employee employee){
        //来到员工列表界面
        //保存员工
         employeeDao.save(employee);
        //redirect：表示重定向到一个地址 /代表当前项目下的路径
        //forword：表示转发到一个地址
        return "redirect:/emps";
    }

    //来到修改界面，查出当前员工，在页面回显
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id,Model model){
        Employee employee=employeeDao.get(id);
        model.addAttribute("emp",employee);

        //页面要显示所有部门列表
        Collection<Department> departments=departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        //回到修改页面（add是一个修改添加二合一的页面）
        return "emp/add";

    }
    //员工修改
    @PutMapping("/emp")
    public String updateEmployee(Employee employee){
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    //员工删除
   @DeleteMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){
            employeeDao.delete(id);
            System.out.println(id);
            return "redirect:/emps";
    }


}
