import java.util.ArrayList;

public class TicTacToe3 implements TicTacToe{
	
	private final int n = 3;
	private int[][] mtx;
	
	public TicTacToe3(int[][] mtx) {
		this.mtx = mtx;
	}
	
	public Position stepMiniMax(int xo,int[][] mtxTmp, int depth) {
		
		ArrayList<Position> pos = new ArrayList<Position>();
		Position actPos = null;
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(mtxTmp[i][j] == 0) {
					
					if(xo == -1) mtxTmp[i][j] = 2;
							else mtxTmp[i][j] = 1;
					
					// System.out.println("Próba: "+i+" "+j+": "+actPos.p);
					
					actPos = new Position();
					actPos.x = i;
					actPos.y = j;
					actPos.p = evaluate(mtxTmp);
					
					// Van ures hely -> nem vagyunk az aljan
					if(actPos.p == 2 ) {
						actPos.p = stepMiniMax(-xo,mtxTmp,depth+1).p;
					}
					
					pos.add(actPos);
					mtxTmp[i][j] = 0;
				}
			}
		}
		
		int best = -xo;
		Position bestPos = null;
		
		//MiniMax
		//X -> X: -1 -> -1
		//O -> O: 1 -> 1
		
		for( Position i : pos ) {
			if(xo > 0) {
				if(i.p >= best) {
					best = i.p;
					bestPos = i;
				}
			}else {
				if(i.p <= best) {
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
		
		if ( (pos = stepMiniMax(-1,mtx,0)) == null ) {
			System.out.println("Dontetlen!");
			return -2;
		}else {
			mtx[pos.x][pos.y] = 2;
		}
		
		if( evaluate(mtx) == 1 ){
			System.out.println("X nyert!!!!");
			return 1;
		}
		if (evaluate(mtx) == -1) {
			System.out.println("O nyert!!!!");
			return -1;
		}

		return 0;
	}
	
	public int evaluate(int[][] mtxTmp) {
		
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
			if (xCount == n ) {
				return 1;
			}
			if (oCount == n ) {
				return -1;
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
			if (xCount == n ) {
				return 1;
			}
			if (oCount == n ) {
				return -1;
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
		
		if (xCount == n || xCountDiag == n ) {
			//X nyer
			return 1;
		}
		if (oCount == n || oCountDiag == n ) {
			//O nyer
			return -1;
		}
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {		
				if(mtxTmp[i][j] == 0 ) {
					//nem lehet eldonteni
					return 2;
				}
			}
		}
		
		//dontetlen
		return 0;
	}
}