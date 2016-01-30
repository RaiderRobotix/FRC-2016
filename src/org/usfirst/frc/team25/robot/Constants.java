package org.usfirst.frc.team25.robot;

public abstract class Constants {

	// PWMs (Control)
	public static final int LEFT_DRIVES_PWM = 0;
	public static final int RIGHT_DRIVES_PWM = 1;
	public static final int LEFT_BRAKE_PWM = 9;
	public static final int RIGHT_BRAKE_PWM = 2;
	
	public static final double LEFT_BRAKES_ON = 0.34;
	public static final double LEFT_BRAKES_OFF = 0.57;
	public static final double RIGHT_BRAKES_ON = 0.63;
	public static final double RIGHT_BRAKES_OFF = 0.35;

	// Digital Sensors
	public static final int LEFT_ENCODER_PWM_A = 2;
	public static final int LEFT_ENCODER_PWM_B = 3;
	public static final int RIGHT_ENCODER_PWM_A = 0;
	public static final int RIGHT_ENCODER_PWM_B = 1;

	private static final double TIRE_CIRCUMFERENCE = 28.117254;
	private static final double COUNTS_PER_REVOLUTION = 80.0;
	public static final double INCHES_PER_COUNT = TIRE_CIRCUMFERENCE
			/ COUNTS_PER_REVOLUTION;
	
	// Analog Sensors
	public static final int ULTRASONIC_PWM = 3;

	// Joysticks
	public static final int LEFT_JOYSTICK_PORT = 0;
	public static final int RIGHT_JOYSTICK_PORT = 1;
	public static final int OPERATOR_JOYSTICK_PORT = 2;
	public static final double DEADBAND = 0.2;

}
