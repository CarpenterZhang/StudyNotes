package com.cptz.mapper;

import com.cptz.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DepartmentMapper {

    Map<String, Department> departments = new TreeMap<>();

    public List<Department> getDepartmentList() {

        List<Department> departmentList = new ArrayList<>();

        if(departments.isEmpty()) {
            departments.put("1", new Department("1", "市场部"));
            departments.put("2", new Department("2", "销售部"));
            departments.put("3", new Department("3", "后勤部"));
            departments.put("4", new Department("4", "体育部"));
            departments.put("5", new Department("5", "外联部"));
        } else {
            for(Map.Entry<String, Department> en : departments.entrySet()) {
                departmentList.add(en.getValue());
            }
        }
        return departmentList;
    }

    public Department getDepartment(String id) {
        return departments.get(id);
    }
}
