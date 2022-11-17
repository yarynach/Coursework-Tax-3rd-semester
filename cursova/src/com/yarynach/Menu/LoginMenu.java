package com.yarynach.Menu;

import com.yarynach.Menu.Commands.ShowSortedBySum;
import com.yarynach.User;

import java.util.LinkedHashMap;
import java.util.Map;

public class LoginMenu {

    private Map<String,Command> menuCommands;
    public LoginMenu(){
    menuCommands = new LinkedHashMap<>();
    menuCommands.put("1",new RegisterCommand());
    menuCommands.put("2",new LoginCommand());
    menuCommands.put("3", new Exit());
    }
    public void execute(String command){
        Command cmd = menuCommands.get(command);
        if(cmd!=null){
            cmd.execute();
        }else{
            System.out.println("Incorrect command");
        }
        }
    }

