import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
// unit test for dp and cmeans

import plsa.Plsa;

public class test {
   public static void main(String args[]) throws FileNotFoundException, IOException {
		
	  String path="data";
	   DocumentParser dp=new DocumentParser() ;
	  System.out.println("preprocessing ...");
	   dp.parseFiles(path);
	   /*
	   System.out.println("tfidf calculation ...");
		   dp.tfIdfCalculator();

		   System.out.println("Cmean running ...");
			   double mat[][]=dp.get_tfidf();
			   CMean.db(mat);

CMean cm=new CMean(mat , 2, 0.01,2);
   cm.cluster();
  */
	   
	   int n_dw[][]=dp.get_n_dw();
	   System.out.println("calculating n_dW");
	    	
		plsa.Plsa plsa=new plsa.Plsa(n_dw,20);
		plsa.EM(50);
		
/* print
 * */
	for(int i=0;i<4000;i++){
		if(plsa.pw_z[3][i]> 0.001)
	System.out.println(plsa.pw_z[3][i]+"   "+dp.allTerms.get(i));	
	}	
		
   }
}