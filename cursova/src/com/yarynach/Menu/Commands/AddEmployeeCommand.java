package com.yarynach.Menu.Commands;

import com.yarynach.Menu.Command;
import com.yarynach.employees.Bookkeeping;
import com.yarynach.employees.Person;

public class AddEmployeeCommand implements Command {
    @Override
    public void execute() {
        Bookkeeping b=new Bookkeeping();
        b.calc();
    }
}
