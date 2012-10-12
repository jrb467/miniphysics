import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entities.Entity;
import graphic.RenderParameters;

import main.World;

public class Tester extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel renderPane;
	private World world;
	private ArrayList<Float> drawPoints = new ArrayList<Float>();
	private boolean isStatic = true;
	private Image buffer;
	private Graphics bufGraphics;
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				new Tester();
			}
		});
	}
	
	public Tester(){
		renderPane = new JPanel();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1600, 1000);
		this.setContentPane(renderPane);
		this.setVisible(true);
		world = new World();
		world.setRenderParameters(RenderParameters.create(RenderParameters.SOLID_BACKGROUND, RenderParameters.EDGED_ENTITIES, Color.LIGHT_GRAY, Color.gray.darker(), Color.gray));
		renderPane.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if((e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) == MouseEvent.SHIFT_DOWN_MASK){
					drawPoints.add(new Float(e.getX(), e.getY()));
					if(drawPoints.size() >= 3){
						Float[] temp = new Float[0];
						temp = drawPoints.toArray(temp);
						world.addEntity(new Entity(new Float(0,0), isStatic, temp));
						drawPoints.clear();
					}
				}else{
					drawPoints.add(new Float(e.getX(), e.getY()));
				}
			}
		});
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_R){
					synchronized(world.entities){
						world.entities.clear();
					}
				}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					System.exit(0);
				}else if(e.getKeyCode() == KeyEvent.VK_T){
					isStatic = !isStatic;
				}
			}
		});
		Thread thread = new Thread(){
			public void run(){
				while(true){
					try {
						buffer = renderPane.createImage(renderPane.getWidth(), renderPane.getHeight());
						bufGraphics = buffer.getGraphics();
						world.tick();
						world.draw(bufGraphics, renderPane.getWidth(), renderPane.getHeight());
						bufGraphics.setColor(Color.black);
						for(int i = 0; i < drawPoints.size()-1; i++){
							Float one = drawPoints.get(i);
							Float two = drawPoints.get(i+1);
							bufGraphics.drawLine((int)one.x, (int)one.y, (int)two.x, (int)two.y);
						}
						if(drawPoints.size() > 0){
							Float temp = drawPoints.get(drawPoints.size() - 1);
							Point p = renderPane.getMousePosition();
							if(p != null){
								bufGraphics.drawLine((int)temp.x, (int)temp.y, p.x, p.y);
							}
						}
						bufGraphics.setFont(bufGraphics.getFont().deriveFont(30f));
						if(isStatic){
							bufGraphics.setColor(Color.green);
							bufGraphics.drawString("Static Draw On", 100, 100);
						}else{
							bufGraphics.setColor(Color.red);
							bufGraphics.drawString("Static Draw Off", 100, 100);
						}
						renderPane.getGraphics().drawImage(buffer, 0, 0, null);
						Thread.sleep(10);
					} catch (InterruptedException e) {}
				}
			}
		};
		thread.start();
	}
}
