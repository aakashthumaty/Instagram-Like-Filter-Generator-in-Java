package a8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class FramePuzzleWidget extends JPanel implements KeyListener, MouseListener{
	
	private Picture pic;
	private PictureView[][] picGrid;
	private Coordinate blankLoc;
	
	
	public FramePuzzleWidget(Picture p){
		pic = p;
		int hcounter = 0;
		int wcounter = 0;
		
		setFocusable(true);
		
		picGrid = new PictureView[5][5];
		
//creates 2d array of picture views for the puzzle
		for(int i = 0; hcounter < 5; i += pic.getHeight()/5){
			for(int j = 0; wcounter < 5; j += pic.getWidth()/5){	
				PictureView picView = new PictureView (pic.extract(j,i,pic.getWidth()/5,pic.getHeight()/5).createObservable());

				picGrid[wcounter][hcounter] = picView;
				wcounter++;

			}
			hcounter++;
			wcounter = 0;
		}
		
		//create the blank pictureview
		Picture gPic = new PictureImpl(pic.getWidth()/5,pic.getHeight()/5);
		PictureView gpView = new PictureView(gPic.createObservable());
		picGrid[4][4] = gpView;
		blankLoc = new Coordinate(4,4);
		
		//create the grid and layout
		GridLayout grid = new GridLayout(5,5,1,1);
		setLayout(grid);
		update();
		addKeyListener(this);
	}
	
	private void update(){
		//update method
		removeAll();
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				picGrid[j][i].addMouseListener(this);
				add(picGrid[j][i]);
				System.out.println("hi" + i + j);
			}
		}
		
		revalidate();
		requestFocusInWindow();
	}
	
	public void switchPics(int a,int b,int a2,int b2){
		//helper method used to switch the places of two picture views in the 2d array
		if(a < 0 || b <0 || a2<0 || b2<0 || a>5 || b >5 || a2>5 || b2>5){
			throw new IllegalArgumentException("darn");
		}
		
		PictureView tempPic = picGrid[a][b];
		picGrid[a][b] = picGrid[a2][b2];
		picGrid[a2][b2] = tempPic;

	}
		
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//logic to find location/picture view of click
		Coordinate click = new Coordinate(0,0);
		PictureView picView = (PictureView)e.getSource();
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				if(picGrid[i][j] == picView){
					click = new Coordinate(i,j);
					break;
				}
			}
			
		}
		
		//4 cases for direction to move picture views in
		
		if(click.getX() < blankLoc.getX() && click.getY() == blankLoc.getY()){
			for(int i = 0; i < blankLoc.getX()-click.getX(); i++){
				if(blankLoc.getX()>0){
					switchPics(blankLoc.getX(), blankLoc.getY(), blankLoc.getX()-1,blankLoc.getY());
					blankLoc = new Coordinate(blankLoc.getX()-1, blankLoc.getY());
				}
			}
			update();

		}else if(click.getX() > blankLoc.getX() && click.getY() == blankLoc.getY()){
			for(int i = 0; i < click.getX() - blankLoc.getX(); i++){
				if(blankLoc.getX() < 4){
					switchPics(blankLoc.getX(), blankLoc.getY(), blankLoc.getX()+1, blankLoc.getY());
					blankLoc = new Coordinate(blankLoc.getX() + 1, blankLoc.getY());
				}
			}
			update();

		}else if(click.getY() < blankLoc.getY() && click.getX() == blankLoc.getX()){
			for(int i = 0; i < blankLoc.getY() - click.getY(); i++){
				if(blankLoc.getY() > 0){
					switchPics(blankLoc.getX(),blankLoc.getY(), blankLoc.getX(), blankLoc.getY() - 1);
					blankLoc = new Coordinate(blankLoc.getX(), blankLoc.getY() - 1);
				}
			}
			update();

		}else if(click.getY() > blankLoc.getY() && click.getX() == blankLoc.getX()){
			for(int i = 0; i < click.getY() - blankLoc.getY(); i++) {
				if(blankLoc.getY() < 4){
					switchPics(blankLoc.getX(), blankLoc.getY(), blankLoc.getX(), blankLoc.getY()+1);
					blankLoc = new Coordinate(blankLoc.getX(), blankLoc.getY()+1);
				}
			}
			update();

		}
		
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

	@Override
	public void keyTyped(KeyEvent e) {
		//unneeded implementation
		
	}

	@Override
	//checks the key pressed and moves picture views accordingly
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()){
		case KeyEvent.VK_UP:
			if(blankLoc.getY() > 0){
				switchPics(blankLoc.getX(),blankLoc.getY(), blankLoc.getX(), blankLoc.getY() - 1);
				blankLoc = new Coordinate(blankLoc.getX(), blankLoc.getY() - 1);
			}
			update();

			break;
			
		case KeyEvent.VK_RIGHT:
			if(blankLoc.getX() < 4){
				switchPics(blankLoc.getX(), blankLoc.getY(), blankLoc.getX()+1, blankLoc.getY());
				blankLoc = new Coordinate(blankLoc.getX() + 1, blankLoc.getY());
			}
			update();

			break;
			
		case KeyEvent.VK_DOWN:
			if(blankLoc.getY() < 4){
				switchPics(blankLoc.getX(), blankLoc.getY(), blankLoc.getX(), blankLoc.getY()+1);
				blankLoc = new Coordinate(blankLoc.getX(), blankLoc.getY()+1);
			}
			
			update();

			break;
			
		case KeyEvent.VK_LEFT:
			if(blankLoc.getX()>0){
				switchPics(blankLoc.getX(), blankLoc.getY(), blankLoc.getX()-1,blankLoc.getY());
				blankLoc = new Coordinate(blankLoc.getX()-1, blankLoc.getY());
			}
			update();

			break;
		}
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//unneeded implementation
		
	}

}
