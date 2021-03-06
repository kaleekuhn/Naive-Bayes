/*
* In this Node file, we put together all of the necessary attributes for our different attribute classes
*
* */
public class Node {

    int numberOfOnes;
    int numberOfZeros;
    int totalClassNumber;
    String name;

    public int getTotalNumOf(String n) {
        if(n.compareTo("1")==0)
            return numberOfOnes;
        else
            return numberOfZeros;
    }
    public int getTotalClassNumber() {
        return totalClassNumber;
    }

    public void setTotalClassNumber(int totalClassNumber) {
        this.totalClassNumber = totalClassNumber;
    }

    public int getNumberOfOnes() {
        return numberOfOnes;
    }

    public void setNumberOfOnes(int numberOfOnes) {
        this.numberOfOnes = numberOfOnes;
    }

    public int getNumberOfZeros() {
        return numberOfZeros;
    }

    public void setNumberOfZeros(int numberOfZeros) {
        this.numberOfZeros = numberOfZeros;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





}
