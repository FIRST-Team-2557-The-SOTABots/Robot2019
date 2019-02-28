/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ArduinoSensors extends Subsystem {

  public static String lastRead = null;

  public class MyPair{
    public char id;
    public int value;
  }

  public MyPair parseInput(String input){
    MyPair pair = new MyPair();

    pair.id = input.charAt(0);

    if(pair.id > 'z' || pair.id < 'a'){
      return null;
    }

    pair.value = Integer.parseInt(input.substring(1, input.length() - 1));
    return pair;
  }

  // public void sensors(){
    
  //   try{
  //     if(RobotMap.serial.getBytesReceived() <= 0){
  //       return;
  //     }

  //     String str = RobotMap.serial.readString();

  //     if(lastRead != null){
  //       str = lastRead + str;
  //       lastRead = null;
  //     }

  //     int nextLN = str.indexOf('\n', 0);

  //     if(nextLN < str.length() - 1){
  //       lastRead = str.substring(nextLN);
  //     }

  //     MyPair result = parseInput(str);

  //     if(result == null){
  //       return;
  //     }

  //     if(result.id == 'a'){
  //       RobotMap.tof1 = result.value;
  //       SmartDashboard.putNumber("Tof1", RobotMap.tof1);
  //     }else if(result.id == 'b'){
  //       RobotMap.tof2 = result.value;
  //       SmartDashboard.putNumber("Tof2", RobotMap.tof2);
  //     }else if(result.id == 'c'){
  //       RobotMap.IR1 = result.value;
  //       SmartDashboard.putNumber("IR1", RobotMap.IR1);
  //     }else if(result.id == 'd'){
  //       RobotMap.tof1 = result.value;
  //       SmartDashboard.putNumber("IR2", RobotMap.IR2);
  //     }else if(result.id == 'e'){
  //       RobotMap.tof1 = result.value;
  //       SmartDashboard.putNumber("IR3", RobotMap.IR3);
  //     }

  //   }catch(Exception e){
  //     SmartDashboard.putString("Ard Error", "Errrr");
  //   }

  // }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
