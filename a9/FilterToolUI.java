package a9;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FilterToolUI extends JPanel implements ActionListener{

	private JButton filter1;
	private JButton filter2;
	private JButton filter4;
	private JButton filter3;
	private JButton filter5;	
	private JButton original;
	private JButton clearFilter;

	//adds filter choices buttons
	public FilterToolUI(){
		
		filter1 = new JButton("Rise (Filter 1)");
		filter2 = new JButton("Toaster (Filter 2)");
		filter3 = new JButton("Brannan (Filter 3)");
		filter4 = new JButton("Fire (Filter 4)");
		filter5 = new JButton("Water (Filter 5)");
		original = new JButton("Original");
		clearFilter = new JButton("Clear Filter");
		
		filter1.addActionListener(this);
		filter2.addActionListener(this);
		filter3.addActionListener(this);
		filter4.addActionListener(this);
		filter5.addActionListener(this);
		original.addActionListener(this);
		clearFilter.addActionListener(this);

		setLayout(new GridLayout(5,1));
		add(filter1);
		add(filter2);
		add(filter3);
		add(filter4);
		add(filter5);
		add(original);
	}
	@Override
	
	//changes image with various filters or back to original
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("poop");

		if(((JButton)e.getSource()).equals(filter1)){
			System.out.println("poop");
			//FilterTool.model.clearPic();
			FilterTool.model.saturate(50);
			FilterTool.model.makeBrighter(50);
		}
		
		if(((JButton)e.getSource()).equals(filter2)){
			System.out.println("poop2");
			//FilterTool.model.clearPic();
			FilterTool.model.makeBrighter(-50);
			FilterTool.model.saturate(50);

		}
		
		if(((JButton)e.getSource()).equals(filter3)){
			System.out.println("poop3");
			//FilterTool.model.clearPic();
			FilterTool.model.saturate(-50);

		}
		
		if(((JButton)e.getSource()).equals(filter4)){
			System.out.println("poop4");
			//FilterTool.model.clearPic();
			FilterTool.model.redTint(0.5);

		}
		
		if(((JButton)e.getSource()).equals(filter5)){
			System.out.println("poop5");
			//FilterTool.model.clearPic();
			FilterTool.model.blueTint(0.75);


		}
		
		if(((JButton)e.getSource()).equals(clearFilter)){
			System.out.println("clearFilt");
			FilterTool.model.blueTint(-0.5);
			FilterTool.model.redTint(-0.5);
			FilterTool.model.saturate(0);
			FilterTool.model.makeBrighter(0);

		}
		
		if(((JButton)e.getSource()).equals(original)){
			System.out.println("poopOG");
			FilterTool.model.clearPic();

		}
		
	}
	
	
	
	

}
