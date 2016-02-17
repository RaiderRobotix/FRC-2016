package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.VictorSP;

public class Hanger {

	private static Hanger m_instance;
	private final VictorSP m_winch;

	private Hanger() {
		m_winch = new VictorSP(Constants.HANGER_WINCH_PWM);
	}

	public static Hanger getInstance() {
		if (m_instance == null) {
			m_instance = new Hanger();
		}
		return m_instance;
	}

	public void setSpeed(double speed) {
		m_winch.set(speed);
	}
	
}
