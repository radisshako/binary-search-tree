/* 
 * Defines a Binary Search Tree that contains Tree Nodes defined as an inner class "TNode",
 * with a key field for the account.
 * Contains a left Tree Node for the left child.
 * Contains a right Tree Node for the right child.
 * Allows Tree Nodes to be inserted, removed, updated,
 * Allows finding Tree Nodes, Traversing the tree in-order
 * Allows printing the path down the tree to where an item is or where it should be
 */
public class BankBST {

    /*Inner class defining a binary search tree node
     *The  key is for the bank account 
     *The left TNode is the left subtree
     *The right TNode is the right subtree
     */
    private class TNode{
        private Account key;
        private TNode left;
        private TNode right;

        /*Contructs a TNode object with the key set to the account
         * number passed in as a parameter.
         * Sets Left and Right TNode to null as default
         */
        public TNode(Account account){
            this.key = account;
            this.left = null;
            this.right = null;
        }
    }

    

    //TNode reference pointing to the root of the tree
    private TNode root;

    
    /*Private recursive method that adds a TNode to the BST
      Takes a TNode and an account object as parameters and returns a TNode
    */
    private TNode add(TNode t, Account acc){
        //If the tree is empty make a new TNode and make it the root
        if(t == null){
            TNode newNode = new TNode(acc);
            t = newNode;
        }
        else{
            //If the account key is equal to the TNode t key set its balance
            if(acc.getKey() == t.key.getKey()){
                t.key.setBalance(acc.getBalance());
            }
            //if the account key is smaller that the t node call add again for the left subtree
            else if(acc.getKey() < t.key.getKey()){
                t.left = add(t.left, acc);
            }
            //if the account key is greater than the t node call add again for the right subtree
            else{
                t.right = add(t.right, acc);
            }
        }
        return t;
    }
    
    //Public insert method that inserts the account by calling the private add method
    //takes an account object as a parameter
    public void insert(Account acc){
        root = add(root, acc);
    }


    /*Private recursive method that finds and returns the account TNode in relation
     to the rootNode and account number provided.
     Returns null if the account isn't found
     Takes a TNode and an account number as a parameter
     */
    private TNode findR(TNode rootNode, int accountNum){
        ////decare local TNode found
        TNode found;
        //if the tree is empty return null
        if(rootNode == null){
            found = null;
        }
        //if the rootNode key is equal to the accountNum sought set found to this TNode
        else if(rootNode.key.getKey() == accountNum){
            found = rootNode;
        }
        //if the accountNum is less than the rootNode, set found to the result of calling findR on the left subtree
        else if(accountNum < rootNode.key.getKey()){
            found = findR(rootNode.left, accountNum);
        }
        //if the accountNum is greater than the rootNode, set found to the result of calling findR on the right subtree
        else{
            found = findR(rootNode.right, accountNum);
        }
        //return found
        return found;
    }

    /*Prints the path to the node with the account specified
     * Or where it would be if it existed. 
     * Takes a TNode and an account number as parameters 
     */
    private void printPathR(TNode rootNode, int accountNum){
        //if the tree is empty print the account number only
        if(rootNode == null){
            System.out.print(accountNum + " ");
            return;
        }
        //if the root of the tree has the same key as account number print its key
        else if(rootNode.key.getKey() == accountNum){
            System.out.print(rootNode.key.getKey() + " ");
            return;
        }
        //if the accountNum is less than the rootNode key print the rootNode key
        // then call printPathR for the left subtree
        else if(accountNum < rootNode.key.getKey()){
            System.out.print(rootNode.key.getKey() + " ");
            printPathR(rootNode.left, accountNum);
        }
        //if the accountNum is greater than the rootNode key print the rootNode key
        // then call printPathR for the right subtree
        else{
            System.out.print(rootNode.key.getKey() + " ");
            printPathR(rootNode.right, accountNum);
        }
    }


    /* Public method that prints the path to an account node.
     * Takes an account number and calls printPathR
     * with the account number and the root node as parameters
     */
    public void printPath(int accountNum){
        printPathR(root, accountNum);
    }

    /* Public method that finds an account node.
     * Takes an account number and calls findR
     * with the account number and the root node as parameters
     * returns the TNode that is found, null if not found
     */
    public TNode find(int accountNum){
        TNode result = findR(root, accountNum);
        return result;
    }

    /*public method that updates the Account balance field of the Node specified
     * Takes the node to update and the balance to add/subtract as parameters
     *
     */
    public void update(TNode uNode, double balance){
        uNode.key.setBalance(balance);
    }


    /*Private method that removes the Node with the account number given
     * Takes a root TNode and an account nummber as parameters
     * Deletes a TNode from the tree,
     * Returns a TNode
     */
    private TNode removeR(TNode rootNode, int accountNum){
        //If the tree is empty return rootNode and do nothing
        if(rootNode == null){
            return rootNode;
        }

        //if the account number is less than the rootNode key,
        //set rootNode left field to another call of removeR on the rootNodes left subtree
        else if(accountNum < rootNode.key.getKey()){
            rootNode.left = removeR(rootNode.left, accountNum);
        }

        //if the account number is greater than the rootNode key,
        //set rootNode right field to another call of removeR on the rootNodes right subtree
        else if(accountNum > rootNode.key.getKey()){
            rootNode.right = removeR(rootNode.right, accountNum);
        }
        else{
            //if the left node is null return the right node
            if(rootNode.left == null){
                return rootNode.right;
            }
            //if the right node is null return the left node
            else if(rootNode.right == null){
                return rootNode.left;
            }
            //if both children are not null
            else{
                //replace the current node key with the smallest key from the right subtree
                rootNode.key = replacement(rootNode.right);
                //remove the original node from the right subtree
                rootNode.right = removeR(rootNode.right, rootNode.key.getKey());
            }
        }
        //return the node
        return rootNode;
    }

    /* public method that removes a node from the tree, 
     * it takes an account number and calls removeR
     * With the root and account number as paramters
     */
    public void remove(int accountNum){
        root = removeR(root, accountNum);
    }

    /*private method that finds the smallest Node in the right subtree
     * Takes a TNode as a parameter
     * Returns the Account of the smallest Node
     */
    private Account replacement(TNode rootNode){
        //assign to smallest the rootNodes key
        Account smallest = rootNode.key;
        //while rootNodes left field isnt null
        while(rootNode.left != null){
            //make smallest the key of the leftnode
            smallest = rootNode.left.key;
            rootNode = rootNode.left;
        }
        //return the smallest leftmost node
        return smallest;
    }


     /*Private method that takes a TNode as a parameter and
     * Traverses the tree in order printing the Bank account number and  balance
     */
    private void traverseR(TNode rootNode){
        //if the rootNode is empty return
        if(rootNode == null){
            return;
        }
        //Traverse the left subtree
        traverseR(rootNode.left);
        //Process the root
        System.out.println(rootNode.key.getKey() + " " + rootNode.key.getBalance());
        //Traverse the right subtree
        traverseR(rootNode.right);
    }

    /*Public method that calls the private traverseR method
     * with the tree roor passed in as a parameter
     */
    public void traverse(){
        traverseR(root);
    }
}
