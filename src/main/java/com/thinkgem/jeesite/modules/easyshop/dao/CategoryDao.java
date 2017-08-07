/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.easyshop.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.easyshop.entity.Category;

/**
 * 商品分类信息DAO接口
 * @author RuYang
 * @version 2017-08-07
 */
@MyBatisDao
public interface CategoryDao extends TreeDao<Category> {
	
}