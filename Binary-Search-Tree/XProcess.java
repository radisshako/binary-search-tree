//import java file and scanner package
import java.io.File;
import java.util.Scanner;


/*
 * XProcess class that opens the file entered by the user
 * and if the data is valid, parses its contents into local variables.
 * This data is then used to either initialises a bank account with the 
 * amount specified, or deposits or withdraws an amount from an exisitng bank account.
 * Also closes bank accounts where specified. Outputs the path to each Node before an
 * operation occurs and the type of operation at the end of the line.
 * Completes an in-order traversal of the BST displaying the account number and balance on each line
 */
public class XProcess {

    //BankBST object bst intialised as private member variable
    private static BankBST bst = new BankBST();

    public static void main(String[] args){
        //if no filename is entered print an error message and return
        if(args.length != 1){
            System.err.println("Please enter a single file name in form: java Xprocess transactions.txt");
            return;
        }
        
        //line variable for each line in txt file
        String line = "";
        //array to store elements of the line
        String[] lineArray;
        //the account number of the account to operate on
        int accountNum;
        //the type of operation to carry out
        String type;
        //the amount to either deposit or withdraw from the account
        double amount;

        //try to read in the file and process it
        try{
            //decalre scanner object to read the file entered by user
            Scanner scanner = new Scanner(new File(args[0]));

            //while there are more lines to read
            while(scanner.hasNextLine()){

                //assign to line the entire line with leading and trialing spaces removed
                line = scanner.nextLine().trim();
                //Split the line by the space characters, and add to the lineArray
                lineArray = line.split("\\s+");

                /*If the line doenst have 3 elemenst and its second element isnt w c or d skip this loop[ iteration */
                if(lineArray.length != 3 || (!lineArray[1].equals("w") && !lineArray[1].equals("d") && !lineArray[1].equals("c"))){
                    //skip this loop iteration
                    continue;
                }
                //Try to parse the accountNum and amount elements to their data type and assign them to their local variable
                try{
                    //assign the first item to accountNum variable parsing it to an integer
                    accountNum = Integer.parseInt(lineArray[0]);
                    //assign third item to amount variable parsing it to a double
                    amount = Double.parseDouble(lineArray[2]);
                }
                catch(NumberFormatException ex){
                    //If the first and last element(accopunt number and amount to add/withdraw) are not able to be converted to numbers
                    //skip this line iteration on the loop
                    continue;
                }
                //assing the second item to type variable
                type = lineArray[1];

                //if the account doesn't exist
                if(bst.find(accountNum) == null){
                    //print the path so far
                    bst.printPath(accountNum);

                    //if the type is a deposit make a new account and add amount to it
                    if(type.equals("d")){
                        //make a new account with zero balance
                        Account newAccount = new Account(accountNum, 0.0);
                        //add the amount to the account
                        newAccount.setBalance(amount);
                        //insert our new account to the bst
                        bst.insert(newAccount);

                        //print out DEPOSIT then print a new line
                        System.out.print("DEPOSIT");
                        System.out.println();
                    }
                    //if the type is withdrawal make a new account and subtract the account from it
                    else if(type.equals("w")){
                        //make a new account with zero balance
                        Account newAccount = new Account(accountNum, 0.0);
                        //subtract the withdrawal amount from the account
                        newAccount.setBalance(-amount);
                        //add the new account to the bst
                        bst.insert(newAccount);

                        //print withdraw and then print a new line
                        System.out.print("WITHDRAW");
                        System.out.println();
                    }
                }
                //if an account is found with the key
                else{
                    //print the path so far
                    bst.printPath(accountNum);

                    //if the operation is a deposit add amount to the existing account
                    if(type.equals("d")){
                        //update the existing node by adding the amount
                        bst.update(bst.find(accountNum), amount);

                        //print deposit then print a new line
                        System.out.print("DEPOSIT");
                        System.out.println();

                    }
                    //if the operation is a withdrawal subtract the amount from the account
                    else if (type.equals("w")){
                        //update the existing node key by subtracting the amount
                        bst.update(bst.find(accountNum), -amount);

                        //print withraw then print a new line
                        System.out.print("WITHDRAW");
                        System.out.println();
                    }
                    //if the operation is a closure of an account, remove the account from the tree
                    else if(type.equals("c")){
                        //remove this account
                        bst.remove(accountNum);

                        //print close then print a new line
                        System.out.print("CLOSE");
                        System.out.println();
                    }

                }
                

            }
            //Print out a line with result
            System.out.println("RESULT");

        }
        //In case of an error
        catch(Exception ex){
            //write out the exception and the line it occured on 
            System.err.println("Exception type: "+ ex + " " + line);
        }
        //call the traverse method to do in-order traversal of the bst
        bst.traverse();
    }
}
