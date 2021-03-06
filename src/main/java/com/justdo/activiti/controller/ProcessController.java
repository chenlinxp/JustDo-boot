package com.justdo.activiti.controller;

import com.justdo.activiti.service.ProcessService;
import com.justdo.activiti.vo.ProcessVO;
import com.justdo.common.controller.BaseController;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.R;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

@RequestMapping("/activiti/process")
@Controller
public class ProcessController extends BaseController{

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessService processService;

    @Autowired
    private RuntimeService runtimeService;

    @GetMapping()
    @RequiresPermissions("activiti:process:list")
    String process() {
        return "activiti/process/process";
    }

    @GetMapping("/list")
    @ResponseBody
    @RequiresPermissions("activiti:process:list")
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

    @GetMapping("/add")
    @RequiresPermissions("activiti:process:add")
    public String add() {
        return "activiti/process/add";
    }

    @PostMapping("/save")
    @ResponseBody
    @RequiresPermissions("activiti:process:add")
    @Transactional(readOnly = false)
    public R deploy(String exportDir, String category, MultipartFile file) {

        String message = "";
        String fileName = file.getOriginalFilename();
        try {
            InputStream fileInputStream = file.getInputStream();
            Deployment deployment = null;
            String extension = FilenameUtils.getExtension(fileName);
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
            } else if (extension.equals("png")) {
                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            } else if (fileName.indexOf("bpmn20.xml") != -1) {
                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            } else if (extension.equals("bpmn")) { // bpmn扩展名特殊处理，转换为bpmn20.xml
                String baseName = FilenameUtils.getBaseName(fileName);
                deployment = repositoryService.createDeployment().addInputStream(baseName + ".bpmn20.xml", fileInputStream).deploy();
            } else {
                message = "不支持的文件类型：" + extension;
            }

            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();

            // 设置流程分类
            for (ProcessDefinition processDefinition : list) {
//					ActUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir);
                repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);
                message += "部署成功，流程ID=" + processDefinition.getId() + "<br/>";
            }

            if (list.size() == 0) {
                message = "部署失败，没有流程。";
            }

        } catch (Exception e) {
            throw new ActivitiException("部署失败！", e);
        }
        return R.ok(message);
    }

    /**
     * 将部署的流程转换为模型
     *
     * @param procDefId
     * @param redirectAttributes
     * @return
     * @throws UnsupportedEncodingException
     * @throws XMLStreamException
     */
    @GetMapping(value = "/convertToModel/{procDefId}")
    @ResponseBody
    @RequiresPermissions("activiti:process:convertToModel")
    public R convertToModel(@PathVariable("procDefId") String procDefId, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException, XMLStreamException {

        org.activiti.engine.repository.Model modelData = null;
        try {
            modelData = processService.convertToModel(procDefId);
            return R.ok( "转换模型成功，模型ID=" + modelData.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.ok( "转换模型失败");
        }

    }

    @GetMapping(value = "/resource/read/{xml}/{id}")
    public void resourceRead(@PathVariable("xml") String resType, @PathVariable("id") String id, HttpServletResponse response) throws Exception {
        InputStream resourceAsStream = processService.resourceRead(id,resType);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    @PostMapping("/del")
    @ResponseBody
    @RequiresPermissions("activiti:process:del")
    public R remove(String id){

        repositoryService.deleteDeployment(id,true);
        return R.ok();
    }

    @PostMapping("/batchDel")
    @ResponseBody
    @RequiresPermissions("activiti:process:batchDel")
    public R batchDel(@RequestParam("ids[]") String[] ids) {

        for (String id : ids) {
            repositoryService.deleteDeployment(id,true);
        }
        return R.ok();
    }
}
