package com.helvetica.controller.command;

import com.helvetica.model.entity.RequestState;
import com.helvetica.services.MainPageService;

import javax.servlet.http.HttpServletRequest;

public class IndexCommentCommand implements Command {

    private MainPageService mainPageService;

    public IndexCommentCommand() {
        this.mainPageService = new MainPageService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("request_comment_id"));
        request.setAttribute("completed", RequestState.COMPLETED.name());
        String comment = request.getParameter("comment");

        mainPageService.leaveComment(id, comment);
        return "redirect:/index";
    }
}
