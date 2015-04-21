package fr.imac.myrddin.game;

public class MyrddinUtil {
	
	public static float project(float oldValue, float oldMin, float oldMax, float newMin, float newMax) {
		return (((oldValue - oldMin) * (newMax - newMin)) / (oldMax - oldMin)) + newMin;
	}
}
