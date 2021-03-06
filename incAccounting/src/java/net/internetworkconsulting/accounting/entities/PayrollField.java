package net.internetworkconsulting.accounting.entities;

import java.util.HashMap;
import java.util.List;
import net.internetworkconsulting.accounting.data.PayrollFieldsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class PayrollField extends PayrollFieldsRow {
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}
	
	
	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;
		
		Statement stmt = new Statement(adapter.getSession().readJar(PayrollField.class, "PayrollField.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}
	private static HashMap<String, List<Option>> hmOptionsByType = new HashMap<>();
	public static List<Option> loadOptionsByType(AdapterInterface adapter, boolean force, String type_guid) throws Exception {
		if(hmOptionsByType.containsKey(type_guid) && !force)
			return hmOptionsByType.get(type_guid);
		
		Statement stmt = new Statement(adapter.getSession().readJar(PayrollField.class, "PayrollField.loadOptionsByType.sql"));		
		stmt.getParameters().put("{Type GUID}", type_guid);
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);

		hmOptionsByType.put(type_guid, lst);
		return hmOptionsByType.get(type_guid);
	}
}
