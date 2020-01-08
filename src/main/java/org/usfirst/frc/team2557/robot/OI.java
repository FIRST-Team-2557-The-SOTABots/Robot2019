package org.usfirst.frc.team2557.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public Joystick joystick1;
	public Joystick joystick2;

	public JoystickButton da; 
	public JoystickButton db; 
	public JoystickButton dx;
	public JoystickButton dy;
	public JoystickButton dbumperLeft;
	public JoystickButton dbumperRight;
	public JoystickButton dback;
	public JoystickButton dstart;
	public JoystickButton dterribleLeft;
	public JoystickButton dterribleRight;

	public JoystickButton ma; 
	public JoystickButton mb; 
	public JoystickButton mx;
	public JoystickButton my;
	public JoystickButton mbumperLeft;
	public JoystickButton mbumperRight;
	public JoystickButton mback;
	public JoystickButton mstart;
	public JoystickButton mterribleLeft;
	public JoystickButton mterribleRight;


	public OI(){
		joystick1 = new Joystick(0);
		joystick2 = new Joystick(1);

		da = new JoystickButton(joystick1, 1); //a is for apathy
		db = new JoystickButton(joystick1, 2); //b is for belfast
		dx = new JoystickButton(joystick1, 3); 
		dy = new JoystickButton(joystick1, 4);
		dbumperLeft = new JoystickButton(joystick1, 5); //vision forward
		dbumperRight = new JoystickButton(joystick1, 6);
		dback = new JoystickButton(joystick1, 7); //8s
		dstart = new JoystickButton(joystick1, 8); //12s
		dterribleLeft = new JoystickButton(joystick1, 9);
		dterribleRight = new JoystickButton(joystick1, 10);

		ma = new JoystickButton(joystick2, 1);
		mb = new JoystickButton(joystick2, 2);
		mx = new JoystickButton(joystick2, 3);
		my = new JoystickButton(joystick2, 4);
		mbumperLeft = new JoystickButton(joystick2, 5);
		mbumperRight = new JoystickButton(joystick2, 6);
		mback = new JoystickButton(joystick2, 7);
		mstart = new JoystickButton(joystick2, 8);
		mterribleLeft = new JoystickButton(joystick2, 9);
		mterribleRight = new JoystickButton(joystick2, 10);
	}
}