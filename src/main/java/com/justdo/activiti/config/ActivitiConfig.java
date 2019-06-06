package com.justdo.activiti.config;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
@Configuration
public class ActivitiConfig {
    //流程配置，与spring整合采用SpringProcessEngineConfiguration这个实现
    @Bean
    public ProcessEngineConfiguration processEngineConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager){
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        processEngineConfiguration.setDataSource(dataSource);
        processEngineConfiguration.setDatabaseSchemaUpdate("true");
        processEngineConfiguration.setDatabaseType("mysql");

        processEngineConfiguration.setTransactionManager(transactionManager);

        //流程图字体
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");

        return processEngineConfiguration;
    }

    //流程引擎，与spring整合使用factoryBean
    @Bean
    public ProcessEngineFactoryBean processEngine(ProcessEngineConfiguration processEngineConfiguration){
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        return processEngineFactoryBean;
    }

    //八大接口

    /**
     * Activiti 中每一个不同版本的业务流程的定义都需要使用一些定义文件，部署文件和支持数据 ( 例如 BPMN2.0 XML 文件，表单定义文件，流程定义图像文件等 )，
     * 这些文件都存储在 Activiti 内建的 Repository 中。Repository Service 提供了对 repository 的存取服务。
     * @param processEngine
     * @return
     */
    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine){
        return processEngine.getRepositoryService();
    }

    /**
     * 在 Activiti 中，每当一个流程定义被启动一次之后，都会生成一个相应的流程对象实例。
     * Runtime Service 提供了启动流程、查询流程实例、设置获取流程实例变量等功能。
     * 此外它还提供了对流程部署，流程定义和流程实例的存取服务。
     * @param processEngine
     * @return
     */
    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine){
        return processEngine.getRuntimeService();
    }

    /**
     * 在 Activiti 中业务流程定义中的每一个执行节点被称为一个 Task，对流程中的数据存取，状态变更等操作均需要在 Task 中完成。
     * Task Service 提供了对用户 Task 和 Form 相关的操作。
     * 它提供了运行时任务查询、领取、完成、删除以及变量设置等功能。
     * @param processEngine
     * @return
     */
    @Bean
    public TaskService taskService(ProcessEngine processEngine){
        return processEngine.getTaskService();
    }

    /**
     * History Service 用于获取正在运行或已经完成的流程实例的信息，
     * 与 Runtime Service 中获取的流程信息不同，历史信息包含已经持久化存储的永久信息，并已经被针对查询优化。
     * @param processEngine
     * @return
     */
    @Bean
    public HistoryService historyService(ProcessEngine processEngine){
        return processEngine.getHistoryService();
    }

    /**
     * Activiti 中的流程和状态 Task 均可以关联业务相关的数据。
     * 通过使用 Form Service 可以存取启动和完成任务所需的表单数据并且根据需要来渲染表单。
     * @param processEngine
     * @return
     */
    @Bean
    public FormService formService(ProcessEngine processEngine){
        return processEngine.getFormService();
    }

    /**
     * Activiti 中内置了用户以及组管理的功能，必须使用这些用户和组的信息才能获取到相应的 Task。
     * Identity Service 提供了对 Activiti 系统中的用户和组的管理功能。
     * @param processEngine
     * @return
     */
    @Bean
    public IdentityService identityService(ProcessEngine processEngine){
        return processEngine.getIdentityService();
    }

    /**
     * Management Service 提供了对 Activiti 流程引擎的管理和维护功能，这些功能不在工作流驱动的应用程序中使用，主要用于 Activiti 系统的日常维护。
     * @param processEngine
     * @return
     */
    @Bean
    public ManagementService managementService(ProcessEngine processEngine){
        return processEngine.getManagementService();
    }

    @Bean
    public DynamicBpmnService dynamicBpmnService(ProcessEngine processEngine){
        return processEngine.getDynamicBpmnService();
    }

    //八大接口 end
}
