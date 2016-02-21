package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
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
	private boolean m_brakesOn;
	private int m_driveStep;
	private final AnalogGyro m_gyro;

	private Drivebase() {
		m_leftDrives = new VictorSP(Constants.LEFT_DRIVES_PWM);
		m_rightDrives = new VictorSP(Constants.RIGHT_DRIVES_PWM);

		m_leftBrake = new Servo(Constants.LEFT_BRAKE_PWM);
		m_rightBrake = new Servo(Constants.RIGHT_BRAKE_PWM);
		m_brakesOn = false;

		m_leftEncoder = new Encoder(Constants.LEFT_ENCODER_PWM_A, Constants.LEFT_ENCODER_PWM_B);
		m_rightEncoder = new Encoder(Constants.RIGHT_ENCODER_PWM_A, Constants.RIGHT_ENCODER_PWM_B, true);

		m_leftEncoder.setDistancePerPulse(Constants.INCHES_PER_COUNT);
		m_rightEncoder.setDistancePerPulse(Constants.INCHES_PER_COUNT);

		m_gyro = new AnalogGyro(Constants.GYRO_PWM);
		//m_gyro.setSensitivity(Constants.GYRO_SENSITIVITY);

		m_driveStep = 0;
	}

	public static Drivebase getInstance() {
		if (m_instance == null) {
			m_instance = new Drivebase();
		}
		return m_instance;
	}

	public void resetStep() {
		m_driveStep = 0;
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

	public boolean turnToAngle(double angle, double speed) {
		System.out.println("Gyro: " + m_gyro.getAngle());
		brakesOff();
		double error = getGyroAngle() - angle;
		if (Math.abs(error) <= Constants.DRIVE_STRAIGHT_TOLERANCE) {
			setSpeed(0.0);
			return true;
		}
		if (Math.abs(error) <= 30.0) {
			speed /= 1.5;
		}
		if (error > 0.0) {
			speed *= -1.0;
		}
		setSpeed(speed, -speed);
		return false;
	}

	/**
	 * Keep the robot driving straight for specified distance.
	 * 
	 * @param distance
	 *            (in inches)
	 * @param speed
	 *            (initial speed)
	 * 
	 * @return True when complete.
	 */
	public boolean driveStraight(double distance, double speed) {
		brakesOff();
		double absoluteDistance = Math.abs(distance);
		double averageDistance = Math.abs(getLeftEncoderDistance());
		if (m_driveStep == 0) {
			resetEncoders();
			resetGyro();
			m_driveStep++;
			return false;
		}else if (m_driveStep == 1) {
			if (averageDistance >= (absoluteDistance - 18.0)) {
				m_driveStep++;
			}
		} else if (m_driveStep == 2) {
			if (averageDistance >= absoluteDistance) {
				m_driveStep++;
			} else {
				if (distance > 0.0) {
					setSpeed(0.18, 0.12);
				} else {
					setSpeed(-0.12, -0.18);
				}
			}
		} else {
			setSpeed(0.0);
			m_driveStep = 0;
			return true;
		}
		speed = Math.abs(speed) * (distance / Math.abs(distance));
		double adjustment = speed / 10.0;
		double leftSpeed = speed + (distance < 0.0 ? adjustment : 0.0);
		double rightSpeed = speed - (distance > 0.0 ? adjustment : 0.0);
		double error = getGyroAngle();
		if (distance > 0.0) {
			if (error > Constants.DRIVE_STRAIGHT_TOLERANCE) {
				leftSpeed -= 0.12;
			} else if (error < -Constants.DRIVE_STRAIGHT_TOLERANCE) {
				rightSpeed -= 0.12;
			}
		} else {
			if (error > Constants.DRIVE_STRAIGHT_TOLERANCE) {
				rightSpeed += 0.12;
			} else if (error < -Constants.DRIVE_STRAIGHT_TOLERANCE) {
				leftSpeed += 0.12;
			}
		}
		setSpeed(leftSpeed, rightSpeed);
		return false;
	}

	public void resetGyro() {
		m_gyro.reset();
	}

	public void calibrateGyro() {
		m_gyro.calibrate();
	}
	
	public double getGyroAngle() {
		return m_gyro.getAngle();
	}
}
