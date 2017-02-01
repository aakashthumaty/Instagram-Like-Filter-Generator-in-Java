package a8;

import java.io.IOException;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageAdjusterRunner {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		//gets image from web url and sets up picture view
		Picture p = A8Helper.readFromURL("http://www.cs.unc.edu/~kmp/kmp.jpg");
		ImageAdjusterWidget simple_widget = new ImageAdjusterWidget(p);

		//sets up jframe
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Assignment 8 Simple Picture View");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//sets up jpanel
		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		top_panel.add(simple_widget, BorderLayout.CENTER);
		main_frame.setContentPane(top_panel);

		//makes ui visible
		main_frame.pack();
		main_frame.setVisible(true);
	}

}
