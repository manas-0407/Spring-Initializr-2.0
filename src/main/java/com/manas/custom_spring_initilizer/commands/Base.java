package com.manas.custom_spring_initilizer.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.IOException;
import java.util.Arrays;

@ShellComponent
public class Base {

    @Autowired
    CreationCommand creationCommand;

    @Autowired
    UpdateCommand updationCommand;

    @Autowired
    PomCommands pomCommands;

    @ShellMethod(key = "hello", value = "command to say hello")
    public String hello(
            @ShellOption(help = "Input name", defaultValue = "manas") String name,
            @ShellOption(help = "Input age", defaultValue = "21") String age) {
        return "Hello " + name + " age is " + age;
    }

    @ShellMethod(key = "spring init", value = "Interactive Spring Project Generator")
    public String spring_initializr2() {
        return creationCommand.init();
    }

    @ShellMethod(key = "spring cd", value = "Change working directory")
    public String changeDirectory(@ShellOption(help = "Path to the directory") String root) {
        return updationCommand.changeDirectory(root);
    }

    @ShellMethod(key = "spring cd ..", value = "List current directory")
    public String clearCurrentDir() {
        return updationCommand.clearCurrDirectory();
    }

    @ShellMethod(key = "spring root --ls", value = "List current directory files")
    public String directoryListFiles() {
        return updationCommand.list_items();
    }

    @ShellMethod(key = "spring root", value = "List current directory")
    public String currentDir() {
        return updationCommand.currentDirectory();
    }

    // Spring properties for Application.properties edit
    @ShellMethod(key = "spring properties", value = "Add Boilerplate to app.prop")
    public String add_properties(@ShellOption(help = "Dependencies (comma-separated): ") String dependencies) throws IOException {
        String[] dependency = Arrays.stream(dependencies.split(","))
                .map(String::trim)
                .toArray(String[]::new);

        return updationCommand.add_properties_to_dir(dependency);
    }

    @ShellMethod(key = "spring error" , value  = "Get help for your error")
    public String error_fix(
            @ShellOption(help = "Log file name", defaultValue = "logfile.log") String file,
            @ShellOption(help = "Description for error", defaultValue = "") String description) {

        try {
            return updationCommand.error_fix(file, description);
        } catch (IOException e) {
            return "Error Occurred";
        }
    }

    @ShellMethod(key = "spring versionCheck" , value  = "Get info for latest update of dependencies")
    public String versionCheck(
            @ShellOption(value = "--update", defaultValue = "false") boolean update){
        try{
            return pomCommands.dependencyVersionCheck(update);
        }catch (Exception e){
            return "Error occurred";
        }
    }
}
