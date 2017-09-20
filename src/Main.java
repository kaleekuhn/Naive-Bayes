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

        //Print Array
        for (int x = 1; x < decisionData.get(decisionData.size() - 1).size(); x++) {
            if (decisionData.get(decisionData.size() - 1).get(x).equals("1")) {
                numOfOnes++;
            } else
                numOfZeros++;
        }

        Node parentTree = createDecisionTree(decisionData,0);
        printTree(parentTree, -1);
        System.out.println("Accuracy on Training set ("+(decisionData.get(0).size()-1)+") instances "+treeAnalysis(parentTree,decisionData)  );
        System.out.println("Accuracy on Training set ("+(testingData.get(0).size()-1)+") instances "+ treeAnalysis(parentTree,testingData)  );

    }

    private static void printTree(Node tree, int depth) {
        int move = ++depth;
        if(tree.hasLeftChild()){
            for(int x=0; x<depth; x++){
                System.out.print(" | ");
            }
            System.out.println(tree.getName() + " 0 : " + tree.getData());
            printTree(tree.getLeftChild(), move);
        }
        else {
            for(int x=0; x<depth; x++){
                System.out.print(" | ");
            }
            System.out.println(tree.getName() + " 0 : " + tree.getData());
        }
        if(tree.hasRightChild()){
            for(int x=0; x<depth; x++){
                System.out.print(" | ");
            }
            System.out.println(tree.getName() + " 1 : " + tree.getData());
            printTree(tree.getRightChild(), move);
        }
        else {
            for(int x=0; x<depth; x++){
                System.out.print(" | ");
            }
            System.out.println(tree.getName() + " 1 : " + tree.getData());
        }
    }

    private static Node createDecisionTree(ArrayList<ArrayList<String>> data, int direction) {
        //System.out.println("Inside Create Decision Tree");
       /* for(int x=0;x<data.size(); x++) {
            for(int y=0; y<data.get(x).size(); y++) {
                System.out.print(data.get(x).get(y));
            }
            System.out.println();
        }
*/
        //First step, get the Entropy of the current Node
        Node nodey = new Node();
        nodey.setEntropy(1);
        int posVals = 0, negVals = 0, totalVals = data.get(data.size()-1).size();
        for(int x = 0; x<totalVals; x++) {
            if(data.get(data.size()-1).get(x).compareTo("1")==0) {
                posVals++;
            }
            else
                negVals++;
        }

        //temp.setTotalPositive(posVals);
        //temp.setTotalNegative(negVals);
        double maxIG = -1;
        int IGIndex = 0;
        double ig = 0;

        for(int x=0; x<data.size()-1;x++) {
            //System.out.println("IG: " + (1- infoGain(x, posVals, negVals), data)));

            ig = (nodey.getEntropy() - infoGain(x, data));
            if(ig > maxIG) {
                IGIndex = x;
                maxIG = ig;
            }
            //System.out.println("IG " + data.get(x).get(0) + ": " + (1- infoGain(x, data)) + " Index: " + IGIndex);

        }
        nodey.setName(data.get(IGIndex).get(0));
     //   System.out.println(nodey.getName() +"  "+direction);
        //Need to remove the attribute title and depending on if true or false, only seed appropriate data
        //System.out.println("Starting Second");

        ArrayList<ArrayList<String>> Left = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> Right = new ArrayList<ArrayList<String>>();

        for(int x=0; x<data.size(); x++) {
            Left.add(new ArrayList<String>());
            Right.add(new ArrayList<String>());
            Right.get(x).add(data.get(x).get(0));
            Left.get(x).add(data.get(x).get(0));
            for(int y=1;y<data.get(x).size();y++){
                if(data.get(IGIndex).get(y).compareTo("1")==0)
                    Right.get(x).add(data.get(x).get(y));
                else
                    Left.get(x).add(data.get(x).get(y));
            }
        }
        if(maxIG>10)
        {



                if (direction == 0) {
                    if (Count(Left.get(1)) == 1)
                        nodey.setData("1");
                    else
                        nodey.setData("0");
                    return nodey;
                } else if (direction == 1) ;
                {
                    if (Count(Right.get(1)) == 1)//22
                    {
                        nodey.setData("1");
                    } else
                        nodey.setData("0");
                    return nodey;
                }

        }

        Left.remove(IGIndex);
        Right.remove(IGIndex);
        /*System.out.println("Printing H0 new");
        for(int x=0;x<Left.size(); x++) {
            for(int y=0; y<Left.get(x).size(); y++) {
                System.out.print(Left.get(x).get(y));
            }
            System.out.println();
        }*/
        if(Left.size()>=1) {
            nodey.setLeftChild(createDecisionTree(Left,0));
            nodey.setDirection("0");
        }
        if(Right.size()>=1) {
            nodey.setRightChild(createDecisionTree(Right,1));
            nodey.setDirection("1");
        }
        //System.out.println("Contains westly? " + getIndexOfName("wesley", data));
        return nodey;
    }

    private static int getIndexOfName ( String name, ArrayList<ArrayList<String>> data) {
        for(int x=0; x<data.size(); x++) {
            if(data.get(x).get(0).equals(name)){
                return x;
            }
        }
        return -1;
    }

    private static double calcEntropy1 (int numPos, ArrayList<ArrayList<String>> data) {
        //int index = getIndexOfName("class", data);
        int posVals = 0, negVals = 0, totalVals = data.get(numPos).size();
        for(int x = 0; x<totalVals; x++) {
            if(data.get(numPos).get(x).compareTo("1")==0) {
                posVals++;
            }
            else
                negVals++;
        }
        //System.out.println("Pos: " + posVals + " Neg: " + negVals + " total: " + totalVals);
        double posFrac = posVals/(totalVals*1.0);
        double negFrac = negVals/(totalVals*1.0);
        //System.out.println(posFrac + " " + negFrac);
        double entropy = -(posFrac)*(Math.log(posFrac)/Math.log(2)) -(negFrac)*(Math.log(negFrac)/Math.log(2));
        //System.out.println("Entropy: " + entropy);
        return entropy;
    }

    private static double infoGain(int index, ArrayList<ArrayList<String>> data) {
        if(data.size()<=2)
            return -11;
        ArrayList<ArrayList<String>> H0 = new ArrayList<ArrayList<String>>(data.size());
        ArrayList<ArrayList<String>> H1 = new ArrayList<ArrayList<String>>(data.size());


        for(int x=0; x<data.size(); x++) {
            H0.add(new ArrayList<String>());
            H1.add(new ArrayList<String>());
            for(int y=0;y<data.get(x).size();y++){
                if(data.get(index).get(y).compareTo("1")==0)
                    H1.get(x).add(data.get(x).get(y));
                else
                    H0.get(x).add(data.get(x).get(y));
            }
        }
/*        System.out.println("Printing New");
        for(int x=0;x<H0.size(); x++) {
            for(int y=0; y<H0.get(x).size(); y++) {
                System.out.print(H0.get(x).get(y));
            }
            System.out.println();
        }*/
        //System.out.println("Entropy for H0 " + calcEntropy1(data.size()-1, H0) + " Entropy for H1 " +
        //calcEntropy1(data.size()-1, H1);

        double fracPos = 1.0*H1.get(0).size()/data.get(0).size();
        double fracNeg = 1.0*H0.get(0).size()/data.get(0).size();


        double IG = calcEntropy1(data.size()-1, H1) * fracPos + calcEntropy1(data.size()-1, H0) * fracNeg;
        //System.out.println("IG is: " + (1-IG));
        return IG;
    }

    private static ArrayList<ArrayList<String>> fileToArrayList(String fileName) {
        File testData = new File("D:\\Machine learning\\Decision-Tree\\data/" + fileName);
        //File testData = new File("../project1/data/" + fileName);

        //System.out.println(testData.getAbsolutePath());

        try {
            Scanner sc = new Scanner(testData);
            String headerLine = sc.nextLine();

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

    private static double treeAnalysis(Node tree, ArrayList<ArrayList<String>> data)
    {
        int index;
        Node next=tree;
        int totalcorrect=0;
        for(int x=1;x<(data.get(data.size()-1).size()-1);x++) {
            while (!next.isLeafNode()) {

                index = getIndexOfName(tree.getName(), data);
                if (data.get(index).get(x).equals("0")) {
                    next = next.getLeftChild();
                } else if (data.get(index).get(x).equals("1")) {
                    next = next.getRightChild();
                }
            }

            if (next.getData().equals(data.get(data.size() - 1).get(x)))
                totalcorrect++;
            next = tree;
        }
        return ((double)totalcorrect/(data.get(0).size()-1))*100;
    }
}
