package com.helvetica.controller.command;

import com.helvetica.services.RequestService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class RequestDisplayCommandTest {

    private RequestDisplayCommand requestDisplayCommand;

    @Before
    public void init(){
        requestDisplayCommand = new RequestDisplayCommand(new RequestService());

    }

    @Test
    public void execute() {


        String expectedPath = "/WEB-INF/view/request_display.jsp";


        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        Mockito.doReturn(session).when(request)
                .getSession();

        String receivedPath = requestDisplayCommand.execute(request);

        Assert.assertEquals(expectedPath, receivedPath);

    }
}