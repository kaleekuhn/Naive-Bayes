import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println("Params?: " + args[0]);

        ArrayList<ArrayList<String>> listOfLists = fileToArrayList(args[0]);

        //Print Array
        for(int x=0; x<listOfLists.size(); x++) {
            for(int y=0; y<listOfLists.get(x).size(); y++) {
                System.out.print(listOfLists.get(x).get(y));
            }
            System.out.println();
        }
    }

    private static ArrayList<ArrayList<String>> fileToArrayList(String fileName) {
        File testData = new File("../project1/data/" + fileName);
        System.out.println(testData.getAbsolutePath());

        try {
            Scanner sc = new Scanner(testData);
            String headerLine = sc.nextLine();

            int numberOfAttributes = 0;
            Scanner sc1 = new Scanner(headerLine).useDelimiter("\\s");
            while(sc1.hasNext()){
                System.out.println(sc1.next());
                numberOfAttributes++;
            }

            ArrayList<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>(numberOfAttributes);
            for(int x=0; x<numberOfAttributes; x++) {
                listOfLists.add(new ArrayList<String>());
            }

            String fullLine;
            int counter=0;

            Scanner scHeader = new Scanner(headerLine).useDelimiter("\\s");
            while(scHeader.hasNext()) {
                listOfLists.get(counter).add(scHeader.next());
                counter++;
            }

            while(sc.hasNext()) {
                fullLine = sc.nextLine();
                Scanner sc2 = new Scanner(fullLine).useDelimiter("\\s");
                counter = 0;
                while (sc2.hasNext()) {
                    listOfLists.get(counter).add(sc2.next());
                    counter++;
                }
                sc2.close();
            }
            return listOfLists;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
