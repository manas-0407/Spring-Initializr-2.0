package com.manas.custom_spring_initilizer.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@ShellComponent
public class CustomCommands {

    @Autowired
    RestTemplate restTemplate;

    @ShellMethod(key = "hello", value = "command to say hello")
    public String hello(
            @ShellOption(help = "Input name", defaultValue = "manas") String name,
            @ShellOption(help = "Input age", defaultValue = "21") String age) {
        return "Hello " + name + " age is " + age;
    }

    @ShellMethod(key = "spring", value = "Generate Spring Project")
    public String spring_initializr2(
            @ShellOption(help = "Project Build tool", defaultValue = "maven-project") String type,
            @ShellOption(help = "Project Language", defaultValue = "java") String language,
            @ShellOption(help = "Spring Boot Version", defaultValue = "3.3.2") String bootVersion,
            @ShellOption(help = "Project Group ID", defaultValue = "com.example") String group,
            @ShellOption(help = "Project Artifact ID", defaultValue = "demo") String artifactId,
            @ShellOption(help = "Project Name", defaultValue = "maven") String name,
            @ShellOption(help = "Project Description", defaultValue = "Demo project for Spring Boot") String description,
            @ShellOption(help = "Project Package Name", defaultValue = "com.example.demo") String packageName,
            @ShellOption(help = "Project Packaging type", defaultValue = "jar") String packaging,
            @ShellOption(help = "Java version", defaultValue = "17") String javaVersion,
            @ShellOption(help = "Comma-separated list of dependency identifiers to be included", defaultValue = "web") String dependencies) {
        String projectUrl = baseURL(type, language, bootVersion, group, artifactId, name, description, packageName, packaging, javaVersion, dependencies);

        try {
            downloadProject(projectUrl, artifactId + ".zip");
            return "Spring Boot project generated successfully.";
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }

    public String baseURL(String type, String lang, String bootVersion,
                          String group, String artifactId, String name, String description,
                          String packageName, String packaging, String javaVersion, String dependencies) {

        dependencies = dependencies.replace(" ", "");

        return "https://start.spring.io/starter.zip?" +
                "type=" + type +
                "&language=" + lang +
                "&bootVersion=" + bootVersion +
                "&baseDir=" + artifactId +
                "&groupId=" + group +
                "&artifactId=" + artifactId +
                "&name=" + name +
                "&description=" + description +
                "&packageName=" + packageName +
                "&packaging=" + packaging +
                "&javaVersion=" + javaVersion +
                "&dependencies=" + dependencies;
    }

    public void downloadProject(String urlString, String outputFileName) throws IOException {
        try (InputStream in = new ByteArrayInputStream(restTemplate.getForObject(urlString, byte[].class));
             FileOutputStream out = new FileOutputStream(outputFileName)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
