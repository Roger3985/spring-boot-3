package com.atguigu.boot3.crud.controller;

import com.atguigu.boot3.crud.entity.Dept;
import com.atguigu.boot3.crud.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Roger
 * @Description
 * @create 2024-07-31
 */
@Tag(name = "部門", description = "部門管理(CRUD)")
@RestController
public class DeptController {

    @Autowired
    DeptService deptService;

    @Operation(method = "按照 id 查詢部門")
    @GetMapping("/dept/{id}")
    public Dept getDept(@PathVariable("id") Long id) {
        return deptService.getDeptById(id);
    }

    @GetMapping("/depts")
    public List<Dept> getDept() {
        return deptService.getDepts();
    }

    @PostMapping("/dept")
    public String saveDept(@RequestBody Dept dept) {
        deptService.saveDept(dept);
        return "ok";
    }

    @DeleteMapping("/dept/{id}")
    public String deleteDept(@PathVariable("id") Long id) {
        deptService.deleteDept(id);
        return "ok";
    }
}
