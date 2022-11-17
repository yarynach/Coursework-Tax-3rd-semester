package com.yarynach;

import com.yarynach.Menu.LoginMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        LoginMenu menu = new LoginMenu();
        while(true){
            System.out.println("""
                    Available commands:
                    1.Register
                    2.Login
                    3.Exit
                    """);
            String command = scan.next();
            menu.execute(command);
        }
    }
    }

