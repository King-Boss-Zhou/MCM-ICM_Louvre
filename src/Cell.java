import java.awt.Color;
import java.awt.Graphics;

public class Cell {
	private int Wall;
	private int num;
	private int Exit;
	private int Speed;
	private int max_num;
	private double Safe[];
	private double S_Safe[];
	private double D_Safe;
	private int x;
	private int y;
	
	public Cell(int x, int y) {
		Wall = 0;
		num = 0;
		Exit = 0;
		Speed = 20;
		max_num = 200;
		D_Safe = 0;
		this.x = x;
		this.y = y;
	}
	public void setwall() {
		Wall = 1;
	}
	public int isWall() {
		return Wall;
	}
	public int isExit() {
		return Exit;
	}
	public int isFull() {
		return max_num - num;
	}
	public void setnum(int n) {
		num = n;
	}
	public void addnum(int n) {
		num += n;
	}
	public void subnum(int n) {
		num -= n;
	}
	public void outnum() {
		if(num > Speed) {
			num -= Speed;
		}
		else {
			num = 0;
		}
		
	}
	public int getnum() {
		return num;
	}
	public int getx() {
		return x;
	}
	public int gety() {
		return y;
	}
	public double getsafe(int n) {
		return Safe[n];
	}
	public double getssafe(int n) {
		return S_Safe[n];
	}
	public double getdsafe() {
		return D_Safe;
	}
	public void setexit(int n) {
		Exit = 1;
		Safe[n] = 1000;
		S_Safe[n] = 1000;
		max_num = 200;
	}
	public void setdexit() {
		Exit = 1;
		D_Safe = 1000;
		max_num = 200;
	}
	public void setsafe(int n, double s) {
		Safe[n] = s;
	}
	public void setssafe(int n, double s) {
		S_Safe[n] = s;
	}
	public void setdsafe(double s) {
		D_Safe = s;
	}
	public void initsafe(int n) {
		Safe = new double[n];
		S_Safe = new double[n];
		for(int i = 0; i < n; i++) {
			Safe[i] = 0;
			S_Safe[i] = 0;
		}
	}
	public void clearnum() {
		num = 0;
	}
	public void draw(Graphics g, int x, int y, int size) {
		if(Wall == 0) {
			if(Exit == 0) {
				if(num == 0) {
					Color c1 = new Color(178, 215, 227);
					g.setColor(c1);
					g.fillRect(x, y, size, size);
				}
				else {
					//double temp = num / 100.0;
					Color c2 = new Color(102, 149, 125);
					g.setColor(c2);
					g.fillRect(x, y, size, size);
				}
			}
			if(Exit == 1) {
				Color c4 = new Color(0, 0, 255);
				g.setColor(c4);
				g.fillRect(x, y, size*5, size*5);
			}
		}
		else {
			Color c3 = new Color(252, 251, 244);
			g.setColor(c3);
			g.fillRect(x, y, size, size);
		}
	}
}
