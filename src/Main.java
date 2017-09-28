/*
The functionality of this program is simple, from the users perspective. When running
this program, you must pass in 2 arguments, the train.dat file and the test.dat file.
Once these are generated, the program will create a 2 dimensional arraylist of arraylists,
these hold the data that the decision tree will be made on. Next the program will iterate
through the arraylist and calculate the information gain at each node. Branching on
whatever node has the highest information gain. Finally, the tree is constructed using a
linked list with 2 children which are the branches.

We simply iterate through our decision tree array using a Depth First Search algorithm to
print out the tree to the console. We test the accuracy of the tree in a similar fashion.
We iterate through the test data and if the selection is equal to what we selected, we can
increment the "correctness" by 1. Finally we can perform simple math to determine the
percentage of correctness.

*/
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
/*

*/

public class Main {

    public static void main(String[] args) {
        // System.out.println("Hello World!");
        int numOfZeros = 0, numOfOnes = 0;
        //System.out.println("Params?: " + args[1]);
        //System.out.println("Params?: " + args[0]);
        ArrayList<ArrayList<String>> decisionData = fileToArrayList(args[0]);
        ArrayList<ArrayList<String>> testingData = fileToArrayList(args[1]);


        //Split the array based on the class being one or zero
        int numOnes = 0;
        int numZeros = 0;
        ArrayList<ArrayList<String>> decisionOnes = new ArrayList<ArrayList<String>>();
        //decisionOnes.add(new ArrayList<String>());
        ArrayList<ArrayList<String>> decisionZero = new ArrayList<ArrayList<String>>();
        //decisionZero.add(new ArrayList<String>());
        ArrayList<Node> listOfNodes0 = new ArrayList<Node>();
        ArrayList<Node> listOfNodes1 = new ArrayList<Node>();


        for(int x=0; x<decisionData.get(decisionData.size()-1).size(); x++) {
            ArrayList<String> data = new ArrayList<String>();
            if(x==0){
                for(int y = 0; y<decisionData.size()-1; y++){
                    data.add(decisionData.get(y).get(x));
                }
                decisionOnes.add(data);
                decisionZero.add(data);
                continue;
            }
            if(decisionData.get(decisionData.size()-1).get(x).compareTo("1") == 0){
                for(int y = 0; y<decisionData.size()-1; y++){
                    data.add(decisionData.get(y).get(x));
                }
                decisionOnes.add(data);
            }
            else {
                for(int y = 0; y<decisionData.size()-1; y++){
                    data.add(decisionData.get(y).get(x));
                }
                decisionZero.add(data);
            }
        }

        for(int y=0; y<decisionOnes.get(0).size(); y++) {
            Node temp = new Node();
            int counter1 = 0;
            int counter0 = 0;

            for (int x = 0; x < decisionOnes.size(); x++) {
                if (decisionOnes.get(x).get(y).compareTo("1") == 0){
                    counter1++;
                }
                else {
                    counter0++;
                }
            }
            temp.setNumberOfOnes(counter1);
            temp.setNumberOfZeros(counter0);
            temp.setName(decisionOnes.get(0).get(y));
            temp.setTotalClassNumber(decisionOnes.size());
            listOfNodes1.add(temp);
        }

        for(int y=0; y<decisionZero.get(0).size(); y++) {
            Node temp = new Node();
            int counter1 = 0;
            int counter0 = 0;

            for (int x = 0; x < decisionZero.size(); x++) {
                if (decisionZero.get(x).get(y).compareTo("1") == 0){
                    counter1++;
                }
                else {
                    counter0++;
                }
            }
            temp.setNumberOfOnes(counter1);
            temp.setNumberOfZeros(counter0);
            temp.setName(decisionZero.get(0).get(y));
            temp.setTotalClassNumber(decisionZero.size());
            listOfNodes0.add(temp);
        }


        for(int x=0; x<listOfNodes1.size(); x++) {
            System.out.println("Name: " + listOfNodes1.get(x).getName() + " GetNumOnes: " + listOfNodes1.get(x).getNumberOfOnes());
        }
        for(int x=0; x<listOfNodes0.size(); x++) {
            System.out.println("Name: " + listOfNodes0.get(x).getName() + " GetNumZeros: " + listOfNodes0.get(x).getNumberOfZeros());
        }

        System.out.println("Printing decision ones");
        for(int x=0; x<decisionOnes.size(); x++){
            for(int y=0; y<decisionOnes.get(0).size(); y++){
                System.out.print(decisionOnes.get(x).get(y));
            }
            System.out.println();
        }


        String moose = testStuff(decisionData, listOfNodes1, listOfNodes0, decisionOnes.size(), decisionZero.size());


        //Print Array
        for (int x = 1; x < decisionData.get(decisionData.size() - 1).size(); x++) {
            if (decisionData.get(decisionData.size() - 1).get(x).equals("1")) {
                numOfOnes++;
            } else
                numOfZeros++;
        }

        ArrayList<ArrayList<String>> Smaller = new ArrayList<ArrayList<String>>();
        for(int x=0; x<decisionData.size(); x++) {
            Smaller.add(new ArrayList<String>());
            for(int y=0; y<450; y++){
                Smaller.get(x).add(decisionData.get(x).get(y));
            }
        }

    /*    Node parentTree = createDecisionTree(decisionData,0);
        //Node parentTree = createDecisionTree(Smaller,0);

        printTree(parentTree, -1);
        System.out.println("Accuracy on Training set ("+(decisionData.get(0).size()-1)+") instances "+treeAnalysis(parentTree,decisionData)  );
        System.out.println("Accuracy on Testing set ("+(testingData.get(0).size()-1)+") instances "+ treeAnalysis(parentTree,testingData)  );
*/
    }

