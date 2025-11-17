import java.math.BigInteger;
import java.util.Stack;
import java.util.Random;
import java.util.Scanner;

public class rsaGenerator {
    static BigInteger zero = new BigInteger("0");
    static BigInteger one = new BigInteger("1");
    static BigInteger two = new BigInteger("2");
    static Random rand = new Random();

    public static boolean greater(int x){return (x == 1) ? true : false;}
    public static boolean lesser(int x){return (x == -1) ? true : false;}
    
    public static BigInteger getCoprime(BigInteger zCopy){
        BigInteger ans = new BigInteger("2");
        ans = ans.add(BigInteger.probablePrime(40, rand));
        for (boolean chosen = false; !chosen; ans = ans.add(one)){
            BigInteger D = ans;
            BigInteger z = zCopy;
            while (!(D.equals(zero))){
                BigInteger rem = z.mod(D);
                z = D;
                D = rem;
            }
            if (z.equals(one)) chosen = true;
        }
        ans = ans.subtract(one);
        return ans;
    }

    public static BigInteger getMultModInv(BigInteger z, BigInteger D){
        BigInteger zCopy = z, eCopy = D;
        Stack<BigInteger> product = new Stack<>(), multiplicand = new Stack<>();
        while (!(eCopy.equals(zero))){
            product.push(zCopy);
            multiplicand.push(eCopy);

            BigInteger rem = zCopy.mod(eCopy);
            zCopy = eCopy;
            eCopy = rem;
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
        BigInteger E = (a1.equals(D)) ? a2 : b2;

        if (!((E.multiply(D)).mod(z)).equals(one)){
            E = E.multiply((E.multiply(D)).mod(z));
            E = E.mod(z);
        }

        return E;
    } 

    public static void main(String[] arg){
        BigInteger P = BigInteger.probablePrime(40, rand);
        BigInteger Q = BigInteger.probablePrime(40, rand);
        if (!P.isProbablePrime(1000000)) System.exit(1);
        if (!Q.isProbablePrime(1000000)) System.exit(1);

        BigInteger n = P.multiply(Q);
        BigInteger z = ((P.subtract(one)).multiply((Q.subtract(one))));

        BigInteger D = getCoprime(z);
        BigInteger E = getMultModInv(z, D);

        System.out.println("P: " + P);
        System.out.println("Q: " + Q);
        System.out.println("n: " + n);
        System.out.println("z: " + z);
        System.out.println("D: " + D);
        System.out.println("E: " + E);

        Scanner scan = new Scanner(System.in);
        System.out.print("your message: ");
        String msgT = scan.nextLine();
        scan.close();

        BigInteger msg = new BigInteger(msgT);
        BigInteger cypher = msg.modPow(E, n);
        System.out.println("your message ecrypted: " + cypher);

        BigInteger decr = cypher.modPow(D, n);
        if (decr.equals(msg)) System.out.println("\n" + decr + " key valid");
        else System.out.println("<!> ERROR OCCURED, KEY INVALID");
    }
}

// if D > n, the decryptor will find a D < n

// 7663 2995 2800
// 934189 203657 887713
// 2087573 1073729 1925411
// 45633619 546477 16371957 = record <1s instant 

// absolute limit record 1m16s
// 99999999100000001881 42203242516986742153 32047461171559566556 
// P: 9999999943
// Q: 9999999967
// z: 99999999080000001972
// D: 70369
// message: 12345
