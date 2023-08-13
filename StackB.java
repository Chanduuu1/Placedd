import java.util.*;

class stackUsingAL{
    ArrayList<Integer> list= new ArrayList<>();
    
    public boolean isEmpty(){
        return list.size() == 0;
    }
    
    public void push(int data){
        list.add(data);
    }

    public int pop(){
        if(isEmpty()){
            return -1;
        }
        int top = list.get(list.size() - 1);
        list.remove(list.size() - 1);
        return top;  // returns the element that has been just removed
    }

    public int peek(){
        if(isEmpty()){
            return -1;
        }   
        return list.get(list.size() - 1);
    }
}

class stackUsingLL{
    public class Node{
        int data;
        Node next;
        public Node(int data){
            this.data = data;
            this.next = null;
        }
    }
    public Node head;
    public Node tail;

    //push operation using add first
    public boolean isEmpty(){
        return head == null;
    }
    public void push(int data){
        Node newNode = new Node(data);
        if(head == null){
            head = tail = newNode;
            return;
        }
        newNode.next = head;
        head = newNode;
        // right now head is pointing at the top of the stack
    }

    public int pop(){
        if(head == null){
            System.out.println("the stack is empty");
            return -1;
        }
        int val = head.data;
        head = head.next;
        return val;
    }

    public int peek(){
        if(head == null){
            System.out.println("the stack is empty");
            return -1;
        }
        return head.data;
    }
}

public class StackB{
    public static void pushAtBottom(Stack<Integer> s, int data){
        if(s.isEmpty()){
            s.push(data);
            return;
        }
        int val = s.pop();
        pushAtBottom(s,data);
        s.push(val);
    }

    public static String printReverseString(String str){
        Stack<Character> s = new Stack<>();
        int i = 0;
        while(i < str.length()){
            s.push(str.charAt(i));
            i++;
        }
        StringBuilder result = new StringBuilder("");
        while(!s.isEmpty()){
            char ch = s.pop();
            result.append(ch);
        }

        return result.toString();

    }

    public static void reverseStack(Stack<Integer> s){
        // base case
        if(s.isEmpty()){
            return;
        }
        //main step
        int val = s.pop();
        reverseStack(s);
        pushAtBottom(s,val);
    }

    public static void showStack(Stack<Integer> s){
        while(!s.isEmpty()){
            System.out.println(s.peek());
            s.pop();    
        }
    }

    public static void main(String[] args){
        Stack<Integer> s = new Stack<>();
        s.push(1);
        s.push(2);
        s.push(3);
        reverseStack(s);
        showStack(s);
        

        
    }
}
