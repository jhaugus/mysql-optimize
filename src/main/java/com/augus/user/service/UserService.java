package com.augus.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.augus.common.utils.PageUtils;
import com.augus.user.entity.UserEntity;

import java.util.Map;

/**
 * 
 *
 * @author augus
 * @email 724971721@qq.com
 * @date 2024-08-07 14:49:56
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

