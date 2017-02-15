package net.internetworkconsulting.mvc;

import net.internetworkconsulting.template.Template;

public class CheckTag  extends Tag {
	public static String TYPE_CHECK = "checkbox";
	public static String TYPE_RADIO = "radio";

	
	public CheckTag(ControllerInterface controller, String property, Object model) throws Exception { super(controller, property, model); }
	public CheckTag(ControllerInterface controller, String prefix, String property, String unique_key, Object model) throws Exception { super(controller, prefix, property, unique_key, model); }
	public CheckTag(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public void createControls(Template document, Object model) throws Exception { }

	private String sType = TYPE_CHECK;
	public String getInputType() { return sType; }
	public void setInputType(String value) { sType = value; }

	public boolean getIsChecked() throws Exception { return getValue() != null && getValue().length() > 0 && (getValue().toLowerCase().startsWith("t") ||getValue().toLowerCase().startsWith("y")); }
	public boolean setIsChecked(boolean value) throws Exception {
		if(value)
			return setValue("true");
		else
			return setValue("false");
	}

	public void updateControls() throws Exception {
		if(getIsReadOnly())
			return;
		
		String value = getRequest().getParameter(getName());
		if(value == null)
			setIsValueChanged(this.setIsChecked(false));
		else
			setIsValueChanged(this.setIsChecked(true));
	}

	public void populateDocument() throws Exception { 
		if(getName() == null || getName().length() < 1)
			throw new Exception("You must provie a 'name' for your tags!");
		if(getController() == null)
			throw new Exception("Tag must be a subcrontoller, they are not standalone pages.");

		String html = "<input  type=\"" + getInputType() + "\" value=\"true\" ";
	
		if(getIsChecked())
			html += " checked ";

		html += generateAttributes(true);
						
		html += " >";
				
		getController().getDocument().set(getDocumentKeyword(), html);
	}	
}
