import java.util.ArrayList;

public class TicTacToe4 implements TicTacToe{
	
	private final int maxDepth = 16;
	private final int n = 4;
	private int[][] mtx;
	private int alpha; 
	private int beta;

	public TicTacToe4(int[][] mtx) {
		this.mtx = mtx;
	}
	
	public Position stepMiniMax(int xo,int[][] mtxTmp, int depth) {
		
		ArrayList<Position> pos = new ArrayList<Position>();
		Position actPos = null;
		
		HERE:
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(mtxTmp[i][j] == 0) {
					
					if(xo == -1) mtxTmp[i][j] = 2;
							else mtxTmp[i][j] = 1;
					
					actPos = new Position();
					actPos.x = i;
					actPos.y = j;
					actPos.p = evaluate(mtxTmp);
					
					if (depth < maxDepth && actPos.p != 1000 && actPos.p != -1000) {
						
						Position point;
						if ((point = stepMiniMax(-xo,mtxTmp,depth+1)) != null)
							actPos.p = point.p;
						
						if (xo == 1)
							if ( beta <= actPos.p) {
								mtxTmp[i][j] = 0;
								break HERE;
							}		
						else
							if ( alpha >= actPos.p) {
								mtxTmp[i][j] = 0;
								break HERE;
							}
					}
					
					pos.add(actPos);
					mtxTmp[i][j] = 0;
				}
			}
		}
		
		int best = -xo*1000;
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

	public int step(){
		
		Position pos;
		
		alpha = -10000;
		beta = 10000;
		
		if ((pos = stepMiniMax(-1,mtx,1)) == null) {
			System.out.println("Dontetlen!");
			return 0;
		}else {
			mtx[pos.x][pos.y] = 2;
		}
		if (pos.p == 1000) {
			System.out.println("X nyert!!!!");
			return 1;
		}
		if (pos.p == -1000) {
			System.out.println("O nyert!!!!");
			return -1;
		}
		return 2;
	}
	
	public int evaluate(int[][] mtxTmp) {
		
		int value = 0;
		int xCount = 0;
		int oCount = 0;

		//vertical
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				
				if(mtxTmp[j][i] == 1) {
					xCount++;
				}
				if(mtxTmp[j][i] == 2) {
					oCount++;
				}
			}
			if (oCount == 0 && xCount > 0) {
				value += Math.pow(10, xCount - 1);
			}
			if (xCount == 0 && oCount > 0) {
				value -= Math.pow(10, oCount - 1);
			}
			oCount = 0;
			xCount = 0;
		}
		
		//horizontal
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(mtxTmp[i][j] == 1) {
					xCount++;
				}
				if(mtxTmp[i][j] == 2) {
					oCount++;
				}
			}
			if (oCount == 0 && xCount > 0) {
				value += Math.pow(10, xCount - 1);
			}
			if (xCount == 0 && oCount > 0) {
				value -= Math.pow(10, oCount - 1);
			}
			oCount = 0;
			xCount = 0;
		}
		
		//diagonal
		int xCountDiag = 0;
		int oCountDiag = 0;	
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if( i == j && mtxTmp[i][j] == 1) 
					xCount++;
				if( i == j && mtxTmp[i][j] == 2) 
					oCount++;
				if( (i + j) == (n-1) && mtxTmp[i][j] == 1) 
					xCountDiag++;
				if( (i + j) == (n-1) && mtxTmp[i][j] == 2) 
					oCountDiag++;
				
			}
		}
		
		if (oCount == 0 && xCount > 0) {
			value += Math.pow(10, xCount - 1);
		}
		if (xCount == 0 && oCount > 0) {
			value -= Math.pow(10, oCount - 1);
		}
		
		if (oCountDiag == 0 && xCountDiag > 0) {
			value += Math.pow(10, xCountDiag - 1);
		}
		if (xCountDiag == 0 && oCountDiag > 0) {
			value -= Math.pow(10, oCountDiag - 1);
		}
		
		for (int i = 0; i < n; i++) 
			for (int j = 0; j < n; j++) 
				if(mtxTmp[i][j] == 0) return value;
		
		return 0;
	}

}