package com.augus.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.augus.user.common.utils.PageUtils;
import com.augus.user.common.utils.Query;

import com.augus.user.dao.UserDao;
import com.augus.user.entity.UserEntity;
import com.augus.user.service.UserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private UserDao userMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }
    @Override
    public List<Long> getActiveUserIds() {
        return userMapper.selectActiveUserIds();
    }

    @Override
    public List<Long> getAllUserIds() {
        return userMapper.selectAllUserIds();
    }

}