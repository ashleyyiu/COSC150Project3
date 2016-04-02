import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Runner extends JPanel{

	public static void main(String[] args) {

		try {
			RandomAccessFile file = new RandomAccessFile("C:\\Users\\Ashley Yiu\\workspace\\COSC150P3\\src\\toy.data", "r");
			int length = (int) file.length();
			byte[] temp = new byte[length];
			file.read(temp);
			file.close();
			ToyList toyList = new ToyList(temp, length);
			System.out.println("There are "+toyList.getNumOfToys()+" toys.");
			
			for (int i=0; i<toyList.getNumOfToys(); i++){
			    BufferedImage bi = toyList.getToy(i).getImage();
				File outputfile = new File(i+".png");
			    ImageIO.write(bi, "png", outputfile);
				
			    System.out.println(toyList.getToy(i).getToyName() + " costs $"+toyList.getToy(i).getPrice());

			    
			}
		}
		catch (IOException e) {
			e.printStackTrace();

		}
	}

}
