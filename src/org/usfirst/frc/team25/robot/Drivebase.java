package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.VictorSP;

public class Drivebase {

	private static Drivebase m_instance;

	private final VictorSP m_leftDrives;
	private final VictorSP m_rightDrives;
	private final Servo m_rightBrake;
	private final Servo m_leftBrake;
	private final Encoder m_leftEncoder;
	private final Encoder m_rightEncoder;
	private final AnalogInput m_ultrasonic;
	private boolean m_brakesOn;
	private int m_driveStep;

	public Drivebase() {
		m_leftDrives = new VictorSP(Constants.LEFT_DRIVES_PWM);
		m_rightDrives = new VictorSP(Constants.RIGHT_DRIVES_PWM);

		m_leftBrake = new Servo(Constants.LEFT_BRAKE_PWM);
		m_rightBrake = new Servo(Constants.RIGHT_BRAKE_PWM);
		m_brakesOn = false;

		m_leftEncoder = new Encoder(Constants.LEFT_ENCODER_PWM_A, Constants.LEFT_ENCODER_PWM_B);
		m_rightEncoder = new Encoder(Constants.RIGHT_ENCODER_PWM_A, Constants.RIGHT_ENCODER_PWM_B, true);

		m_leftEncoder.setDistancePerPulse(Constants.INCHES_PER_COUNT);
		m_rightEncoder.setDistancePerPulse(Constants.INCHES_PER_COUNT);

		m_ultrasonic = new AnalogInput(Constants.ULTRASONIC_PWM);

		m_driveStep = 0;
	}

	public static Drivebase getInstance() {
		if (m_instance == null) {
			m_instance = new Drivebase();
		}
		return m_instance;
	}

	public void setSpeed(double speed) {
		setSpeed(speed, speed);
	}

	public void setSpeed(double leftSpeed, double rightSpeed) {
		m_leftDrives.set(leftSpeed);
		m_rightDrives.set(-rightSpeed);
	}

	public void brakesOn() {
		m_brakesOn = true;
		m_leftBrake.set(Constants.LEFT_BRAKES_ON);
		m_rightBrake.set(Constants.RIGHT_BRAKES_ON);
	}

	public void brakesOff() {
		m_brakesOn = false;
		m_leftBrake.set(Constants.LEFT_BRAKES_OFF);
		m_rightBrake.set(Constants.RIGHT_BRAKES_OFF);
	}

	public boolean brakesAreOn() {
		return m_brakesOn;
	}

	public double getLeftEncoderDistance() {
		return m_leftEncoder.getDistance();
	}

	public double getRightEncoderDistance() {
		return m_rightEncoder.getDistance();
	}

	public void resetEncoders() {
		m_leftEncoder.reset();
		m_rightEncoder.reset();
	}

	/**
	 * Keep the robot driving straight for specified distance.
	 * 
	 * @param distance (in inches)
	 * @param speed (initial speed)
	 * 
	 * @return True if complete 
	 */
	public boolean driveStraight(double distance, double speed) {
		brakesOff();
		double absoluteDistance = Math.abs(distance);
		double averageDistance = Math.abs(getLeftEncoderDistance() + getRightEncoderDistance()) / 2.0;
		if (m_driveStep == 0) {
			resetEncoders();
			m_driveStep++;
			return false;
		} else if (m_driveStep == 1) {
			if (absoluteDistance * (2.0 / 3.0) <= averageDistance) {
				m_driveStep++;
			}
		} else if (m_driveStep == 2) { // If over 2/3 distance, reduce speed to 3/4
			if (absoluteDistance * (3.0 / 4.0) <= averageDistance) {
				m_driveStep++;
			} else {
				speed /= 3.5;
			}
		} else if (m_driveStep == 3) {
			if (averageDistance < absoluteDistance) { // If close to the end, go to small speed
				if(distance > 0.0) {
					setSpeed(0.18, 0.1);
				} else {
					setSpeed(-0.1, -0.18);
				}
				return false;
			} else {
				setSpeed(0.0);
				m_driveStep = 0;
				return true;
			}
		}
		speed = Math.abs(speed) * (distance / Math.abs(distance)); // Get correct speed sign
		double adjustment = speed / 10.0;
		double leftSpeed = speed + (distance < 0.0 ? adjustment : 0.0);
		double rightSpeed = speed - (distance > 0.0 ? adjustment : 0.0);
		double error = getLeftEncoderDistance() - getRightEncoderDistance();
		if (distance > 0.0) { // Adjust for backwards motor controllers (Forward)
			if (error > 0.75) {
				leftSpeed -= 0.1;
			} else if (error < -0.75) {
				rightSpeed -= 0.1;
			}
		} else {
			if (error > 0.75) { // (Backward)
				rightSpeed += 0.1;
			} else if (error < -0.75) {
				leftSpeed += 0.1;
			}
		}
		setSpeed(leftSpeed, rightSpeed);
		return false;
	}

	public int getUltrasonic() {
		return m_ultrasonic.getValue();
	}

}
