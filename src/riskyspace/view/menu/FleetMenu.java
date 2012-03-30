package riskyspace.view.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import riskyspace.model.Colony;
import riskyspace.model.Fleet;
import riskyspace.model.Player;
import riskyspace.services.Event;
import riskyspace.services.EventBus;
import riskyspace.services.EventHandler;
import riskyspace.view.Button;
import riskyspace.view.Clickable;

public class FleetMenu implements IMenu, Clickable, EventHandler {
	
	private boolean enabled;
	
	private Color ownerColor = null;
	
	private int x, y;
	private int menuHeight = 0;
	private int menuWidth = 0;
	private int margin = 30;
	
	private Image background = null;
	private Image fleetPicture = null;
	
	private Button mergeButton = null;
	private Button splitButton = null;
	
	private Image fleetBlue = null;
	private Image fleetRed = null;
	
	public FleetMenu(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		menuHeight = height;
		menuWidth = width;
		background = Toolkit.getDefaultToolkit().getImage("res/menu/menubackground.png")
				.getScaledInstance(menuWidth, menuHeight, Image.SCALE_DEFAULT);
		
	}
	
	public void setFleet(Colony colony) {
		ownerColor = colony.getOwner() == Player.BLUE ? Color.BLUE : Color.RED;
		fleetPicture = colony.getOwner() == Player.BLUE ? fleetBlue : fleetRed;
	}

	@Override
	public void performEvent(Event evt) {
		if (evt.getTag() == Event.EventTag.SHOW_FLEETMENU) {
			if (evt.getObjectValue() instanceof Fleet) {
				setFleet((Colony) evt.getObjectValue());
				setVisible(true);
			}
		} else if (evt.getTag() == Event.EventTag.HIDE_MENU) {
				setVisible(false);
		}
	}

	public boolean contains(Point p) {
		/*
		 * Only handle mouse event if enabled
		 */
		if (enabled) {
			boolean xLegal = p.x >= x && p.x <= x + menuWidth;
			boolean yLegal = p.y >= y && p.y <= y + menuHeight;
			return xLegal && yLegal;
		}
		return false;
	}


	@Override
	public boolean mousePressed(Point p) {
		/*
		 * Only handle mouse event if enabled
		 */
		if (enabled) {
			if (splitButton.mousePressed(p)) {
				//TODO:
			} else if (mergeButton.mousePressed(p)) {
				//TODO:
			}
			if (this.contains(p)) {return true;}
			else {
				return false;
			}
		}
		return false;
	}
	@Override
	public boolean mouseReleased(Point p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Graphics g) {
		/*
		 * Only draw if enabled
		 */
		if (enabled) {
			g.drawImage(background, x, y, null);
			g.drawImage(fleetPicture, x + margin, y + margin ,null);
			mergeButton.draw(g);
			splitButton.draw(g);
		}
	}

	@Override
	public void setVisible(boolean set) {
		enabled = set;
	}

	@Override
	public boolean isVisible() {
		return enabled;
	}
}