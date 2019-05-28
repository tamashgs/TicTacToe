
public interface TicTacToe {
		
	public Position stepMiniMax(int xo,int[][] mtx, int depth);

	public int step();
	
	public int evaluate(int[][] mtx);
	
	public static int[][] copy(int[][] from) {
		int n = from.length;
		int [][] to = new int[n][];
		for(int i = 0; i < n; i++){
		  int[] aMatrix = from[i];
		  to[i] = new int[n];
		  System.arraycopy(aMatrix, 0, to[i], 0, n);
		}
		return to;
	}
	
	public static void outMtx(int[][] mtx) {
		int n = mtx.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(mtx[j][i]+" ");
			}
			System.out.println();
		}
	}
}
