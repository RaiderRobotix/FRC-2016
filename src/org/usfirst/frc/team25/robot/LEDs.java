package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.I2C;

public class LEDs {

	private static LEDs m_instance;

	private final I2C m_i2c;
	private byte[] m_dataToSend;

	private LEDs() {
		m_i2c = new I2C(I2C.Port.kOnboard, Constants.I2C_DEVICE_ADDRESS);
		m_dataToSend = new byte[] { 0, 1 };
		update(0);
	}

	public static LEDs getInstance() {
		if (m_instance == null) {
			m_instance = new LEDs();
		}
		return m_instance;
	}

	public void update(int sequence) {
		byte seq = (byte) sequence;
		if (seq != m_dataToSend[1]) {
			m_dataToSend[1] = seq;
			m_dataToSend[0] = (byte) (DriverStation.getInstance().getAlliance().equals(Alliance.Red) ? 0 : 1);
			m_i2c.writeBulk(m_dataToSend);
		}
	}

}
