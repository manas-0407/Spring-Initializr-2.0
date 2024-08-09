package com.manas.custom_spring_initilizer.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.*;
import java.io.*;

@ShellComponent
public class Base {

    @Autowired
    CreationCommand creationCommand;

    @ShellMethod(key = "hello", value = "command to say hello")
    public String hello(
            @ShellOption(help = "Input name", defaultValue = "manas") String name,
            @ShellOption(help = "Input age", defaultValue = "21") String age) {
        return "Hello " + name + " age is " + age;
    }

    @ShellMethod(key = "spring", value = "Interactive Spring Project Generator")
    public String spring_initializr2(@ShellOption(help = "Subcommand for Spring", defaultValue = "help") String subcommand) {

        switch (subcommand.toLowerCase()){
            case "init":
                return creationCommand.init();

            default:
                return "Available command: \ninit \n";
        }
    }
}
