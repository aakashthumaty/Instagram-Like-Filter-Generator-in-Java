package a8;

import java.io.IOException;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PixelInpectorRunner {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		//gets picture from web url
		Picture p = A8Helper.readFromURL("http://www.cs.unc.edu/~kmp/kmp.jpg");
		PixelInspectorWidget simple_widget = new PixelInspectorWidget(p);
		
		//sets up the jframe
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Assignment 8 Simple Picture View");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//sets up the jpanel
		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		top_panel.add(simple_widget, BorderLayout.CENTER);
		main_frame.setContentPane(top_panel);

		main_frame.pack();
		main_frame.setVisible(true);
	}

}
