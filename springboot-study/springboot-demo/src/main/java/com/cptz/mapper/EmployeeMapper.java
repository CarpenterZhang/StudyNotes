package com.cptz.mapper;

import com.cptz.entity.Department;
import com.cptz.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EmployeeMapper {

    private Map<String, Employee> employeesMap = new TreeMap<>();

    @Autowired
    private DepartmentMapper departmentMapper;

    public List<Employee> getEmployeeList() {
        List<Employee> employees = new ArrayList<>();
        if (employeesMap.isEmpty()) {
            employeesMap.put("1001", new Employee("1001", "zhangsan", "1", new Department("1", "市场部"), new Date()));
            employeesMap.put("1002", new Employee("1002", "lisi", "0", new Department("1", "市场部"), new Date()));
            employeesMap.put("1003", new Employee("1003", "wangwu", "1", new Department("2", "销售部"), new Date()));
            employeesMap.put("1004", new Employee("1004", "zhaoliu", "0", new Department("2", "销售部"), new Date()));
            employeesMap.put("1005", new Employee("1005", "tianqi", "1", new Department("3", "后勤部"), new Date()));
        }

        for(Map.Entry<String, Employee> em : employeesMap.entrySet()) {
            employees.add(em.getValue());
        }
        return employees;
    }


    public void addEmployee(Employee employee) {
        employee.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        employee.setDepartment(departmentMapper.getDepartment(employee.getDepartment().getId()));
        employeesMap.put(employee.getId(), employee);
    }


    public void delEmp(String id) {
        employeesMap.remove(id);
    }

    public void updateEmp(Employee employee) {
        Employee emp = employeesMap.get(employee.getId());

        BeanUtils.copyProperties(employee, emp);
        emp.setDepartment(departmentMapper.getDepartment(employee.getDepartment().getId()));
    }

    public Employee getEmployee(String id) {
        return employeesMap.get(id);
    }
}
