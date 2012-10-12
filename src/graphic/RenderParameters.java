package graphic;

import java.awt.Color;

/**
 * Provides the parameters for rendering of a "world", including fill colors, line colors, backgrounds and such.
 * 
 * @author jacob
 *
 */
public class RenderParameters {
	public static final int SOLID_BACKGROUND = 0;
	public static final int IMAGE_BACKGROUND = 1;
	
	public static final int EDGED_ENTITIES = 0;
	public static final int UNEDGED_ENTITIES = 1;
	
	public final int backgroundStyle;
	public final int edgeStyle;
	
	public final Color backgroundColor;
	public final Color lineColor;
	public final Color shapeColor;
	
	private RenderParameters(int backgroundStyle, int edgeStyle, Color backgroundColor, Color lineColor, Color shapeColor){
		this.backgroundStyle = backgroundStyle;
		this.edgeStyle = edgeStyle;
		this.backgroundColor = backgroundColor;
		this.lineColor = lineColor;
		this.shapeColor = shapeColor;
	}
	
	public static RenderParameters create(int backgroundStyle, int edgeStyle, Color backgroundColor, Color lineColor, Color shapeColor){
		return new RenderParameters(backgroundStyle, edgeStyle, backgroundColor, lineColor, shapeColor);
	}
}
