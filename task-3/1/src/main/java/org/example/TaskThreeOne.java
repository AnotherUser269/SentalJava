public class TaskThreeOne {
    public static void main(String[] args) {
        int finalNum = (new java.util.Random()).nextInt(900) + 100;
        int digSum = (finalNum/100) + (finalNum/10%10) + finalNum%10;

        System.out.println(finalNum);
        System.out.println(digSum);
    }
}
