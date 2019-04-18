import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Train {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("F:\\CS\\VSC-CPP\\JAVA\\Louvre1.0\\pic\\1.jpg");
		BufferedImage image = null;
		double rate = 0.99;
		try {
			image = ImageIO.read(file);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		int width = image.getWidth();
		int height = image.getHeight();
		Field field = new Field(width, height);
		ArrayList<Cell>exitlist = new ArrayList<Cell>();
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
		for(int index = 0; index < exitlist.size(); index++) {
			ArrayList<Person>train_list = new ArrayList<Person>();
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					Cell cell = field.getCell(i, j);
					int isWall = cell.isWall();
					if(isWall == 0) {
						if(Math.random() < 1) {
							int num = (int)(Math.random() * 100) + 1;
							Person person = new Person(i, j, num);
							train_list.add(person);
							cell.setnum(num);
						}
					}
				}
			}
			for(int t = 0; t < 1000; t++) {
				for(Person p: train_list) {
					int px = p.getx();
					int py = p.gety();
					int num = p.getnum();
					Cell cell = field.getCell(px, py);
					//System.out.println(cell.getsafe());
					Cell [] neighbor = field.getneighbor(px,py);
					double max = 0;
					for(Cell temp: neighbor) {
						double safe = temp.getsafe(index);
						if(safe > max && temp.isFull() > num)max = safe;
						if(temp.isExit() == 1)max = safe;
					}
					//System.out.println(max);
					ArrayList<Cell>arrayc = new ArrayList<Cell>();
					for(Cell temp: neighbor) {
						double safe = temp.getsafe(index);
						if(safe == max)arrayc.add(temp);
					}
					int size = arrayc.size();
					Cell []max_neighbor = arrayc.toArray(new Cell[arrayc.size()]);
					//System.out.println(size);
					if(size != 0) {
						int _index = (int)(Math.random() * 100) % size;
						Cell c = max_neighbor[_index];
						if(max_neighbor[_index].isWall() == 0 && p.isout() == 0) {
							c.addnum(num);
							cell.subnum(num);
							int next_x = c.getx();
							int next_y = c.gety();
							//System.out.println(x-next_x);
							//System.out.println(y-next_y);
							Cell e = exitlist.get(index);
							int ex = e.getx();
							int ey = e.gety();
							if(next_x == ex && next_y == ey) {
								p.setout();
							}
							p.updatex(next_x);
							p.updatey(next_y);
							double s = max * rate;
							if(s > cell.getsafe(index)) {
								cell.setsafe(index, s);
							}
						}
					}
				}
			}
		}
		try {
            File writeName = new File("train.txt"); 
            writeName.createNewFile(); 
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                for(int i = 0; i < height; i++) {
                	for(int j = 0; j < width; j++) {
                		Cell cell = field.getCell(i, j);
                		for(int k = 0; k < exitlist.size(); k++) {
                			double safe = cell.getsafe(k);
                    		out.write(safe + "\r\n");
                		}
                	}
                }
                out.flush(); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		System.out.println("Train successfuly!");
	}
}
