package fi.ipsc_result_server.controller.result;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/results/overall")
public class OverAllResultsController {
	@RequestMapping(method = RequestMethod.GET)
	public String getOverallResultsPage() {
		return "results/overallResults";
	}
}
