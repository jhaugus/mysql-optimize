package com.augus.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author augus
 * @email 724971721@qq.com
 * @date 2024-08-07 14:49:56
 */
@Data
@TableName("user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String username;
	/**
	 * 
	 */
	private String password;
	/**
	 * 
	 */
	private String email;
	/**
	 * 
	 */
	private String firstName;
	/**
	 * 
	 */
	private String lastName;
	/**
	 * 
	 */
	private Date birthDate;
	/**
	 * 
	 */
	private String gender;
	/**
	 * 
	 */
	private Date createdAt;
	/**
	 * 
	 */
	private Date updatedAt;
	/**
	 * 
	 */
	private Date lastLogin;
	/**
	 * 
	 */
	private String status;
	/**
	 * 
	 */
	private String role;

}
