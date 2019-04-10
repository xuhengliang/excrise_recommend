package exam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller
public class ValidController {

	@RequestMapping("/valid")
	public String valid() {
		return "valid";
	}
	
}
