package com.helvetica.controller.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import javax.servlet.http.HttpSession;



public class LogoutCommandTest {

    private LogoutCommand logoutCommand;

    @Before
    public void init(){
        logoutCommand = new LogoutCommand();

    }

    @Test
    public void execute() {

        String expectedPath = "/WEB-INF/view/login.jsp";


        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        Mockito.doReturn(session).when(request)
                .getSession();

        String receivedPath = logoutCommand.execute(request);

        Assert.assertEquals(expectedPath, receivedPath);

    }
}