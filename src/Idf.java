import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Idf {

	private Map<String,Double> idf_map;
	
	public Idf(){
		idf_map=new HashMap<String,Double>();
		}
	
    public double calculate(List <String[]>allTerms, String termToCheck) {
    	
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
