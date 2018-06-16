package com.cn.beauty.controller;

import java.net.URLEncoder;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cn.beauty.entity.User;
import com.cn.beauty.service.UserService;
import com.cn.beauty.util.Md5;
import com.cn.beauty.util.ResultInfo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;

	private Logger logger = Logger.getLogger(String.valueOf(UserController.class));

	@ResponseBody
	@RequestMapping("/register")
	public ResultInfo addUser(HttpServletRequest request) {
		ResultInfo result = new ResultInfo();
		String image = request.getParameter("image");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String mail = request.getParameter("mail");

		try {
			String pwdMd5 = Md5.md5(password, "jcy");
			Long userId = this.userService.addUser(image, username, pwdMd5, mail);
			User user = this.userService.getUserById(userId);
			result.setCode(200);
			result.setInfo(user);
		} catch (Exception e) {
			result.setCode(500);
			result.setMsg("注册失败，请重试！");
			return result;
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/login")
	public ResultInfo login(HttpServletRequest request, HttpServletResponse response) {
		ResultInfo result = new ResultInfo();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			String pwdMd5 = Md5.md5(password, "jcy");
			User user = this.userService.loginCheck(username, pwdMd5);
			if (user != null) {
				// 将登录信息保存到cookie和redis缓存
				UUID uuid = UUID.randomUUID();
				String key = "userInfo" + uuid + "-" + user.getId() + "-" + username;
				
				// 保存到redis缓存
				redisTemplate.opsForValue().set(key, username, 60 * 60 * 30);
				
				key = URLEncoder.encode(key, "UTF-8");
				Cookie cookie = new Cookie("userInfo", key);
				cookie.setPath("/");
				response.addCookie(cookie);

				JSONObject json = new JSONObject();
				json.put("name", username);
				result.setCode(200);
				result.setInfo(json);
			} else {
				result.setCode(1001);
				result.setMsg("用户名或密码错误，登录失败！");
			}
		} catch (Exception e) {
			logger.info("登录异常：" + e);
			result.setCode(500);
			result.setMsg("登录失败，请重试！");
			return result;
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/checkUsername")
	public ResultInfo checkUsername(HttpServletRequest request) {
		ResultInfo result = new ResultInfo();
		String username = request.getParameter("username");
		int count = this.userService.checkUsername(username);
		JSONObject repeat = new JSONObject();
		repeat.put("repeat", count > 0 ? true : false);
		result.setCode(200);
		result.setInfo(repeat);
		return result;
	}

	@ResponseBody
	@RequestMapping("/loginout")
	public ResultInfo loginout(HttpServletRequest request, HttpServletResponse response) {
		ResultInfo result = new ResultInfo();
		String redisUserInfo = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userInfo")) {
					redisUserInfo = cookie.getValue();
					cookie.setValue(null);
					// 立即销毁cookie
					cookie.setMaxAge(0);
					cookie.setPath("/");
					logger.info("被删除的cookie名字为:" + cookie.getName());
					response.addCookie(cookie);
					break;
				}
			}
		}
		redisTemplate.delete(redisUserInfo);
		result.setCode(200);
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/getUserInfo")
	public ResultInfo getUserInfo(HttpServletRequest request) {
		ResultInfo result = new ResultInfo();
		String user = "";
		Long userId = null;
		User u = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userInfo")) {
					user = cookie.getValue();
					break;
				}
			}
		}
		if(!"".equals(user)) {
			String[] userArray = user.split("-");
			userId = Long.parseLong(userArray[userArray.length-2]);
			u = userService.getUserById(userId);
		}
		result.setCode(200);
		result.setInfo(u);
		return result;
	}
}
