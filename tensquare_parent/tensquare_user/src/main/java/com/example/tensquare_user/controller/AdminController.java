package com.example.tensquare_user.controller;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.example.tensquare_user.dao.AdminDao;
import com.example.tensquare_user.pojo.Admin;
import com.example.tensquare_user.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


import entity.PageResult;
import entity.Result;
import entity.StatusCode;
/**
 * admin控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		List<Admin> adminList= (List<Admin>) redisTemplate.opsForValue().get("admin");
		if(adminList==null) {
			adminList = adminService.findAll();
			redisTemplate.opsForValue().set("admin", adminList,1, TimeUnit.DAYS);
		}
		return new Result(true,StatusCode.OK,"查询成功",adminList);
	}

	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		Admin admin= (Admin)redisTemplate.opsForValue().get("admin_"+id);
		if(admin==null) {
			admin = adminService.findById(id);
			redisTemplate.opsForValue().set("admin_" + id, admin);
		}
		return new Result(true,StatusCode.OK,"查询成功",admin);
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",adminService.findSearch(searchMap));
    }

	@Autowired
	AdminDao adminDao;

    @RequestMapping(value = "/{loginname}",method = RequestMethod.GET)
	public Result findByLoginName(@PathVariable String loginname){
		return new Result(true,StatusCode.OK,"查询成功",adminDao.findByLoginname(loginname));
	}

	/**
	 * 增加
	 * @param admin
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Admin admin  ){
		adminService.add(admin);
		return new Result(true,StatusCode.OK,"增加成功");
	}

	/**
	 * 修改
	 * @param admin
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public synchronized Result update(@RequestBody Admin admin, @PathVariable String id ){
		Lock lock=new ReentrantLock();
		lock.lock();
		admin.setId(id);
		adminService.update(admin);
		return new Result(true,StatusCode.OK,"修改成功");
	}

	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id){
		adminService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

}
