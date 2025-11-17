import java.math.BigInteger;
import java.util.Scanner;

public class modPowChecker {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.print("n: ");
        BigInteger n = new BigInteger(scan.next());
        System.out.print("\nD: ");
        BigInteger D = new BigInteger(scan.next());
        System.out.print("\nm: ");
        BigInteger cypher = new BigInteger(scan.next());
        scan.close();

        // System.out.println(n);
        // System.out.println(D);
        // System.out.println(cypher);

        BigInteger msg = cypher.modPow(D, n);
        System.out.print("\n(m^D)%n: " + msg);
    }
}
