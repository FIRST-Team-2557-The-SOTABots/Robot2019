package org.usfirst.frc.team2557.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
	public Joystick joystick;
	
	public OI(){
		joystick = new Joystick(0);
	}
}