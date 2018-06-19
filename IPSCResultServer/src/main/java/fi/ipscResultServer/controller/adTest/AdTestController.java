package fi.ipscResultServer.controller.adTest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.ipscResultServer.domain.AdTest;

@Controller
@RequestMapping("/adtest")
@SessionAttributes("adtest")
public class AdTestController {
	@RequestMapping(method = RequestMethod.GET)
	public String getAdTestPage(Model model) {
		AdTest test = new AdTest();
		test.setAdTest(true);
		model.addAttribute("adtest", test);
		
		return "redirect:/";
	}
	@RequestMapping(value="/clear", method = RequestMethod.GET)
	public String clearAdTest(Model model) {
		AdTest test = new AdTest();
		test.setAdTest(false);
		model.addAttribute("adtest", test);
		
		return "redirect:/";
	}
}
