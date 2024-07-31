package com.atguigu.boot3.crud.service;

import com.atguigu.boot3.crud.entity.Dept;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Roger
 * @Description
 * @create 2024-07-31
 */
@Service
public class DeptService {

    Map<Long, Dept> data = new ConcurrentHashMap<Long, Dept>();

    public Dept getDeptById(Long id) {
        return data.get(id);
    }

    public List<Dept> getDepts() {
        return data.values().stream().toList();
    }

    public void saveDept(Dept dept) {
        data.put(dept.getId(), dept);
    }

    public void deleteDept(Long id) {
        data.remove(id);
    }


}
