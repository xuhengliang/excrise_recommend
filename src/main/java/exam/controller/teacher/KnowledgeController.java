package exam.controller.teacher;

import exam.model.Knowledge;
import exam.model.page.PageBean;
import exam.model.role.Teacher;
import exam.service.KnowledgeService;
import exam.util.DataUtil;
import exam.util.StringUtil;
import exam.util.json.JSON;
import exam.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller("exam.controller.teacher.KnowledgeController")
@RequestMapping("/teacher/knowledge")
public class KnowledgeController {

    @Resource
    private KnowledgeService knowledgeService;
    @Value("#{properties['knowledge.pageSize']}")
    private int pageSize;
    @Value("#{properties['knowledge.pageNumber']}")
    private int pageNumber;

    /**
     * 教师之间的各添加的知识点是隔离的
     * @param pn
     * @param search
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(String pn, String search,  HttpServletRequest request,Model model) {
        int pageCode = DataUtil.getPageCode(pn);
        String where = null;
        if(DataUtil.isValid(search)) {
            where = " where knowledge_name like '%" + search + "%'";
        }
        String tid = ((Teacher) request.getSession().getAttribute("teacher")).getId();
        if (where == null) {
            where = " where tid = " + tid ;
        } else {
            where = where + " and tid = " + tid;
        }
        PageBean<Knowledge> pageBean = knowledgeService.pageSearch(pageCode, pageSize, pageNumber, where, null, null);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("search", search);
        return "teacher/knowledge_list";
    }


    /**
     * 添加知识点
     * @param knowledgeName
     * @param pid
     * @param request
     * @param response
     */
    @RequestMapping("/add")
    @ResponseBody
    public void add(String knowledgeName,String pid,HttpServletRequest request,HttpServletResponse response) {
        JSON json = new JSONObject();
        knowledgeName = StringUtil.htmlEncode(knowledgeName);
        String tid = ((Teacher) request.getSession().getAttribute("teacher")).getId();
        if(!DataUtil.isValid(knowledgeName)) {
            json.addElement("result", "0").addElement("message", "请输入知识点名称");
        }else {
            knowledgeService.saveKnowledge(knowledgeName,Integer.parseInt(pid),tid);
            json.addElement("result", "1").addElement("message", "知识点添加成功");
        }
        DataUtil.writeJSON(json, response);
    }

    /**
     * 更新知识点不更新tid
     * @param knowledgeName
     * @param pid
     * @param response
     */
    @RequestMapping("/edit")
    @ResponseBody
    public void edit(String id,String knowledgeName, String pid,HttpServletResponse response) {
        JSONObject json = new JSONObject();
        knowledgeName = StringUtil.htmlEncode(knowledgeName);
        if(!DataUtil.isNumber(id)) {
            json.addElement("result", "0").addElement("message", "参数无效");
        }else if(!DataUtil.isValid(knowledgeName)) {
            json.addElement("result", "0").addElement("message", "请输入知识点名称");
        } else {
            knowledgeService.updateKnowledgeName(Integer.parseInt(id),knowledgeName,Integer.parseInt(pid));
            json.addElement("result", "1").addElement("message", "修改成功");
        }
        DataUtil.writeJSON(json, response);
    }

    /**
     * 删除知识点
     * @param id
     * @param response
     */
    @RequestMapping("/delete")
    @ResponseBody
    public void delete(String id, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        if(!    DataUtil.isValid(id)) {
            json.addElement("result", "0").addElement("message", "参数无效");

        }else {
            knowledgeService.delete(id);
            json.addElement("result", "1").addElement("message", "删除成功");
        }

        DataUtil.writeJSON(json, response);
    }
}
