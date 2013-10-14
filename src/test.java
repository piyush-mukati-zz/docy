import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
// unit test for dp and cmeans

public class test {
   public static void main(String args[]) throws FileNotFoundException, IOException {
		
	   
	   DocumentParser dp=new DocumentParser() ;
	  System.out.println("preprocessing ...");
	   dp.parseFiles("E:\\eclipse\\work_pllace\\my_docy\\data");
	   
	   System.out.println("tfidf calculation ...");
		   dp.tfIdfCalculator();

		   System.out.println("Cmean running ...");
			  
   CMean cm=new CMean();
   double mat[][]=dp.get_tfidf();
  cm.cluster(mat , 2, .4,2);
   
   }
}