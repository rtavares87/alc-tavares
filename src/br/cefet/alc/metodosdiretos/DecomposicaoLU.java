package br.cefet.alc.metodosdiretos;

import br.cefet.alc.util.Util;


/**
 * @author rtavares
 */
public class DecomposicaoLU implements Metodo {
	
	@Override
	public void executar(double[][] matriz) throws Exception {
		
		getX(matriz);
		
	}
	
	public double[] getX(double[][] matriz) throws Exception {
		
		Util.get().escrever("Matir Original: ");
		Util.get().escreveMatriz(matriz);
		
		double[][] a = Util.get().getMatrizA(matriz);
		double[] b = Util.get().getMatrizB(matriz);
		
		int m = a.length;
		
		int[] permut = new int[m];
		
		for (int i = 0; i < m; i++)
			permut[i] = i;
		
		double[][] LU = getLU(a, permut);
		
		Util.get().escrever("");
 		Util.get().escrever("LU: ");
        Util.get().escreveMatriz(LU);
		
		double[][] L = getL(LU);
		
		Util.get().escrever("");
 		Util.get().escrever("L: ");
        Util.get().escreveMatriz(L);
		
		double[][] U = getU(LU);
        
		Util.get().escrever("");
        Util.get().escrever("U: ");
        Util.get().escreveMatriz(U);
		
        double[] bPerm = new double[m];
        for(int i = 0; i < m; i++)
        	bPerm[i] = b[permut[i]];
        
		// L * y = b
		
		double[] y = new double[L.length];
		
		for(int i = 0; i < L.length; i++){
			
			double soma = 0;
			
			for(int j = 0; j < L[i].length; j++){
				
				if(i == j)
					continue;
				
				soma = soma + (L[i][j] * y[j]);
				
			}
			
			y[i] = (bPerm[i] - soma) / L[i][i];
			
		}
		
		Util.get().escrever("");
 		Util.get().escrever("Y: ");
        Util.get().escreveVetor(y);
		
		// U * x = y
		
		double[] x = new double[U.length];
		
		for(int i = (U.length - 1); i >= 0; i--){
			
			double soma = 0;
			
			for(int j = (U.length - 1); j >= 0; j--){
				
				if(i == j)
					continue;
				
				soma = soma + (U[i][j] * x[j]);
				
			}
			
			x[i] = (y[i] - soma) / U[i][i];
			
		}
		
		Util.get().escrever("");
		Util.get().escrever("X: ");
		Util.get().escreveVetor(x);
		
		return x;
		
	}
	
	public double[][] getLU(double[][] a, int[] permut){
		
		double[][] LU = a;
		
		int m = LU.length;
		int n = LU[0].length;
		
		int pivot = 1;
		double[] LUlinhai;
		double[] LUcolunaj = new double[m];

	    for (int j = 0; j < n; j++) {

	         for (int i = 0; i < m; i++)
	        	 LUcolunaj[i] = LU[i][j];
	         
	         for (int i = 0; i < m; i++) {
	            
	        	 LUlinhai = LU[i];

	        	 int kmax = Math.min(i,j);
	        	 double soma = 0.0;
	            
	        	 for (int k = 0; k < kmax; k++)
	            	soma += LUlinhai[k]*LUcolunaj[k];
	            

	        	 LUlinhai[j] = LUcolunaj[i] -= soma;
	        	 
	         }
	   
	         int p = j;
	         
	         for (int i = j+1; i < m; i++) {
	            
	        	 if (Math.abs(LUcolunaj[i]) > Math.abs(LUcolunaj[p])) {
	               p = i;
	            }
	        	 
	         }
	         
	         if (p != j) {
	            
	        	 for (int k = 0; k < n; k++) {
	               
	        		 double t = LU[p][k]; 
	        		 LU[p][k] = LU[j][k]; 
	        		 LU[j][k] = t;
	        		 
	            }
	            
	            int k = permut[p]; 
	            permut[p] = permut[j]; 
	            permut[j] = k;
	            pivot = -pivot;
	            
	         }

	         if (j < m & LU[j][j] != 0.0) {
	            
	        	 for (int i = j+1; i < m; i++)
	               LU[i][j] /= LU[j][j];
	            
	         }
	         
	      }
	    
	    return LU;
		
	}
	
	public double[][] getL(double[][] LU) {
		
		int n = LU.length;
		
		double[][] L = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i > j) {
					L[i][j] = LU[i][j];
				} else if (i == j) {
					L[i][j] = 1.0;
				} else {
					L[i][j] = 0.0;
				}
			}
	  }
	  
	  return L;
		
	}
	
	public double[][] getU(double[][] LU) {
		
		int n = LU.length;
		
		double[][] U = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i <= j) {
					U[i][j] = LU[i][j];
				} else {
					U[i][j] = 0.0;
				}
			}
		}
		
		return U;
        
	}

}
