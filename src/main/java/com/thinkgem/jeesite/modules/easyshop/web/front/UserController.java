/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.easyshop.web.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.HttpUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.easyshop.entity.Ad;
import com.thinkgem.jeesite.modules.easyshop.entity.User;
import com.thinkgem.jeesite.modules.easyshop.service.AdService;
import com.thinkgem.jeesite.modules.easyshop.service.UserService;

/**
 * 会员Controller
 * 
 * @author RuYang
 * @version 2017-08-08
 */
@Controller("FrontUserController")
@RequestMapping(value = "${frontPath}/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AdService adService;

	@RequestMapping(value = { "auth", "" }, method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		String callback = request.getParameter("callback");
		if (StringUtils.isNotEmpty(callback)) {
			model.addAttribute("callback", callback);
		}
		String bg = "";
		List<Ad> list =  adService.findList(new Ad());
		if(list!=null&&list.size()>0){
			bg = list.get(0).getImageUrl();
		}
		model.addAttribute("bg", bg);
		return "front/auth";
	}
	
	@ResponseBody
	@RequestMapping(value = { "auth", "" }, method = RequestMethod.POST)
	public Map<String, Object> auth(HttpServletRequest request, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", 200);
		String callback = request.getParameter("callback");
		String sn = request.getParameter("sn");
		// 获取文章内容
		User user = userService.getBySn(sn);
		if (user == null || !User.DEL_FLAG_NORMAL.equals(user.getDelFlag())) {
			result.put("code", 400);
			result.put("message", "您输入的订单编号有误，请重新输入！");
			return result;
		}
		request.getSession().setAttribute(User.SESSION_KEY, user);
		/*
		 * 登录需要跳转登录前页面，自己处理 callback 使用 HttpUtil.decodeURL(xx) 解码后重定向
		 */
		if (StringUtils.isEmpty(callback)) {
			callback = frontPath;
		} else {
			callback = HttpUtil.decodeURL(callback);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("callback", callback);
		result.put("data", data);
		return result;
	}
	
	@RequestMapping(value = { "info"}, method = RequestMethod.GET)
	public String info(HttpServletRequest request, Model model) {
		User user = (User) request.getSession().getAttribute(User.SESSION_KEY);
		if(user==null){
			return "redirect:"+Global.getFrontPath()+"/goods";
		}
		model.addAttribute("user", user);
		return "front/info";
	}
}