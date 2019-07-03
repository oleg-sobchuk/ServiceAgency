package com.serviceagency.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class OrderCreationFormFilterWrapper extends HttpServletRequestWrapper {
    public OrderCreationFormFilterWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getMethod() {
        return "GET";
    }


}
