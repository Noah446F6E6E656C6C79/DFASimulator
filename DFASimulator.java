import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DFASimulator {
    private static DFASimulator Simulator;
    private int numStates;
    private Set<Integer> acceptStates;
    private int[][] transitionTable;

    public DFASimulator(String filePath) throws FileNotFoundException {
        //Initialize data structures
        Scanner sc = new Scanner(new File(filePath));
        numStates = sc.nextInt();
        transitionTable = new int[numStates+1][2];
        acceptStates = new HashSet<>();
        //read file line by line
        loadAcceptStates(sc, acceptStates);
        //populate transition table
        while(sc.hasNextInt()){
            int num1 = sc.nextInt();
            int num2 = sc.nextInt();
            int num3 = sc.nextInt();

            transitionTable[num1][num2] = num3;
        }

    }

    public boolean Simulate(String inputString){
        //Start at state 1
        int currentState = 1;
        //Loop through each character in inputstring
        for(int i = 1; i < inputString.length(); i++){
            int symbol = inputString.charAt(currentState);
            int character = symbol-'0';
            currentState = transitionTable[currentState][character];
        }
        //update current state using transition table
        //return true if the final state is in acceptStates, otherwise false
        if(acceptStates.contains(currentState)){
            return true;
        }else{
            return false;
        }

    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        //Ask user for DFA file path
        System.out.println("Enter file path: ");
        String file = scanner.nextLine();
        //initiate DFAsim using the file
        Simulator = new DFASimulator(file);
        //ask user for input string w
        System.out.println("Please input your string: ");
        String string = scanner.nextLine();
        //call simulator.Simulate(w)
        boolean isAccepted = Simulator.Simulate(string);
        //Print accept or reject based off result
        if(isAccepted){
            System.out.println("ACCEPT");
        }else{
            System.out.println("REJECT");
        }


    }
    private void loadAcceptStates(Scanner fileScanner, Set<Integer> acceptStates){
        fileScanner.nextLine(); //consume newline
        Scanner scan = new Scanner(fileScanner.nextLine());
        while(scan.hasNextInt()){
            acceptStates.add(scan.nextInt());
        }
        scan.close();

    }

}
