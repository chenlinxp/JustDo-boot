package com.justdo.activiti.controller;

import com.justdo.activiti.service.ActTaskService;
import com.justdo.activiti.vo.ProcessVO;
import com.justdo.activiti.vo.TaskVO;
import com.justdo.common.controller.BaseController;
import com.justdo.common.utils.PageUtils;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**

 */
@RequestMapping("activiti/task")
@Controller
public class TaskController  extends BaseController {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    FormService formService;
    @Autowired
    TaskService taskService;
    @Autowired
    ActTaskService actTaskService;
    @Autowired
    HistoryService processEngine;

    /**
     * 可以发起的流程任务列表页面
     * @return
     */
    @GetMapping("/goto")
    @RequiresPermissions("activiti:task:goto")
    public String gotoTask(){
        return "activiti/task/gotoTask";
    }



    /**
     * 可以发起的流程任务列表
     * @param offset
     * @param limit
     * @return
     */
    @GetMapping("/gotoList")
    @ResponseBody
    @RequiresPermissions("activiti:task:goto")
    PageUtils list(int offset, int limit) {
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .listPage(offset, limit);
        int count = (int) repositoryService.createProcessDefinitionQuery().count();
        List<Object> list = new ArrayList<>();
        for(ProcessDefinition processDefinition: processDefinitions){
            list.add(new ProcessVO(processDefinition));
        }
        PageUtils pageUtils = new PageUtils(list, count);
        return pageUtils;
    }

    /**
     *
     * 发起任务
     * @param procDefId
     * @param response
     * @throws IOException
     */
    @GetMapping("/start/{procDefId}")
    @RequiresPermissions("activiti:task:startTask")
    public void startForm(@PathVariable("procDefId") String procDefId  ,HttpServletResponse response) throws IOException {
        String formKey = actTaskService.getFormKey(procDefId, null);
        response.sendRedirect(formKey);
    }

    /**
     * 审批任务
     * @param procDefId
     * @param taskId
     * @param response
     * @throws IOException
     */
    @GetMapping("/handle/{procDefId}/{taskId}")
    @RequiresPermissions("activiti:task:handleTask")
    public void form(@PathVariable("procDefId") String procDefId,@PathVariable("taskId") String taskId ,HttpServletResponse response) throws IOException {

        // 获取流程XML上的表单KEY

        String formKey = actTaskService.getFormKey(procDefId, taskId);


        response.sendRedirect(formKey+"/"+taskId);
    }

    /**
     * 待办的任务列表页面
     * @return
     */
    @GetMapping("/todo")
    @RequiresPermissions("activiti:task:todo")
    String todo(){
        return "activiti/task/todoTask";
    }

    /**
     * 待办的任务列表
     * @return
     */
    @GetMapping("/todoList")
    @ResponseBody
    @RequiresPermissions("activiti:task:todo")
    List<TaskVO> todoList(){
        String employeename = getEmployeename();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(employeename).list();
        List<TaskVO> taskVOS =  new ArrayList<>();
        for(Task task : tasks){
            TaskVO taskVO = new TaskVO(task);
            taskVOS.add(taskVO);
        }
        return taskVOS;
    }

    /**
     * 已办的任务列表页面
     * @return
     */
    @GetMapping("/done")
    @RequiresPermissions("activiti:task:done")
    String done(){
        return "activiti/task/doneTask";
    }

    /**
     * 已办的任务列表
     * @return
     */
    @GetMapping("/doneList")
    @ResponseBody
    @RequiresPermissions("activiti:task:done")
    List<TaskVO> doneList() {
        String employeename = getEmployeename();
        List<TaskVO> taskVOS = new ArrayList<>();
        List<HistoricTaskInstance> list = processEngine.createHistoricTaskInstanceQuery().finished().list();
        for (HistoricTaskInstance hti : list) {
           if(hti.getAssignee().equals(employeename)){
               TaskVO taskVO = new TaskVO(hti);
               taskVOS.add(taskVO);
           }
        }
        return taskVOS;

//
//        List<HistoricTaskInstance> list = processEngine.createHistoricTaskInstanceQuery().finished().listPage(offset, limit);
//        int count = (int) repositoryService.createProcessDefinitionQuery().count();
//        List<Object> list = new ArrayList<>();
//        for(ProcessDefinition processDefinition: processDefinitions){
//            list.add(new ProcessVO(processDefinition));
//        }
//        PageUtils pageUtils = new PageUtils(list, count);
//        return pageUtils;
    }
    /**
     * 跟踪的图片
     * @param procDefId
     * @param execId
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/trace/photo/{procDefId}/{execId}")
    @RequiresPermissions("activiti:task:trace")
    public void tracePhoto(@PathVariable("procDefId") String procDefId, @PathVariable("execId") String execId, HttpServletResponse response) throws Exception {
        InputStream imageStream = actTaskService.tracePhoto(procDefId, execId);

        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len;
        while ((len = imageStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }


}
