package bankex;

public class BankAccount {
    private double  balance;

    public BankAccount( double balance) {
        if(balance < 0){
            this.balance = 0;
        }else{
            this.balance = balance;
        }
        
        
    }


    public void deposit(double amount){
        if(amount <= 0){
            return;
        }
        this.balance += amount;
    }
    
    public void withdraw(double amount){
        if(amount <= 0){
            return;
        }

    
        if(this.balance - amount < 0){
            return;
        }
        this.balance -= amount;        
        }

    public String showBalance(){
        
        return String.valueOf(this.balance);
    }
    }

    

