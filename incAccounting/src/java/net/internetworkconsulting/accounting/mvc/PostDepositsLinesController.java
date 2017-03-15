package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Deposit;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.TransactionType;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;

public class PostDepositsLinesController extends Controller {
	private CheckTag chkIsPosted;
	public PostDepositsLinesController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		Deposit objModel = (Deposit) getModel();

		chkIsPosted = new CheckTag(this, "Row Posted");
		chkIsPosted.setIsChecked(objModel.getPostedTransactionsGuid() != null);				
		chkIsPosted.setName("Posted" + objModel.getGuid());
		
		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		TextTag litItems = new TextTag(this, "Row", Deposit.ITEMS, objModel.getGuid(), objModel);
		litItems.setIsReadOnly(true);
		TextTag litDate = new TextTag(this, "Row", Deposit.DATE, objModel.getGuid(), objModel);
		litDate.setIsReadOnly(true);
		TextTag litNumber = new TextTag(this, "Row", Deposit.NUMBER, objModel.getGuid(), objModel);
		litNumber.setIsReadOnly(true);
		TextTag litTotal = new TextTag(this, "Row", Deposit.TOTAL, objModel.getGuid(), objModel);
		litTotal.setIsReadOnly(true);
		litTotal.setFormat(sMoneyFormat);

		ButtonTag btnOpen = new ButtonTag(this, "Row", "Open", objModel.getGuid(), "Open");
		btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });
	}
	public History createHistory() throws Exception { return null; }
	public boolean getIsPosted() throws Exception {
		return chkIsPosted.getIsChecked();
	}

	private void btnOpen_OnClick() throws Exception {
		Deposit objModel = (Deposit) getModel();
		TransactionType objType = TransactionType.loadByGuid(getUser().login(), TransactionType.class, Deposit.TRANSACTION_TYPE_GUID);
		redirect(objType.getRootUrl() + "&GUID=" + objModel.getGuid());
	}
	
}