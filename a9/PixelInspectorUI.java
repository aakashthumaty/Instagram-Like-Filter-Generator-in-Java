package a9;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class PixelInspectorUI extends JPanel implements ActionListener {

	private JLabel x_label;
	private JLabel y_label;
	public JLabel pixel_info;
	
	private JButton takeCurPix;
	private JButton openButton;

	private List<ColorCopiedInterface> list;

	public static Pixel pix;
	
	public PixelInspectorUI() {
		x_label = new JLabel("X: ");
		y_label = new JLabel("Y: ");
		pixel_info = new JLabel("(r,g,b)");

		setLayout(new GridLayout(3,0));
		add(x_label);
		add(y_label);
		add(pixel_info);
		
		takeCurPix = new JButton("Change Brush to Selected Pixel Color");
		takeCurPix.addActionListener(this);
		add(takeCurPix);
		
		openButton = new JButton("Open New Picture");
		openButton.addActionListener(this);
		add(openButton);
		
		list = new ArrayList<>();
		
	}
	
	public void setInfo(int x, int y, Pixel p) {
		pix = p;
		System.out.println(p);
		System.out.println(pix);

		x_label.setText("X: " + x);
		y_label.setText("Y: " + y);
		pixel_info.setText(String.format("(%3.2f, %3.2f, %3.2f)", p.getRed(), p.getBlue(), p.getGreen()));		
	}
	public static Pixel getInfo(){
		return pix;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if(((JButton)e.getSource()).equals(takeCurPix)){
			System.out.println("Copy Button");

			if(pix == null){
				// do nothing
				return;
			}
			for(ColorCopiedInterface c: list){
				c.colorCopied(pix);
			}
		}else{
			System.out.println("Open Button");
			String newURL = JOptionPane.showInputDialog("Please input URL for new Picture.");
			if(newURL != null){
				//System.out.print(newURL.toString());

				try {
					ImageEditorController.changePicture(newURL);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		
	}
	//adds controller as listener for copy pixel functionality
	public void addColorCopiedInterface(ColorCopiedInterface i){
		list.add(i);
	}
	
}
