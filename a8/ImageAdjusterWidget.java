package a8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
//worked with jake browne, ben, vishy

public class ImageAdjusterWidget extends JPanel implements ChangeListener{
	
	private JSlider bSlider;
	private JSlider sSlider;
	private JSlider brSlider;
	private List<ChangeListener> change_listeners;
	
	private Picture pic;
	private Picture changedPic;
	PictureView picture_view;
	
	public ImageAdjusterWidget(Picture p){
		//constructor
		
		//sets up layout with pciture view
		setLayout(new BorderLayout());

		picture_view =   new PictureView(p.createObservable());
		add(picture_view, BorderLayout.EAST);
		
		//creates two pictures to keep current and changed in memory when editing picture
		pic =   picture_view.getPicture();
		changedPic =   p;

		//sets up sliders in jpanel
		JPanel slider_panel =   new JPanel();
		slider_panel.setLayout(new GridLayout(3,2));

		
		//adds sliders
		bSlider =   new JSlider(0,5, 0);
		bSlider.setMajorTickSpacing(1);
		bSlider.setPaintTicks(true);
		bSlider.setPaintLabels(true);
		
		sSlider =   new JSlider(-100,100);
		sSlider.setMajorTickSpacing(25);
		sSlider.setPaintTicks(true);
		sSlider.setPaintLabels(true);
		
		brSlider =   new JSlider(-100,100);
		brSlider.setMajorTickSpacing(25);
		brSlider.setPaintTicks(true);
		brSlider.setPaintLabels(true);


		//adds labels to sliders
		slider_panel.add(new JLabel("Blur:"));
		slider_panel.add(bSlider);
		slider_panel.add(new JLabel("Saturation:"));
		slider_panel.add(sSlider);
		slider_panel.add(new JLabel("Brightness"));
		slider_panel.add(brSlider);

		add(slider_panel, BorderLayout.SOUTH);
		
		//adds listeners to sliders
		bSlider.addChangeListener(this);
		sSlider.addChangeListener(this);
		brSlider.addChangeListener(this);
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		if(!((JSlider)e.getSource()).getValueIsAdjusting()){
			
			//creates temp pic to manipulate for blurring so we can save current blur state in memory and replace with new blur state
			Picture tempPic = new PictureImpl(pic.getWidth(), pic.getHeight());
			
			//blur 
				for(int i =   0; i < pic.getHeight(); i++){
				for(int j =   0; j < pic.getWidth(); j++){
					tempPic.setPixel(j, i, makeBlur(j,i,bSlider.getValue()));
				}
				}
		
			changedPic = tempPic;	
			
			//saturate
			changedPic =   saturate(sSlider.getValue(), changedPic);
			changedPic =   makeBrighter(brSlider.getValue(), changedPic);
		
		//sets new picture
		picture_view.setPicture(changedPic.createObservable());
		
		//resets temp pic
		changedPic = pic;
		}
	}
	
	public Picture saturate(int amount, Picture p) 
	{
		//saturate method
		Picture saturated =  new PictureImpl(p.getWidth(),p.getHeight());
		double amtOH = amount/100.0;
		if(amount== 0)
			return p;
		else if(amount>= -100 && amount<0){
			double r, g, b;
			for(int j= 0; j<saturated.getHeight(); j++){
				for(int i= 0; i<saturated.getWidth(); i++){
					if(p.getPixel(i, j).getIntensity()== 0.0){
						saturated.setPixel(i, j, p.getPixel(i, j));
						continue;
					}
					r= (p.getPixel(i, j).getRed())*(1.0+amtOH) - (p.getPixel(i, j).getIntensity()*amtOH);
					g= (p.getPixel(i, j).getGreen())*(1.0+amtOH) - (p.getPixel(i, j).getIntensity()*amtOH);
					b= (p.getPixel(i, j).getBlue())*(1.0+(amtOH)) - (p.getPixel(i, j).getIntensity()*amtOH);
					saturated.setPixel(i, j, new ColorPixel(r,g,b));
				}
			}
			return saturated;
		}
		else if(amount>0 && amount<= 100){
			double r, g, b;
			for(int j= 0; j<saturated.getHeight(); j++){
				for(int i= 0; i<saturated.getWidth(); i++){
					if(p.getPixel(i, j).getIntensity()== 0.0){
						saturated.setPixel(i, j, p.getPixel(i, j));
						continue;
					}
					double big =  Math.max(Math.max(p.getPixel(i, j).getRed(),p.getPixel(i, j).getGreen()),p.getPixel(i, j).getBlue());
					r= p.getPixel(i, j).getRed() * ((big + ((1.0 - big) * amtOH)) / big);
					g= p.getPixel(i, j).getGreen() * ((big + ((1.0 - big) * amtOH)) / big);
					b= p.getPixel(i, j).getBlue() * ((big + ((1.0 - big) * amtOH)) / big);
					saturated.setPixel(i, j, new ColorPixel(r,g,b));
				}
			}
			return saturated;
		}
		else throw new RuntimeException("didnt work");
	}
	
