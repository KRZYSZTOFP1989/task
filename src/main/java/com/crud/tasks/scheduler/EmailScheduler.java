package com.crud.tasks.scheduler;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    public static final String SUBJECT = "Tasks: Once a day email";
    public static String MESSAGE_PREFIX = "Currently in database you got: ";

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    @Scheduled(fixedDelay = 10000)
    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();

        String text = size == 1 ? " task" : " tasks";

        String message = MESSAGE_PREFIX + size + text;
        simpleEmailService.send(new Mail(
                adminConfig.getAdminMail(),
                "", SUBJECT, message));
    }

}