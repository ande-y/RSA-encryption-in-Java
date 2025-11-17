import java.math.BigInteger;
import java.util.Stack;
import java.util.Scanner;

public class rsaCracker_nextProbPrime {
    static BigInteger zero = new BigInteger("0");
    static BigInteger one = new BigInteger("1");
    static BigInteger two = new BigInteger("2");

    public static boolean greater(int x){return (x == 1) ? true : false;}
    public static boolean lesser(int x){return (x == -1) ? true : false;}

    public static BigInteger[] solvePrimes(BigInteger n){
        BigInteger P = two, Q = zero;
        BigInteger sq = (n.sqrt()).add(one);

        boolean found = false;
        while (true){
            if (greater(P.compareTo(sq))) break;
            if ((n.mod(P)).equals(zero)){
                Q = n.divide(P);
                found = true;
                break;
            }
            P = P.nextProbablePrime();
        }
        if (!found){
            System.err.println( "\n<!> n itself is prime\n<!> process terminated\n");
            System.exit(1);
        }

        BigInteger[] ans = {P, Q};
        return ans;
    }

    public static BigInteger getMultModInv(BigInteger z, BigInteger E){
        BigInteger zCopy = z, eCopy = E;
        Stack<BigInteger> product = new Stack<>(), multiplicand = new Stack<>();
        while (!(eCopy.equals(zero))){
            product.push(zCopy);
            multiplicand.push(eCopy);

            BigInteger rem = zCopy.mod(eCopy);
            zCopy = eCopy;
            eCopy = rem;
        }
        if (!(zCopy.equals(one))){
            System.out.println("\n<!> there is no D which satisfies (D*E)%z=1\n<!> process terminated\n");
        }

        product.pop();
        multiplicand.pop();
        
        BigInteger a1 = product.pop(), a2 = one, b1 = multiplicand.pop(), b2 = a1.divide(b1);
        // System.out.println("(" + a1 + " * " + a2 + ") * (" + b1 + " * " + b2 + ")");
        while (!product.empty()){
            BigInteger prod = product.pop(), multcand = multiplicand.pop();
            BigInteger multlier = prod.divide(multcand), rem = prod.mod(multcand);
            if (a1.equals(rem)){
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
        BigInteger D = (a1.equals(E)) ? a2 : b2;

        if (!((E.multiply(D)).mod(z)).equals(one)){
            D = D.multiply((E.multiply(D)).mod(z));
            D = D.mod(z);
        }

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
