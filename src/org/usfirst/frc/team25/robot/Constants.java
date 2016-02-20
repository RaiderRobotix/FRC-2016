package org.usfirst.frc.team25.robot;

public abstract class Constants {

	// Assorted Constants
	public static final double DRIVE_STRAIGHT_TOLERANCE = 1.0; // (In Degrees)
	public static final int PICKUP_PDP_PORT = 13; // Arm
	public static final double PICKUP_CURRENT_LIMIT = 35.0;
	public static final double GYRO_SENSITIVITY = 0.007;

	// PWMs (Control)
	public static final int PICKUP_ROLLERS_PWM = 5;
	public static final int PICKUP_ARM_PWM = 6;
	public static final int LEFT_DRIVES_PWM = 8;
	public static final int RIGHT_DRIVES_PWM = 0;
	public static final int LEFT_BRAKE_PWM = 9;
	public static final int RIGHT_BRAKE_PWM = 1;
	public static final int HANGER_WINCH_PWM = 7; // TODO: Fix

	// Brake Positions
	public static final double LEFT_BRAKES_ON = 0.42;
	public static final double LEFT_BRAKES_OFF = 0.57;
	public static final double RIGHT_BRAKES_ON = 0.55;
	public static final double RIGHT_BRAKES_OFF = 0.34;

	// Digital Sensors
	public static final int PICKUP_LINE_BREAKER_PWM = 0;
	public static final int LEFT_ENCODER_PWM_A = 9;
	public static final int LEFT_ENCODER_PWM_B = 4;
	public static final int RIGHT_ENCODER_PWM_A = 6;
	public static final int RIGHT_ENCODER_PWM_B = 7;

	private static final double TIRE_CIRCUMFERENCE = 28.117254; // In inches
	private static final double COUNTS_PER_REVOLUTION = 85.75;
	public static final double INCHES_PER_COUNT = TIRE_CIRCUMFERENCE / COUNTS_PER_REVOLUTION;

	// Analog Sensors
	public static final int PICKUP_POT_PWM = 0;
	public static final int GYRO_PWM = 1;

	// Joysticks
	public static final int LEFT_JOYSTICK_PORT = 0;
	public static final int RIGHT_JOYSTICK_PORT = 1;
	public static final int OPERATOR_JOYSTICK_PORT = 2;
	public static final double DEADBAND = 0.2;

	// Relays
	public static final int GREEN_SPIKE_PWM = 0; // TODO: Fix both of these.
	public static final int RED_BLUE_SPIKE_PWM = 0;

	// Pickup Positions
	public static final double PICKUP_BACK_LIMIT = 0.9348;
	public static final double PICKUP_ARM_UP = 0.9289;
	public static final double PICKUP_ARM_DOWN = 0.7722;
	public static final double PICKUP_PORT_CULLIS = 0.7759;
	public static final double PICKUP_PORT_CULLIS_HIGH = 0.9002;
	public static final double PICKUP_RAMPS_HEIGHT = 0.7758;
}
