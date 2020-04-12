package com.tensquare.spit.dao;


import com.tensquare.spit.pojo.Spit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpitDao extends JpaRepository<Spit, String>, JpaSpecificationExecutor<Spit> {


}