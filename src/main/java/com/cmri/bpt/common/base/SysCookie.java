package com.cmri.bpt.common.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysCookie {

	public static Integer DefMaxAge = -1;// -1 关闭浏览器后消失，0 立即失效 ，其余 n>0 表示 n秒后失效
	public static String DefDomain = ".yikuaixiu.com";
	public static Boolean DefSecure = false;

	/**
	 * 设置cookie
	 * 
	 * @param response
	 * @param name
	 *            cookie名字
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            cookie生命周期 以秒为单位
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0)
			cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	/**
	 * 设置cookie
	 * 
	 * @param resp
	 * @param cookieName
	 * @param cookieValue
	 */
	public static void AddCookie(HttpServletResponse resp, String cookieName, String cookieValue) {
		AddCookie(resp, DefDomain, cookieName, cookieValue, DefMaxAge, DefSecure);
	}

	/**
	 * 设置cookie
	 * 
	 * @param resp
	 * @param domain
	 * @param cookieName
	 * @param cookieValue
	 * @param maxAge
	 * @param flag
	 *            是否安全cookie 若设置为true 需要https请求传递cookie http请求不会传递该信息，
	 */
	public static void AddCookie(HttpServletResponse resp, String domain, String cookieName, String cookieValue,
			Integer maxAge, Boolean flag) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		if (null == domain) {
			domain = DefDomain;
		}
		cookie.setDomain(domain);
		if (null == domain) {
			maxAge = DefMaxAge;
		}
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		if (null == flag) {
			flag = DefSecure;
		}
		cookie.setSecure(flag);
		resp.addCookie(cookie);
	}

	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 移除cookie
	 * 
	 * @param req
	 * @param resp
	 * @param cookieName
	 */
	public static void removeCookie(HttpServletRequest req, HttpServletResponse resp, String cookieName) {
		Cookie cookie = getCookieByName(req, cookieName);
		if (cookie != null) {
			cookie.setMaxAge(0);// -1 表示 关闭浏览器删除 ，0 表示 立即删除
			cookie.setPath("/");
			cookie.setDomain(DefDomain);
			cookie.setValue(null);// 让浏览器无需退出就可以生效。
			resp.addCookie(cookie);
		}
	}

}
