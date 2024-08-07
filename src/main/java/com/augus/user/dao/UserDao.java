package com.augus.user.dao;

import com.augus.user.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 
 * 
 * @author augus
 * @email 724971721@qq.com
 * @date 2024-08-07 14:49:56
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    @Select("SELECT id FROM user WHERE status='Active'")
    List<Long> selectActiveUserIds();


    @Select("SELECT * FROM user WHERE status='Active'")
    List<Long> selectAllUserIds();

}
