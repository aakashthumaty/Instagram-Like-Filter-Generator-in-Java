package a9;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class ImageEditorController implements ToolChoiceListener, MouseListener, MouseMotionListener, ColorCopiedInterface {

	private static ImageEditorView view;
	private static ImageEditorModel model;
	private Tool current_tool;
	public PixelInspectorTool inspector_tool;
	public PaintBrushTool paint_brush_tool;
	public FilterTool filterTool;
	
	public ImageEditorController(ImageEditorView view, ImageEditorModel model) {
		this.view = view;
		this.model = model;

		inspector_tool = new PixelInspectorTool(model);
		paint_brush_tool = new PaintBrushTool(model);
		filterTool = new FilterTool(model);
		
		//makes controller listener that I made for copy pixel functionality
		inspector_tool.addColorCopiedInterface(this);
		
		view.addToolChoiceListener(this);
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
		
		this.toolChosen(view.getCurrentToolName());
	}

	@Override
	public void toolChosen(String tool_name) {
		if (tool_name.equals("Pixel Inspector")) {
			view.installToolUI(inspector_tool.getUI());
			current_tool = inspector_tool;
		} else if (tool_name.equals("Paint Brush")) {
			view.installToolUI(paint_brush_tool.getUI());
			current_tool = paint_brush_tool;
		} else if(tool_name.equals("Filter Tool")){
			view.installToolUI(filterTool.getUI());
			current_tool = filterTool;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		current_tool.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		current_tool.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		current_tool.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		current_tool.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		current_tool.mouseExited(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		current_tool.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		current_tool.mouseMoved(e);
	}

	@Override
	public void colorCopied(Pixel p) {
		// TODO Auto-generated method stub
		paint_brush_tool.setColor(p);
		
	}
	
	//used to change picture in model and view and update observables
	public static void changePicture(String url) throws IOException {
		// TODO Auto-generated method stub
		Picture f = PictureImpl.readFromURL(url);
		model.updatePicture(f);
		view.updatePicture(model.getCurrent());
		
	}

}
