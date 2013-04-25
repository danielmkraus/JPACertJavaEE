package com.abank.data.helper;

import com.abank.data.dao.BranchDaoIf;
import com.abank.data.dao.impl.AccountDao;
import com.abank.data.dao.impl.DepartmentDao;
import com.abank.data.dao.impl.EmployeeDao;
import com.abank.data.dao.impl.ProjectDao;
import com.abank.data.domain.*;
import com.abank.data.domain.enums.DepartmentNameEnum;
import com.abank.data.domain.enums.JobRoleEnum;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by: gruppd, 11.02.13 20:12
 */
@Model
public class DataCreator {

    final static Logger logger = Logger.getLogger(DataCreator.class);

    // create application managed entity manager
    @PersistenceUnit(name = "JpaPU")
    private EntityManagerFactory emf;

    // no EJB, so we need manual transaction demarcation
    @Resource
    UserTransaction tx;

    @Inject
    BranchDaoIf branchDao;

    @Inject
    EmployeeDao employeeDao;

    @Inject
    AccountDao accountDao;

    @Inject
    DepartmentDao departmentDao;

    @Inject
    ProjectDao projectDao;

    private EntityManager em;

    private List<Employee> employeesITDepartment = new ArrayList<Employee>();
    private List<Employee> employeesFinanceDepartment = new ArrayList<Employee>();
    private List<Employee> employeesLegalDepartment = new ArrayList<Employee>();

    private void init() {
        em = emf.createEntityManager();
    }

    // em needs to be closed by hand, in JavaEE emf is closed automatically
    private void close() {
        if (null != em) {
            em.close();
        }
    }

    public void generateData() {
        init();
        generateBranches();
        generateProjects();
        generateCustomers();
        assignAccountsToBranches();
        generateEmployees();
        assignEmployeesToProject();
        assignBranchDepartment();
        generateInfoMaterial();
        generateEmployeesWithoutDepartment();
        determineManager(employeesITDepartment);
        determineManager(employeesLegalDepartment);
        determineManager(employeesFinanceDepartment);
        close();
    }

