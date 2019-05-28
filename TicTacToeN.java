import java.util.ArrayList;

public class TicTacToeN implements TicTacToe{
	
	private final int maxDepth = 2;
	private final int m = 5;
	private int n;
	private int[][] mtx;
	private int alpha; 
	private int beta;

	public TicTacToeN(int[][] mtx, int n) {
		this.n = n;
		this.mtx = mtx;
	}
	
	public Position stepMiniMax(int xo,int[][] mtxTmp, int depth) {
		
		ArrayList<Position> pos = new ArrayList<Position>();
		Position actPos = null;
		
		HERE:
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(mtxTmp[i][j] == 0) { // ha van mellette valami
					
					if(xo == -1) mtxTmp[i][j] = 2;
							else mtxTmp[i][j] = 1;
					
					actPos = new Position();
					actPos.x = i;
					actPos.y = j;
					actPos.p = evaluate(mtxTmp);
					
					if (depth < maxDepth && actPos.p < Math.pow(10, Math.pow(10, m-1)) && actPos.p > -Math.pow(10, Math.pow(10, m-1))) {
						
						Position point;
						if ((point = stepMiniMax(-xo,mtxTmp,depth+1)) != null)
							actPos.p = point.p;
						
						if (xo == 1)
							if ( beta <= actPos.p) {
								System.out.println("Vágás beta < actPos");
								mtxTmp[i][j] = 0;
								break HERE;
							}		
						else
							if ( alpha >= actPos.p) {
								System.out.println("Vágás alpha > actPos");
								mtxTmp[i][j] = 0;
								break HERE;
							}
					}
					
					pos.add(actPos);
					mtxTmp[i][j] = 0;
				}
			}
		}
		
		int best = -xo*1000000;
		Position bestPos = null;
		
		for( Position i : pos ) {
			if(xo == 1) {
				if(i.p >= best) {
					if (i.p > alpha)
						alpha = i.p;
					best = i.p;
					bestPos = i;
				}
			} else {
				if(i.p <= best) {
					if (i.p < beta)
						beta = i.p;
					best = i.p;
					bestPos = i;
				}
			}
		}
		
		pos.removeAll(pos);
		return bestPos;
	} 

	public int step() {
		
		Position pos;
		
		alpha = (int) -Math.pow(10, m);
		beta = (int) Math.pow(10, m);
		
		if ((pos = stepMiniMax(-1,mtx,1)) == null) {
			System.out.println("Dontetlen!");
			return 0;
		} else {
			mtx[pos.x][pos.y] = 2;
		}
		
		System.out.println(pos.p);
		System.out.println(evaluate(mtx));
		
		if (evaluate(mtx)+1000 >= Math.pow(10, m-1)) {
			System.out.println("X nyert!!!!");
			return 1;
		}
		if (evaluate(mtx)-1000 <= -Math.pow(10, m-1)) {
			System.out.println("O nyert!!!!");
			return -1;
		}
		return 2;
	}

	public ArrayList<ArrayList<Integer>> getVertical() {
		ArrayList<ArrayList<Integer>> vertical = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n-m+1; j++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>();
				for(int k = j; k<j+m; k++) {
					tmp.add(mtx[i][k]);
				}
				vertical.add(tmp);
			}
		}
		return vertical;
	}
	
	public ArrayList<ArrayList<Integer>> getHorizontal() {
		ArrayList<ArrayList<Integer>> horizontals = new ArrayList<ArrayList<Integer>>();
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n-m+1; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>();
				for(int k = i; k<i+m; k++) {
					tmp.add(mtx[k][j]);
				}
				horizontals.add(tmp);
			}
		}
		return horizontals;
	}
	
	public ArrayList<ArrayList<Integer>> getDiagonal() {
		ArrayList<ArrayList<Integer>> diagonal = new ArrayList<>();
        // (i=j +/- x)
        for (int x = 0; x < n; x++) {
            for (int i = 0; i < n-m+1; i++) {
            	ArrayList<Integer> tmp1 = new ArrayList<Integer>();
                ArrayList<Integer> tmp2 = new ArrayList<Integer>();
            	for(int k = i; k<i+m; k++) {
	                if (k-x >= 0 && k-x < n) tmp1.add(mtx[k][k - x]);
	                if (k+x >= 0 && k+x < n) tmp2.add(mtx[k][k + x]);
            	}
            	if (tmp1.size() == m) diagonal.add(tmp1);
                if (tmp2.size() == m && x != 0) diagonal.add(tmp2);
            }
        }
        // (j = (n-1) - i +/- x)
        for (int x = 0; x < n; x++) {
            for (int i = 0; i < n-m+1; i++) {
            	ArrayList<Integer> tmp1 = new ArrayList<Integer>();
                ArrayList<Integer> tmp2 = new ArrayList<Integer>();
            	for(int k = i; k<i+m; k++) {
            		if ((n-1)-k-x>=0 && (n-1)-k-x<n) tmp1.add(mtx[k][(n-1) - k - x]);
                    if ((n-1)-k+x>=0 && (n-1)-k+x<n) tmp2.add(mtx[k][(n-1) - k + x]);
            	}
            	if (tmp1.size() == m) diagonal.add(tmp1);
                if (tmp2.size() == m && x != 0) diagonal.add(tmp2);
            }
        }
        return diagonal;
	}
	
	public int evaluate(int[][] mtxTmp) {
		
		ArrayList<ArrayList<Integer>> horizontal = getHorizontal();
		ArrayList<ArrayList<Integer>> vertical = getVertical();
		ArrayList<ArrayList<Integer>> diagonal = getDiagonal();
		
		int value = 0;
		int xCount = 0;
		int oCount = 0;

		for (ArrayList<Integer> i : horizontal) {
			xCount = 0;
			oCount = 0;
			for (Integer item : i) {
				if(item == 1) {
					xCount++;
				}
				if(item == 2) {
					oCount++;
				}
			}
			if (oCount == 0 && xCount > 0) {
				value += Math.pow(10, xCount - 1);
			}
			if (xCount == 0 && oCount > 0) {
				value -= Math.pow(10, oCount - 1);
			}
		}
		
		for (ArrayList<Integer> i : vertical) {
			xCount = 0;
			oCount = 0;
			for (Integer item : i) {
				if(item == 1) {
					xCount++;
				}
				if(item == 2) {
					oCount++;
				}
			}
			if (oCount == 0 && xCount > 0) {
				value += Math.pow(10, xCount - 1);
			}
			if (xCount == 0 && oCount > 0) {
				value -= Math.pow(10, oCount - 1);
			}
		}
		
		for (ArrayList<Integer> i : diagonal) {
			xCount = 0;
			oCount = 0;
			for (Integer item : i) {
				if(item == 1) {
					xCount++;
				}
				if(item == 2) {
					oCount++;
				}
			}
			if (oCount == 0 && xCount > 0) {
				value += Math.pow(10, xCount - 1);
			}
			if (xCount == 0 && oCount > 0) {
				value -= Math.pow(10, oCount - 1);
			}
		}
				
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(mtxTmp[i][j] == 0) return value;
			}
		}
		
		return 0;
	}
}