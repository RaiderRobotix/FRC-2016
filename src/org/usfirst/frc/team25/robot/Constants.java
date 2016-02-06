package org.usfirst.frc.team25.robot;

public abstract class Constants {

	public static final double DRIVE_STRAIGHT_TOLERANCE = 1.0; // (In Degrees)
	
	// PWMs (Control)
	public static final int PICKUP_ROLLERS_PWM = 5;
	public static final int PICKUP_ARM_PWM = 6;
	public static final int LEFT_DRIVES_PWM = 8;
	public static final int RIGHT_DRIVES_PWM = 0;
	public static final int LEFT_BRAKE_PWM = 9;
	public static final int RIGHT_BRAKE_PWM = 1;

	// Brake Positions
	public static final double LEFT_BRAKES_ON = 0.42;
	public static final double LEFT_BRAKES_OFF = 0.57;
	public static final double RIGHT_BRAKES_ON = 0.55;
	public static final double RIGHT_BRAKES_OFF = 0.35;

	// Digital Sensors
	public static final int LEFT_ENCODER_PWM_A = 9;
	public static final int LEFT_ENCODER_PWM_B = 2;
	public static final int RIGHT_ENCODER_PWM_A = 6;
	public static final int RIGHT_ENCODER_PWM_B = 7;

	private static final double TIRE_CIRCUMFERENCE = 28.117254;
	private static final double COUNTS_PER_REVOLUTION = 85.75;
	public static final double INCHES_PER_COUNT = TIRE_CIRCUMFERENCE / COUNTS_PER_REVOLUTION;

	// Analog Sensors
	public static final int ULTRASONIC_PWM = 3;
	public static final int PICKUP_POT_PWM = 0;
	public static final int GYRO_PWM = 1;

	// Joysticks
	public static final int LEFT_JOYSTICK_PORT = 0;
	public static final int RIGHT_JOYSTICK_PORT = 1;
	public static final int OPERATOR_JOYSTICK_PORT = 2;
	public static final double DEADBAND = 0.2;

	// Pickup Positions
	public static final double PICKUP_ARM_UP = 0.5088;
	public static final double PICKUP_ARM_DOWN = 0.3945;
	public static final double PICKUP_PORT_CULLIS = 0.4057;

}
