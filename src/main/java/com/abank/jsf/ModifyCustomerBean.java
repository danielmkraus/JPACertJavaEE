package com.abank.jsf;

import com.abank.data.domain.Customer;
import com.abank.ejb.ModifyCustomerStateHolder;
import org.apache.log4j.Logger;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * This class serves as a backing bean for customer modifications.
 * It uses a stateful session bean with an extended persistence context
 * for retrieval and storing of an existing customer until a modification
 * is written back into the database.
 */
@ConversationScoped
@Named("modifyCustomer")
public class ModifyCustomerBean implements Serializable {

    private static final long serialVersionUID = -4074257382291097307L;

    final static Logger logger = Logger.getLogger(ModifyCustomerBean.class);

    @Inject
    Conversation conversation;

    @Inject
    ModifyCustomerStateHolder customerStateHolder;

    private Customer customer = null;
    private String firstname = null;
    private String lastname = null;
    private Calendar birthDate = null;

    public String edit() {
        return "editCustomer";
    }

    /**
     * Find and set the customer
     *
     * @return navigation point to display the customer for edit confirmation
     */
    public synchronized String submit() {
        if (conversation.isTransient()) {
            conversation.begin();
            checkConversation("Started");
            initializeCustomerStateHolder();
        }
        customer = customerStateHolder.findCustomer(firstname, lastname, birthDate);
        // since Customer.birthDate is Calendar typed but for JSF we need Date
        if (null != customer) {
            this.birthDate = customer.getBirthDate();
        }
        // if no customer was found terminate the conversation again
        if (null == customer) {
            if (!conversation.isTransient()) {
                checkConversation("Terminating");
                conversation.end();
            }
        }
        return "selectCustomer";
    }

    public synchronized String save() {
        logger.debug("Submitting order");
        customerStateHolder.mergeCustomer(customer);
        removeCustomerStateHolder();
        if (!conversation.isTransient()) {
            checkConversation("Terminating");
            conversation.end();
        }
        return "navigationOptions";
    }

    public String cancel() {
        logger.debug("Cancelling order");
        customerStateHolder.cancel();
        removeCustomerStateHolder();
        if (!conversation.isTransient()) {
            checkConversation("Terminating");
            conversation.end();
        }
        return "navigationOptions";
    }

    /**
     * Method to add the customerStateHolder to the HttpSession.
     * Since the HttpSession object is not thread-safe it is being synchronized.
     *
     * @return
     */
    private synchronized void initializeCustomerStateHolder() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("customerStateHolder", customerStateHolder);
        logger.info("customerStateHolder initialized and attached to HttpSession object");
    }

    /**
     * Method to remove the customerStateHolder from the HttpSession.
     * Since the HttpSession object is not thread-safe it is being synchronized.
     *
     * @return
     */
    private synchronized void removeCustomerStateHolder() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.removeAttribute("customerStateHolder");
        customerStateHolder = null;
        logger.info("customerStateHolder removed from HttpSession object and set to null");

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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthDate() {
        if (null != birthDate) {
            logger.debug("Returning date " + birthDate.getTime().toString());
            return birthDate.getTime();
        }
        return null;
    }

    public void setBirthDate(Date birthdate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthdate);
        this.birthDate = calendar;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
