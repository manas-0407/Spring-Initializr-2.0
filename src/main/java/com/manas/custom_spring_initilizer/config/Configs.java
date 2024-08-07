package com.manas.custom_spring_initilizer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.command.annotation.CommandScan;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.io.*;

@Configuration
@ComponentScan
public class Configs {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
