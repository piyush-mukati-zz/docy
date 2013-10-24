
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to read documents
 *
 **/
public class DocumentParser {
	//This variable will hold all terms of each document in an array.
    public List <String[]> termsDocsArray = new ArrayList<String[]>();
    public List <String> allTerms = new ArrayList<String>(); //to hold all terms
    public List <double[]> tfidfDocsVector = new ArrayList<double[]>();
    public int n_dw[][]=null;
   
    public   DocumentParser(){
    	n_dw=null;
    }
    
    
      public void parseFiles(String filePath) throws FileNotFoundException, IOException {
        File[] allfiles = new File(filePath).listFiles();
        BufferedReader in = null;
        int cnt=0;
    	
        for (File f : allfiles) {
            if (f.getName().endsWith(".lem")) {
        		 cnt++; System.out.println("file- "+cnt+ "  "+f.getName());
            
                in = new BufferedReader(new FileReader(f));
                StringBuilder sb = new StringBuilder();
                String s = null;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                }
                
                String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
                for (String term : tokenizedTerms) {
                    if (!allTerms.contains(term)) {  //avoid duplicate entry
                        allTerms.add(term);
                    }
                }
                termsDocsArray.add(tokenizedTerms);
            }
        }
/*
 * read testing 
 for(String [] ss :termsDocsArray)
	 for(String s: ss ){
		 System.out.println(s);
	 }
  */
 
    }
/*
 * 
 */
      public void Calculate_n_dw(){
    	  int nd=termsDocsArray.size();
    	  int nw=allTerms.size();
    	 
    	  System.out.println("total doc ="+nd+"  total word="+nw);
    	  n_dw=new int[nd][nw];
    	  
    	  int docno=0;
    	  for (String[] doc : termsDocsArray) {
          System.out.println("dw running on doc "+ (docno));	
          	  
              int wrdno=0;
          	  for (String term : allTerms) {
          		
          		  int count = 0;
          		for(int i=0;i<doc.length;i++){
          				if(doc[i].equals(term))count++;
          				}
          	
          		n_dw[docno][wrdno]=count;
          	  wrdno++;
          	  count=0;
          	  }
          	 docno++;
          	 }
      }
      /**
     * Method to create termVector according to its tfidf score.
     */
      public void tfIdfCalculator() {
    	/*
    	// * read testing 
    	 for(String [] ss :termsDocsArray)
    		 for(String s: ss ){
    			 System.out.println(s);
    		 }
    	//  */
    	 
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency        
        Idf object_idf=new Idf(); 
        int docno=0;
        for (String[] doc : termsDocsArray) {
        System.out.println("TfIdf running on doc "+ (++docno));	
        	Tf object_tf=new Tf();// it must be here b coz need to refresh hash for each document
            
            
        	double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String term : allTerms) {
                tf = object_tf.calculate(doc, term);
                idf = object_idf.calculate(termsDocsArray, term);
                tfidf = tf * idf*10000;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        
        }
    
        /*
       //  * tfidf testing 
         for(double [] vec :tfidfDocsVector){
        	 for(double d: vec ){
        		 System.out.print(d+" ");
        	 }
        	 System.out.println();
        	 }
        //  */

    }
   
    public double [][] get_tfidf(){
    	double [][]ans =new double[this.tfidfDocsVector.size()][];

    	for(int i=0,n=this.tfidfDocsVector.size();i<n;i++){
    		
    		ans[i]=this.tfidfDocsVector.get(i);
    	}
    	this.tfidfDocsVector.toArray(ans);

    	return ans;
    	}

    
    public int [][] get_n_dw(){
    if(false && this.n_dw!=null){
    System.out.println("calculating n_dW");
    	Calculate_n_dw();
    	 
    }	
    System.out.println("calculating n_dW");
    
    Calculate_n_dw();
    return this.n_dw;
    }
    
    
    public static void main(String [] args) throws FileNotFoundException, IOException{
    	
    	
    	DocumentParser dp=new 	DocumentParser() ;
    dp.parseFiles("E:\\eclipse\\work_pllace\\my_docy\\data");
    dp.tfIdfCalculator();
    double ans[][]=dp.get_tfidf();
     //CMean.db(ans);
    
    }
}



