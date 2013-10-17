import java.util.List;
import java.util.HashMap;
import java.util.Map;

//
// Class to calculate TfIdf of term.
public class TfIdf {
	
	private Map<String,Double> idf_map;
	
	public TfIdf(){
		idf_map=new HashMap<String,Double>();
	}
	
    public double tfCalculator(String[] totalterms, String termToCheck) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }
//        System.out.println("tf_coutn="+count);
        
        return count / totalterms.length;
    }
    //
    public double idfCalculator(List <String[]>allTerms, String termToCheck) {
    	
    	if(idf_map.containsKey(termToCheck)){
    	//    System.out.println("hahahah  ... cache used ....");   
    		return (double)idf_map.get(termToCheck); 	
    	        }
    	        
    	double count = 0;
    	double ans = 0;
        for (String[] ss : allTerms) {
            for (String s : ss) {
                if (s.equalsIgnoreCase(termToCheck)) {
                    count++;  // calculatting no of document containing the word so need to bresk
                    break;
                }
            }
        }
        

 //       System.out.println("idf_coutn="+ (allTerms.size()/count ));

         ans=Math.log(allTerms.size() / count);
        idf_map.put(termToCheck, ans);        
        
         return ans;
                }
//

}
//