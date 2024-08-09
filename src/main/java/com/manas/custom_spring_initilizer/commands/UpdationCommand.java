package com.manas.custom_spring_initilizer.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.*;
import java.io.*;

@ShellComponent
public class UpdationCommand {

    @ShellMethod(key = "cd", value = "Change working directory")
    public String changeDirectory(@ShellOption(help = "Path to the directory") String path) {
        File directory = new File(path);
        if (directory.exists() && directory.isDirectory()) {
            System.setProperty("user.dir", directory.getAbsolutePath());
            return "Changed directory to " + directory.getAbsolutePath();
        } else {
            return "Directory not found: " + path;
        }
    }


}
