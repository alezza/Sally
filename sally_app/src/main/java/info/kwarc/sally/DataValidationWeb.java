package info.kwarc.sally;

import info.kwarc.sally.core.workflow.ISallyWorkflowManager;
import info.kwarc.sally.networking.TemplateEngine;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.slf4j.Logger;

import com.google.inject.Inject;

@Path("sally/datavalid")
public class DataValidationWeb {
	Logger log;
	TemplateEngine te;
	ISallyWorkflowManager kb;

	@Inject
	public DataValidationWeb(TemplateEngine te, ISallyWorkflowManager kb) {
		this.te = te;
		log = org.slf4j.LoggerFactory.getLogger(getClass());
		this.kb = kb;
	}

	@GET
	public String get(){
		HashMap<String, Object> data = new HashMap<String, Object>();
		return te.generateTemplate("validate/validate.ftl", data);
	}
}
