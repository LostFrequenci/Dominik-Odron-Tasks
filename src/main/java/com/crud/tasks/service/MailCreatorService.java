package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("message",message);
        context.setVariable("tasks_url","https://lostfrequenci.github.io/");
        context.setVariable("button","Visit website");
        context.setVariable("preview_message","New trello card created");
        context.setVariable("goodbye_message","Have a nice day !");
        context.setVariable("admin_name",adminConfig.getAdminName());
        context.setVariable("company_name",adminConfig.getCompanyName());
        context.setVariable("company_goal",adminConfig.getCompanyGoal());
        context.setVariable("company_email",adminConfig.getCompanyEmail());
        return templateEngine.process("created-trello-card-mail",context);
    }
}
