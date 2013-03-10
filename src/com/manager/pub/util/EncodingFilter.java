package com.manager.pub.util;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.manager.pub.bean.JspForm;


/**
 * 字符编码的过滤器类
 * @author 张杰
 * @since 2010-01
 *
 */
public class EncodingFilter implements Filter {

	protected FilterConfig filterConfig;
	private String targetEncoding = "UTF-8";
	ActionMapping mapping = new ActionMapping();
	private CringStringUtils csUtils = new CringStringUtils();
	private JspForm jspForm = null;

	//Filter接口中的销毁方法���
	public void destroy() {
		this.filterConfig=null;
	}

	public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)srequest;
		request.setCharacterEncoding(targetEncoding);
		//System.out.println("----------------进入过滤器 doFilter");
		if (csUtils.requestIfXss(request)) {
			request.getRequestDispatcher("/xxs.jsp").forward(request, sresponse);
			return;
		}
		//把处理权发送到下一个
		chain.doFilter(srequest,sresponse); 
	} 
	public void setFilterConfig(final FilterConfig filterConfig)
	{
		this.filterConfig=filterConfig;
	}
	//Filter接口中的初始化方法ӿ��еĳ�ʼ������
	public void init(FilterConfig config) throws ServletException {
		//System.out.println("----------------进入过滤器 init");
		this.filterConfig = config; 
		if(config.getInitParameter("encoding") !=null){
			this.targetEncoding = config.getInitParameter("encoding"); 
		}
	}

	public ActionForward outUser(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("");//被踢����
	}
}
