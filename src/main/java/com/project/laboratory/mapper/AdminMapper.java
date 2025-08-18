package com.project.laboratory.mapper;




import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.project.laboratory.model.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper extends BaseMapper<Admin> {
    // 按用户名查管理员（登录用）
    Admin selectByUsername(@Param("username") String username);
}