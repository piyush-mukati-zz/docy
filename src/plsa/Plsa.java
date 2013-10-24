package plsa;

public class Plsa {
private	int d_w[][];
private int w;
private int z;
private int d;

public double[] pz;
public double[][] pd_z;
public double[][] pw_z;
public double[][][] pz_dw;

public Plsa(int mat[][],int topics){
	if(mat==null)System.err.println("mat is null-- kya kyu koun ... ");
	
	d_w=mat;	
	w=mat[0].length;
	d=mat.length;
	z=topics;
}

private boolean init()
{
	// p(z), size: z
    double zvalue = (double) 1 / (double) this.z;
    for (int zz = 0; zz < this.z; zz++)
    {
    	pz[zz] = zvalue;
    }

    // p(d|z), size: d x z
    for (int zz = 0; zz < this.z; zz++)
    {
    	double norm = 0.0;
        for (int m = 0; m < this.d; m++)
        {
        	pd_z[zz][m] = Math.random();
            norm += pd_z[zz][m];
        }

        for (int m = 0; m < this.d; m++)
        {
        	pd_z[zz][m] /= norm;
        }
    }

    // p(w|z), size: z x w
    for (int zz = 0; zz < this.z; zz++)
    {
    	double norm = 0.0;
        for (int ww = 0; ww < this.w; ww++)
        {
        	pw_z[zz][ww] = Math.random();
            norm += pw_z[zz][ww];
        }

        for (int ww = 0; ww < this.w; ww++)
        {
        	pw_z[zz][ww] /= norm;
        }
    }

    // p(z|d,w), size: z x d x w
    for (int zz = 0; zz < this.z; zz++)
    {
    	for (int dd = 0; dd < this.d; dd++)
        {
     		pz_dw[zz][dd] = new double[this.w]; ///could be optimized
        }
    }
    return false;
}
public void EM(int max_itr){
	
  	// p(z), size: z
     pz = new double[this.z];

      // p(d|z), size: z x d
      pd_z = new double[this.z][this.d];

      // p(w|z), size: z x w
       pw_z = new double[this.z][this.w];

      // p(z|d,w), size: z x d x doc.size()
       pz_dw = new double[this.z][this.d][];

       // L: log-likelihood value
       double l = -1;

	this.init();
	
	for(int itr=1;itr<=max_itr;itr++){
		Estep();
		Mstep();
	l=	calcLoglikelihood();
		System.out.println(itr+" log liklihood ="+l);
		
	}
	
}


private void Estep()
{
	for (int dd = 0; dd < this.d; dd++)
    {
        for (int ww = 0; ww < this.w; ww++)
        {
        	
            double norm = 0.0;
            for (int zz = 0; zz < this.z; zz++)
            {
            	double val = pz[zz] * pd_z[zz][dd] * pw_z[zz][ww];
                pz_dw[zz][dd][ww] = val;
                norm += val;
            }

            // normalization
            for (int zz = 0; zz < this.z; zz++)
            {
            	pz_dw[zz][dd][ww] /= norm;
            }
        }
    }
}

private void Mstep()
{
	// p(w|z)
    for (int zz = 0; zz < this.z; zz++)
    {
    	double norm = 0.0;
        for (int ww = 0; ww < this.w; ww++)
        {
        	double sum = 0.0;

            for (int dd=0;dd<this.d;dd++)
            {
            	
              sum += this.d_w[dd][ww] * pz_dw[zz][dd][ww];
            }
            pw_z[zz][ww] = sum;
            norm += sum;
        }

        // normalization
        for (int ww = 0;ww < this.w; ww++)
        {
        	pw_z[zz][ww] /= norm;
        }
    }

    // p(d|z)
    for (int zz = 0; zz < this.z; zz++)
    {
    	double norm = 0.0;
        for (int dd = 0; dd < this.d; dd++)
        {
        	double sum = 0.0;
          
        	for (int ww = 0; ww < this.w; ww++)
            {
                sum += this.d_w[dd][ww] * pz_dw[zz][dd][ww];
            }
            pd_z[zz][dd] = sum;
            norm += sum;
        }

        // normalization
        for (int dd = 0; dd < this.d; dd++)
        {
        	pd_z[zz][dd] /= norm;
        }
    }

    
    //p(z)     
    double norm = 0.0;
    for (int zz = 0; zz < this.z; zz++)
    {
    	double sum = 0.0;
        
    	for (int dd = 0; dd < this.d; dd++)
        {
    		for (int ww = 0; ww < this.w; ww++)
            {
            
        	sum += this.d_w[dd][ww] * pz_dw[zz][dd][ww];
        }
   }
        pz[zz] = sum;
        norm += sum;
    }

    // normalization
    for (int zz = 0; zz < this.z; zz++)
    {
    	pz[zz] /= norm;
    	//System.out.format("%10.4f", pz[z]);  //here you can print to see
    }
    //System.out.println();

    
}

private double calcLoglikelihood()
{
	double L = 0.0;
    for (int dd = 0; dd < this.d; dd++)
    {
    	for (int ww = 0; ww < this.w; ww++)
        {
           double sum = 0.0;
            for (int zz = 0; zz < this.z; zz++)
            {
            	sum += pz[zz] * pd_z[zz][dd] * pw_z[zz][ww];
            }
            L += this.d_w[dd][ww] * Math.log10(sum);
        }
    }
    return L;
}

public static void main(String [][]args){
	int mat[][]=new int[2][3];
	int tmat[]={1,1,1};
	int pmat[]={1,1,1};
	mat[0]=tmat;
	mat[1]=pmat;
	
	Plsa plsa=new Plsa(mat,2);
	plsa.EM(7);
}



}
