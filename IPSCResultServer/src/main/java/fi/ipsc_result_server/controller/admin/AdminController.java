package fi.ipsc_result_server.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@RequestMapping(method = RequestMethod.GET)
	public String getAdminMainPage() {
		return "admin/adminMainPage";
	}

}
