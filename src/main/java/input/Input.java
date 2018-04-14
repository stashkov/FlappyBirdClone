package input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Input extends GLFWKeyCallback {
	
	private static boolean[] keys = new boolean[65536];

	public void invoke(long window, int key, int scanCode, int action, int mods) {
		keys[key] = action != GLFW.GLFW_RELEASE; 
	}
	
	public static boolean isKeyDown(int keycode) {
		return keys[keycode];
	}
	
}
