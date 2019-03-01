package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.drive.GyroSwerveDriveCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GyroSwerveDrive extends Subsystem {
  public double[] speed = new double[4];
  public double[] angle = new double[4];
  private boolean fcd = true;

  public void gyroDrive (double str, double fwd, double rot) {
    computeInputs(str, fwd, rot);
    setSetpoints(rot);
    // scaleOutput(); // meant to reduce drift/error
    for(int i = 0; i < 4; i++) RobotMap.swerveMod[i].drive(speed[i], angle[i]);
  }

  public void scaleOutput(){
    double diff = 0;
    for(int i = 0; i < 4; i++){
      if(Math.abs(angle[i] - RobotMap.swerveMod[i].encoder.pidGet()) > diff){
        diff = Math.abs(angle[i] - RobotMap.swerveMod[i].encoder.pidGet());
      }
    }
    if(diff > RobotMap.SWERVE_ENC_CIRC/8){
      for(int i = 0; i < 4; i++){
        speed[i] *= RobotMap.SWERVE_ENC_CIRC/8 / diff;  // up for changes
      }
    }
  }

  public double getOppositeAngle(int index){
    double opp = angle[index];
    if(opp < RobotMap.SWERVE_ENC_CIRC/2) opp += RobotMap.SWERVE_ENC_CIRC/2;
    else opp -= RobotMap.SWERVE_ENC_CIRC/2;
    return opp;
  }

  public void computeInputs (double str, double fwd, double rot){
    double gyroAngle = -1 * Math.toRadians(RobotMap.gyro.getAngle() % 360);
    if(fcd){ 
      double intermediary = fwd * Math.cos(gyroAngle) + str * Math.sin(gyroAngle);
      str = -fwd * Math.sin(gyroAngle) + str * Math.cos(gyroAngle);
      fwd = intermediary;
    }
    
    // if(Robot.m_oi.start.get() || Robot.m_oi.back.get()) fcd = !fcd; //.get() is a while loop and may be cause unpredictable amounts of switching
    if(Robot.m_oi.back.get()) fcd = false;
    if(Robot.m_oi.start.get()) fcd = true;

    double a = str - rot * (RobotMap.SWERVE_LENGTH / RobotMap.SWERVE_RADIUS);
		double b = str + rot * (RobotMap.SWERVE_LENGTH / RobotMap.SWERVE_RADIUS);
		double c = fwd - rot * (RobotMap.SWERVE_WIDTH / RobotMap.SWERVE_RADIUS);
    double d = fwd + rot * (RobotMap.SWERVE_WIDTH / RobotMap.SWERVE_RADIUS);
    
    speed[1] = Math.sqrt ((a * a) + (d * d));
    speed[2] = Math.sqrt ((a * a) + (c * c));
    speed[0] = Math.sqrt ((b * b) + (d * d));
    speed[3] = Math.sqrt ((b * b) + (c * c));

    angle[1] = Math.atan2 (a, d) / Math.PI;
    angle[2] = Math.atan2 (a, c) / Math.PI;
    angle[0] = Math.atan2 (b, d) / Math.PI;
    angle[3] = Math.atan2 (b, c) / Math.PI;
    
    // double max = speed[1];
    // if (speed[2] > max) speed[2] = max; 
    // if (speed[0] > max) speed[0] = max; 
    // if (speed[3] > max) speed[3] = max;
    // if (max > 1) speed[3] /= max; speed[0] /= max; speed[2] /= max;
  }

  public void setSetpoints(double rot){
    for(int i = 0; i < 4; i++){
      // SmartDashboard.putNumber("angle: " + i, angle[i]);
      // SmartDashboard.putNumber("speed: " + i, speed[i]);

      double encCount = RobotMap.swerveMod[i].encoder.pidGet();
      angle[i] = (angle[i] + 1) * RobotMap.SWERVE_ENC_CIRC / 2 + RobotMap.SWERVE_SETPOINT_OFFSET[i]; 
      if(angle[i] > RobotMap.SWERVE_ENC_CIRC) angle[i] -= RobotMap.SWERVE_ENC_CIRC;

      double degreesBeforeFlip = 90.0;
      if(Math.abs(encCount - angle[i]) > RobotMap.SWERVE_ENC_CIRC / 360 * degreesBeforeFlip) {
        angle[i] = getOppositeAngle(i);
        speed[i] *= -1;
      }
    }
  }
  
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new GyroSwerveDriveCommand());
  }
}