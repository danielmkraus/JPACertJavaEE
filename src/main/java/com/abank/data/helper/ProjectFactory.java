package com.abank.data.helper;

import com.abank.data.domain.*;
import com.abank.data.domain.enums.DepartmentNameEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: gruppd, 23.03.13 12:53
 */
public class ProjectFactory {

    private static List<Project> projectList = null;
    private static DesignProject javaProject = null;
    private static DesignProject rubyProject = null;
    private static QualityProject testProject = null;

    private ProjectFactory() {}

    public static List<Project> createProjects() {
        projectList = new ArrayList<Project>();

        javaProject = new DesignProject();
        javaProject.setName("billing engine design project");
        projectList.add(javaProject);

        rubyProject = new DesignProject();
        rubyProject.setName("ruby on rails design project");
        projectList.add(rubyProject);

        testProject = new QualityProject();
        testProject.setName("test project for billing engine");
        projectList.add(testProject);

        return projectList;
    }

}
