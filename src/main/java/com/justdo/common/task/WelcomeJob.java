package com.justdo.common.task;

import com.justdo.system.notice.domain.Response;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;


@Component
public class WelcomeJob implements Job{
	@Autowired
	SimpMessageSendingOperations template;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {

    	template.convertAndSend("/topic/greetings", new Response("欢迎体验JustDo,这是一个任务计划!!" ));


	   // template.convertAndSendToUser("1", "/queue/message", "新消息：");


    }

}