import java.util.HashMap;
import java.util.Map;


public class Tf {
private Map<String,Double> tf_map;
	
	public Tf(){
		tf_map=new HashMap<String,Double>();
		}
	
    public double calculate(String[] totalterms, String termToCheck) {
    	if(tf_map.containsKey(termToCheck)){
        		return (double)tf_map.get(termToCheck); 	
        	        }
        
    	double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }

        double ans= count / totalterms.length;
       tf_map.put(termToCheck, ans);           
        return ans;
    } 

}
