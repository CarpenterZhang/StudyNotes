package com.cptz.controller;

import com.cptz.entity.Employee;
import com.cptz.mapper.DepartmentMapper;
import com.cptz.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    @RequestMapping("/employeelist")
    public String getEmplyeeList(Model model) {
        model.addAttribute("employeelist", employeeMapper.getEmployeeList());
        return "emp/employeeList";
    }

    @RequestMapping("/toAdd")
    public String toAdd(Model model) {
        model.addAttribute("departments", departmentMapper.getDepartmentList());
        return "emp/toAdd";
    }

    @PostMapping("/addEmp")
    public String add(Employee employee) {
        employeeMapper.addEmployee(employee);
        return "redirect:employeelist";
    }

    @GetMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable("id") String id, Model model) {
        Employee employee = employeeMapper.getEmployee(id);
        model.addAttribute("departments", departmentMapper.getDepartmentList());
        model.addAttribute("employee", employee);
        return "emp/toUpdate";
    }

    @PostMapping("/updateEmp")
    public String updateEmp(Employee employee) {
        employeeMapper.updateEmp(employee);
        return "redirect:employeelist";
    }

    @RequestMapping("/delEmp/{id}")
    public String delEmp(@PathVariable("id") String id) {
        employeeMapper.delEmp(id);
        return "redirect:/employeelist";
    }
}
