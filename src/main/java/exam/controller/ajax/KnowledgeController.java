package exam.controller.ajax;

import exam.model.Knowledge;
import exam.model.role.Teacher;
import exam.service.KnowledgeService;
import exam.util.DataUtil;
import exam.util.json.JSONArray;
import exam.util.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ajax
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller
@RequestMapping("/knowledge")
public class KnowledgeController {
	
	@Resource
	private KnowledgeService knowledgeService;

	/**
	 * 异步拉取知识点数据
	 * @param request
	 * @param response
	 */
	@RequestMapping("/ajax")
	@ResponseBody
	public void ajax(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
        String tid = ((Teacher) request.getSession().getAttribute("teacher")).getId();
        Knowledge knowledge = new Knowledge();
        knowledge.setTid(tid);
        List<Knowledge> knowledges = knowledgeService.find(knowledge);
        json.addElement("result", "1");
        for(Knowledge value : knowledges) {
			array.addObject(value.getJSON());
		}
		json.addElement("data", array);
		DataUtil.writeJSON(json, response);
	}

	/**
	 * 已关联知识点信息
	 * @param qid
	 * @param request
	 * @param response
	 */
	@RequestMapping("/ajax/linked")
	@ResponseBody
	public void linked(String qid,HttpServletRequest request,HttpServletResponse response) {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		String tid = ((Teacher) request.getSession().getAttribute("teacher")).getId();
		List<Knowledge> knowledges = knowledgeService.findLinked(Integer.parseInt(qid),tid);
		json.addElement("result", "1");
		for(Knowledge value : knowledges) {
			array.addObject(value.getJSON());
		}
		json.addElement("data", array);
		DataUtil.writeJSON(json, response);
	}

	/**
	 * 未关联知识点信息
	 * @param qid
	 * @param request
	 * @param response
	 */
	@RequestMapping("/ajax/not-linked")
	@ResponseBody
	public void notLinked(String qid,HttpServletRequest request,HttpServletResponse response) {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		String tid = ((Teacher) request.getSession().getAttribute("teacher")).getId();
		List<Knowledge> knowledges = knowledgeService.findNotLinked(Integer.parseInt(qid),tid);
		json.addElement("result", "1");
		for(Knowledge value : knowledges) {
			array.addObject(value.getJSON());
		}
		json.addElement("data", array);
		DataUtil.writeJSON(json, response);
	}

	/**
	 * 异步移除关联
	 * @param qid
	 * @param kids
	 * @param request
	 * @param response
	 */
	@RequestMapping("/ajax/remove-link")
	@ResponseBody
	public void removeLink(String qid,int[] kids,HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String tid = ((Teacher) request.getSession().getAttribute("teacher")).getId();
		knowledgeService.removeLink(Integer.parseInt(qid),tid,kids);
		json.addElement("result", "1");
		DataUtil.writeJSON(json, response);
	}

	/**
	 * 异步添加关联
	 * @param qid
	 * @param kids
	 * @param request
	 * @param response
	 */
	@RequestMapping("/ajax/insert-link")
	@ResponseBody
	public void insertLink(String qid,int[] kids,HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String tid = ((Teacher) request.getSession().getAttribute("teacher")).getId();
		knowledgeService.insertLink(Integer.parseInt(qid),tid,kids);
		json.addElement("result", "1");
		DataUtil.writeJSON(json, response);
	}
}
