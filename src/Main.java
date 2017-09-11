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
        decisionData.remove(decisionData.size()-1);
        System.out.println(decideSplit(decisionData,classList,CalcEntropy((double)numOfOnes,(double)numOfZeros)));

    }

    private static int decideSplit ( ArrayList<ArrayList<String>> check, ArrayList<String> test, double entropy)
    {

        int[] whenTestOne=new int[(int)Math.pow(check.size(),2)];
        int[] whenTestZero=new int[(int)Math.pow(check.size(),2)];
        int split=0;
        double min=1;
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
            if(min>InfoGain[z]) {
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
}
