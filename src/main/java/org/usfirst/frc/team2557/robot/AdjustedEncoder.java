package org.usfirst.frc.team2557.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class AdjustedEncoder implements PIDSource {
    private PIDSourceType pst;
    private AnalogInput enc;
    private boolean inverted;
    private double offset;

    public AdjustedEncoder(int index, boolean encInverted, double offset){
        pst = PIDSourceType.kDisplacement;
        enc = new AnalogInput(index);
        inverted = encInverted;
        this.offset = offset;
    }

    public PIDSourceType getPIDSourceType()	{
        return pst;
    }

    public double pidGet(){
        double encVal = enc.pidGet();
        encVal += offset;
        if(encVal >= RobotMap.SWERVE_ENC_CIRC){
			encVal -= RobotMap.SWERVE_ENC_CIRC;
		}
        if(inverted){
            encVal -= RobotMap.SWERVE_ENC_CIRC;
            encVal *= -1;
            encVal += RobotMap.SWERVE_ENC_CIRC;
        }
        return encVal;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        pst = pidSource;
    }
}