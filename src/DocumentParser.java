
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
    private List <String[]> termsDocsArray = new ArrayList<String[]>();
    private List <String> allTerms = new ArrayList<String>(); //to hold all terms
    private List <double[]> tfidfDocsVector = new ArrayList<double[]>();

    
      public void parseFiles(String filePath) throws FileNotFoundException, IOException {
        File[] allfiles = new File(filePath).listFiles();
        BufferedReader in = null;
        int cnt=0;
    	
        for (File f : allfiles) {
            if (f.getName().endsWith(".txt")) {
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
        TfIdf object_tfidf=new TfIdf();
        
        for (String[] doc : termsDocsArray) {
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String term : allTerms) {
                tf = object_tfidf.tfCalculator(doc, term);
                idf = object_tfidf.idfCalculator(termsDocsArray, term);
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
    public static void main(String [] args) throws FileNotFoundException, IOException{
    	
    	
    	DocumentParser dp=new 	DocumentParser() ;
    dp.parseFiles("E:\\eclipse\\work_pllace\\my_docy\\data");
    dp.tfIdfCalculator();
    double ans[][]=dp.get_tfidf();
     //CMean.db(ans);
    
    }
}



