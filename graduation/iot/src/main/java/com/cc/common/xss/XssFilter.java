package com.cc.common.xss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cc.common.utils.StringUtils;
import com.cc.common.xss.XssHttpServletRequestWrapper;


/**
 * 防止XSS攻击的过滤器
 *
 * @author ruoyi
 */
public class XssFilter implements Filter
{
    /**
     * 排除链接
     */
    public List<String> excludes = new ArrayList<>();

    /**
     * xss过滤开关
     */
    public boolean enabled = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        String tempExcludes = filterConfig.getInitParameter("excludes");
        String tempEnabled = filterConfig.getInitParameter("enabled");
        if (StringUtils.isNotEmpty(tempExcludes))
        {
            String[] url = tempExcludes.split(",");
            for (int i = 0; url != null && i < url.length; i++)
            {
                excludes.add(url[i]);
            }
        }
        if (StringUtils.isNotEmpty(tempEnabled))
        {
            enabled = Boolean.valueOf(tempEnabled);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (handleExcludeURL(req, resp))
        {
            chain.doFilter(request, response);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
        chain.doFilter(xssRequest, response);
    }

    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response)
    {
        if (!enabled)
        {
            return true;
        }
        if (excludes == null || excludes.isEmpty())
        {
            return false;
        }
        String url = request.getServletPath();
        for (String pattern : excludes)
        {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy()
    {

    }
}

//package com.cc.common.xss;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import com.cc.common.utils.StringUtils;
//
///**
// * 防止XSS攻击的过滤器
// *
// * @author cc
// */
//public class XssFilter implements Filter
//{
//    /**
//     * 排除链接
//     */
//    public List<String> excludes = new ArrayList<>();
//    /**
//     * xss过滤开关
//     */
//    public boolean enabled = false;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException
//    {
//        //FilterConfig是Servlet API提供的一个用于获取Filter程序在web.xml文件中的配置信息的接口，该接口封装了Filter程序在web.xml中的所有注册信息
//        String tempExcludes = filterConfig.getInitParameter("excludes");//不允许访问路径，用逗号隔开
//        String tempEnabled = filterConfig.getInitParameter("enabled");//是否开启xss过滤
//        if (StringUtils.isNotEmpty(tempExcludes))//web.xml是否配置excludes参数
//        {
//            String[] url = tempExcludes.split(",");//用逗号分割路径
//            for (int i = 0; url != null && i < url.length; i++)
//            {
//                excludes.add(url[i]);//将不允许访问路径存入数组
//            }
//        }
//        if (StringUtils.isNotEmpty(tempEnabled))
//        {
//            enabled = Boolean.valueOf(tempEnabled);
//        }
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException
//    {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse resp = (HttpServletResponse) response;
//        if (handleExcludeURL(req, resp))//访问路径不合法
//        {
//            String message = "不允许访问该路径";
//            resp.getWriter().write(message,0,message.length());
//            return;
//        }
//        if (enabled)//开启xss过滤
//        {
//            XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
//            chain.doFilter(xssRequest, response);
//            return;
//        }
//        chain.doFilter(request, response);
//    }
//
//    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response)
//    {
//        if (excludes == null || excludes.isEmpty())//需要验证的字符不存在或为空，停止请求执行
//        {
//            return false;
//        }
//        String url = request.getServletPath();
//        //验证请求的路径
//        for (String pattern : excludes)
//        {
//            Pattern p = Pattern.compile("^" + pattern);
//            Matcher m = p.matcher(url);
//            if (m.find())
//            {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void destroy()
//    {
//
//    }
//}