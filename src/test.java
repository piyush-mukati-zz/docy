import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
// unit test for dp and cmeans

public class test {
   public static void main(String args[]) throws FileNotFoundException, IOException {
		
	   String path="data";
	   DocumentParser dp=new DocumentParser() ;
	  System.out.println("preprocessing ...");
	   dp.parseFiles(path);
	   
	   System.out.println("tfidf calculation ...");
		   dp.tfIdfCalculator();

		   System.out.println("Cmean running ...");
			   double mat[][]=dp.get_tfidf();
			   CMean.db(mat);

CMean cm=new CMean(mat , 2, 0.01,2);
   cm.cluster();
   
   }
}