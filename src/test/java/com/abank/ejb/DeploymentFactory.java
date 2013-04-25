package com.abank.ejb;

import com.abank.common.Credentials;
import com.abank.common.exceptions.BankException;
import com.abank.data.dao.AccountDaoIf;
import com.abank.data.dao.impl.AccountDao;
import com.abank.data.domain.Account;
import com.abank.data.helper.AccountFactory;
import com.abank.data.producers.Login;
import com.abank.jsf.ModifyCustomerBean;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * Created by: gruppd, 12.03.13 19:30
 */
public class DeploymentFactory {

    public static WebArchive createDeployment() {

        WebArchive war = ShrinkWrap.create(WebArchive.class, "myTest.war");
        war.addPackages(
                true,
                BankException.class.getPackage(),
                Credentials.class.getPackage(),
                AccountDao.class.getPackage(),
                AccountDaoIf.class.getPackage(),
                Account.class.getPackage(),
                AccountFactory.class.getPackage(),
                Login.class.getPackage(),
                LoginService.class.getPackage(),
                ModifyCustomerBean.class.getPackage()
        );
        war.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
        war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        // System.out.println("WAR module\n-------------");
        // System.out.println(war.as(WebArchive.class).toString(true));
        // war.as(ZipExporter.class).exportTo(new
        // File("c:/Daten/as24BillingTest.war"), true);

        return war;
    }

}