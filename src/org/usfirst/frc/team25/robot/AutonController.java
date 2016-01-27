package org.usfirst.frc.team25.robot;

public class AutonController {

	private static AutonController m_instance;
	private int m_step;
	private final Drivebase m_drives;
	
	public AutonController() {
		m_drives = Drivebase.getInstance();
		m_step = 0;
	}
	
	public static AutonController getInstance() {
		if(m_instance == null) {
			m_instance = new AutonController();
		}
		return m_instance;
	}
	
	public void resetStep() {
		m_step = 0;
	}
	
}
