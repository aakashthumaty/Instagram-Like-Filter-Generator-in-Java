package a8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class PixelInspectorWidget extends JPanel implements MouseListener {

	private PictureView picture_view;
	private PixelInspector inspector;
	private Picture pic;
	
	private JLabel xLabel;
	private JLabel yLabel;
	private JLabel rLabel;
	private JLabel gLabel;
	private JLabel bLabel;
	private JLabel brLabel;

	
	public PixelInspectorWidget(Picture picture){
		//constructor
		
		//sets layout
		setLayout(new BorderLayout());
		
		//creates pictureview
		picture_view = new PictureView(picture.createObservable());
		picture_view.addMouseListener(this);
		add(picture_view, BorderLayout.EAST);
		
		pic = picture;
		
		//creates label panel with gridlayout
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(6,3));
		
		//adds label to panels
		//adds 3rd column of panels to make room so that you can see the labels without having to expand window
		labelPanel.add(new JLabel("X:"));
		labelPanel.add(xLabel = new JLabel(""));
		labelPanel.add(new JLabel("       "));

		labelPanel.add(new JLabel("Y:"));
		labelPanel.add(yLabel = new JLabel(""));
		labelPanel.add(new JLabel("       "));

		labelPanel.add(new JLabel("Red:"));
		labelPanel.add(rLabel = new JLabel(""));
		labelPanel.add(new JLabel("       "));

		labelPanel.add(new JLabel("Green:"));
		labelPanel.add(gLabel = new JLabel(""));
		labelPanel.add(new JLabel("       "));

		labelPanel.add(new JLabel("Blue:"));
		labelPanel.add(bLabel = new JLabel(""));
		labelPanel.add(new JLabel("       "));

		labelPanel.add(new JLabel("Brightness:"));
		labelPanel.add(brLabel = new JLabel(""));
		labelPanel.add(new JLabel("       "));

		add(labelPanel, BorderLayout.WEST);
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//finds pixel that was clicked
		int x = e.getX();
		int y = e.getY();
		DecimalFormat df = new DecimalFormat("####.##");
		
		//updates labels accordingly to the pixel that was clicked
		 xLabel.setText("" + e.getX()); 
		 yLabel.setText(""+e.getY());
		 gLabel.setText("" + df.format(pic.getPixel(x,y).getGreen()));
		 bLabel.setText(""+df.format(pic.getPixel(x,y).getBlue()));
		 rLabel.setText(df.format(pic.getPixel(x,y).getRed()) + "");
		 brLabel.setText("" + pic.getPixel(x, y).getIntensity());
		 System.out.println("poop in a basket");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//unneeded implementation
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//unneeded implementation
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//unneeded implementation
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//unneeded implementation
		
	}
	public void labelGrid(Picture p){
		setLayout(new GridLayout(6,2));
		
		
	}
	public void update(){
		//unneeded implementation

	}

}
