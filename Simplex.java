package hackpack;

public class Simplex {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	// Linear equations
	// 3x + 2y + z = 7
	// put coefficients into matrix
	// put zeros into first spots, and one into top spot 
	// zeros in lower left, ones in \ diagonal
	// cant pick next non-zero number
	// smaller the value, the less precise the division
	// pivot on maximum to reduce floating point error
	/*
	 * 1
	 * 0 1 
	 * 0 0 1
	 * 0 0 0 1 num
	 */
	// Gasssian elimination
	
	
	// Linear equations and mod
	// multiply by mod inverse to divide
	// write it out and try to divide through, and then try to get mod inverse
	// 2x == 4 mod 10
	// 2x = 10x+4
	// x == 2 mod 5
	
	// Simplex (Linear Programming)
	// x+y+z >= 0
	// x+3y >= 15
	// maximize 4x+2y+x
	// add slack variables
	// x+y+z + slack1 = 0
	// x+3y + slack2 = 15
	// find feasible solutions 
	// imagine hyperplanes, travel on edge, climb to another feasible point
}
