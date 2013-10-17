

//
//@author piyush mukati
//
//
public class CMean {
	private int c;					//no of centers
	private int m;
	private double e;
	
	private  double  [][] pop=null;	//population
	
	public double[][] centers =null;		//centers of clusters
	public double [][] U=null;       //membership
	
	
	public  CMean( double pop[][] , int c,double e,int m ){
		this.m=m;
		this.e=e;
		this.c=c;
		this.pop=pop;
		this.centers=new double [c][ (pop[0].length)  ]; //center [class][fetures]
		this.U=new double [pop.length][c];    //u membership  [document]*[class]
	

		//init to u with random
		for(int i=0;i<U.length;i++){
			for(int j=0;j<U[i].length;j++ ){
			U[i][j]=Math.random();	
			}
			}
		normaliseOnRow(U);
	
	}	
	
	public static void db(double [][] a){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[i].length;j++ ){
	System.out.print(a[i][j]+ "   ");
	}System.out.println("");
			}
	
	}
	
	private double obj_func( ){
		double ans=0;
	for(int i=0;i<this.centers.length;i++){
		for(int j=0;j<this.pop.length;j++){
			ans+=( Math.pow(this.U[j][i],this.m)*diff(this.pop[j],this.centers[i]) );
			}
		}
		//System.out.println(ans);
		return ans;
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
	
	private void normaliseOnRow(double [][] U){
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
	

	private double find_delta(double[][] Unew){
		double delta=0.0;
		
		for(int i=0;i<U.length;i++){
			for(int j=0;j<U[i].length;j++){
				delta=Math.max(Math.abs(U[i][j]-Unew[i][j]),delta);
		
			}
			
		}
		/*
		delta=Math.abs(obj_func( )-obj_func( x ,c, Unew , 2));
			*/
		return delta;
		
	}
	
	
	public  double[][] cluster( ){
		
		System.out.println("initializing....");
		 
		double Unew [][]=new double [pop.length][c];    //u membership of i+1 step [document]*[class]
		
		int itr=0;
		
		int Max_itr=50;
		double delta=0.0;
		
		// thresohold ki condition bich mai hai..
		//@ main loop
		while(itr<Max_itr ){
		itr++;
		System.out.println("itration = "+itr+"objective function = "+obj_func());
		System.out.println("finding centers ....");
		
		
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
			for(int k=0;k<x.length;k++){centers[i][k]= x[k]/deno ;}	
		}
		
		System.out.println("calculating membership...");
		
		for(int i=0;i<pop.length;i++){ //for each document
			
			double x[]=new double[pop[0].length];
			for(int k=0;k<x.length;k++){x[k]= pop[i][k];}
			
			for(int j=0;j<c;j++){ //for each class
				
				//calculation
				double tmp=0.0;
				for(int k=0;k<c;k++){ //for each class
					double dd=0.0;
					
					dd=diff(x,centers[j]) /diff(x,centers[k]);
					tmp+=(Math.pow(dd ,2.0/(double)(m-1)));		
				}
//doc,class
			Unew[i][j]=1.0/tmp;
			}
		}
		//end of second formulae............................
		delta=find_delta( Unew);
		//coping	
for(int i=0;i<pop.length;i++){ //for each document
			for(int j=0;j<c;j++){ //for each class
	U[i][j]=Unew[i][j];
	}
	}
///.......debug///......................................................
		int debug=0;
		if(debug==1){
		//db(pop);
		System.out.println("...................................");
		db(centers);
		System.out.println("UUUUUU");
		db(U);
		System.out.println("...................................");
		//db(Unew);
//		System.out.println("...................................");
		}
if( e>delta )		
break;
}
System.out.println("membership values...");
		db(U);
		
		return centers;
	}
	
	/*/.............................................................................................
	
	public static void main(String[] args) {
		
		double pop [][]={
	
				{ 0.8147 ,   0.0975},
					
		    {0.1270  ,  0.5469},
		    {.9134   , .9575},
		    {.6324   , .9649}
		    };
		

		CMean cm=new CMean(pop, 3,0.01,3);
	
		cm.cluster();
	}
*/
	
}
