package org.usfirst.frc.team3316.robot.commands;

import org.usfirst.frc.team3316.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.RGBValue;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */

public class SaveImage extends Command {

	Image frame;
	int session;
	boolean failedToOpenCamera;

	public SaveImage() {
		setRunWhenDisabled(true);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.v);
		this.setRunWhenDisabled(true);
		failedToOpenCamera = false;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		try {
			session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
			NIVision.IMAQdxConfigureGrab(session);
			NIVision.IMAQdxStartAcquisition(session);
		} catch (Exception e) {
			e.printStackTrace();
			failedToOpenCamera = true;
		}

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		System.out.println("aaaaaa");
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		NIVision.IMAQdxGrab(session, frame, 1);
		NIVision.imaqWriteVisionFile(frame, "/home/lvuser/Pics/image_from_camarea.jpeg", new RGBValue(0, 0, 0, 1));
		frame.free();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return failedToOpenCamera;
	}

	// Called once after isFinished returns true
	protected void end() {
		NIVision.IMAQdxCloseCamera(session);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
