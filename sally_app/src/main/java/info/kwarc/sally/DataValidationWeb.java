package info.kwarc.sally;

import info.kwarc.sally.core.doc.DocumentInformation;
import info.kwarc.sally.core.doc.DocumentManager;
import info.kwarc.sally.core.workflow.ISallyWorkflowManager;
import info.kwarc.sally.networking.TemplateEngine;
import info.kwarc.sally.sharejs.models.SpreadsheetModel.Sheet;
import info.kwarc.sally.spreadsheet.SpreadsheetDocument;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;

import com.google.inject.Inject;

@Path("sally/datavalid")
public class DataValidationWeb {
	Logger log;
	TemplateEngine te;
	ISallyWorkflowManager kb;
	DocumentManager docManager;
	
	@Inject
	public DataValidationWeb(TemplateEngine te, ISallyWorkflowManager kb, DocumentManager docManager) {
		this.te = te;
		log = org.slf4j.LoggerFactory.getLogger(getClass());
		this.kb = kb;
		this.docManager = docManager;
	}

	@GET
	public String get(@QueryParam("file") String file, @QueryParam("sheet") String sheet, @QueryParam("sc") int startCol, @QueryParam("ec") int endCol, @QueryParam("sr") int startRow, @QueryParam("er") int endRow){
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		DocumentInformation docInfo = docManager.getDocumentInformation(file);
		SpreadsheetDocument spdoc = (SpreadsheetDocument) docInfo.getDocumentModel();
		Sheet sheetData = spdoc.getConcreteSpreadsheetModel().getSheets().get(sheet);

		log.info(sheetData.getCells().get(4).get(6).getFormula());
		data.put("incident_id", sheetData.getCells().get(3).get(1).getFormula());
		data.put("incident_description", sheetData.getCells().get(3).get(9).getFormula());
		data.put("incident_resolution", sheetData.getCells().get(3).get(10).getFormula());
		data.put("incident_resolution", sheetData.getCells().get(3).get(3).getFormula());
		
		return te.generateTemplate("validate/validate.ftl", data);
	}
}
