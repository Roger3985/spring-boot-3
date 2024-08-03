package com.atguigu.boot3.ssm.mapper;

import com.atguigu.boot3.ssm.bean.TUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-29
 */
@Mapper
public interface UserMapper {

    /**
     * 1. 每個方法都在 Mapper 文件中有一個 sql 標籤對應。
     * 2. 所有參數都應該用 @Param 進行簽名，以後使用指定的名子在 SQL 中取值。
     * @param id
     * @return
     */
    TUser getUserById(@Param("id") Integer id);

}
