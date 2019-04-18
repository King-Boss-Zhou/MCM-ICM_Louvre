import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Manager implements MouseMotionListener,MouseListener{
	private static JFrame frame;
	private static Field field;
	private static ArrayList<Cell> exitlist2;
	public Manager() {
		// TODO Auto-generated constructor stub
		frame = new JFrame("Cell");
		exitlist2 = new ArrayList<Cell>();
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	
	}
	@Override
	public void mouseClicked(MouseEvent e) {
        int x = e.getY();
        int y = e.getX();
        Cell exit;
        exit = field.getCell(x - 35, y - 5);
		exitlist2.add(exit);
		exit.setdexit();
		return;
    }
	 
	@Override
	public void mousePressed(MouseEvent e) {
   
	}
	@Override
    public void mouseReleased(MouseEvent e) {
	 
    }
	@Override
	public void mouseDragged(MouseEvent e) {

	}

    @Override
    public void mouseMoved(MouseEvent e) {
    	int x = e.getY();
    	int y = e.getX();
    	//System.out.println("x: " + x + " y: " + y);
    }
    @Override
    public void mouseExited(MouseEvent e) {  
    }

	public static void main(String[] args) {
		Manager manager = new Manager();
		// TODO Auto-generated method stub
		File file = new File("F:\\CS\\VSC-CPP\\JAVA\\Louvre1.0\\pic\\1.jpg");
		BufferedImage image = null;
		int all_num = 0;
		int out_num = 0;
		double rate = 0.9;
		double re = 5;
		try {
			image = ImageIO.read(file);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		int width = image.getWidth();
		int height = image.getHeight();
		field = new Field(width, height);
		ArrayList<Person> arrayList = new ArrayList<Person>();
		ArrayList<Cell> exitlist = new ArrayList<Cell>();
		for (int row = 0; row < height; row++ ) {
			for (int col = 0; col < width; col++ ) {
				field.place(row, col, new Cell(row, col));
			}
		}
		Cell exit = field.getCell(80, 190);
		exitlist.add(exit);
		exit = field.getCell(230, 190);
		exitlist.add(exit);
		exit = field.getCell(230, 360);
		exitlist.add(exit);
		exit = field.getCell(230, 470);
		exitlist.add(exit);
		exit = field.getCell(425, 635);
		exitlist.add(exit);
		exit = field.getCell(600, 120);
		exitlist.add(exit);
		exit = field.getCell(650, 280);
		exitlist.add(exit);
		exit = field.getCell(600, 470);
		exitlist.add(exit);
		exit = field.getCell(580, 860);
		exitlist.add(exit);
		exit = field.getCell(600, 1040);
		exitlist.add(exit);
		exit = field.getCell(425, 1040);
		exitlist.add(exit);
		exit = field.getCell(215, 1040);
		exitlist.add(exit);
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				Cell cell = field.getCell(i, j);
				cell.initsafe(exitlist.size());
				int pixel = image.getRGB(j, i);
				int r = (pixel & 0x00ff0000) >> 16;
				int g = (pixel & 0x0000ff00) >> 8;
				int b = (pixel & 0x000000ff);
				int gray = (r + g + b) / 3;
				if(gray > 240) {
					cell.setwall();
				}
			}
		}
		for(int i = 0; i < exitlist.size(); i++) {
			Cell temp = exitlist.get(i);
			temp.setexit(i);
		}
		//init_safe(field, 100, 400);
		train(field, exitlist.size());
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				Cell cell = field.getCell(i, j);
				int isWall = cell.isWall();
				if(isWall == 0 && cell.isExit() == 0) {
					if(true) {
						if(Math.random() < 1) {
							int num = (int)(Math.random() * 10) + 1;
							//int num=1;
							all_num += num;
							Person person = new Person(i, j, num);
							arrayList.add(person);
							cell.setnum(num);
						}
					}
//					else {
//						if(Math.random() < 1) {
//							int num = (int)(Math.random() * 20) + 1;
//							//int num=1;
//							all_num += num;
//							Person person = new Person(i, j, num);
//							arrayList.add(person);
//							cell.setnum(num);
//						}
//					}
				}
			}
		}
		View view = new View(field);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Cells");
		frame.add(view);
		frame.pack();
		frame.setVisible(true);
		
		
		for(int t = 0; t < 100000; t++) {
			for(Person p: arrayList) {
				if(p.isout() == 0) {
					int px = p.getx();
					int py = p.gety();
					int num = p.getnum();
					Cell cell = field.getCell(px, py);
					//System.out.println(cell.getsafe());
					Cell [] neighbor = field.getneighbor(px,py);
					int max_index = 0;
					//max_index = 1;
					double max = 0;
					int count = 0;
					int flag = 0;
					for(int m = 0; m < exitlist.size(); m++) {
						for(Cell temp: neighbor) {
							double safe = temp.getsafe(m);
							if(safe > max && temp.isFull() >= num) {
								max = safe;
								max_index = m;
								count++;
							}
						}
					}
					for(Cell temp : neighbor) {
						double safe = temp.getdsafe();
						if(safe > max && temp.isFull() >= num) {
							max = safe;
							flag = 1;
						}
					}
					//System.out.println(max);
					ArrayList<Cell>arrayc = new ArrayList<Cell>();
					for(Cell temp: neighbor) {
						double safe;
						if(flag == 0) {
							safe = temp.getsafe(max_index);
						}
						else {
							safe = temp.getdsafe();
						}
						if(safe == max && temp.isFull() >= num)arrayc.add(temp);
					}
					int size = arrayc.size();
					Cell []max_neighbor = arrayc.toArray(new Cell[arrayc.size()]);
					//System.out.println(size);
					if(size != 0 && max > (flag == 0?cell.getsafe(max_index):cell.getdsafe())) {
						int index = (int)(Math.random() * 100) % size;
						Cell c = max_neighbor[index];
						if(max != 0 && max_neighbor[index].isWall() == 0) {
							c.addnum(num);
							//System.out.println(c.getnum());
							cell.subnum(num);
							int next_x = c.getx();
							int next_y = c.gety();
							//System.out.println(x-next_x);
							//System.out.println(y-next_y);
							for(Cell e: exitlist) {
								int ex = e.getx();
								int ey = e.gety();
								if(next_x == ex && next_y == ey) {
									p.setout();
								}
							}
							for(Cell e: exitlist2) {
								int ex = e.getx();
								int ey = e.gety();
								if(next_x == ex && next_y == ey) {
									p.setout();
								}
							}
							p.updatex(next_x);
							p.updatey(next_y);
							if(flag == 1) {
								double s = max * rate;
								cell.setdsafe(s);
							}
						}
					}
					else{
						if(count != 0 && cell.isExit() != 1) {
							double s = cell.getsafe(max_index) * 0.001;
							cell.setsafe(max_index, s);
						}
						else {
							p.setout();
						}
					}
				}
			}
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					Cell cell = field.getCell(i, j);
					for(int k = 0; k < exitlist.size(); k++) {
						double s_safe = cell.getssafe(k);
						double o_safe = cell.getsafe(k);
						double n_safe =  s_safe - (Math.pow(Math.E, (-1 * re)) * (s_safe - o_safe));
						cell.setsafe(k, n_safe);
						if(n_safe == 1000) {
							//System.out.println("x:" + i + "y: " + j);
						}
					}
				}
			}
			for(Cell e: exitlist) {
				int temp = e.getnum();
				e.outnum();
				temp -= e.getnum();
				out_num += temp;
				//
				Cell [] exit1 = field.getneighbor(e.getx(), e.gety());
				for(Cell ex: exit1) {
					//System.out.println("safe:1 " + ex.getsafe(0) + " safe2: " + ex.getsafe(1));
				}
				//System.out.println();
				//System.out.println(e.getnum());
			}
			frame.repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Cell result = field.getCell(100, 400);
			int num = result.getnum();
			//System.out.println(out_num);
			if(out_num > 0.9 * all_num) {
				System.out.println(t);
				break;
			}
			//System.out.println(num);
		}
		
	}
