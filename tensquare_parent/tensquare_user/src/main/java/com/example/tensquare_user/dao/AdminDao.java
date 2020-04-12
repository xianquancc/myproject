package com.example.tensquare_user.dao;

import com.example.tensquare_user.pojo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * admin数据访问接口
 * @author Administrator
 *
 */
public interface AdminDao extends JpaRepository<Admin,String>,JpaSpecificationExecutor<Admin>{
	Admin findByLoginname(String loginName);
	Admin findByPasswordOrderByIdDesc(String pwd);
}
