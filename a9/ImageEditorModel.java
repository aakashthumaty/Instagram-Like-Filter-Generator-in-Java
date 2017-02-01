package a9;

import java.util.ArrayList;


public class ImageEditorModel {

	private Picture original;
	private ObservablePicture current;
	
	public ImageEditorModel(Picture f) {
		original = f;
		current = original.copy().createObservable();
	}

	public ObservablePicture getCurrent() {
		return current;
	}

	//returns pixel
	public Pixel getPixel(int x, int y) {
		return current.getPixel(x, y);
	}

	//paint method
	public void paintAt(int x, int y, Pixel brushColor, int brush_size) {
		current.suspendObservable();;
		
		for (int xpos=x-brush_size+1; xpos <=x+brush_size-1; xpos++) {
			for (int ypos=y-brush_size+1; ypos <=y+brush_size-1; ypos++) {
				if (xpos >= 0 &&
					xpos < current.getWidth() &&
					ypos >= 0 &&
					ypos < current.getHeight()) {
					current.setPixel(xpos, ypos, brushColor);
				}
			}
		}
		current.resumeObservable();
	}
	
	public void clearFilter(){
		current.suspendObservable();
		
		current.resumeObservable();
	}
	
	//used to revert pic back to original
	public void clearPic(){
		current.suspendObservable();
		
		for(int j= 0; j<current.getHeight(); j++){
			for(int i= 0; i<current.getWidth(); i++){
					current.setPixel(i, j, original.getPixel(i, j));
				}
		}
		
		current.resumeObservable();
	}
	
	//adds red tint for various filters
	public void redTint(double amt){
		current.suspendObservable();
		
		for(int j= 0; j<current.getHeight(); j++){
			for(int i= 0; i<current.getWidth(); i++){
				
				double big =  Math.max(Math.max(current.getPixel(i, j).getRed(),current.getPixel(i, j).getGreen()),current.getPixel(i, j).getBlue());

				double red = current.getPixel(i, j).getRed() * ((big + ((1.0 - big) * amt)) / big);
				
					current.setPixel(i, j, (new ColorPixel(red,current.getPixel(i,j).getGreen(),current.getPixel(i,j).getBlue())));
				}
		}
		
		current.resumeObservable();
	}
	
	//adds blue tint for various filters
	public void blueTint(double amt){
		current.suspendObservable();
		
		for(int j= 0; j<current.getHeight(); j++){
			for(int i= 0; i<current.getWidth(); i++){
				
				double big =  Math.max(Math.max(current.getPixel(i, j).getRed(),current.getPixel(i, j).getGreen()),current.getPixel(i, j).getBlue());

				double blue = current.getPixel(i, j).getBlue() * ((big + ((1.0 - big) * amt)) / big);
				
					current.setPixel(i, j, (new ColorPixel(current.getPixel(i,j).getRed(),current.getPixel(i,j).getGreen(),blue)));
				}
		}
		
		current.resumeObservable();
	}
	
