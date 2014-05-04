package info.kwarc.sissi.model.document.spreadsheet;

import info.kwarc.sally.spreadsheet3.Util;
import info.kwarc.sally.spreadsheet3.export.ModelRDFExport;
import info.kwarc.sally.spreadsheet3.model.Block;
import info.kwarc.sally.spreadsheet3.model.CellDependencyDescription;
import info.kwarc.sally.spreadsheet3.model.CellSpaceInformation;
import info.kwarc.sally.spreadsheet3.model.Manager;
import info.kwarc.sally.spreadsheet3.model.Relation;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.drools.command.runtime.rule.GetAgendaEventListenersCommand;

import sally.SpreadsheetAlexData;

import com.hp.hpl.jena.rdf.model.Model;

public class IUIPaperDataSAir {
	Manager asm;

	public IUIPaperDataSAir() {
		asm = new Manager(null);
	}

	Integer createRowFB(String sheet, int startRow, int startCol, String [] content, Integer [] ids) {
		if (content.length == 0) {
			return null;
		}
		return createFB(sheet, startRow, startCol, startRow, startCol+content.length-1, new String[][] {content}, ids);
	}

	Integer createColFB(String sheet, int startRow, int startCol, String [] content, Integer [] ids) {
		if (content.length == 0) {
			return null;
		}
		String [][] c = new String[content.length][1];
		for (int i=0; i<content.length; ++i)
			c[i][0] = content[i];
		return createFB(sheet, startRow, startCol, startRow+content.length-1, startCol, c, ids);
	}

	Integer createFB(String sheet, int startRow, int startCol, int endRow, int endCol, String [][] content, Integer [] ids) {
		startRow--; startCol--;
		endRow--; endCol--;
		
		List<CellSpaceInformation> cellInfo = Util.expandRange(new CellSpaceInformation(sheet, startRow, startCol), new CellSpaceInformation(sheet, endRow, endCol));
		Block block = asm.createComposedBlock(cellInfo);
		return block.getId();
	}

	// This should be used for header and titles of data
	Integer setHeaderLabel(String sheet, int startRow, int startCol, int length, String text) {
		String [] names = new String[length];
		for (int i=0; i<length; ++i)
			names[i] = text;
		return setRowTableHeaders(sheet, startRow, startCol, names);
	}

	Integer setRowTableHeaders(String sheet, int startRow, int startCol, String [] text) {
		startRow--; startCol--;
		List<CellSpaceInformation> cellInfo = Util.expandRange(new CellSpaceInformation(sheet, startRow, startCol), new CellSpaceInformation(sheet, startRow, startCol+text.length));
		Block block = asm.createComposedBlock(cellInfo);
		return block.getId();
	}

	Integer setColTableHeaders(String sheet, int startRow, int startCol, String [] text) {
		startRow--; startCol--;
		List<CellSpaceInformation> cellInfo = Util.expandRange(new CellSpaceInformation(sheet, startRow, startCol), new CellSpaceInformation(sheet, startRow + text.length, startCol));
		Block block = asm.createComposedBlock(cellInfo);
		return block.getId();
	}

	public void addOntologyLink(int blockid, String uri) {
		Block blk = asm.getBlockByID(blockid);
		List<Block> blocksInput = new ArrayList<Block>();
		blocksInput.add(blk);

		List<CellDependencyDescription> relationInputDesc = new ArrayList<CellDependencyDescription>();
		relationInputDesc.add(new CellDependencyDescription(0, blk.getMaxRow()-blk.getMinRow(),0, blk.getMaxColumn()-blk.getMinColumn(),"x,y"));
		Relation r = asm.createRelation(Relation.RelationType.FUNCTIONALRELATION, blocksInput, relationInputDesc);
		r.setUri(uri);
	}

	public void setData() {
		buildValidateSheet();
	}

	static final String IDURI="http://mathhub.info/alezza/sallyair/incident_module.omdoc?inc?identifier";

	public void buildValidateSheet() {
		String workSheetid = "Report 1";

		Integer tableProps = setRowTableHeaders(workSheetid, 6, 1, new String[] {"Incident ID", "EADS_CI Business Service", "Status", "Priority", "Summary", "Resolution", ""});

		Integer IncidentIDCol = setColTableHeaders(workSheetid, 8, 1, new String[] {"INC000001506107", "INC000001547884", "INC000001594575", "INC000001712981", "INC000001824763"});
		Integer BusinessServiceCol = setColTableHeaders(workSheetid, 8, 2, new String[] { "SMARTER", "SMARTER", "SMARTER", "SEMAPHORE", "SMARTER"});
		Integer StatusCol = setColTableHeaders(workSheetid, 8, 3, new String[] { "Closed", "Closed", "Assigned", "Closed", "Closed"});
		Integer PriorityCol = setColTableHeaders(workSheetid, 8, 4, new String[] { "Low", "Low", "Low", "Low", "Low"});
		Integer SummaryCol = setColTableHeaders(workSheetid, 8, 5, new String[] { "#DE SMARTER GENERAL INFORMATION", "#FR/1/SMARTER/HOW DO I", "#DE SMARTER GENERAL INFORMATION", "FR / 1 / SMARTER REPORTING", "#UK FINK/1/SMARTER"});
		Integer ResolutionCol = setColTableHeaders(workSheetid, 8, 6, new String[] { "_", "_", "_", "_", "_"});

		addOntologyLink(IncidentIDCol, "");
		addOntologyLink(BusinessServiceCol, "");
		addOntologyLink(StatusCol, "");
		addOntologyLink(PriorityCol, "");
		addOntologyLink(SummaryCol, "");
		addOntologyLink(ResolutionCol, "");
	}


	public void writeRDF() {
		OutputStream file;
		try {
			file = new FileOutputStream("iui-model.rdf");
			getModel().write(file);
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeProto() {
		OutputStream file;
		try {
			file = new FileOutputStream("iui-model.rdf.64");
			ByteArrayOutputStream so = new ByteArrayOutputStream();
			SpreadsheetAlexData model = SpreadsheetAlexData.newBuilder().setAsm(getAsm().serialize()).build();
			model.writeTo(so);
			byte[] b = Base64.encodeBase64(so.toByteArray());
			file.write(b);
			file.write(new byte[]{10, 49, 49, 10});

			int off = 0; int len = b.length;
			while (len>0) {
				int toWrite = Math.min(10000, len);
				file.write(b, off, toWrite);
				file.write(new byte[]{10});
				len -= toWrite;
				off += toWrite;
			}

			file.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Manager getAsm() {
		return asm;
	}

	public Model getModel() {
		return ModelRDFExport.getRDF(asm, "http://AirbusReport.xls");
	}

	public static void main(String[] args) {
		IUIPaperDataSAir t = new IUIPaperDataSAir();

		t.setData();
		t.writeRDF();
		t.writeProto();

	}
}