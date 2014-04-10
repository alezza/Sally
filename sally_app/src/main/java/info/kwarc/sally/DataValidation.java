package info.kwarc.sally;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import info.kwarc.sally.core.composition.SallyContext;
import info.kwarc.sally.core.composition.SallyInteraction;
import info.kwarc.sally.core.composition.SallyInteractionResultAcceptor;
import info.kwarc.sally.core.composition.SallyService;
import info.kwarc.sally.core.doc.DocumentInformation;
import info.kwarc.sally.core.doc.DocumentManager;
import info.kwarc.sally.core.interaction.CallbackManager;
import info.kwarc.sally.core.interaction.IMessageCallback;
import info.kwarc.sally.core.interaction.SallyMenuItem;
import info.kwarc.sally.core.rdf.RDFStore;
import info.kwarc.sally.core.theo.Theo;
import info.kwarc.sally.core.workflow.ISallyWorkflowManager;
import info.kwarc.sally.sharejs.models.SpreadsheetModel.Sheet;
import info.kwarc.sally.spreadsheet.SpreadsheetDocument;

import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sally.AlexClick;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.protobuf.AbstractMessage;

@Singleton
public class DataValidation {

	SallyInteraction sally;
	Logger log;
	ISallyWorkflowManager kb;
	DocumentManager docManager;
	CallbackManager callbackManager;

	@Inject
	public DataValidation(DocumentManager docManager, SallyInteraction sally, ISallyWorkflowManager kb, RDFStore rdfStore, CallbackManager callbackManager) {
		this.docManager = docManager; 
		this.callbackManager = callbackManager;
		this.sally = sally;
		this.kb = kb;
		log = LoggerFactory.getLogger(getClass());
	}
	
	@SallyService
	public void dataValidation(final AlexClick uri, SallyInteractionResultAcceptor acceptor, final SallyContext context) {
		
		acceptor.acceptResult(new SallyMenuItem("Validate", "Validate this document", "Open validation information associated to the chosen CAD object") {
			@Override
			public void run() {
				Long parentProcessInstanceID = context.getContextVar("processInstanceId", Long.class);
				DocumentInformation docInfo = docManager.getDocumentInformation(uri.getFileName());
				Theo theo = docInfo.getTheo();
				String getReq = null;
				try {
					getReq = String.format("http://localhost:8181/sally/datavalid?file=%s&sheet=%s&sc=%d&ec=%d&sr=%d&er=%d", 
									URLEncoder.encode(uri.getFileName(), "UTF-8"),
									URLEncoder.encode(uri.getSheet(), "UTF-8"),
									uri.getRange().getStartCol(),
									uri.getRange().getEndCol(),
									uri.getRange().getStartRow(),
									uri.getRange().getEndRow()
									);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				theo.openWindow(docInfo, parentProcessInstanceID, "Instance Selector", getReq, 450, 600);
			}
		});
	}
}
