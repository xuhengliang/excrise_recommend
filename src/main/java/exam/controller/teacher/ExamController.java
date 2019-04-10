package exam.controller.teacher;

import exam.dto.StatisticsData;
import exam.dto.StudentReport;
import exam.model.Exam;
import exam.model.page.PageBean;
import exam.model.role.Teacher;
import exam.service.ExamService;
import exam.service.ExaminationResultService;
import exam.util.DataUtil;
import exam.util.ExcelUtil;
import exam.util.JFreechartUtil;
import exam.util.StringUtil;
import exam.util.json.JSON;
import exam.util.json.JSONArray;
import exam.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * 老师试卷操作模块
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller("exam.controller.teacher.ExamController")
@RequestMapping("/teacher/exam")
public class ExamController {

	@Resource
	private ExamService examService;
	@Resource
	private ExaminationResultService examinationResultService;
	@Value("#{properties['exam.pageSize']}")
	private int pageSize;
	@Value("#{properties['exam.pageNumber']}")
	private int pageNumber;

	/**
	 * 试卷列表
	 * @param pn
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String list(String pn, Model model, HttpServletRequest request) {
		int pageCode = DataUtil.getPageCode(pn);
		String tid = ((Teacher) request.getSession().getAttribute("teacher")).getId();
		PageBean<Exam> pageBean = examService.pageSearchByTeacher(pageCode, pageSize, pageNumber, tid);
		model.addAttribute("pageBean", pageBean);
		return "teacher/exam_list";
	}

	/**
	 * 跳转到试卷添加页面
	 * @return
	 */
	@RequestMapping("/add")
	public String addUI() {
		return "teacher/exam_add";
	}

	/**
	 * 保存试卷信息
	 * @param exam
	 * @param request
	 * @param response
	 */
	@RequestMapping("/save")
	@ResponseBody
	public void add(String exam, HttpServletRequest request, HttpServletResponse response) {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		JSON json = new JSONObject();
		Exam result = DataUtil.parseExam(exam, teacher);
		if (result.getPoints() == 0) {
			json.addElement("result", "0").addElement("message", "请不要提交空试卷!");
		} else {
			examService.saveOrUpdate(result);
			json.addElement("result", "1");
		}
		DataUtil.writeJSON(json, response);
	}

	/**
	 * 删除考试
	 * @param examId
	 * @param response
	 */
    @RequestMapping("/remove")
    @ResponseBody
    public void delete(Integer examId, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        if (!DataUtil.isValid(examId)) {
            json.addElement("result", "0");
        } else {
            examService.delete(examId);
            json.addElement("result", "1");
        }
        DataUtil.writeJSON(json, response);
    }

	/**
	 * 操作试卷开启和关闭
	 * @param examId
	 * @param status
	 * @param days
	 * @param response
	 */
    @RequestMapping("/status")
    @ResponseBody
    public void switchStatus(Integer examId, String status, Integer days, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        if (!DataUtil.isValid(examId) || !DataUtil.isValid(status)) {
            json.addElement("result", "0");
        } else {
            examService.switchStatus(examId, status, days);
            json.addElement("result", "1");
        }
        DataUtil.writeJSON(json, response);
    }

	/**
	 * 统计考试情况
	 * @param eid
	 * @param model
	 * @return
	 */
    @RequestMapping("/statistics/{eid}")
    public String toStatistics(@PathVariable Integer eid, Model model) {
    	model.addAttribute("eid", eid);
    	return "teacher/statistics";
    }

	/**
	 * 表盘
	 * @param eid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
    @RequestMapping("/statistics/do/{eid}")
    @ResponseBody
    public void statistics(@PathVariable Integer eid, HttpServletRequest request, HttpServletResponse response) throws IOException {
    	JSONObject json = new JSONObject();
    	StatisticsData data = examinationResultService.getStatisticsData(eid);
    	if (data == null) {
    		json.addElement("result", "0");
    	} else {
	    	String realPath = request.getServletContext().getRealPath("/") + "/";
	    	checkPath(realPath + "charts");
	    	String imagePath = "charts/pie_" + eid + ".png";
	 		JFreechartUtil.generateChart(data, realPath + imagePath);
	 		json.addElement("result", "1").addElement("url", imagePath).addElement("highestPoint", String.valueOf(data.getHighestPoint()))
	 			.addElement("lowestPoint", String.valueOf(data.getLowestPoint())).addElement("title", data.getTitle()).addElement("count", String.valueOf(data.getPersonCount()));
	 		JSONArray highestNames = new JSONArray();
	 		for (String name : data.getHighestNames()) {
	 			highestNames.addElement("name", name);
	 		}
	 		json.addElement("highestNames", highestNames);
	 		JSONArray lowestNames = new JSONArray();
	 		for (String name : data.getLowestNames()) {
	 			lowestNames.addElement("name", name);
	 		}
	 		json.addElement("lowestNames", lowestNames);
    	}
 		DataUtil.writeJSON(json, response);
    }

	/**
	 * 下载
	 * @param eid
	 * @param model
	 * @return
	 */
    @RequestMapping("/download/{eid}")
    public String download(@PathVariable Integer eid, Model model) {
    	model.addAttribute("eid", eid);
    	return "teacher/download";
    }

	/**
	 * 成绩单.xls
	 * @param eid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
    @RequestMapping("/report/{eid}")
    @ResponseBody
    public void report(@PathVariable("eid") Integer eid, HttpServletRequest request, HttpServletResponse response) throws IOException {
    	List<StudentReport> reportData = examinationResultService.getReportData(eid);
    	String realPath = request.getServletContext().getRealPath("/") + "/reports";
    	checkPath(realPath);
    	InputStream is = ExcelUtil.generateExcel(reportData, realPath + "/report_" + eid + ".xls");
    	response.setContentType("application/zip");
    	String fileName = URLEncoder.encode(reportData.get(0).getTitle() + "成绩单.xls", "UTF-8");
    	response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    	OutputStream os = response.getOutputStream();
    	byte[] b = new byte[1024];
    	int i = 0;
    	while ((i = is.read(b)) > 0) {
    		os.write(b, 0, i);
    	}
    	os.flush();
    	is.close();
    	os.close();
    }

	/**
	 * 编辑试卷信息
	 * @param eid
	 * @param title
	 * @param limit
	 * @param response
	 */
    @RequestMapping("/update/{eid}")
    @ResponseBody
    public void update(@PathVariable Integer eid, String title, Integer limit, HttpServletResponse response) {
    	JSONObject json = new JSONObject();
    	title = StringUtil.htmlEncode(title);
    	if (!DataUtil.isValid(eid, limit) || !DataUtil.isValid(title)) {
    		json.addElement("result", "0");
    	} else  {
    		Exam exam = new Exam();
    		exam.setId(eid);
    		exam.setLimit(limit);
    		exam.setTitle(title);
    		examService.saveOrUpdate(exam);
    		json.addElement("result", "1");
    	}
    	DataUtil.writeJSON(json, response);
    }

    private void checkPath(String path) {
    	File file = new File(path);
    	if (!file.exists())
    		file.mkdirs();
    }

}
