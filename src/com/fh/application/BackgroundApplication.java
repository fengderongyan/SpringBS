package com.fh.application;


import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;


public class BackgroundApplication implements ServletContextListener {
    
    public void contextDestroyed(ServletContextEvent arg0) {

    }
    public void contextInitialized(ServletContextEvent arg0) {
        System.setProperty("dir_path", arg0.getServletContext().getRealPath("/"));  
        System.out.println("线程启动了  了了浏览了浏览");
        TimingOperation timingOperation = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext()).getBean(TimingOperation.class);
        timingOperation.start();
        TimingMyPointOperation timingMyPointOperation = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext()).getBean(TimingMyPointOperation.class);
        timingMyPointOperation.start();
        TimingMyCertificate timingMyCertificate = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext()).getBean(TimingMyCertificate.class);
        timingMyCertificate.start();
        TimingWeather timingWeather = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext()).getBean(TimingWeather.class);
        timingWeather.start();
    }
}
