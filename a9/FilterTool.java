package a9;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class FilterTool implements Tool{

	//my own unique filter tool which allows to modify picture with multiple preset filters like instagram
	private FilterToolUI ui;
	public static ImageEditorModel model;
	
	public FilterTool(ImageEditorModel m){
		this.model = m;
		this.ui = new FilterToolUI();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JPanel getUI() {
		// TODO Auto-generated method stub
		return ui;
	}
	
	

}
