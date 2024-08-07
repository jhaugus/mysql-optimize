package com.augus.user.controller;
import java.util.Date;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.augus.user.entity.UserEntity;
import com.augus.user.service.UserService;
import com.augus.common.utils.PageUtils;
import com.augus.common.utils.R;



/**
 * 
 *
 * @author augus
 * @email 724971721@qq.com
 * @date 2024-08-07 14:49:56
 */
@RestController
@RequestMapping("user/user")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping("/users/list")
    public R test(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(0);
        userEntity.setUsername("augus");
        userEntity.setPassword("123456");
        userEntity.setEmail("");
        userEntity.setFirstName("");
        userEntity.setLastName("");
        userEntity.setBirthDate(new Date());
        userEntity.setCreatedAt(new Date());
        userEntity.setUpdatedAt(new Date());
        userEntity.setLastLogin(new Date());

        List<UserEntity> list = userService.list();
        list.add(userEntity);
        String string = list.toString();

        return R.ok().put("users", string);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("user:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("user:user:info")
    public R info(@PathVariable("id") Integer id){
		UserEntity user = userService.getById(id);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("user:user:save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("user:user:update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("user:user:delete")
    public R delete(@RequestBody Integer[] ids){
		userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
