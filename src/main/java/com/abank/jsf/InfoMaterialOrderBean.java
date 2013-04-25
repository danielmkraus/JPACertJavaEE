package com.abank.jsf;

import com.abank.data.dao.InfoMaterialDaoIf;
import com.abank.data.domain.InfoMaterial;
import com.abank.data.domain.Systemuser;
import com.abank.data.producers.LoggedIn;
import com.abank.ejb.InfoMatOrderCreator;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Backing bean for orders of info material (brochures, etc.)
 * using an own data type (class) for displaying the jsf table
 * 
 * Created by: gruppd, 09.03.13 20:38
 */
@Named("infoMaterialOrderBean")
@ConversationScoped
public class InfoMaterialOrderBean implements Serializable {

	final static Logger logger = Logger.getLogger(InfoMaterialOrderBean.class);

	private static final long serialVersionUID = 4961548908512317407L;

	@Inject
	InfoMaterialDaoIf infoDao;

	@Inject
	@LoggedIn
	Systemuser systemUser;

	@Inject
	Conversation conversation;

	@Inject
	InfoMatOrderCreator orderCreator;

	@Inject
	private InfoMaterialOrderCart infoMaterialOrderCart;

	private List<InfoMaterialOrderTableEntry> tableEntryList = null;

	/**
	 * Initialize the values displayed in the GUI
	 */
	@PostConstruct
	public void postConstruct() {
		logger.debug("InfoMaterialOrderBean object created");
        tableEntryList = infoDao.findAllTableEntry();
		initializeCart();
	}
	
	@PreDestroy
	public void preDestroy() {
		logger.debug("InfoMaterialOrderBean object destroyed");
	}

	/**
	 * getter for the list of available table entries
	 * 
	 * @return
	 */
	public List<InfoMaterialOrderTableEntry> getTableEntryList() {
		return tableEntryList;
	}

	/**
	 * getter for the products so far in the trolley
	 * 
	 * @return
	 */
	public List<InfoMaterialOrderTableEntry> getCartContent() {
		List<InfoMaterialOrderTableEntry> cartContentList = new ArrayList<InfoMaterialOrderTableEntry>();
		if (null != infoMaterialOrderCart) {
			for (Map.Entry<InfoMaterial, Integer> entry : infoMaterialOrderCart.getInfoMaterialMap().entrySet()) {
                InfoMaterialOrderTableEntry tableEntry = new InfoMaterialOrderTableEntry(entry.getKey(), entry.getValue());
				cartContentList.add(tableEntry);
			}
		}
		return cartContentList;
	}
	
	/**
	 * Method to trigger the changes in the trolley.
	 * Since the infoMaterialOrderCart object is not thread-safe it is being synchronized.
	 * 
	 * @param tableEntryJsf
	 * @return a String leading back to the selection page
	 */
	public synchronized String updateOrder(InfoMaterialOrderTableEntry tableEntryJsf) {
		if (conversation.isTransient()) {
			conversation.begin();
			checkConversation("Started");
		}
		InfoMaterial infoMaterial = new InfoMaterial(tableEntryJsf.getInfoMaterial());
		Integer quantity = new Integer(tableEntryJsf.getQuantity());
		logger.debug("Change infoMaterialOrderCart content: " + infoMaterial + " and quantity " + quantity);
		if (quantity == 0) {
			return "infoMaterial";
		}
		infoMaterialOrderCart.updateCart(infoMaterial, quantity);
		// reset quantity for display in the GUI
		tableEntryJsf.setQuantity(0);

		return "infoMaterial";
	}

	/**
	 * Method to implement the cancel behavior
	 */
	public String cancel() {
		logger.debug("Cancelling order");
		removeCart();
		if (!conversation.isTransient()) {
			checkConversation("Terminating");
			conversation.end();
		}
        return "navigationOptions";
	}

	/**
	 * Method to implement the submit behavior
	 */
	public String submit() {
		logger.debug("Submitting order");
		if (null == systemUser) {
			return "start";
		}
		orderCreator.createOrdersNew(getCartContent());
		removeCart();
		if (!conversation.isTransient()) {
			checkConversation("Terminating");
			conversation.end();
		}
		return null;
	}

	/**
	 * Method to add the infoMaterialOrderCart to the HttpSession.
	 * Since the HttpSession object is not thread-safe it is being synchronized.
	 * 
	 * @return
	 */
	private synchronized void initializeCart() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		session.setAttribute("infoMaterialOrderCart", infoMaterialOrderCart);
		logger.info("InfoMaterialOrderCart initialized and attached to HttpSession object");
	}

	/**
	 * Method to remove the infoMaterialOrderCart from the HttpSession.
	 * Since the HttpSession object is not thread-safe it is being synchronized.
	 * 
	 * @return
	 */
	private synchronized void removeCart() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		session.removeAttribute("infoMaterialOrderCart");
		infoMaterialOrderCart = null;
		logger.info("InfoMaterialOrderCart removed from HttpSession object and set to null");

	}

	/**
	 * Just a helper method
	 */
	public void checkConversation(String action) {
		if (conversation == null) {
			logger.debug("conversation is null");
		} else {
			logger.debug(action + " conversation (id=" + conversation.getId() + ", isTransient=" + conversation.isTransient() + ", timeoutValue="
					+ conversation.getTimeout() + ")");
		}
	}

}
