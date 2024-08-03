package com.atguigu.boot3.crud.controller;

import com.atguigu.boot3.crud.entity.Dept;
import com.atguigu.boot3.crud.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    // Knife4j 對 swagger 的增強
    @Operation(summary = "按照 id 查詢部門")
    @GetMapping("/dept/{id}")
    public Dept getDept(@PathVariable("id") Long id) {
        return deptService.getDeptById(id);
    }

    @Operation(summary = "查詢所有部門")
    @GetMapping("/depts")
    public List<Dept> getDept() {
        return deptService.getDepts();
    }

    @Operation(summary = "保存部門", description = "必須提交 json 格式資料")
    @PostMapping("/dept")
    public String saveDept(@RequestBody Dept dept) {
        deptService.saveDept(dept);
        return "ok";
    }

    @Operation(summary = "按照 id 刪除部門", description = "必須提交 json 格式資料")
    @DeleteMapping("/dept/{id}")
    public String deleteDept(@PathVariable("id")
                             @Parameter(description = "部門 id") Long id) {
        deptService.deleteDept(id);
        return "ok";
    }
}
