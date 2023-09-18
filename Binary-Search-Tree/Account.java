/*  
    External class for a bank account containing 
    an integer key for the account number and
    a double balance for the account balance*/
public class Account {
    //private key field to hold bank account number
    private int key;
    //private key field to hold bank account balance
    private double balance;

    /*Contructor that intialises an account object with
     the account number and balance given*/
    public Account(int key, double balance){
        this.key = key;
        this.balance = balance;
    }

    //Gets the bank account number or key
    public int getKey(){
        return key;
    }

    //Gets the bank account balance
    public double getBalance(){
        return balance;
    }

    /*Sets the account balance by adding or subtracting
     the double amount given as a parameter from the balance*/
    public void setBalance(double amount){
        //if the amount is negative subtract it from the balance
        if (amount < 0){
            this.balance -= Math.abs(amount);
        } 
        //if amount is positive add it to the balance
        else{
            this.balance += amount;
        }
    }
    
}
