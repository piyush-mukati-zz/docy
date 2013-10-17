import java.util.List;
import java.util.HashMap;
import java.util.Map;

//
// Class to calculate TfIdf of term.
public class TfIdf {
	
	private Map<String,Double> idf_map;
	private Map<String,Double> tf_map;
	
	public TfIdf(){
		idf_map=new HashMap<String,Double>();
		tf_map=new HashMap<String,Double>();
		}
	
    public double tfCalculator(String[] totalterms, String termToCheck) {
    	if(idf_map.containsKey(termToCheck)){
        		return (double)tf_map.get(termToCheck); 	
        	        }
        
    	double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }

        double ans= count / totalterms.length;
       idf_map.put(termToCheck, ans);           
        return ans;
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