//	public static void init_safe(Field field, int e_x, int e_y) {
//		int safe = 999;
//		int step = 1;
//		int w = field.getwidth();
//		int h = field.getheight();
//		while(safe > 0) {
//			for (int i = e_x - step; i <= e_x + step; i++) {
//				if(i == e_x-step || i == e_x + step) {
//					for(int j = e_y - step; j <= e_y + step; j++) {
//						if(i > -1 && i < h && j > -1 && j < w) {
//							Cell cell = field.getCell(i, j);
//							if(cell.isWall() == 0) {
//								cell.setsafe(safe);
//							}
//						}
//					}
//				}
//				else {
//					if(i > -1 && i < h && e_y - step > -1 && e_y - step < w) {
//						Cell cell1 = field.getCell(i, e_y-step);
//						if(cell1.isWall() == 0) {
//							cell1.setsafe(safe);
//						}
//					}
//					if(i > -1 && i < h && e_y + step > -1 && e_y + step < w) {
//						Cell cell1 = field.getCell(i, e_y+step);
//						if(cell1.isWall() == 0) {
//							cell1.setsafe(safe);
//						}
//					}
//				}
//			}
//			safe--;
//			step++;
//		}
//	}
	public static void train(Field field, int n) {
		int height = field.getheight();
		int width = field.getwidth();
		String filename = "train.txt";
		try{
			FileReader reader = new FileReader(filename);
            BufferedReader br = new BufferedReader(reader);
	        for(int i = 0; i < height; i++) {
            	for(int j = 0; j < width; j++) {
	        		Cell cell = field.getCell(i, j);
	        		String line;
	        		for(int k = 0; k < n; k++) {
	        			line = br.readLine();
			            double safe = Double.valueOf(line).doubleValue();
			            cell.setsafe(k, safe);
			            cell.setssafe(k, safe);
	        		}
            	}
            }
	        br.close();
	        System.out.println("Load successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
