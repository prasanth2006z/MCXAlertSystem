package com.mcx;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @Author: pxp167
 * @Date: 5/7/2018
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class SimpleCORSFilterTest {

  //@Autowired
  SimpleCORSFilter filter;
  private MockHttpServletRequest request;
  private MockHttpServletResponse response;
  private FilterChain mockFilterChain;

  @Before
  public void setUp() throws Exception {
    filter = new SimpleCORSFilter();
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
    mockFilterChain=Mockito.mock(FilterChain.class);
  }

  @Test
  public void testDoFilter() {
    try {
      request.addHeader("Origin", "http://github.com");
      request.addHeader("Access-Control-Allow-Methods", "POST");
      request.addHeader("Access-Control-Max-Age", "3600");
      request.addHeader("Access-Control-Allow-Headers", "Accept");
      filter.doFilter(request, response, mockFilterChain);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ServletException e) {
      e.printStackTrace();
    }
  }

  @Test(expected = NullPointerException.class)
  public void testDoFilterWhereFilterIsNull() {
    try {
      request.addHeader("Origin", "http://github.com");
      request.addHeader("Access-Control-Allow-Methods", "POST");
      request.addHeader("Access-Control-Max-Age", "3600");
      request.addHeader("Access-Control-Allow-Headers", "Accept");
      filter.doFilter(request, response, null);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ServletException e) {
      e.printStackTrace();
    }
  }
}