	//saturates pixels for various filters
	public void saturate(int amount) 
	{
		current.suspendObservable();

		//saturate method
		System.out.println("made it to the sat method");
		Picture saturated =  new PictureImpl(current.getWidth(),current.getHeight());
		double amtOH = amount/100.0;
		if(amount== 0)
			//return current;
			return;
		else if(amount>= -100 && amount<0){
			double r, g, b;
			for(int j= 0; j<current.getHeight(); j++){
				for(int i= 0; i<current.getWidth(); i++){
					if(current.getPixel(i, j).getIntensity()== 0.0){
						current.setPixel(i, j, current.getPixel(i, j));
						continue;
					}
					r= (current.getPixel(i, j).getRed())*(1.0+amtOH) - (current.getPixel(i, j).getIntensity()*amtOH);
					g= (current.getPixel(i, j).getGreen())*(1.0+amtOH) - (current.getPixel(i, j).getIntensity()*amtOH);
					b= (current.getPixel(i, j).getBlue())*(1.0+(amtOH)) - (current.getPixel(i, j).getIntensity()*amtOH);
					current.setPixel(i, j, new ColorPixel(r,g,b));
				}
			}
			//return saturated;
			System.out.println("Got here");
			current.resumeObservable();
			return;
		}
		else if(amount>0 && amount<= 100){
			double r, g, b;
			for(int j= 0; j<current.getHeight(); j++){
				for(int i= 0; i<current.getWidth(); i++){
					if(current.getPixel(i, j).getIntensity()== 0.0){
						current.setPixel(i, j, current.getPixel(i, j));
						continue;
					}
					double big =  Math.max(Math.max(current.getPixel(i, j).getRed(),current.getPixel(i, j).getGreen()),current.getPixel(i, j).getBlue());
					r= current.getPixel(i, j).getRed() * ((big + ((1.0 - big) * amtOH)) / big);
					g= current.getPixel(i, j).getGreen() * ((big + ((1.0 - big) * amtOH)) / big);
					b= current.getPixel(i, j).getBlue() * ((big + ((1.0 - big) * amtOH)) / big);
					current.setPixel(i, j, new ColorPixel(r,g,b));
				}
			}
			//return saturated;
			System.out.println("Got here");
			current.resumeObservable();
			return;
		}
		else throw new RuntimeException("didnt work");
	}
	
	
	//used for lightning the pixels in an image in various filters
	public void makeBrighter(int amount){
		current.suspendObservable();
		//brighten method
		
		Picture bPic =   new PictureImpl(current.getWidth(),current.getHeight());
		
		double d= amount/100.0;
		if(d==  0){
			//return current;
			current.resumeObservable();

			return;
		}
		else if(d<0){
			for(int j =   0; j< current.getHeight(); j++){
 				for(int i=  0; i<current.getWidth(); i++){
 					current.setPixel(i, j, blend(new GrayPixel(0.0),current.getPixel(i, j),Math.abs(d)));
				}
			}
			//return bPic;
			current.resumeObservable();

			return;

		}
		else if(d>0){
			for(int j=  0; j<current.getHeight(); j++){
				for(int i=  0; i<current.getWidth(); i++){
					current.setPixel(i, j, blend(new GrayPixel(1.0),current.getPixel(i, j),Math.abs(d)));
				}
			}
			//return bPic;
			current.resumeObservable();

			return;

		}
			current.resumeObservable();

			throw new RuntimeException("didnt work");
		
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
		
		if(x-amount>=   0 && y-amount>=   0 && x+amount<current.getWidth() && y+amount<current.getHeight()){
			for(int j=   y-amount; j<=   y+amount; j++){
				for(int i=   x-amount; i<=   x+amount; i++){
					toBlur.add(current.getPixel(i, j));
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
		else if(!(x-amount>=   0) && y-amount>=   0 && x+amount<current.getWidth() && y+amount<current.getHeight()){
			for(int j=   y-amount; j<=   y+amount; j++){
				for(int i=   0; i<=   x+amount; i++){
					toBlur.add(current.getPixel(i, j));
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
		else if(x-amount>=  0 && !(y-amount>=  0) && !(x+amount<current.getWidth()) && y+amount<current.getHeight()){
			for(int j=  0; j<=  y+amount; j++){
				for(int i=  x-amount; i<current.getWidth(); i++){
					toBlur.add(current.getPixel(i, j));
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
		
		else if(!(x-amount>=  0) && !(y-amount>=  0) && x+amount<current.getWidth() && y+amount<current.getHeight()){
			for(int j=  0; j<=  y+amount; j++){
				for(int i=  0; i<=  x+amount; i++){
					toBlur.add(current.getPixel(i, j));
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

		else if(!(x-amount>=  0) && y-amount>=  0 && x+amount<current.getWidth() && !(y+amount<current.getHeight())){
			for(int j=  y-amount; j<current.getHeight(); j++){
				for(int i=  0; i<=  x+amount; i++){
					toBlur.add(current.getPixel(i, j));
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
		else if(x-amount>=  0 && !(y-amount>=  0) && x+amount<current.getWidth() && y+amount<current.getHeight()){
			for(int j=  0; j<=  y+amount; j++){
				for(int i=  x-amount; i<=  x+amount; i++){
					toBlur.add(current.getPixel(i, j));
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
		
		else if(x-amount>=  0 && y-amount>=  0 && x+amount<current.getWidth() && !(y+amount<current.getHeight())){
			for(int j=  y-amount; j<current.getHeight(); j++){
				for(int i=  x-amount; i<=  x+amount; i++){
					toBlur.add(current.getPixel(i, j));
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

		else if(x-amount>=  0 && y-amount>=  0 && !(x+amount<current.getWidth()) && !(y+amount<current.getHeight())){
			for(int j=  y-amount; j<current.getHeight(); j++){
				for(int i=  x-amount; i<current.getWidth(); i++){
					toBlur.add(current.getPixel(i, j));
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
		else if(x-amount>=  0 && y-amount>=  0 && !(x+amount<current.getWidth()) && y+amount<current.getHeight()){
			for(int j=  y-amount; j<=  y+amount; j++){
				for(int i=  x-amount; i<current.getWidth(); i++){
					toBlur.add(current.getPixel(i, j));
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
	//updates picture in model(data)
	public void updatePicture (Picture p) {
		//TODO
		original = p;
		current = p.copy().createObservable();
	}
}
