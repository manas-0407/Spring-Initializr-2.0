package com.manas.custom_spring_initilizer.commands;

import com.manas.custom_spring_initilizer.constants.ApplicationPropertiesConstants;
import com.manas.custom_spring_initilizer.service.AI_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class UpdateCommand {

    @Autowired
    AI_Service aiService;

    public String changeDirectory(String path) {
        File directory = new File(path);
        if (directory.exists() && directory.isDirectory()) {
            System.setProperty("user.dir", directory.getAbsolutePath());
            return "Changed directory to " + directory.getAbsolutePath();
        } else {
            return "Directory not found: " + path;
        }
    }

    public String add_properties_to_dir(String[] dependencies) throws IOException {

        String childPath = "src/main/resources/application.properties";
        List<String> propertyLines = readFileLines(childPath);
        StringBuilder response = new StringBuilder();

        outer: for(String dependency : dependencies){

            ArrayList<String> list = ApplicationPropertiesConstants.map.get(dependency);

            for(String property : list){
                String[] separatedProperties = property.split("=");
                if(containsDependency(separatedProperties[0] , propertyLines)) {
                    response.append("Already Contained dependency: ").append(dependency).append("\n");
                    continue outer;
                }
            }
            int first = 1;

            for(String property : list){
                boolean status = updateApplicationProperties(property, first-- > 0);
                if(!status){
                    response.append("Failed adding for dependency: ").append(dependency).append("\n");
                    continue outer;
                }
            }

            response.append("Added dependency: ").append(dependency).append("\n");
        }

        return response.toString();
    }

    public boolean updateApplicationProperties(String property, boolean firstAppend) {
        String childPath = "src/main/resources/application.properties";
        File propertiesFile = new File(System.getProperty("user.dir"), childPath);
        if (propertiesFile.exists()) {
            try (FileWriter writer = new FileWriter(propertiesFile, true)) {
                if(firstAppend) writer.write("\n");
                writer.write(property + "\n");
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean containsDependency(String key, List<String> lines){

        for(String line : lines)
            if(line.contains(key.toLowerCase()))
                return true;

        return false;
    }

    public String list_items(){
        File directory = new File(System.getProperty("user.dir"));

        if (!directory.exists()){
            return "No directory specified";
        }

        if(!directory.isDirectory()) {
            return "Invalid directory path!";
        }
        String currentDirectory = directory.getAbsolutePath();
        String[] filesList = directory.list();
        if (filesList != null && filesList.length > 0) {
            return "Files in directory " + currentDirectory + ":\n" +
                    Arrays.stream(filesList).collect(Collectors.joining("\n"));
        } else {
            return "The directory is empty.";
        }
    }

    public String currentDirectory(){

        if(System.getProperty("user.dir") == null) return "No root directory";

        File directory = new File(System.getProperty("user.dir"));

        if (!directory.exists()){
            return "No directory specified";
        }

        if(!directory.isDirectory()) {
            return "Invalid directory path!";
        }

        return directory.getAbsolutePath();
    }

    public String clearCurrDirectory(){
        if (System.getProperty("user.dir") != null) {
            System.clearProperty("user.dir");
            return "Cleared Saved directory path.";
        } else {
            return "No directory path is currently saved.";
        }
    }

    public List<String> readFileLines(String childFilePath) throws IOException{
        File directory = new File(System.getProperty("user.dir")+"/"+childFilePath);

        if (!directory.exists())
            return null;

        return Files.readAllLines(Path.of(directory.getPath()));
    }

    public String error_fix(String file_name, String description) throws IOException {
        List<String> logLines = readFileLines(file_name);

        if(logLines == null)
            return "No directory specified";

        List<String> filterLine = new ArrayList<>();

        int nextLinePick = 0;
        boolean firstError = true;
        for(String lines : logLines){
            if(lines.contains("ERROR")){
                if(!firstError) break;
                filterLine.add(lines);
                nextLinePick = 2;
                firstError = false;
            }
            if(nextLinePick>0){
                filterLine.add(lines);
                nextLinePick--;
            }
            if(lines.contains("Caused by"))
                filterLine.add(lines);
        }

        StringBuilder prompt = new StringBuilder();

        for(String lines : filterLine){
            prompt.append(lines).append("\n");
        }
        prompt.append(description);

        // Empty Prompt Handle
        if(prompt.isEmpty())
            return "No error";

        return aiService.generate_response(prompt.toString()); // API CALL
    }

    void writingInFile(String prompt) {
        File filePath = new File("C:\\Users\\manas\\IdeaProjects\\custom-spring-initilizer\\PromptFile.txt");

        if (filePath.exists()) {
            try (FileWriter writer = new FileWriter(filePath, true)) {
                writer.write(prompt);
                writer.write(System.lineSeparator()); // Adds a newline after each prompt
            } catch (IOException e) {
                e.printStackTrace(); // Print the stack trace to help with debugging
                throw new RuntimeException("Error while writing to the file: " + filePath, e);
            }
        } else {
            throw new RuntimeException("File does not exist: " + filePath);
        }
    }


}
