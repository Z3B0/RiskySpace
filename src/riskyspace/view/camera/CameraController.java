package riskyspace.view.camera;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;

public class CameraController extends Thread {
	
	private static final long SLEEP_TIME = 1000/60;
	private Camera currentCamera;
	private double maximumXValue, maximumYValue;
	
	public void setCamera(Camera cam) {
		currentCamera = cam;
		maximumXValue = Toolkit.getDefaultToolkit().getScreenSize().getWidth()-1;
		maximumYValue = Toolkit.getDefaultToolkit().getScreenSize().getHeight()-1;
	}
	
	@Override
	public void run() {
		while(!isInterrupted()) {
			Point loc = MouseInfo.getPointerInfo().getLocation();
			if (loc.x >= maximumXValue) {
				currentCamera.moveCamera(Camera.Direction.RIGHT);
			} else if (loc.x <= 0) {
				currentCamera.moveCamera(Camera.Direction.LEFT);
			}
			if (loc.y >= maximumYValue) {
				currentCamera.moveCamera(Camera.Direction.DOWN);
			} else if (loc.y <= 0) {
				currentCamera.moveCamera(Camera.Direction.UP);
			}
			try {
				sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				
			}
		}
	}

}
