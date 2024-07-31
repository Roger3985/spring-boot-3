package com.atguigu.boot3.crud.service;

import com.atguigu.boot3.crud.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Roger
 * @Description
 * @create 2024-08-01
 */
@Service
public class EmployeeService {

    Map<Long, Employee> data = new ConcurrentHashMap<Long, Employee>();

    public Employee getEmployeeById(Long id) {
        return data.get(id);
    }

    public List<Employee> getEmployees() {
        return data.values().stream().toList();
    }

    public void saveEmployee(Employee employee) {
        data.put(employee.getId(), employee);
    }

    public void deleteEmployee(Long id) {
        data.remove(id);
    }


}
