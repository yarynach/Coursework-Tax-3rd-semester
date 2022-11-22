package com.yaryna.ch.tax.Taxes;

public  class Income {
    public int emp_id;
    public double income;
    protected String nameOfIncome;
    protected double taxedPercentage;



    public double taxedIncome;
    protected final double pm=2481;
    protected final double psp=pm/2;
    protected final double pspd=psp*1.5;
    protected final double grd=pm*1.4;
    protected final double vz=0.015;


    public Income(int emp_id, double income, String nameOfIncome, double taxedPercentage) {
        this.emp_id=emp_id;
        this.income = income;
        this.nameOfIncome = nameOfIncome;
        this.taxedPercentage = taxedPercentage;
    }

    public Income() {

    }

    public void setTaxedIncome() {
        taxedIncome=income*taxedPercentage;
    }

    public double getTaxedIncome() {
        return taxedIncome;
    }
    public double getIncome() {
        return income;
    }
    public void addToDB(){

    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public void setTaxedIncome(double taxedIncome) {
        this.taxedIncome = taxedIncome;
    }

    @Override
    public String toString() {
        return "Income{" +
                "income=" + income +
                '}';
    }

    public void setIncome(double income) {
        this.income = income;
    }
    public void addVz(){
        taxedIncome+=income*vz;
    }
    public void calc(){
        setTaxedIncome();
        addVz();
        addToDB();
    }
}
