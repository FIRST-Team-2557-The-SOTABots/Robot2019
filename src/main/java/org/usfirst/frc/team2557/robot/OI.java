package org.usfirst.frc.team2557.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public Joystick joystick;

	//minimal use
	public JoystickButton db; 
	public JoystickButton da; 

	//actual use
	public JoystickButton dx;
	public JoystickButton dy;

	public JoystickButton start;
	public JoystickButton back;

	public OI(){
		joystick = new Joystick(0);

		db = new JoystickButton(joystick, 2);
		da = new JoystickButton(joystick, 1);

		dx = new JoystickButton(joystick, 3);
		dy = new JoystickButton(joystick, 4);

		start = new JoystickButton(joystick, 8);
		back = new JoystickButton(joystick, 7);
	}
}