    public void generateBranches() {
        List<Branch> branchList = BranchFactory.createBranches();
        try {
            tx.begin();
            em.joinTransaction();
            try {
                for (Branch branch : branchList) {
                    em.persist(branch);
                }
            } finally {
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateProjects() {
        List<Project> projectList = ProjectFactory.createProjects();
        try {
            tx.begin();
            em.joinTransaction();
            try {
                for (Project project : projectList) {
                    em.persist(project);
                }
            } finally {
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateCustomers() {
        List<Customer> customerList = CustomerFactory.createCustomers(200);
        List<List<Customer>> customerListParts = DataCreatorHelper.chopped(customerList, 100);
        for (List<Customer> custList : customerListParts) {
            try {
                tx.begin();
                em.joinTransaction();
                try {
                    for (Customer customer : custList) {
                        for (int i = 0; i < DataCreatorHelper.randIntBetween(0, 3); i++) {
                            Account account = AccountFactory.generateRandomAccount();
                            account.setCustomer(customer);
                            customer.getAccounts().add(account);
                        }
                        Account account = AccountFactory.generateRandomAccount();
                        account.setCustomer(customer);
                        customer.getAccounts().add(account);
                        em.persist(customer);
                    }
                } finally {
                    tx.commit();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void assignAccountsToBranches() {
        try {
            tx.begin();
            em.joinTransaction();
            try {
                List<Branch> branchList = branchDao.findAll();
                List<Account> accountList = accountDao.findAll();
                for (Account account : accountList) {
                    Branch randomBranch = branchList.get(DataCreatorHelper.randIntBetween(0, branchList.size() - 1));
                    account.setBranch(randomBranch);
                    randomBranch.getAccounts().add(account);
                    accountDao.merge(account);
                    branchDao.merge(randomBranch);
                }
            } finally {
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateEmployees() {
        List<Employee> employeeList = EmployeeFactory.createEmployees(40);
        employeeList.add(EmployeeFactory.generateTestEmployee());
        List<Systemuser> userList = UserFactory.createUserList(employeeList);
        try {
            tx.begin();
            em.joinTransaction();
            try {
                for (Employee employee : employeeList) {
                    em.persist(employee);
                }
                for (Systemuser user : userList) {
                    em.persist(user);
                }
            } finally {
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assignBranchDepartment() {
        try {
            tx.begin();
            em.joinTransaction();
            try {
                List<Employee> employeeList = employeeDao.findAll();
                List<Branch> branchList = branchDao.findAll();
                for (Employee employee : employeeList) {
                    Branch chosenBranch = branchList.get(DataCreatorHelper.randIntBetween(0, branchList.size() - 1));
                    chosenBranch.getEmployees().add(employee);

                    List<Department> depList = chosenBranch.getDepartments();
                    Department chosenDepartment = depList.get(DataCreatorHelper.randIntBetween(0, depList.size() - 1));
                    if ((chosenDepartment.getDepartmentName().equals(DepartmentNameEnum.CASH_DEP.getStrValue())) || (chosenDepartment.getDepartmentName().equals(DepartmentNameEnum.INVEST.getStrValue()))) {
                        int random = DataCreatorHelper.randIntBetween(0, DataArray.jobroleFinance.length - 1);
                        employee.setJobRole(JobRoleEnum.strValueOf(DataArray.jobroleFinance[random]));
                        employee.setTurnover(new BigDecimal(DataCreatorHelper.randIntBetween(10000, 1000000)));
                        employeesFinanceDepartment.add(employee);
                    } else if (chosenDepartment.getDepartmentName().equals(DepartmentNameEnum.IT.getStrValue())) {
                        int random = DataCreatorHelper.randIntBetween(0, DataArray.jobroleIt.length - 1);
                        employee.setJobRole(JobRoleEnum.strValueOf(DataArray.jobroleIt[random]));
                        employeesITDepartment.add(employee);
                    } else {
                        int random = DataCreatorHelper.randIntBetween(0, DataArray.jobroleLegal.length - 1);
                        employee.setJobRole(JobRoleEnum.strValueOf(DataArray.jobroleLegal[random]));
                        employeesLegalDepartment.add(employee);
                    }
                    chosenDepartment.getEmployees().add(employee);

                    employee.setBranch(chosenBranch);
                    employee.setDepartment(chosenDepartment);
                }
            } finally {
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void determineManager(List<Employee> employees) {
        Employee manager = employees.get(DataCreatorHelper.randIntBetween(0, employees.size() - 1));
        try {
            tx.begin();
            em.joinTransaction();
            try {
                for (Employee employee : employees) {
                    if (!employee.equals(manager)) {
                        employee.setManager(manager);
                        manager.getDirects().add(employee);
                    }
                    employeeDao.merge(employee);
                }
                employeeDao.merge(manager);
            } finally {
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateEmployeesWithoutDepartment() {
        List<Employee> employeeList = EmployeeFactory.createEmployees(10);
        List<Systemuser> userList = UserFactory.createUserList(employeeList);
        try {
            tx.begin();
            em.joinTransaction();
            try {
                for (Employee employee : employeeList) {
                    em.persist(employee);
                }
                for (Systemuser user : userList) {
                    em.persist(user);
                }
            } finally {
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assignEmployeesToProject() {
        List<Employee> employeeList = null;
        List<Project> projectList = null;
        try {
            tx.begin();
            em.joinTransaction();
            try {
                employeeList = employeeDao.findAll();
                projectList = projectDao.findAll();
                // one project will have no employees being assigned
                projectList.remove(projectList.size() - 1);
                // for half of the number of employees...
                int numberEmployeesInProject = employeeList.size() - (employeeList.size() / 4);
                List<Employee> employeesToBeAssigned = new ArrayList<Employee>();
                for (int j = 0; j < numberEmployeesInProject; j++) {
                    Employee employee = employeeList.get(j);
                    employeesToBeAssigned.add(employee);
                }
                for (int i = 0; i < projectList.size(); i++) {
                    List<Employee> assignedEmployeesList = new ArrayList<Employee>();
                    Project project = projectList.get(i);
                    logger.info("Assigning project: " + project.toString());
                    if (i == 0) {
                        // for the first project assign all of the selected employees
                        for (Employee employee : employeesToBeAssigned) {
                            logger.info("Assigning employee: " + employee.toString());
                            assignedEmployeesList.add(employee);
                            //employee.getProjects().add(project);
                            //employeeDao.merge(employee);
                        }
                    } else {
                        // for the rest of the projects assign only some employees
                        for (Employee employee : employeesToBeAssigned) {
                            int randomInt = DataCreatorHelper.randIntBetween(0, 5);
                            if (randomInt == 2) {
                                logger.info("randomInt = " + randomInt + " -> assigning employee " + employee);
                                assignedEmployeesList.add(employee);
                                //employee.getProjects().add(project);
                                //employeeDao.merge(employee);
                            }
                        }
                    }
                    project.setEmployees(assignedEmployeesList);
                    projectDao.merge(project);
                }
            } finally {
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateInfoMaterial() {
        List<InfoMaterial> infoMaterialList = InfoMaterialFactory.createInfoMaterialFactoryList();
        try {
            tx.begin();
            em.joinTransaction();
            try {
                for (InfoMaterial infoMaterial : infoMaterialList) {
                    em.persist(infoMaterial);
                }
            } finally {
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
