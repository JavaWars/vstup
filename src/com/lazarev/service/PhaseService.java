package com.lazarev.service;

import org.apache.log4j.Logger;

import com.lazarev.db.dao.PhaseDao;
import com.lazarev.db.entity.Phase;

public class PhaseService {
	private static final Logger LOGGER = Logger.getLogger(PhaseService.class);

	private static Phase currentPhase;
	
	private static PhaseService phaseService;
	
	private PhaseService() {
		currentPhase=new PhaseDao().getCurrentPhase();
	}
	
	public static synchronized PhaseService instance() {
		if (phaseService==null) {
			phaseService=new PhaseService();
		}
		return phaseService;
	}
	
	public static synchronized Phase getCurrentPhase() {
		return PhaseService.instance().currentPhase;
	}
	
	public synchronized void setPhase(String phaseName) {
		LOGGER.debug("setting new phase");
		new PhaseDao().setCurrentPhase(Phase.valueOf(phaseName));
		currentPhase=new PhaseDao().getCurrentPhase();
	}
	
}
