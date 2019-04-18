import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class View extends JPanel{
	private static final long serialVersionUID = -5258995676212660595L;
	private static final int GRID_SIZE = 1;
	private Field theField;
	
	public View(Field field) {
		theField = field;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		ArrayList<Cell>exit = new ArrayList<Cell>();
		for (int row = 0; row<theField.getheight(); row++ ) {
			for (int col = 0; col<theField.getwidth(); col++ ) {
				Cell cell = theField.getCell(row, col);
				if ( cell != null ) {
					cell.draw(g, col*GRID_SIZE, row*GRID_SIZE, GRID_SIZE);
					if(cell.isExit() == 1) {
						exit.add(cell);
					}
				}
			}
		}
		for(Cell c: exit) {
			int x = c.getx();
			int y = c.gety();
			c.draw(g, y*GRID_SIZE, x*GRID_SIZE, GRID_SIZE);
		}
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(theField.getwidth()*GRID_SIZE+1, theField.getheight()*GRID_SIZE+1);
	}
}
