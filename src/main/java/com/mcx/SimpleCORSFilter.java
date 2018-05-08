package com.mcx;

import com.mcx.constants.NotificationConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: pxp167
 * @Date: 5/3/2018
 *
 */

@Component
public class SimpleCORSFilter implements Filter {

  private static final Logger logger = LogManager.getLogger(SimpleCORSFilter.class);

  public SimpleCORSFilter() {
    logger.info("SimpleCORSFilter init");
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    logger.debug("Inside SimpleCORSFilter");
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    //response.setHeader(NotificationConstants.Access_Control_Allow_Origin, request.getHeader(NotificationConstants.Access_Control_Allow_Origin_VALUES));
    response.setHeader(NotificationConstants.Access_Control_Allow_Credentials, NotificationConstants.Access_Control_Allow_Credentials_VALUES);
    response.setHeader(NotificationConstants.Access_Control_Allow_Methods, NotificationConstants.Access_Control_Allow_Methods_VALUES);
    response.setHeader(NotificationConstants.Access_Control_Max_Age, NotificationConstants.Access_Control_Max_Age_VALUE);
    response.setHeader(NotificationConstants.Access_Control_Allow_Headers, NotificationConstants.Access_Control_Allow_Headers_VALUES);
    chain.doFilter(request, res);
  }

  @Override
  public void init(FilterConfig filterConfig) {
  }

  @Override
  public void destroy() {
  }

}
