package com.abank.jsf.listeners;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.log4j.Logger;

/**
 * PhaseListener that logs request parameters before restore view phase.
 * 
 * @author dgrupp
 */
public class ParameterPhaseListener implements PhaseListener {
    
	private static final long serialVersionUID = 918257274515368377L;
	
	final static Logger logger = Logger.getLogger(ParameterPhaseListener.class);

    public void afterPhase(PhaseEvent event) {
	}

	public void beforePhase(PhaseEvent event) {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> map = fc.getExternalContext().getRequestParameterMap();
		for (String key : map.keySet()) {
			StringBuilder param = new StringBuilder();
			param.append("Parameter: ");
			param.append(key);
			param.append(" = ");
			param.append(map.get(key));
			logger.debug(param.toString());
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.APPLY_REQUEST_VALUES;
	}

}
