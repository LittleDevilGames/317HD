package rs.input;

import rs.GameShell;
import rs.input.model.Area;

import java.awt.event.*;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

    public static GameShell applet;
    public static int clickButton;
    public static long clickTime;
    public static int clickX;
    public static int clickY;
    public static int dragButton;
    public static int lastClickButton;
    public static long lastClickTime;
    public static int lastClickX;
    public static int lastClickY;
    public static int lastX;
    public static int lastY;
    public static int wheelAmount = 0;
    public static int x;
    public static int y;

    public Mouse(GameShell applet) {
        Mouse.applet = applet;
    }

    public static boolean clicked(int i) {
        return lastClickButton == i;
    }

    public static boolean clicked(int x, int y, int width, int height) {
        return clickButton != 0 && clickX >= x && clickY >= y && clickX <= x + width && clickY <= y + height;
    }

    public static boolean isDragging() {
        return dragButton != 0;
    }

    public static boolean isDragging(int button) {
        return dragButton == button;
    }

    public static void process() {
        Mouse.clickButton = Mouse.lastClickButton;
        Mouse.clickX = Mouse.lastClickX;
        Mouse.clickY = Mouse.lastClickY;
        Mouse.clickTime = Mouse.lastClickTime;
        Mouse.lastClickButton = 0;
    }

    public static boolean within(Area area) {
        return area.contains(Mouse.lastX, Mouse.lastY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Mouse.applet.idleCycle = 0;
        Mouse.lastX = x;
        Mouse.lastY = y;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        Mouse.applet.idleCycle = 0;
        Mouse.lastX = -1;
        Mouse.lastY = -1;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Mouse.applet.idleCycle = 0;
        Mouse.lastX = x;
        Mouse.lastY = y;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        Mouse.applet.idleCycle = 0;
        Mouse.lastClickX = x;
        Mouse.lastClickY = y;
        Mouse.lastClickTime = System.currentTimeMillis();

        if (e.isMetaDown()) {
            Mouse.lastClickButton = 2;
            Mouse.dragButton = 2;
        } else {
            Mouse.lastClickButton = 1;
            Mouse.dragButton = 1;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Mouse.applet.idleCycle = 0;
        Mouse.dragButton = 0;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        wheelAmount = e.getWheelRotation();
    }

}
