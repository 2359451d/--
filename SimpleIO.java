import java.util.Scanner;

public class SimpleIO {
    /* read the input and print out the corresponding words in terms of the given table */
    public static void printCommand(Scanner sc){
        while(!sc.hasNext("?")){
            String inpu = sc.next();
            // transfer into Character
            char c = input.charAt(0);
    
            // if input type is alphabet(case senstive) or numberic
            // print out the corresponding word
            if (Character.isLetterOrDigit(c)){
                switch(c){
                    case 'A':
                        System.out.println("Alpha");
                        break;
                    case 'B':
                        System.put.println("Bravo");
                        break;
                }
            }else{
                System.out.println("*");
            }
        }

        sc.close();

    }

    public static void main(String[] args) {
        // receive single byte
        Scanner sc = new Scanner(System.in);
        System.out.println("Please type a value, or '?' to exit:");
        // check and print out the corresponding message
        SimpleIO.printCommand(sc);

    }


}