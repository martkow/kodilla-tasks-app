package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * To ensure our template works seamlessly with the Thymeleaf library, we need to prepare two essential components:<br><br>
 * Template Engine – Provided by the Thymeleaf library, which is already available as a bean by default. It allows for the interpretation of the values set by us in the template as variables.<br><br>
 * Thymeleaf Context – Responsible for storing and providing variables directly to the template.
 */
@Service
public class MailCreatorService {
    /**
     * When multiple beans of the same type exist in the Spring container, causing Spring to be unsure which specific bean to inject. To resolve this, you can use the @Qualifier annotation and set its value to the name of the bean, as in the example above.
     *
     * As you can see, in the Thymeleaf library, there are already two beans of type TemplateEngine. Therefore, we need to declare which specific bean we need.
      */
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private CompanyConfig companyConfig;

    /**
     * The buildTrelloCardEmail method creates a new Context object provided in the org.thymeleaf.context package.
     * Within this context, we declare variables for the view, which will later be processed by the template
     * engine using templateEngine.process().
     * In this case, we do not use a model to extend a map because Thymeleaf requires a context for the view
     * template engine. Therefore, it is necessary to create a view with variables inserted into the template
     * within a single method before sending the email.<br><br>
     *
     * The process() method takes two arguments: one is the path to the email template.
     * The method knows to look for templates in the resources/templates directory, but if our template is
     * located deeper in the folder structure, this information should be included in the argument.
     * The second argument is the Context, which is an object that holds the variables for the view.
     * @param message
     * @return String
     */
    public String buildTrelloCardEmail(String message, String previewMessage) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8080/index.html");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("company_config", companyConfig);
        context.setVariable("preview_message", previewMessage);
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildNoReplyEmail(String message, String previewMessage) {
        Context context = new Context();
        context.setVariable("preview_message", previewMessage);
        context.setVariable("welcome_message", "Your statistics");
        context.setVariable("message", message);
        context.setVariable("show_button", true);
        context.setVariable("button", "Visit website");
        context.setVariable("tasks_url", "http://localhost:8080/index.html");
        context.setVariable("goodbye_message", "Do not reply to this e-mail");
        context.setVariable("company_config", companyConfig);

        return templateEngine.process("mail/no-reply-mail", context);
    }
}
