
public class Person {
	private int location_x;
	private int location_y;
	private int num;
	private int out;
	
	public Person(int x, int y, int n) {
		location_x = x;
		location_y = y;
		num = n;
		out = 0;
	}
	public int getx() {
		return location_x;
	}
	public int gety() {
		return location_y;
	}
	public int getnum() {
		return num;
	}
	public void updatex(int x) {
		location_x = x;
	}
	public void updatey(int y) {
		location_y = y;
	}
	public void setout() {
		out = 1;
	}
	public int isout() {
		return out;
	}
}