    public static String testStuff(ArrayList<ArrayList<String>> test, ArrayList<Node> Ones, ArrayList<Node> Zeros, int sizeOnes, int sizeZeros) {
        double calculator0 = 1;
        double calculator1 = 1;

        int correctness = 0;

        //goes through everyline in the column
        for(int x=1; x< test.get(0).size()-1;x++)
        {
            //goes through every column per line
            calculator0 = 1;
            calculator1 = 1;
            for(int y=0; y<test.size()-1; y++)
            {
                calculator1 = calculator1*((double)Ones.get(y).getTotalNumOf(test.get(y).get(x))/Ones.get(y).getTotalClassNumber());
                calculator0 = calculator0*((double)Zeros.get(y).getTotalNumOf(test.get(y).get(x))/Zeros.get(y).getTotalClassNumber());

                //still needs to calculate false and determine which is better and keep score of the number of correct
            }
            System.out.println("Ones: " + sizeOnes + " Zeros: " + sizeZeros);
            calculator1 = calculator1*(sizeOnes/800.0);
            calculator0 = calculator0*(sizeZeros/800.0);

            System.out.println("Calc1: " + calculator1);
            System.out.println("Calc0: " + calculator0);
            if(calculator0 >= calculator1 && test.get(test.size()-1).get(x).compareTo("0") == 0) {
                correctness++;
            }
            if(calculator0 <= calculator1 && test.get(test.size()-1).get(x).compareTo("1") == 0) {
                correctness++;
            }
            //
        }
        System.out.println("Correctness: " + correctness/(test.get(0).size()*1.0));
            System.out.println("Calc Final: " + Math.max(calculator1,calculator0));

        return "";
    }

    private static ArrayList<ArrayList<String>> fileToArrayList(String fileName) {
        //File testData = new File("D:\\Machine learning\\Decision-Tree\\data/" + fileName);
        File testData = new File("../Naive-Bayes/data/" + fileName);

        //System.out.println(testData.getAbsolutePath());

        try {
            Scanner sc = new Scanner(testData);
            String headerLine = sc.nextLine();
            while(headerLine.isEmpty())
                headerLine = sc.nextLine();

            int numberOfAttributes = 0;
            Scanner sc1 = new Scanner(headerLine).useDelimiter("\\s");
            while(sc1.hasNext()){
               sc1.next();
               numberOfAttributes++;
            }

            ArrayList<ArrayList<String>> decisionData = new ArrayList<ArrayList<String>>(numberOfAttributes);
            for(int x=0; x<numberOfAttributes; x++) {
                decisionData.add(new ArrayList<String>());
            }

            String fullLine;
            int counter=0;

            Scanner scHeader = new Scanner(headerLine).useDelimiter("\\s");
            while(scHeader.hasNext()) {
                decisionData.get(counter).add(scHeader.next());
                counter++;
            }

            while(sc.hasNext()) {
                fullLine = sc.nextLine();
                Scanner sc2 = new Scanner(fullLine).useDelimiter("\\s");
                counter = 0;
                while (sc2.hasNext()) {
                    decisionData.get(counter).add(sc2.next());
                    counter++;
                }
                sc2.close();
            }
            return decisionData;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    private static int Count(ArrayList<String> data) {
        int pos=0,neg=0;
        for(int x=1;x<data.size();x++)
        {
            if(data.get(x).equals("1"))
                pos++;
            else
                neg++;
        }
        if(neg>pos)
            return 0;
        else
            return 1;
    }

}
