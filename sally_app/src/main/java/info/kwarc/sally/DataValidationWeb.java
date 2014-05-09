package info.kwarc.sally;

import info.kwarc.sally.core.doc.DocumentInformation;
import info.kwarc.sally.core.doc.DocumentManager;
import info.kwarc.sally.core.workflow.ISallyWorkflowManager;
import info.kwarc.sally.networking.TemplateEngine;
import info.kwarc.sally.sharejs.models.SpreadsheetModel.Sheet;
import info.kwarc.sally.spreadsheet.SpreadsheetDocument;

import java.util.HashMap;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
		data.put("incident_businessapp", sheetData.getCells().get(3).get(4).getFormula());
		data.put("incident_status", sheetData.getCells().get(3).get(6).getFormula());
		data.put("incident_priority", sheetData.getCells().get(3).get(7).getFormula());
		data.put("incident_region", sheetData.getCells().get(3).get(11).getFormula());
		data.put("incident_servtarget", sheetData.getCells().get(3).get(12).getFormula());
		data.put("incident_progress", sheetData.getCells().get(3).get(13).getFormula());
		data.put("incident_opcateg", sheetData.getCells().get(3).get(14).getFormula());
		data.put("incident_agroup", sheetData.getCells().get(3).get(16).getFormula());
		data.put("incident_notes", sheetData.getCells().get(3).get(8).getFormula());
		
		
		
		
		return te.generateTemplate("validate/validate.ftl", data);
		//return te.generateTemplate("validate/postInfo.ftl", data);
		
	}
	
	@POST

	public String post(@QueryParam("file") String file, @QueryParam("sheet") String sheet, @QueryParam("sc") int startCol, @QueryParam("ec") int endCol, @QueryParam("sr") int startRow, @QueryParam("er") int endRow){
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		DocumentInformation docInfo = docManager.getDocumentInformation(file);
		SpreadsheetDocument spdoc = (SpreadsheetDocument) docInfo.getDocumentModel();
		Sheet sheetData = spdoc.getConcreteSpreadsheetModel().getSheets().get(sheet);

		log.info(sheetData.getCells().get(4).get(6).getFormula());
		data.put("incident_id", sheetData.getCells().get(3).get(1).getFormula());
		data.put("incident_businessapp", sheetData.getCells().get(3).get(4).getFormula());
		data.put("incident_status", sheetData.getCells().get(3).get(6).getFormula());
		data.put("incident_priority", sheetData.getCells().get(3).get(7).getFormula());
		data.put("incident_notes", sheetData.getCells().get(3).get(8).getFormula());
		data.put("incident_description", sheetData.getCells().get(3).get(9).getFormula());
		data.put("incident_resolution", sheetData.getCells().get(3).get(10).getFormula());
		data.put("incident_region", sheetData.getCells().get(3).get(11).getFormula());
		data.put("incident_servtarget", sheetData.getCells().get(3).get(12).getFormula());
		data.put("incident_progress", sheetData.getCells().get(3).get(13).getFormula());
		data.put("incident_opcateg", sheetData.getCells().get(3).get(14).getFormula());
		data.put("incident_agroup", sheetData.getCells().get(3).get(16).getFormula());
		
		return te.generateTemplate("validate/postInfo.ftl", data);
		
	}
	
	//public String post(@FormParam("incident_id") String incident_id){
	//	return incident_id;
	//}
}
