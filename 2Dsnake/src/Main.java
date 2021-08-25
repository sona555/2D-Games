import javax.swing.JFrame;
import java.awt.Color;

public class Main {

	public static void main(String[] args) {
		
		JFrame frame= new JFrame();
		Gameplay gameplay = new Gameplay(); 
		
		frame.setBounds(10, 10, 905, 700);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(gameplay);
		frame.addMouseListener(gameplay);
	}
}
