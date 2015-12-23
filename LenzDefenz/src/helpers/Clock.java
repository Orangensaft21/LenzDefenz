package helpers;

import org.lwjgl.Sys;

public class Clock {
	
	public static boolean paused = false;
	public static long lastFrame= getTime(),totalTime=0;    //fix, lastFrame am Anfang setzen!
	public static float d=0, multiplier = 1;
	
	public static long getTime(){
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
	public static float getDelta(){
		long currentTime = getTime();
		int delta = (int) (currentTime -lastFrame);
		lastFrame=getTime();
		if (delta>50)
			return 0.05f;
		return delta *0.001f;
	}
	
	public static float Delta(){
		if (paused){
			return 0;
		}else
			return d*multiplier;
	}
	
	public static float TotalTime(){
		return totalTime/1000;
	}
	
	public static float Multiplier(){
		return multiplier;
	}
	
	public static void update(){
		d = getDelta();
		totalTime += d*1000;						//*1000 damit beim casten nix abgeschnitten und da totaltime in msec gemessen wird
	}
	
	public static void changeMultiplier(float change){
		if (multiplier + change < -1 && multiplier +change >7 ){
			
		}else{
			multiplier += change;
		}
	}
	
	public static void Pause(){
		if (paused)
			paused=false;
		else
			paused=true;
	}
	
}

