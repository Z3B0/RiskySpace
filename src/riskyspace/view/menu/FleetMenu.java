package riskyspace.view.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import riskyspace.model.Fleet;
import riskyspace.model.Player;
import riskyspace.model.ShipType;
import riskyspace.services.Event;
import riskyspace.services.EventBus;
import riskyspace.services.EventHandler;
import riskyspace.view.Clickable;
import riskyspace.view.Sprite;
import riskyspace.view.View;

public class FleetMenu implements IMenu, Clickable, EventHandler {
	
	private boolean enabled;
	
	private int x, y;
	private int menuHeight;
	private int menuWidth;
	private int itemSize;
	
	private int margin = 22;
	
	private Image background = null;
	private Image backgroundRed = null;
	private Image backgroundBlue = null;
	private Image fleetPicture = null;
	
	private Map<String, Image> shipIcons = new HashMap<String, Image>();
	private List<Image> fleetIcons = new ArrayList<Image>();
	
	public FleetMenu(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		menuHeight = height;
		menuWidth = width;
		backgroundRed = Toolkit.getDefaultToolkit().getImage("res/menu/red/menubackground.png").getScaledInstance(menuWidth, menuHeight, Image.SCALE_DEFAULT);
		backgroundBlue = Toolkit.getDefaultToolkit().getImage("res/menu/blue/menubackground.png").getScaledInstance(menuWidth, menuHeight, Image.SCALE_DEFAULT);
		fleetPicture = Toolkit.getDefaultToolkit().getImage("res/menu/palpatine.png").getScaledInstance(menuWidth - 3*margin, 2*(menuWidth - 3*margin)/3, Image.SCALE_DEFAULT);
		itemSize = (menuWidth - margin*2) / 5;
		initSprites(itemSize - margin/10);
		EventBus.INSTANCE.addHandler(this);
	}
	
	public void initSprites(int size) {
		shipIcons.put("SCOUT_BLUE", Toolkit.getDefaultToolkit().getImage("res/menu/blue/ships/scout_icon" + View.res).getScaledInstance(size, size, Image.SCALE_DEFAULT));
		shipIcons.put("HUNTER_BLUE", Toolkit.getDefaultToolkit().getImage("res/menu/blue/ships/hunter_icon" + View.res).getScaledInstance(size, size, Image.SCALE_DEFAULT));
		shipIcons.put("DESTROYER_BLUE", Toolkit.getDefaultToolkit().getImage("res/menu/blue/ships/destroyer_icon" + View.res).getScaledInstance(size, size, Image.SCALE_DEFAULT));
		shipIcons.put("COLONIZER_BLUE", Toolkit.getDefaultToolkit().getImage("res/menu/blue/ships/colonizer_icon" + View.res).getScaledInstance(size, size, Image.SCALE_DEFAULT));
		
		shipIcons.put("SCOUT_RED", Toolkit.getDefaultToolkit().getImage("res/menu/red/ships/scout_icon" + View.res).getScaledInstance(size, size, Image.SCALE_DEFAULT));
		shipIcons.put("HUNTER_RED", Toolkit.getDefaultToolkit().getImage("res/menu/red/ships/hunter_icon" + View.res).getScaledInstance(size, size, Image.SCALE_DEFAULT));
		shipIcons.put("DESTROYER_RED", Toolkit.getDefaultToolkit().getImage("res/menu/red/ships/destroyer_icon" + View.res).getScaledInstance(size, size, Image.SCALE_DEFAULT));
		shipIcons.put("COLONIZER_RED", Toolkit.getDefaultToolkit().getImage("res/menu/red/ships/colonizer_icon" + View.res).getScaledInstance(size, size, Image.SCALE_DEFAULT));
	}
	
	public void setFleet(Fleet fleet) {
		background = fleet.getOwner() == Player.BLUE ? backgroundBlue : backgroundRed;
		createFleetIcons(fleet);
	}

	@Override
	public void performEvent(Event evt) {
		if (evt.getTag() == Event.EventTag.SHOW_FLEETMENU) {
			setFleet(new Fleet((Set<Fleet>) evt.getObjectValue()));
			setVisible(true);
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
			if (this.contains(p)) {return true;}
			else {
				return false;
			}
		}
		return false;
	}
	@Override
	public boolean mouseReleased(Point p) {
		if (enabled) {
			if (this.contains(p)) {return true;}
			else {
				return false;
			}
		}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		/*
		 * Only draw if enabled
		 */
		if (enabled) {
			g.drawImage(background, x, y, null);
			g.drawImage(fleetPicture, x + margin + margin/2 + 2, y + margin + 15, null);
			int height = fleetPicture != null ? fleetPicture.getHeight(null) : 0;
			for (int i = 0; i < fleetIcons.size(); i++) {
				int col = i % 5;
				int row = i / 5;
				g.drawImage(fleetIcons.get(i), x + margin + col*itemSize + 2, y + 2*margin + height + row*itemSize, null);
			}
		}
	}
	
	private void createFleetIcons(Fleet fleet) {
		fleetIcons.clear();
		createFleetIcons(fleet, ShipType.DESTROYER);
		createFleetIcons(fleet, ShipType.HUNTER);
		createFleetIcons(fleet, ShipType.SCOUT);
		createFleetIcons(fleet, ShipType.COLONIZER);
	}
	
	private void createFleetIcons(Fleet fleet, ShipType type) {
		int number = fleet.shipCount(type);
		for (int i = 0; i < number; i++) {
			fleetIcons.add(shipIcons.get(type.toString() + "_" + fleet.getOwner().toString()));
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