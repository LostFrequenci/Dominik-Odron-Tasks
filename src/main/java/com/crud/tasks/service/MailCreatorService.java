package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;


@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message",message);
        context.setVariable("tasks_url","https://lostfrequenci.github.io/");
        context.setVariable("button","Visit website");
        context.setVariable("preview_message","New trello card created");
        context.setVariable("goodbye_message","Have a nice day !");
        context.setVariable("admin_config",adminConfig);
        context.setVariable("company_name",adminConfig.getCompanyName());
        context.setVariable("company_goal",adminConfig.getCompanyGoal());
        context.setVariable("company_email",adminConfig.getCompanyEmail());
        context.setVariable("show_button",true);
        context.setVariable("is_friend",true);
        context.setVariable("application_functionality",functionality);
        return templateEngine.process("created-trello-card-mail",context);
    }

    public String buildDailyCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message",message);
        context.setVariable("tasks_url","https://trello.com/b/CUs81PRO/kodilla-application");
        context.setVariable("button","Visit Trello Board");
        context.setVariable("preview_message","Daily status");
        context.setVariable("goodbye_message","Have a nice day !");
        context.setVariable("admin_config",adminConfig);
        context.setVariable("company_name",adminConfig.getCompanyName());
        context.setVariable("company_goal",adminConfig.getCompanyGoal());
        context.setVariable("company_email",adminConfig.getCompanyEmail());
        context.setVariable("show_button",true);
        context.setVariable("is_friend",true);
        context.setVariable("application_functionality",functionality);
        return templateEngine.process("daily-mail",context);
    }
}
