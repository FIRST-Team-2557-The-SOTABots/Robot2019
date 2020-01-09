/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Bunch of math things
 */
public class MathUtil {
    /** 
    * @param horizontal     the horizontal axis
    * @param vertical       the vertical axis
    * @return               if the joystick is out of a circular deadband
    */
    public static boolean inCircularDeadband(double horizontal, double vertical){
        if(Math.sqrt(Math.pow(horizontal,2)+Math.pow(vertical,2)) < .05) return true;
        return false;
    }

    /** 
    * @param axis     the axis
    * @return         if the axis is out of the deadband
    */
    public static boolean inDeadband(double axis){
        if(Math.abs(axis) < .05) return true;
        return false;
    }
}
