package com.example.tensquare_user.dao;

import com.example.tensquare_user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * user数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {
	
}
