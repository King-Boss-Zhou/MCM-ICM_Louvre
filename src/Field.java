import java.util.ArrayList;

public class Field {
	private int width;
	private int height;
	Cell [][] field;
	
	public Field(int w, int h) {
		width = w;
		height = h;
		field = new Cell[h][w];
	}
	public Cell place(int row, int col, Cell o) {
		Cell ret = field[row][col];
		field[row][col] = o;
		return ret;
	}
	public Cell getCell(int x, int y) {
		return field[x][y];
	}
	public int getwidth() {
		return width;
	}
	public int getheight() {
		return height;
	}
	public Cell[] getneighbor(int row, int col) {
		ArrayList<Cell> list = new ArrayList<Cell>();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				int r = row + i;
				int c = col + j;
				if ( r >-1 && r<height && c>-1 && c<width && !(r== row && c == col) ) {
					list.add(field[r][c]);
				}
			}
		}
		return list.toArray(new Cell[list.size()]);
	}
}