	public Picture makeBrighter(int amount, Picture pictureToChange){
		
		//brighten method
		
		Picture bPic =   new PictureImpl(pictureToChange.getWidth(),pictureToChange.getHeight());
		
		double d= amount/100.0;
		if(d==  0){
			return pictureToChange;
		}
		else if(d<0){
			for(int j =   0; j< pictureToChange.getHeight(); j++){
 				for(int i=  0; i<pictureToChange.getWidth(); i++){
					bPic.setPixel(i, j, blend(new GrayPixel(0.0),pictureToChange.getPixel(i, j),Math.abs(d)));
				}
			}
			return bPic;
		}
		else if(d>0){
			for(int j=  0; j<pictureToChange.getHeight(); j++){
				for(int i=  0; i<pictureToChange.getWidth(); i++){
					bPic.setPixel(i, j, blend(new GrayPixel(1.0),pictureToChange.getPixel(i, j),Math.abs(d)));
				}
			}
			return bPic;
		}
		
		else throw new RuntimeException("didnt work");
	}
	
	
	public Pixel blend(Pixel p1, Pixel p2, double weight) 
	{

		
		return new ColorPixel(p1.getRed()*weight + p2.getRed()*(1.0-weight),
				p1.getGreen()*weight + p2.getGreen()*(1.0-weight),
				p1.getBlue()*weight + p2.getBlue()*(1.0-weight));
	}
	
	
	
	
	public Pixel makeBlur(int x, int y, int amount){
 
		//blur method
		
		ArrayList<Pixel> toBlur =    new ArrayList<Pixel>();
		
		if(x-amount>=   0 && y-amount>=   0 && x+amount<pic.getWidth() && y+amount<pic.getHeight()){
			for(int j=   y-amount; j<=   y+amount; j++){
				for(int i=   x-amount; i<=   x+amount; i++){
					toBlur.add(pic.getPixel(i, j));
				}
			}

			double aR =    0;
			double aG =    0;
			double aB =    0;
			for(int a=   0; a<toBlur.size(); a++){
				aR+=   toBlur.get(a).getRed();
				aB+=   toBlur.get(a).getBlue();
				aG+=   toBlur.get(a).getGreen();
			}
			
			aR/=   toBlur.size();
			aB/=   toBlur.size();
			aG/=   toBlur.size();
			return new ColorPixel(aR,aG,aB);
		}
		else if(!(x-amount>=   0) && y-amount>=   0 && x+amount<pic.getWidth() && y+amount<pic.getHeight()){
			for(int j=   y-amount; j<=   y+amount; j++){
				for(int i=   0; i<=   x+amount; i++){
					toBlur.add(pic.getPixel(i, j));
				}
			}
			double aR =    0;
			double aG =    0;
			double aB =    0;
			for(int z=   0; z<toBlur.size(); z++){
				aR+=   toBlur.get(z).getRed();
				aB+=   toBlur.get(z).getBlue();
				aG+=   toBlur.get(z).getGreen();
			}
			aR/=   toBlur.size();
			aB/=   toBlur.size();
			aG/=   toBlur.size();
			return new ColorPixel(aR,aG,aB);
		}
		else if(x-amount>=  0 && !(y-amount>=  0) && !(x+amount<pic.getWidth()) && y+amount<pic.getHeight()){
			for(int j=  0; j<=  y+amount; j++){
				for(int i=  x-amount; i<pic.getWidth(); i++){
					toBlur.add(pic.getPixel(i, j));
				}
			}

			double aR =    0;
			double aG =    0;
			double aB =    0;
			
			for(int a=  0; a<toBlur.size(); a++){
				aR+=  toBlur.get(a).getRed();
				aB+=  toBlur.get(a).getBlue();
				aG+=  toBlur.get(a).getGreen();
			}
			aR/=  toBlur.size();
			aB/=  toBlur.size();
			aG/=  toBlur.size();
			return new ColorPixel(aR,aG,aB);
		}
		
		else if(!(x-amount>=  0) && !(y-amount>=  0) && x+amount<pic.getWidth() && y+amount<pic.getHeight()){
			for(int j=  0; j<=  y+amount; j++){
				for(int i=  0; i<=  x+amount; i++){
					toBlur.add(pic.getPixel(i, j));
				}
			}
			double aR =    0;
			double aG =    0;
			double aB =    0;
			
			for(int a=  0; a<toBlur.size(); a++){
				aR+=  toBlur.get(a).getRed();
				aB+=  toBlur.get(a).getBlue();
				aG+=  toBlur.get(a).getGreen();
			}
			aR/=  toBlur.size();
			aB/=  toBlur.size();
			aG/=  toBlur.size();
			return new ColorPixel(aR,aG,aB);
		}

		else if(!(x-amount>=  0) && y-amount>=  0 && x+amount<pic.getWidth() && !(y+amount<pic.getHeight())){
			for(int j=  y-amount; j<pic.getHeight(); j++){
				for(int i=  0; i<=  x+amount; i++){
					toBlur.add(pic.getPixel(i, j));
				}
			}

			double aR =    0;
			double aG =    0;
			double aB =    0;
			
			for(int a=  0; a<toBlur.size(); a++){
				aR+=  toBlur.get(a).getRed();
				aB+=  toBlur.get(a).getBlue();
				aG+=  toBlur.get(a).getGreen();
			}
	
			
			aR/=  toBlur.size();
			aB/=  toBlur.size();
			aG/=  toBlur.size();
			return new ColorPixel(aR,aG,aB);
		}
		else if(x-amount>=  0 && !(y-amount>=  0) && x+amount<pic.getWidth() && y+amount<pic.getHeight()){
			for(int j=  0; j<=  y+amount; j++){
				for(int i=  x-amount; i<=  x+amount; i++){
					toBlur.add(pic.getPixel(i, j));
				}
			}
			double aR =    0;
			double aG =    0;
			double aB =    0;
			
			for(int z=  0; z<toBlur.size(); z++){
				aR+=  toBlur.get(z).getRed();
				aB+=  toBlur.get(z).getBlue();
				aG+=  toBlur.get(z).getGreen();
			}
			aR/=  toBlur.size();
			aB/=  toBlur.size();
			aG/=  toBlur.size();
			return new ColorPixel(aR,aG,aB);
		}
		
		else if(x-amount>=  0 && y-amount>=  0 && x+amount<pic.getWidth() && !(y+amount<pic.getHeight())){
			for(int j=  y-amount; j<pic.getHeight(); j++){
				for(int i=  x-amount; i<=  x+amount; i++){
					toBlur.add(pic.getPixel(i, j));
				}
			}

			double aR =    0;
			double aG =    0;
			double aB =    0;
			
			for(int z=  0; z<toBlur.size(); z++){
				aR+=  toBlur.get(z).getRed();
				aB+=  toBlur.get(z).getBlue();
				aG+=  toBlur.get(z).getGreen();
			}
			aR/=  toBlur.size();
			aB/=  toBlur.size();
			aG/=  toBlur.size();
			return new ColorPixel(aR,aG,aB);
		}

		else if(x-amount>=  0 && y-amount>=  0 && !(x+amount<pic.getWidth()) && !(y+amount<pic.getHeight())){
			for(int j=  y-amount; j<pic.getHeight(); j++){
				for(int i=  x-amount; i<pic.getWidth(); i++){
					toBlur.add(pic.getPixel(i, j));
				}
			}
			double aR =    0;
			double aG =    0;
			double aB =    0;
			
			for(int z=  0; z<toBlur.size(); z++){
				aR+=  toBlur.get(z).getRed();
				aB+=  toBlur.get(z).getBlue();
				aG+=  toBlur.get(z).getGreen();
			}
			aR/=  toBlur.size();
			aB/=  toBlur.size();
			aG/=  toBlur.size();
			return new ColorPixel(aR,aG,aB);
		}
		else if(x-amount>=  0 && y-amount>=  0 && !(x+amount<pic.getWidth()) && y+amount<pic.getHeight()){
			for(int j=  y-amount; j<=  y+amount; j++){
				for(int i=  x-amount; i<pic.getWidth(); i++){
					toBlur.add(pic.getPixel(i, j));
				}
			}
			
			double aR =    0;
			double aG =    0;
			double aB =    0;
			
			for(int a=  0; a<toBlur.size(); a++){
				aR+=  toBlur.get(a).getRed();
				aB+=  toBlur.get(a).getBlue();
				aG+=  toBlur.get(a).getGreen();
			}
			aR/=  toBlur.size();
			aB/=  toBlur.size();
			aG/=  toBlur.size();
			
			return new ColorPixel(aR,aG,aB);
		}
		else throw new RuntimeException("blurring didnt work");
	}


}
