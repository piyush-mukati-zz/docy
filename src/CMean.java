

//
//@author piyush mukati
//
//
public class CMean {
	
	
	public static void db(double [][] a){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[i].length;j++ ){
	System.out.print(a[i][j]+ "   ");
	}System.out.println("");
			}
	
	}
	
	public double cosSim(double[] docVector1, double[] docVector2) {
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;
        double cosineSimilarity = 0.0;

        for (int i = 0; i < docVector1.length; i++) //docVector1 and docVector2 must be of same length
        {
            dotProduct += docVector1[i] * docVector2[i];  //a.b
            magnitude1 += Math.pow(docVector1[i], 2);  //(a^2)
            magnitude2 += Math.pow(docVector2[i], 2); //(b^2)
        }

        magnitude1 = Math.sqrt(magnitude1);//sqrt(a^2)
        magnitude2 = Math.sqrt(magnitude2);//sqrt(b^2)

        if (magnitude1 != 0.0 | magnitude2 != 0.0) {
            cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
        } else {
            return 0.0;
        }
        return cosineSimilarity;
    }
	
	public double diff(double [] a,double [] b){
		
		double ans= 1-cosSim(a,b);
		if(ans==0.0)ans=0.00000001;
//	System.out.println("cosine= " +ans);
		return ans;
	}
	
	public void normaliseOnRow(double [][] U){
		for(int i=0;i<U.length;i++){
			double sum=0;
			for(int j=0;j<U[i].length;j++ ){
				sum+=U[i][j];	
				}
			for(int j=0;j<U[i].length;j++ ){
				U[i][j]=U[i][j]/sum;	
				}
		}

	}
	

	double find_delta(double[][] U,double[][] Unew){
		double delta=0.0;
		for(int i=0;i<U.length;i++){
			for(int j=0;j<U[i].length;j++){
				delta+=Math.abs(U[i][j]-Unew[i][j]);
		
			}
			
		}
		
		return delta;
		
	}
	
	public CMean(){
	}
	
	public  double[][] cluster(  double pop[][] , int c,double e,int m){
		
		System.out.println("initializing....");
		 
		double center [][]=new double [c][ (pop[0].length)  ]; //center [class][fetures]
		double U [][]=new double [pop.length][c];    //u membership  [document]*[class]
		double Unew [][]=new double [pop.length][c];    //u membership of i+i step [document]*[class]
		
		//init to u with random
		for(int i=0;i<U.length;i++){
			for(int j=0;j<U[i].length;j++ ){
			U[i][j]=Math.random();	
			}
			}
		normaliseOnRow(U);
	
		int itr=0;
		
		int Max_itr=50;
		double delta=0.0;
		
		// rhresohold ki condition bich mai hai..
		//@ main loop
		while(itr<Max_itr ){
		itr++;
		System.out.println("itration = "+itr+"\nfirst part...");
	
		
		// calculation of center first formulae
		for(int i=0;i<c;i++){
			// calculation of denumerator
			double deno=0.0;
			for(int j=0;j<U.length;j++ ){
				deno+=(Math.pow(U[j][i],m) );	
				}
			
				//calculation of numerator
			 double   [] x= new double [pop[0].length]; // dont write in x read only
			for(int xxx=0; xxx<x.length; xxx++)x[xxx]=0.0;
		
			
			for(int j=0;j<pop.length;j++){
				
				double uKiPower=Math.pow(U[j][i],m);
						
				 for(int k=0;k<x.length;k++){
					x[k]+= uKiPower*pop[j][k];
				 
				 }
			}
			
			//devide
			for(int k=0;k<x.length;k++){center[i][k]= x[k]/deno ;}
			
		}
	
		//end of first formulae
		System.out.println("second part...");
		
		for(int i=0;i<pop.length;i++){ //for each document
			
			double x[]=new double[pop[0].length];
			for(int k=0;k<x.length;k++){x[k]= pop[i][k];}
			
			for(int j=0;j<c;j++){ //for each class
				
				//calculation
				double tmp=0.0;
				for(int k=0;k<c;k++){ //for each class
					double dd=0.0;
					
					dd=diff(x,center[j]) /diff(x,center[k]);
					
					tmp+=(Math.pow(dd ,2.0/(double)(m-1)));
					
				}
//doc,class
			Unew[i][j]=1.0/tmp;
			}

		}
		//end of second formulae............................
		
		//coping
		
		delta=find_delta(U,Unew);
		
for(int i=0;i<pop.length;i++){ //for each document
			for(int j=0;j<c;j++){ //for each class
	U[i][j]=Unew[i][j];
	}
	}
		
		///.......debug///....
		//db(pop);
		System.out.println("...................................");
		db(center);

		System.out.println("UUUUUU");
		
		db(U);

		System.out.println("...................................");
		
		//db(Unew);

//		System.out.println("...................................");
			

if( e>delta )		
break;
		}
	
			
		return center;
	}
	
	public static void main(String[] args) {
		CMean cm=new CMean();
	
		
		double pop [][]={
	
				{ 8147 ,   975},
					{ 9058  ,  0},
		    {1270  ,  5469},
		    {9134   , 9575},
		    {6324   , 9649}
		    };
		
		cm.cluster(pop, 3,0.01,3);
	}

}
