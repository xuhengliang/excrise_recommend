package exam.controller.student;

import exam.model.Knowledge;
import exam.model.page.PageBean;
import exam.service.KnowledgeService;
import exam.util.DataUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 学生操作知识点模块 只能拉去列表以及知识点推荐习题
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller("exam.controller.student.KnowledgeController")
@RequestMapping("/student/knowledge")
public class KnowledgeController {

    @Resource
    private KnowledgeService knowledgeService;
    @Value("#{properties['knowledge.pageSize']}")
    private int pageSize;
    @Value("#{properties['knowledge.pageNumber']}")
    private int pageNumber;

    /**
     * 知识点列表
     * @param pn
     * @param search
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(String pn, String search, Model model) {
        int pageCode = DataUtil.getPageCode(pn);
        String where = null;
        if(DataUtil.isValid(search)) {
            where = " where knowledge_name like '%" + search + "%'";
        }
        PageBean<Knowledge> pageBean = knowledgeService.pageSearch(pageCode, pageSize, pageNumber, where, null, null);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("search", search);
        return "student/knowledge_list";
    }

}
