import java.math.BigInteger;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ResultWriter {
    public static void writeResults(int caseNum, BigInteger n, BigInteger P, BigInteger Q, BigInteger E, BigInteger D, BigInteger msg, BigInteger cypher){
        String path = Integer.toString(caseNum) + "_24276472.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("n = ");
            writer.write(n.toString());
            writer.write("\nE = ");
            writer.write(E.toString());
            writer.write("\ncypher = ");
            writer.write(cypher.toString());
            
            writer.write("\n\nP = ");
            writer.write(P.toString());
            writer.write("\nQ = ");
            writer.write(Q.toString());
            writer.write("\nD = ");
            writer.write(D.toString());
            writer.write("\ncypher decrypted = ");
            writer.write(msg.toString());

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
