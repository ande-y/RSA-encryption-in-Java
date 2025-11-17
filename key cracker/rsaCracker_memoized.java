import java.math.BigInteger;
import java.util.Stack;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class rsaCracker_memoized {
    static BigInteger zero = new BigInteger("0");
    static BigInteger one = new BigInteger("1");
    static BigInteger two = new BigInteger("2");

    public static boolean equal(int x){return (x == 0) ? true : false;}
    public static boolean greater(int x){return (x == 1) ? true : false;}
    public static boolean lesser(int x){return (x == -1) ? true : false;}

    public static BigInteger[] solvePrimes(BigInteger n){
        BigInteger P = zero, Q = zero;
        try {
            BufferedReader read = new BufferedReader(new FileReader("primes/primes.0"));
            int filesRead = 0, linesRead = 0;
            BigInteger half = n.divide(two);
            for (boolean found = false; !found; linesRead++){
                if (linesRead >= 100000){
                    linesRead = 0;
                    String nextFileP = "primes/primes." + ++filesRead;
                    read.close();
                    read = new BufferedReader(new FileReader(nextFileP));
                }
                
                String temp = read.readLine();
                if (temp.charAt(temp.length() - 1) == ' ') temp = temp.substring(0, temp.length() - 1);
                P = new BigInteger(temp);

                if (equal(n.mod(P).compareTo(zero))){
                    Q = n.divide(P);
                    found = true;
                }
                else if (greater(P.compareTo(half))){
                    System.err.println( "\n<!> n itself is prime\n<!> process terminated\n");
                    System.exit(1);
                }
            }
            read.close();

            read = new BufferedReader(new FileReader("primes/primes.0"));
            filesRead = 0;
            for (boolean found = false; !found; ){
                String peekTo = "primes/primes." + (filesRead + 1);
                BufferedReader peek = new BufferedReader(new FileReader(peekTo));

                String temp = peek.readLine();
                if (temp.charAt(temp.length() - 1) == ' ') temp = temp.substring(0, temp.length() - 1);
                BigInteger check = new BigInteger(temp);
                peek.close();

                if (greater(P.compareTo(check))){
                    read.close();
                    read = new BufferedReader(new FileReader(peekTo));
                    filesRead++;
                }
                else found = true;

                if (filesRead >= 4550) found = true;    // 4550 FILE LIMIT, CANNOT PEEK THE NEXT FILE
            }
            for (boolean found = false; !found; linesRead++){
                String temp = read.readLine();
                if (temp.charAt(temp.length() - 1) == ' ') temp = temp.substring(0, temp.length() - 1);
                BigInteger check = new BigInteger(temp);

                if (equal(check.compareTo(Q))) found = true;
                else if (greater(check.compareTo(Q))){
                    System.err.println( "\n<!> n is not the product of 2 primes\n<!> process terminated\n");
                    System.exit(1);
                }
            }
            read.close();

        } catch (IOException e) {
            System.err.println(e.getMessage() + "\n<!> read error / ran out of files\n<!> process terminated\n");
            System.exit(1);
        }
        BigInteger[] ans = {P, Q};
        return ans;
    }

    public static BigInteger getMultModInv(BigInteger z, BigInteger E){
        BigInteger zCopy = z, eCopy = E;
        Stack<BigInteger> product = new Stack<>(), multiplicand = new Stack<>();
        while (!equal(eCopy.compareTo(zero))){
            product.push(zCopy);
            multiplicand.push(eCopy);

            BigInteger rem = zCopy.mod(eCopy);
            zCopy = eCopy;
            eCopy = rem;
        }
        if (!equal(zCopy.compareTo(one))){
            System.out.println("\n<!> there is no D which satisfies (D*E)%z=1\n<!> process terminated\n");
        }

        product.pop();
        multiplicand.pop();
        
        BigInteger a1 = product.pop(), a2 = one, b1 = multiplicand.pop(), b2 = a1.divide(b1);
        // System.out.println("(" + a1 + " * " + a2 + ") * (" + b1 + " * " + b2 + ")");
        while (!product.empty()){
            BigInteger prod = product.pop(), multcand = multiplicand.pop();
            BigInteger multlier = prod.divide(multcand), rem = prod.mod(multcand);
            if (equal(a1.compareTo(rem))){
                a1 = prod;
                b2 = b2.add(a2.multiply(multlier));
            }
            else {
                b1 = prod;
                a2 = a2.add(b2.multiply(multlier));
            }
            // System.out.println(prod + " = " + multcand + " * " + multlier + " + " + rem);
            // System.out.println("(" + a1 + " * " + a2 + ") * (" + b1 + " * " + b2 + ")");
        }
        BigInteger D = (equal(a1.compareTo(E))) ? a2 : b2;
        return D;
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        System.out.print("case #: ");
        int caseNum = scan.nextInt();

        System.out.print("GIVEN\nn = ");
        BigInteger n = new BigInteger(scan.next());
        System.out.print("E = ");
        BigInteger E = new BigInteger(scan.next());
        System.out.print("cypher: ");
        BigInteger cypher = new BigInteger(scan.next());
        scan.close();

        BigInteger[] primes = solvePrimes(n);
        BigInteger P = primes[0], Q = primes[1];
        System.out.println("\nPRIMES VALUES:\nP = " + P + "\nQ = " + Q);

        BigInteger z = (P.subtract(one)).multiply(Q.subtract(one));
        System.out.println("DECRYPTOR VALUES:\nz = " + z);

        BigInteger D = getMultModInv(z, E);
        System.out.println("D = " + D);

        BigInteger msg = cypher.modPow(D, n);
        System.out.println("\nmessage: " + msg + "\n");

        ResultWriter.writeResults(caseNum, n, P, Q, E, D, msg, cypher);
    }
}
