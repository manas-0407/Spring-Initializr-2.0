package com.manas.custom_spring_initilizer.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CreationCommand {

    @Autowired
    RestTemplate restTemplate;

    private static final Map<String, String> TEMPLATES = new HashMap<>();

    static {
        TEMPLATES.put("web", "web,thymeleaf");
        TEMPLATES.put("rest", "web,data-jpa");
        TEMPLATES.put("microservice", "web,cloud-eureka,cloud-config");
    }

    public String init(){

        try{

            String type = promptForInput("Project Build tool (e.g., maven-project): ", "maven-project");
            String language = promptForInput("Project Language (e.g., java): ", "java");
            String bootVersion = promptForInput("Spring Boot Version (e.g., 3.3.2): ", "3.3.2");
            String group = promptForInput("Project Group ID (e.g., com.example): ", "com.example");
            String artifactId = promptForInput("Project Artifact ID (e.g., demo): ", "demo");
            String name = promptForInput("Project Name: ", "demo");
            String description = promptForInput("Project Description: ", "Demo project for Spring Boot");
            String packageName = promptForInput("Project Package Name: ", "com.example.demo");
            String packaging = promptForInput("Project Packaging type (e.g., jar): ", "jar");
            String javaVersion = promptForInput("Java Version (e.g., 11): ", "17");
            String dependencies = promptForInput("Dependencies (comma-separated): ", "web");
            String template = promptForInput("Dependencies Template(e.g., web,rest,or NA): ", "NA");

            if(template != null && !template.isEmpty() && !template.equalsIgnoreCase("na")){

                template = template.toLowerCase();
                if(TEMPLATES.containsKey(template)){

                    String s = TEMPLATES.get(template);

                    TreeSet<String> dependency_strings = Arrays.stream(dependencies.split(","))
                            .map(String::trim)  // Trimming each element
                            .collect(Collectors.toCollection(TreeSet::new));

                    String[] template_string = Arrays.stream(s.split(","))
                            .map(String::trim)
                            .filter(str -> !dependency_strings.contains(str))
                            .toArray(String[]::new);


                    StringBuilder mutable_dependency = new StringBuilder();

                    for(String str : template_string) mutable_dependency.append(str).append(",");

                    for(String str : dependency_strings) mutable_dependency.append(str).append(",");

                    dependencies = mutable_dependency.substring(0, mutable_dependency.length() - 1);

                }
                System.out.println();
            }


            String projectUrl = baseURL(type, language, bootVersion, group, artifactId, name, description, packageName, packaging, javaVersion, dependencies);

            try {
                downloadProject(projectUrl, artifactId + ".zip");
                return "Spring Boot project generated successfully.";
            } catch (Exception e) {
                return "Error occurred: " + e.getMessage();
            }

        }catch (Exception e){

            return "\nAborted! Caused due to: "+e.getMessage();
        }
    }

    private String promptForInput(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = System.console().readLine();
        return input.isEmpty() ? defaultValue : input;
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
        try (InputStream in = new ByteArrayInputStream(Objects.requireNonNull(restTemplate.getForObject(urlString, byte[].class)));
             FileOutputStream out = new FileOutputStream(outputFileName)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
