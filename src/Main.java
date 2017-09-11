import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
       // System.out.println("Hello World!");
        int numOfZeros=0, numOfOnes=0;
        System.out.println("Params?: " + args[0]);
        ArrayList<ArrayList<String>> decisionData = fileToArrayList(args[0]);

        //Print Array
        for(int x=1; x<decisionData.get(decisionData.size()-1).size(); x++) {
            if(decisionData.get(decisionData.size()-1).get(x).equals("1"))
            {
                numOfOnes++;
            }
            else
                numOfZeros++;
        }
        ArrayList<String> classList=decisionData.get(decisionData.size()-1);
        ArrayList<String> tree=new ArrayList<String>((int)Math.pow(decisionData.size(),2));
        decisionData.remove(decisionData.size()-1);
        System.out.println();
       tree=createTree(decisionData,classList,CalcEntropy((double)numOfOnes,(double)numOfZeros),decideSplit(decisionData,classList,CalcEntropy((double)numOfOnes,(double)numOfZeros)),tree);
       for(int x=0; x<tree.size();x++)
         System.out.println(tree.get(x)+"  "+x);
    }

    private static int decideSplit ( ArrayList<ArrayList<String>> check, ArrayList<String> test, double entropy) {

        int[] whenTestOne=new int[(int)Math.pow(check.size(),2)];
        int[] whenTestZero=new int[(int)Math.pow(check.size(),2)];
        int split=0;
        double min=-111;
        double [] InfoGain=new double[check.size()];
        double entropyfractionL,entropyfractionR;
        for(int j=0; j<whenTestOne.length;j++) {
        whenTestOne[j]=0;
        whenTestZero[j]=0;
        }

        for(int x=0; x<check.size();x++)
        {
            for(int y=1; y<test.size();y++)
            {

                if(check.get(x).get(y).equals("1"))
                {
                    if(test.get(y).equals("1"))
                        whenTestOne[(((x+1)*2)-1)]++;
                    else
                        whenTestZero[(((x+1)*2)-1)]++;
                }
                else
                if(check.get(x).get(y).equals("0"))
                {
                    if(test.get(y).equals("1"))
                        whenTestOne[(((x+1)*2)-2)]++;
                    else
                        whenTestZero[(((x+1)*2)-2)]++;
                }

            }

            entropyfractionL=(double)(whenTestOne[(((x+1)*2)-1)]+whenTestOne[(((x+1)*2)-2)])/(whenTestOne[(((x+1)*2)-1)]+whenTestOne[(((x+1)*2)-2)]
                    +whenTestZero[(((x+1)*2)-1)]+whenTestZero[(((x+1)*2)-2)]);
            entropyfractionR=(double)(whenTestZero[(((x+1)*2)-1)]+whenTestZero[(((x+1)*2)-2)])/(whenTestOne[(((x+1)*2)-1)]+whenTestOne[(((x+1)*2)-2)]
                    +whenTestZero[(((x+1)*2)-1)]+whenTestZero[(((x+1)*2)-2)]);

           InfoGain[x]= -1*(entropy-((CalcEntropy(whenTestOne[(((x+1)*2)-1)],whenTestOne[(((x+1)*2)-2)]))*entropyfractionL
                   +(CalcEntropy(whenTestZero[(((x+1)*2)-1)],whenTestZero[(((x+1)*2)-2)]))*entropyfractionR));
        }
       for(int z=0;z< InfoGain.length ;z++)
        {
            if(min<InfoGain[z]) {
            min=InfoGain[z];
            split=z;
            }
        }
       return (int)split;

    }
    private static double CalcEntropy (double first, double second) {
        if(second==0||first==0)
            return 0;
        return ((((-1*first)/(first+second))*(Math.log10(first/(first+second))/Math.log10(2)))+
                ((((-1*second)/(first+second))*(Math.log10(second/(first+second))/Math.log10(2)))));
    }
    private static ArrayList<ArrayList<String>> fileToArrayList(String fileName) {
        File testData = new File("D:\\Machine learning\\Decision-Tree\\data/" + fileName);
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
    private static boolean isPure(ArrayList<String> test)
    {   boolean Pure=true;
        for(int x=2; x<test.size(); x++) {
            if(!(test.get(x).equals(test.get(1))))
            {
                return false;
            }

        }
        return true;
    }
    private static ArrayList<String> createTree(ArrayList<ArrayList<String>> check, ArrayList<String> test, double entropy, int split,ArrayList<String> tree){
        int numOfOnes=0,numOfZeros=0;

        if(check.size()<=2||test.size()<=1)
            return tree;
        ArrayList<ArrayList<String>> checkL= new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> checkR= new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> testL= new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> testR= new ArrayList<ArrayList<String>>();
        String Lname,Rname;
        int splitL=0,splitR=0;
        double entropyL=0,entropyR=0;
        for(int x=0; x<check.size();x++)
        {
            checkL.add(new ArrayList<String>());
            checkL.get(x).add(check.get(x).get(0));
            checkR.add(new ArrayList<String>());
            checkR.get(x).add(check.get(x).get(0));
            testL.add(new ArrayList<String>());
            testL.get(x).add(test.get(0));
            testR.add(new ArrayList<String>());
            testR.get(x).add(test.get(0));
        }



        for(int x=0; x<check.size();x++)
        {
            for(int y=1; y<check.get(split).size();y++)
            {
                if(check.get(split).get(y).equals("0"))
                {
                    checkL.get(x).add(check.get(x).get(y));
                    testL.get(x).add(test.get(y));

                }
                else
                if(check.get(split).get(y).equals("1"))
                {
                    checkR.get(x).add(check.get(x).get(y));
                    testR.get(x).add(test.get(y));
                }
            }
        }
        if(tree.size()==0)
            tree.add(check.get(split).get(0));
        if(testL.get(0).size()<2)
        {
            tree.add("T");
            tree.add("F");
            return tree;
        }
        for(int x=1;x<testL.get(split).size();x++)
            if(testL.get(split).get(x).equals("1"))
                numOfOnes++;
        else numOfZeros++;

        checkL.remove(split);
        entropyL=CalcEntropy((double)numOfOnes,(double)numOfZeros);
       splitL= decideSplit(checkL,testL.get(split),entropyL);
        if(testR.get(0).size()<2)
        {

            tree.add("F");
            tree.add("T");
            return tree;
        }
        numOfOnes=0;
        numOfZeros=0;
        for(int x=1;x<testR.get(split).size();x++)
            if(testR.get(split).get(x).equals("1"))
                numOfOnes++;
            else numOfZeros++;

        checkR.remove(split);
        entropyR=CalcEntropy((double)numOfOnes,(double)numOfZeros);
        splitR=decideSplit(checkR,testR.get(split),entropyR);

        if(isPure(testL.get(splitL))){
            if(testL.get(splitL).get(1).equals("1"))
            tree.add("T");
            else
                tree.add("F");
            if(isPure(testR.get(splitR))) {
                if (testR.get(splitR).get(1).equals("1"))
                    tree.add("T");
                else
                    tree.add("F");
            }else {
                tree.add(checkR.get(splitR).get(0));
            }
            return tree;
        }
        else
            if(isPure(testR.get(splitR)))
        {

            tree.add(checkL.get(splitL).get(0));
            if(testR.get(splitR).get(1).equals("1"))
                tree.add("T");
            else
                tree.add("F");
            
        }
        else
            {
                tree.add(checkL.get(splitL).get(0));
                tree.add(checkR.get(splitR).get(0));

            }

       System.out.println();
        if(!(entropyL==0.0))
        {
            System.out.println("Linfo   split"+splitL+"  entropy "
                    + entropyL+"  checkL "+checkL.size()+"  testLlength "+testL.get(0).size()+"  testLsize "+testL.size());
           if(testL.get(0).size()<=2)
               return tree;
            tree= createTree(checkL,testL.get(splitL),entropyL,splitL,tree);
        }

        if(!(entropyR==0.0))
        {
            System.out.println("Rinfo   split"+splitR+"  entropy "
                    + entropyR+"  checkR "+checkR.size()+"  testRlength "+testR.get(0).size()+"  testRsize "+testR.size());
            if(testR.get(0).size()<=2)
                return tree;
            tree = createTree(checkR,testR.get(splitR),entropyR,splitR,tree);
        }

        //split=decideSplit(check,test,CalcEntropy((double)numOfOnes,(double)numOfZeros))

        return tree;
    }
}
