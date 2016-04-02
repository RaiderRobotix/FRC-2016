package org.usfirst.frc.team25.robot;

public abstract class Constants {

	// Assorted Constants
	public static final double DRIVE_STRAIGHT_TOLERANCE = 1.0; // (In Degrees)
	public static final double PICKUP_CURRENT_LIMIT = 35.0;
	public static final int I2C_DEVICE_ADDRESS = 8;
	public static final double SONIC_CONSTANT = 7.8;
	public static final double HANGER_SLOW_SPEED = 0.2;

	// PWMs (Control)
	public static final int PICKUP_ROLLERS_PWM = 5;
	public static final int PICKUP_ARM_PWM = 6;
	public static final int LEFT_DRIVES_PWM = 8;
	public static final int RIGHT_DRIVES_PWM = 0;
	public static final int LEFT_BRAKE_PWM = 9;
	public static final int RIGHT_BRAKE_PWM = 1;
	public static final int HANGER_WINCH_PWM = 7;

	// Brake Positions
	public static final double LEFT_BRAKES_ON = 0.42;
	public static final double LEFT_BRAKES_OFF = 0.57;
	public static final double RIGHT_BRAKES_ON = 0.55;
	public static final double RIGHT_BRAKES_OFF = 0.34;

	// Digital Sensors
	public static final int PICKUP_LINE_BREAKER_PWM = 5;
	public static final int LEFT_ENCODER_PWM_A = 9;
	public static final int LEFT_ENCODER_PWM_B = 4;
	public static final int RIGHT_ENCODER_PWM_A = 6;
	public static final int RIGHT_ENCODER_PWM_B = 7;

	private static final double TIRE_CIRCUMFERENCE = 28.117254; // In inches
	private static final double COUNTS_PER_REVOLUTION = 85.75;
	public static final double INCHES_PER_COUNT = TIRE_CIRCUMFERENCE / COUNTS_PER_REVOLUTION;

	// Analog Sensors
	public static final int PICKUP_POT_PWM = 0;
	public static final int ULTRASONIC_PWM = 3;

	// Joysticks
	public static final int LEFT_JOYSTICK_PORT = 0;
	public static final int RIGHT_JOYSTICK_PORT = 1;
	public static final int OPERATOR_JOYSTICK_PORT = 2;
	public static final double DEADBAND = 0.2;

	// Pickup Positions
	public static final double PICKUP_BACK_LIMIT = 0.9133;
	public static final double PICKUP_ARM_UP = 0.8689;
	public static final double PICKUP_ARM_DOWN = 0.4397;
	public static final double PICKUP_RAMPS_HEIGHT = 0.5654;
	public static final double PICKUP_RAMPS_LOW = 0.4585;
	public static final double PICKUP_PORT_CULLIS = 0.451;
}
