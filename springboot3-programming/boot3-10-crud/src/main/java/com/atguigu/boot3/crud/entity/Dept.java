package com.atguigu.boot3.crud.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Roger
 * @Description
 * @create 2024-07-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "部門信息")
public class Dept {

    @Schema(title = "部門 id")
    private Long id;

    @Schema(title = "部門名字")
    private String deptName;
}
