package info.kwarc.sally;

import info.kwarc.sally.core.composition.SallyContext;
import info.kwarc.sally.core.composition.SallyInteraction;
import info.kwarc.sally.core.composition.SallyInteractionResultAcceptor;
import info.kwarc.sally.core.composition.SallyService;
import info.kwarc.sally.core.doc.DocumentInformation;
import info.kwarc.sally.core.doc.DocumentManager;
import info.kwarc.sally.core.interaction.SallyMenuItem;
import info.kwarc.sally.core.rdf.RDFStore;
import info.kwarc.sally.core.theo.Theo;
import info.kwarc.sally.core.workflow.ISallyWorkflowManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sally.AlexClick;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DataValidation {

	SallyInteraction sally;
	Logger log;
	ISallyWorkflowManager kb;
	DocumentManager docManager;

	@Inject
	public DataValidation(DocumentManager docManager, SallyInteraction sally, ISallyWorkflowManager kb, RDFStore rdfStore) {
		this.docManager = docManager; 

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

				theo.openWindow(docInfo, parentProcessInstanceID, "Instance Selector", "http://localhost:8181/sally/datavalid", 450, 600);
			}
		});
	}
}
