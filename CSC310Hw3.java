/*
    CSC 310 HW 3
Author: Charles Ryan Barrett
Sources used:
(modified code) from: https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java)
   
 */
import java.util.*; //need to import array list since a normal array needs definitive size

class Stack{ //main class to handle stacks
    List<Integer> L = new ArrayList<Integer>();//making an empty list
    int n = 0; //this will hold the lenght of the list.
    
    boolean is_empty(){ //method to check if the list is empty
        return L.isEmpty();
    }
    
    int top(){//method to show what the last entered value is
        if(!is_empty()){
            return L.get(n-1);          
        }
        else
            throw new java.lang.Error("List is empty");           
    }
    
    int pop(){//removes the top element

        //first I will make sure that the list is not emtpy
        if(!is_empty()){
            int tmp = top();
            L.remove(n-1);
            n-=1;//updating the length of the list
            return tmp;
        }
        else
            throw new java.lang.Error("List is empty");      
    }
    
    void push(int x){//method to do the push operation
        L.add(x);//add the value entered
        n+=1;//increment the length of the list
    }
    
    void getMin(){ //returns minimum value in list
        int min = 0;
        if(!is_empty())
            for(int x = 0; x < n; x++){
               if(min > L.get(x)){
                   min = L.get(x);
               } 
            }
        System.out.println(min);
    }
}//end of stack class


public class CSC310Hw3 {   
    //creating the two stacks
    static Stack val = new Stack();
    static Stack2 op = new Stack2();/*How java operates, lists cannot be mixed. 
    So I had to create two different stack classes. One for numeric elements and 
    one for string elements.*/
    
    //methods for problem #2
    public static void EvalExp(String s){ //main evaluating method
        String tmp;
        //I must convert the string into either integers or characters
        for (int i = 0; i < s.length();) {
            
            //tmp will be the value that is to be pushed into one of the stacks
            int k = s.indexOf(' ',i);
            if(k == -1){ //space not found, end of string
                tmp = s.substring(i);
                i = s.length(); //ends the loop
            }
            else{
                tmp = s.substring(i,k);  
                i = k+1;
            }
            //now to check if it is numeric
            if(isNumeric(tmp)){
                val.push(Integer.parseInt(tmp));
            }
            else{
                repeatOps(tmp);
                op.push(tmp);
            }          
        }//end of loop  
    repeatOps("$");
    if(op.is_empty() && !val.is_empty()){
        System.out.println(val.top());
    }
      
    }//end of evalexp
    
    public static void repeatOps(String s){ //calls doOp as many times as needed
        while (val.n > 1 && (prec(s) <= prec(op.top()))){ 
            doOp();
        }
    }
    
    public static void doOp(){ //does the specified operations
        int x = val.pop();
        int y = val.pop();
        String opper = op.pop();
        switch(opper){
            case "+":
                val.push(y+x);
                break;
            case "-":
                val.push(y-x);
                break;
            case "*":
                val.push(x*y);
                break;
            case "/":
                val.push(y/x);
            case "<":
                System.out.println(y<x);
                break;
            case "<=":
                System.out.println(y<=x);
                break;
            case ">":
                System.out.println(y>x);
                break;
            case ">=":
                System.out.println(y>=x);
                break;
            default:
                System.out.println("Illegal character");
                break;         
        }
        
    }//end of doOp
    
    //methods for problem #3
    public static int prefix(String s){ //method for eval. post-fix notation       
        Stack pref = new Stack(); //creating the stack
        for (int i = 0; i < s.length(); i++) {
            String tmp = ""+s.charAt(i);
            if(isNumeric(tmp)){
                pref.push(Integer.parseInt(tmp));
            }
            else if(!pref.is_empty()){
                int y = pref.pop();
                int x = pref.pop();
                switch(tmp){
                    case "+":
                        pref.push(x+y);
                        break;
                    case "-":
                        pref.push(x-y);
                        break;
                    case "*":
                        pref.push(x*y);
                        break;
                    case "/":
                        pref.push(x/y);
                        break;
                    default:
                        break;
                }
            }
        }//end of loop
        
        
        return pref.top();
    }
    
    
    public static void main(String[] args) {
       //Testing Values for problem #1
        System.out.println("Area for problem #1");
        Stack MinStack = new Stack(); //creating stack object
        MinStack.push(-2);
        MinStack.push(0);
        MinStack.push(-3);
        MinStack.getMin();
        MinStack.pop();
        System.out.println(MinStack.top());
        MinStack.getMin();
        System.out.println("");
        
       //Area of work for problem #2
       
       Scanner in = new Scanner(System.in);
        System.out.println("Area for problem #2");
        System.out.println("Enter an Arithmetic Expression: ");
        String input = in.nextLine();
        EvalExp(input);
        System.out.println("");
        
        //Area of work for problem #3
        
        System.out.println("Area for problem #3");
        String pf = "52+83-*4/";
        System.out.println(pf+" = "+prefix(pf));
        
       
       
        
    }//end of main
    
    
    public static boolean isNumeric(String str)  //code taken from source in header
        {  
          try  
          {  
            int i = Integer.parseInt(str);  
          }  
          catch(NumberFormatException nfe)  
          {  
            return false;  
          }  
          return true;  
        }
    public static int prec(String s){//this method will asign precedent values to operators
        int i;
        switch(s){
            case "$":
                i = -1;
            case "<":
            case "<=":
            case ">":
            case ">=":
                i = 0;
                break;
            case "+":
            case "-":
                i = 1;
                break;
            case "*":
            case "/":
                i = 2;
                break;
            default:
                i = 0;
                break;                
        }
        return i;
    }
    
    /*This is nearly identical to the first stack class, but uses string
        instead of integers and lacks a min method*/
    static class Stack2{ //main class to handle stacks
    List<String> L = new ArrayList<String>();//making an empty list
    int n = 0; //this will hold the lenght of the list.
    
    boolean is_empty(){ //method to check if the list is empty
        return L.isEmpty();
    }
    
    String top(){//method to show what the last entered value is
        if(!is_empty()){
            return L.get(n-1);          
        }
        else
            throw new java.lang.Error("List is empty");           
    }
    
    String pop(){//removes the top element

        //first I will make sure that the list is not emtpy
        if(!is_empty()){
            String tmp = top();
            L.remove(n-1);
            n-=1;//updating the length of the list
            return tmp;
        }
        else
            throw new java.lang.Error("List is empty");      
    }
    
    void push(String x){//method to do the push operation
        L.add(x);//add the value entered
        n+=1;//increment the length of the list
    }
    

}//end of stack2 class
    
}
