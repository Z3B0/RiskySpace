package riskyspace.view.menu.swingImpl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import riskyspace.logic.FleetMove;
import riskyspace.model.PlayerStats;
import riskyspace.model.Resource;
import riskyspace.model.Supply;
import riskyspace.services.Event;
import riskyspace.services.EventBus;
import riskyspace.services.EventHandler;
import riskyspace.view.Action;
import riskyspace.view.Button;
import riskyspace.view.Clickable;
import riskyspace.view.ViewResources;
import riskyspace.view.View;
import riskyspace.view.menu.IMenu;

public class TopMenu implements IMenu, Clickable, EventHandler {
	
	private boolean enabled;
	
	private Image metalImage = null;
	private Image gasImage = null;
	private Image supplyImage = null;
	private int metal;
	private int gas;
	private Supply supply;
	
	private Button endTurnButton = null;
	private Button performMovesButton = null;
	private Button menuButton = null;
	private Button buildQueueButton = null;
	
	private int x, y;
	private int menuHeight = 0;
	private int menuWidth = 0;
	private int margin = 5;
	
	private Font resourceFont = null;
	
	public TopMenu (int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		menuHeight = height;
		menuWidth = width;
		
		supplyImage = Toolkit.getDefaultToolkit().getImage("res/menu/supply_square.png");
		metalImage = Toolkit.getDefaultToolkit().getImage("res/menu/metal_square.png");
		gasImage = Toolkit.getDefaultToolkit().getImage("res/menu/gas_square.png");
		
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		menuButton = new Button(0, 0, screenHeight/6, height);
		menuButton.setImage("res/menu/menu" + View.res);
		menuButton.setAction(new Action() {
			@Override
			public void performAction() {
				System.exit(0);
			}
		});
		
		buildQueueButton = new Button(screenHeight/6, 0, screenHeight/6, height);
		buildQueueButton.setImage("res/menu/build_queue" + View.res);
		
		endTurnButton = new Button(width - screenHeight/6, 0, screenHeight/6, height);
		endTurnButton.setImage("res/menu/end_turn" + View.res);
		endTurnButton.setAction(new Action() {
			@Override
			public void performAction() {
				Event evt = new Event(Event.EventTag.NEXT_TURN, null);
				EventBus.INSTANCE.publish(evt);
			}
		});
		
		performMovesButton = new Button(width - screenHeight/3, 0, screenHeight/6, height);
		performMovesButton.setImage("res/menu/perform_moves" + View.res);
		performMovesButton.setAction(new Action() {
			@Override
			public void performAction() {
				Event evt = new Event(FleetMove.isMoving() ? Event.EventTag.INTERRUPT_MOVES : Event.EventTag.PERFORM_MOVES, null);
				EventBus.INSTANCE.publish(evt);
			}
		});
		resourceFont = ViewResources.getFont().deriveFont(17f);
		EventBus.INSTANCE.addHandler(this);
		setVisible(true);
	}

	@Override
	public boolean contains(Point p) {
		return false;
	}

	@Override
	public boolean mousePressed(Point p) {
		if (menuButton.mousePressed(p)) {return true;}
		else if (buildQueueButton.mousePressed(p)) {return true;}
		else if (endTurnButton.mousePressed(p)) {return true;}
		else if (performMovesButton.mousePressed(p)) {return true;}
		else if (this.contains(p)) {return true;}
		return false;
	}

	@Override
	public boolean mouseReleased(Point p) {
		if (menuButton.mouseReleased(p)) {return true;}
		else if (buildQueueButton.mouseReleased(p)) {return true;}
		else if (endTurnButton.mouseReleased(p)) {return true;}
		else if (performMovesButton.mouseReleased(p)) {return true;}
		else if (this.contains(p)) {return true;}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		int a = menuWidth/10;
		g.setColor(ViewResources.WHITE);
		g.setFont(resourceFont);
		
		int fontHeight = g.getFontMetrics(resourceFont).getHeight();
		/*
		 * Draw the player's metal
		 */
		g.drawImage(metalImage, a*6, margin, null);
		g.drawString("" + metal, a*6 + metalImage.getWidth(null) + 5, margin + metalImage.getHeight(null)/2 + fontHeight/2);
		/*
		 * Draw the player's gas
		 */
		g.drawImage(gasImage, a*7, margin, null);
		g.drawString("" + gas, a*7 + gasImage.getWidth(null) + 5, margin + gasImage.getHeight(null)/2 + fontHeight/2);
		/*
		 * Draw the player's supply
		 */
		if (supply.isCapped())
			g.setColor(Color.RED);
		g.drawImage(supplyImage, a*5, margin, null);
		g.drawString(supply.getUsed() + "/" + supply.getMax(), a*5 + supplyImage.getWidth(null) + 5, margin + supplyImage.getHeight(null)/2 + fontHeight/2);
		
		menuButton.draw(g);
		buildQueueButton.draw(g);
		endTurnButton.draw(g);
		performMovesButton.draw(g);
	}

	@Override
	public boolean isVisible() {
		return enabled;
	}

	@Override
	public void setVisible(boolean set) {
		enabled = set;
	}
	
	@Override
	public void performEvent(Event evt) {
		if (evt.getTag() == Event.EventTag.STATS_CHANGED) {
			PlayerStats stats = (PlayerStats) evt.getObjectValue();
			gas = stats.getResource(Resource.GAS);
			metal = stats.getResource(Resource.METAL);
			supply = stats.getSupply();
		}
	}
}