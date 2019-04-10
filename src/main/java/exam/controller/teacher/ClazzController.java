package exam.controller.teacher;

import exam.model.Clazz;
import exam.service.ClazzService;
import exam.util.DataUtil;
import exam.util.json.JSONArray;
import exam.util.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 老师班级操作模块
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller("exam.controller.teacher.ClazzController")
@RequestMapping("/teacher/clazz")
public class ClazzController {

    @Resource
    private ClazzService clazzService;

    /**
     * 班级列表
     * @param examId
     * @param response
     */
    @RequestMapping("/list")
    @ResponseBody
    public void list(Integer examId, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        if (!DataUtil.isValid(examId)) {
            json.addElement("result", "0").addElement("message", "参数无效");
        } else {
            JSONArray array = new JSONArray();
            List<Clazz> clazzs = clazzService.findByExam(examId);
            for (Clazz clazz : clazzs) {
                array.addObject(clazz.getJSON());
            }
            json.addElement("result", "1").addElement("data", array);
        }
        DataUtil.writeJSON(json, response);
    }

    /**
     * 重置
     * @param examId
     * @param clazzIds
     * @param response
     */
    @RequestMapping("/reset")
    @ResponseBody
    public void reset(Integer examId, String clazzIds, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        if (!DataUtil.isValid(examId) || !DataUtil.isValid(clazzIds)) {
            json.addElement("result", "0");
        } else {
            clazzService.resetExamClass(examId, clazzIds);
            json.addElement("result", "1");
        }
        DataUtil.writeJSON(json, response);
    }

}
