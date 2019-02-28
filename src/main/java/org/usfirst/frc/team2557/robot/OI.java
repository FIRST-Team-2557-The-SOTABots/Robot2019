package org.usfirst.frc.team2557.robot;

import org.usfirst.frc.team2557.robot.commands.arm.PIDarm;
import org.usfirst.frc.team2557.robot.commands.intake.IntakeLolz;
import org.usfirst.frc.team2557.robot.commands.intake.IntakeTrigger;
import org.usfirst.frc.team2557.robot.commands.lift.PIDdown;
import org.usfirst.frc.team2557.robot.commands.lift.PIDup;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public Joystick joystick1;
	public Joystick joystick2;

	public JoystickButton da; 
	public JoystickButton db; 
	public JoystickButton dx;
	public JoystickButton dy;
	public JoystickButton bumperLeft;
	public JoystickButton bumperRight;
	public JoystickButton back;
	public JoystickButton start;
	public JoystickButton terribleLeft;
	public JoystickButton terribleRight;

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

		da = new JoystickButton(joystick1, 1);
		db = new JoystickButton(joystick1, 2);
		dx = new JoystickButton(joystick1, 3);
		dy = new JoystickButton(joystick1, 4);
		bumperLeft = new JoystickButton(joystick1, 5);
		bumperRight = new JoystickButton(joystick1, 6);
		back = new JoystickButton(joystick1, 7);
		start = new JoystickButton(joystick1, 8);
		terribleLeft = new JoystickButton(joystick1, 9);
		terribleRight = new JoystickButton(joystick1, 10);
		

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


		// encoder count to inches = 
		//if mback {ball} else {disk}
		if(mx.get() &&  mback.get()) mx.whileHeld(new PIDup(474000)); //83.5
		else mx.whileHeld(new PIDup(464000));
		if(mb.get() &&  mback.get()) mb.whileHeld(new PIDup(160000)); //55.5
		else mb.whileHeld(new PIDup(210000)); //middle (unkonwn rocket location, placeholder)
		if(mx.get() &&  mback.get()) mx.whileHeld(new PIDup(163000)); //27.5 inches
		else mx.whileHeld(new PIDup(153000)); //lower (unkonwn cargo location, placeholder)
		// if(ma.get() &&  mback.get()) ma.whileHeld(new PIDup(110000)); 


		ma.whileHeld(new PIDup(-181000)); //intake

	}
}