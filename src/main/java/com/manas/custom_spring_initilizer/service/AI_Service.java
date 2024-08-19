package com.manas.custom_spring_initilizer.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

// model can vary
@Service
public class AI_Service {

    private final ChatClient chatClient;

    public AI_Service(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    public String generate_response(String prompt){
        return chatClient.prompt().user(prompt).call().content();
    }

}
