package info.kwarc.sissi.model.document.spreadsheet;

import info.kwarc.sally.networking.interfaces.INetworkSenderAdapter;
import info.kwarc.sally.networking.interfaces.NetworkSenderMock;
import info.kwarc.sally.spreadsheet.WorksheetDocument;
import info.kwarc.sally.spreadsheet.interfaces.WorksheetFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sally.CellPosition;
import sally.SpreadsheetModel;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class WorksheetDocumentTest {
	Injector i;
	
	WorksheetDocument doc;
	
	@Before
	public void setup() {
		i = Guice.createInjector(new AbstractModule() {

			@Override
			protected void configure() {
				install(new FactoryModuleBuilder().build(WorksheetFactory.class));
			}
		});
	}

	@Test
	public void serializationTest() {
		try {
			FileInputStream file = new FileInputStream(new File("iui-model.bin"));
			WorksheetFactory wf = i.getInstance(WorksheetFactory.class);
			doc = wf.create("http://iui-paper", SpreadsheetModel.parseFrom(file), new NetworkSenderMock());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int workSheetid = doc.getSheetId("Vendor A");
		Assert.assertEquals("https://tnt.kwarc.info/repos/stc/fcad/flange/cds/ISOhexthread.omdoc?ISOhexthread?ISOhexthread", doc.getSemantics(CellPosition.newBuilder().setSheet(workSheetid).setRow(8).setCol(2).build()));
		Assert.assertEquals(null, doc.getSemantics(CellPosition.newBuilder().setSheet(workSheetid).setRow(9).setCol(9).build()));
		CellPosition pos = doc.getPositionFromMMTURI("https://tnt.kwarc.info/repos/stc/fcad/flange/cds/ISOhexthread.omdoc?ISOhexthread?ISOhexthread");
		//Assert.assertEquals(pos.getSheet(), workSheetid);
		Assert.assertEquals(pos.getRow(), 8);
		Assert.assertEquals(pos.getCol(), 2);
	}
}
