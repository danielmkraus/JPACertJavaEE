package com.abank.jsf.listeners;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.log4j.Logger;

/**
 * PhaseListener that logs a message before and after every phase.
 * 
 * @author dgrupp
 */
public class DebugPhaseListener implements PhaseListener {
	
	private static final long serialVersionUID = -3694607448688124472L;
	
	final static Logger logger = Logger.getLogger(DebugPhaseListener.class);

    public void afterPhase(PhaseEvent event) {
    	logger.debug("After phase: " + event.getPhaseId());
	}

	public void beforePhase(PhaseEvent event) {
		logger.debug("Before phase: " + event.getPhaseId());
	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
