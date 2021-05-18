
package algorithms;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MathComp_Project_2 extends JFrame{
    
    //GLC : 
    // S-> [0-9] | SOS | OS | SO
    // O-> +,-,*,
    MyABB<String> tree;
    String[] myChars = {"0", "1", "2","3","4","5","6","7","8","9","*","-","+","(",")"};
    char[] myOperators = {'+','-','*','(',')'};
    char[] myDigits = {'0','1','2','3','4','5','6','7','8','9'};
    //String myString = "2+3*5-4";
    //String myString = "4+5+3";
    //String myString = "4+5+((2+3))+5";
    String myString = "4+5+(2+3)";
    //String myString = "2*4+5+";
    Queue<String> result = new LinkedList<>();
    int index;
    
    
    public void Check(){
        tree.insert("S", 0);
        if(myString.length() >= 3){
            if(checkSOS()){
                System.out.println("Correcto!!!");
            }else{
                System.out.println("Cadena No Válida");
            }
        }
        preorden(tree.root);
        System.out.println("");
        //printLevelOrder();
    }
    
    public boolean checkSOS(){
        tree.insert("S", 0);
        if(ContainsDigits(myString.charAt(index)) ){
            tree.insert(String.valueOf(myString.charAt(index)), 0, true);
        }else
            return false;
        
        tree.insert("O", 1);
        index++;
        if(ContainsOperators(myString.charAt(index)) ){
            tree.insert(String.valueOf(myString.charAt(index)), 1, true);
        }else
            return false;
        
        tree.insert("S", 2);
        index++;
        if(myString.length() - index <= 1){
            if(ContainsDigits(myString.charAt(index)) || myString.charAt(index) == ')'){
                tree.insert(String.valueOf(myString.charAt(index)), 2, true);
                return true;
            }
        } else if(myString.charAt(index) == '(' || myString.charAt(index) == ')'){
            return checkOS();
        }else if(myString.length() - index >= 3){
            return checkSOS();
        }else{
            return checkSO();
        }
        /*if(ContainsDigits(myString.charAt(index)) ){
            
        }else{
            if(myString.charAt(index) == '('){
                checkOS();
            }else if(myString.length() - index >= 3){
                checkSOS();
            }else{
                checkOS();
            }
        }*/
        
        
        index++;
        return true;
    }
    
    public boolean checkOS(){
        tree.insert("O", 0);
        //System.out.println("Suppose O: " + myString.charAt(index));
        if(ContainsOperators(myString.charAt(index)) ){
            tree.insert(String.valueOf(myString.charAt(index)), 0, true);
        }else
            return false;
        
        tree.insert("S", 1);
        index++;
        
        if(myString.length() - index <= 1){
            if(ContainsDigits(myString.charAt(index)) ){
                tree.insert(String.valueOf(myString.charAt(index)), 1, true);
                return true;
            }
        }else if(myString.charAt(index) == '(' || myString.charAt(index) == ')'){
            return checkOS();
        }else if(myString.length() - index >= 3){
            return checkSOS();
        }else{
            return checkOS();
        }
        
        /*if(ContainsDigits(myString.charAt(index)) ){
            
        }else{
            if(myString.charAt(index) == '(' || myString.charAt(index) == ')'){
                return checkOS();
            }else if(myString.length() - index >= 3){
                return checkSOS();
            }else{
                return checkOS();
            }
        }*/
        
        index++;
        return true;
    }
    
    public boolean checkSO(){
        tree.insert("S", 0);
        //System.out.println("Suppose O: " + myString.charAt(index));
        if(ContainsDigits(myString.charAt(index)) ){
            tree.insert(String.valueOf(myString.charAt(index)), 0, true);
        }else
            return false;
        
        tree.insert("O", 1);
        index++;
        
        /*if(myString.length() - index <= 1){
            if(ContainsOperators(myString.charAt(index)) ){
                tree.insert(String.valueOf(myString.charAt(index)), 1, true);
                return true;
            }
        }else if(myString.charAt(index) == '(' || myString.charAt(index) == ')'){
            return checkOS();
        }else if(myString.length() - index >= 3){
            return checkSOS();
        }else{
            return checkOS();
        }*/
        if(myString.charAt(index) == ')'){
            tree.insert(String.valueOf(myString.charAt(index)), 1, true);
            return true;
        }else
            return false;
        
        //index++;
        //return true;
    }
    
    public boolean ContainsDigits(char x){
        for (char c : myDigits) {
            if (x == c) {
                return true;
            }
        }
        return false;
    }
    public boolean ContainsOperators(char x){
        for (char c : myOperators) {
            if (x == c) {
                return true;
            }
        }
        return false;
    }
    
    public void preorden(NodoABB<String> nodo){
        if(nodo != null){
            System.out.print(nodo.value+",");
            if(nodo.left == null && nodo.mid == null && nodo.right == null){
                result.add(nodo.value);
            }
            preorden(nodo.left);
            preorden(nodo.mid);
            preorden(nodo.right);
        }
    }
    
    void printLevelOrder()
    {
        Queue<NodoABB> queue = new LinkedList<>();
        queue.add(tree.root);
        while (!queue.isEmpty())
        {
            NodoABB tempNode = queue.poll();
            System.out.print(tempNode.value + " ");
 
            /*Enqueue left child */
            if (tempNode.left != null) {
                queue.add(tempNode.left);
            }
            
            if (tempNode.mid != null) {
                queue.add(tempNode.mid);
            }
            
            /*Enqueue right child */
            if (tempNode.right != null) {
                queue.add(tempNode.right);
            }
        }
    }
    
    public boolean ParenthesisAnalyzer(String cadena){
        Stack myStack = new Stack();
        
        for(int x = 0; x < cadena.length();x++){
            if(cadena.charAt(x) == '{' || cadena.charAt(x) == '[' || cadena.charAt(x) == '(')
                myStack.push(cadena.charAt(x));
            
            if(cadena.charAt(x) == '}' || cadena.charAt(x) == ']' || cadena.charAt(x) == ')'){
                //System.out.println("MY X :  " + x + " size: " + myStack.size());
                if(myStack.size() <= 0){
                    //System.out.println("NOOOOOOO VALIDO" + "x val : " + x );
                    break;
                }
                //System.out.println("MY X :  " + x + " size: " + myStack.size() + " " + cadena.charAt(x)); 
                //System.out.println(myStack.pop());
                
                if(cadena.charAt(x) == '}'){
                    if(!myStack.pop().equals('{')){
                    //System.out.println("INSIDEEEE   MY X :  " + x + " size: " + myStack.size() + " " + cadena.charAt(x));    
                    
                        //System.out.println("NOOOOOOO VALIDO" + "x val : " + x );
                        break;
                    }
                }
                else if(cadena.charAt(x) == ']'){ 
                    if(!myStack.pop().equals('[')){
                    
                        //System.out.println("NOOOOOOO VALIDO" + "x val : " + x );
                        break;
                    }
                }else if(cadena.charAt(x) == ')'){
                    if(!myStack.pop().equals('(')){
                    
                        //System.out.println("NOOOOOOO VALIDO" + "x val : " + x );
                        break;
                    }
                }
                /*if(String.valueOf(cadena.charAt(x)) != myStack.peek() ){
                    System.out.println("NOOOOOOO " + "x val : " + x + );
                    
                    break;
                }*/
                //myStack.push(cadena.charAt(x));
                //System.out.println("MY X :  " + x + " size: " + myStack.size());
            }
            //System.out.println("SIIIIIIIIIIII " + myStack.size());
        }
        //System.out.println("SIIIIIIIIIIII " + myStack.size());
        if(myStack.size() <= 0){
            //System.out.println("SIIIIIIIIIIII VALIDO");
            return true;
        }else
            return false;
    }
    
    /*@Override
    public void paintComponent(Graphics g) {
        // Draw Tree Here
        g.drawOval(5, 5, 25, 25);
    }*/
    
    public MathComp_Project_2(){
        /*super("N Tree");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);
        
        this.setVisible(true);*/
    }
    private Point a = new Point(400, 100),
                  b = new Point(50, 550),
                  c = new Point(750, 550);
    private int level = 5;
    private void drawTree(Graphics g, Point a ,  Point b){
        //g.drawLine(a.x, a.y, b.x, b.y);
        //g.drawOval(a.x, a.y, b.x, b.y);
        //g.drawOval(this.getWidth()/2, 50, 50, 55);
        
        //char[] text = {'S', 'H'};
        //g.drawS(text,50,this.getWidth()/2, 50, 50);
        //g.drawString("S", this.getWidth()/2, 80);
        
        //System.out.println(queue.size());
        
        Queue<NodoABB> queue = new LinkedList<>();
        int x = this.getWidth()/2, y = 80;
        /*while(!queue.isEmpty()){
            g.drawString((String) queue.poll().value, x, y);
            
            y+= 50;
        }*/
        
        queue.add(tree.root);
        while (!queue.isEmpty())
        {
            NodoABB tempNode = queue.poll();
            //System.out.println("Size: " + queue.size());
            g.drawString((String)tempNode.value, x, y);
            System.out.print(tempNode.value + " ");
 
            /*Enqueue left child */
            if (tempNode.left != null) {
                queue.add(tempNode.left);

            }
            
            if (tempNode.mid != null) {
                queue.add(tempNode.mid);

            }
            
            /*Enqueue right child */
            if (tempNode.right != null) {
                queue.add(tempNode.right);

            }
            
            y += 50;
            
        }
    }
    
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        //System.out.println("IMPRIME");
        this.drawTree(g, this.a, this.b);
    }
    
    public static void main(String[] args){
        //String cadena = "{()[()]}";
        
        
        /*
        JFrame jFrame = new JFrame();
        jFrame.add(new JPanel());
        jFrame.setSize(500, 500);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        */
         

        MathComp_Project_2 obj = new MathComp_Project_2();
        obj.tree = new MyABB<>();
        /*if(obj.ContainsDigits(obj.myString.charAt(3)) ){
            System.out.println("WOW");
        }*/
        if(obj.ParenthesisAnalyzer(obj.myString)){
            
        
            obj.Check();
            System.out.println("");
            for(String c : obj.result){
                System.out.print(c);
            }
            System.out.println("");
        }else
            System.out.println("Cadena No Válida");
        
    }
}
