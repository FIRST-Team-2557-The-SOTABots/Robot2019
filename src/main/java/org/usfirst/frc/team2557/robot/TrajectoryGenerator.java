package org.usfirst.frc.team2557.robot;

import java.io.File;
import java.io.IOException;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.SwerveModifier;

public class TrajectoryGenerator {
    public static void main(String[]args) {
        // 3 Waypoints
        Waypoint[] points = new Waypoint[] {
            new Waypoint(-4, -1, Pathfinder.d2r(-45)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
            new Waypoint(-2, -2, 0),                        // Waypoint @ x=-2, y=-2, exit angle=0 radians
            new Waypoint(0, 0, 0)                           // Waypoint @ x=0, y=0,   exit angle=0 radians
        };

        // Create the Trajectory Configuration
        //
        // Arguments:
        // Fit Method:          HERMITE_CUBIC or HERMITE_QUINTIC
        // Sample Count:        SAMPLES_HIGH (100 000)
        //                      SAMPLES_LOW  (10 000)
        //                      SAMPLES_FAST (1 000)
        // Time Step:           0.05 Seconds
        // Max Velocity:        1.7 m/s
        // Max Acceleration:    2.0 m/s/s
        // Max Jerk:            60.0 m/s/s/s
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);

        // Generate the trajectory
        Trajectory drive0 = Pathfinder.generate(points, config);
        // String drive0address = "/home/lvuser/Trajectories/drive0.t";
        // write(drive0address, drive0);
        // drive0 = Pathfinder.readFromFile(new File(drive0address));

        // in m?
        double wheelbase_width = 0.5461;
        double wheelbase_depth = 0.5461;

        // The swerve mode to generate will be the 'default' mode, where the 
        // robot will constantly be facing forward and 'sliding' sideways to follow a curved path.
        SwerveModifier.Mode mode = SwerveModifier.Mode.SWERVE_DEFAULT;
        SwerveModifier modifier = new SwerveModifier(drive0);

        // Generate the individual wheel trajectories using the original trajectory as the centre
        modifier.modify(wheelbase_width, wheelbase_depth, mode);

        Trajectory fl = modifier.getFrontLeftTrajectory();       // Get the Front Left wheel
        Trajectory fr = modifier.getFrontRightTrajectory();      // Get the Front Right wheel
        Trajectory bl = modifier.getBackLeftTrajectory();        // Get the Back Left wheel
        Trajectory br = modifier.getBackRightTrajectory();       // Get the Back Right wheel

        String folder = "/home/lvuser/Trajectories/";
        write(folder + "fl.t", fl);
        write(folder + "fr.t", fr);
        write(folder + "bl.t", bl);
        write(folder + "br.t", br);
    }

    public static void write(String file, Trajectory tr){
        File trfile = new File(file);
        try {
            trfile.createNewFile();
            Pathfinder.writeToFile(trfile, tr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Trajectory trajectory(){
        Waypoint[] points = new Waypoint[] {
            new Waypoint(-4, -1, Pathfinder.d2r(-45)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
            new Waypoint(-2, -2, 0),                        // Waypoint @ x=-2, y=-2, exit angle=0 radians
            new Waypoint(0, 0, 0)                           // Waypoint @ x=0, y=0,   exit angle=0 radians
        };

        // Create the Trajectory Configuration
        //
        // Arguments:
        // Fit Method:          HERMITE_CUBIC or HERMITE_QUINTIC
        // Sample Count:        SAMPLES_HIGH (100 000)
        //                      SAMPLES_LOW  (10 000)
        //                      SAMPLES_FAST (1 000)
        // Time Step:           0.05 Seconds
        // Max Velocity:        1.7 m/s
        // Max Acceleration:    2.0 m/s/s
        // Max Jerk:            60 m/s/s/s
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);

        // Generate the trajectory
        Trajectory drive0 = Pathfinder.generate(points, config);
        // String drive0address = "/home/lvuser/Trajectories/drive0.t";
        // write(drive0address, drive0);
        // drive0 = Pathfinder.readFromFile(new File(drive0address));

        // in m?
        double wheelbase_width = 0.5461;
        double wheelbase_depth = 0.5461;

        // The swerve mode to generate will be the 'default' mode, where the 
        // robot will constantly be facing forward and 'sliding' sideways to follow a curved path.
        SwerveModifier.Mode mode = SwerveModifier.Mode.SWERVE_DEFAULT;
        SwerveModifier modifier = new SwerveModifier(drive0);

        // Generate the individual wheel trajectories using the original trajectory as the centre
        modifier.modify(wheelbase_width, wheelbase_depth, mode);

        Trajectory fl = modifier.getFrontLeftTrajectory();       // Get the Front Left wheel
        Trajectory fr = modifier.getFrontRightTrajectory();      // Get the Front Right wheel
        Trajectory bl = modifier.getBackLeftTrajectory();        // Get the Back Left wheel
        Trajectory br = modifier.getBackRightTrajectory();       // Get the Back Right wheel

        String folder = "/home/lvuser/Trajectories/";
        write(folder + "fl.t", fl);
        write(folder + "fr.t", fr);
        write(folder + "bl.t", bl);
        write(folder + "br.t", br);
        return drive0;

    }


}