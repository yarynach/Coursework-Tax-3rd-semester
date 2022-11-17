package com.yarynach.Menu.Commands;

import com.yarynach.Menu.Command;
import com.yarynach.Menu.Exit;
import com.yarynach.Menu.LoginCommand;
import com.yarynach.Menu.RegisterCommand;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommandMenu {
    private Map<String, Command> menuCommands;
    public CommandMenu(){
        menuCommands = new LinkedHashMap<>();
        menuCommands.put("1",new AddEmployeeCommand());
        menuCommands.put("3",new Exit());
        menuCommands.put("2", new ShowEmployees());
        menuCommands.put("4", new ShowSortedBySum());
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



