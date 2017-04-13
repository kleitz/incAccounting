/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class UsersRow extends Row implements UsersInterface {
	public UsersRow() { 
		super(); 
		setSqlTableName("Users");
		setSqlSecurableGuid("f9aae5fda8d810a29f12d1e61b4ab25f");
	}
	public static String TABLE_NAME = "Users";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String IS_ALLOWED = "Is Allowed";
	public boolean setIsAllowed(java.lang.Boolean value) throws Exception { return set(IS_ALLOWED, value); }
	public java.lang.Boolean getIsAllowed() { return (java.lang.Boolean) get(IS_ALLOWED); }
	
	public static String DISPLAY_NAME = "Display Name";
	public boolean setDisplayName(java.lang.String value) throws Exception { return set(DISPLAY_NAME, value); }
	public java.lang.String getDisplayName() { return (java.lang.String) get(DISPLAY_NAME); }
	
	public static String EMAIL_ADDRESS = "Email Address";
	public boolean setEmailAddress(java.lang.String value) throws Exception { return set(EMAIL_ADDRESS, value); }
	public java.lang.String getEmailAddress() { return (java.lang.String) get(EMAIL_ADDRESS); }
	
	public static String PASSWORD_DATE = "Password Date";
	public boolean setPasswordDate(java.sql.Date value) throws Exception { return set(PASSWORD_DATE, value); }
	public java.sql.Date getPasswordDate() { return (java.sql.Date) get(PASSWORD_DATE); }
	
	public static String PASSWORD_HASH = "Password Hash";
	public boolean setPasswordHash(java.lang.String value) throws Exception { return set(PASSWORD_HASH, value); }
	public java.lang.String getPasswordHash() { return (java.lang.String) get(PASSWORD_HASH); }
	
	public static String EMPLOYEES_GUID = "Employees GUID";
	public boolean setEmployeesGuid(java.lang.String value) throws Exception { return set(EMPLOYEES_GUID, value); }
	public java.lang.String getEmployeesGuid() { return (java.lang.String) get(EMPLOYEES_GUID); }
	

	// child loaders
	
	protected Object lstLogsChildren = null;
	public <T extends LogsRow> List<T> loadLogs(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstLogsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Logs\" WHERE \"Users GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstLogsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstLogsChildren;
	}
	
	protected Object lstMembershipsChildren = null;
	public <T extends MembershipsRow> List<T> loadMemberships(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstMembershipsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Memberships\" WHERE \"Users GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstMembershipsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstMembershipsChildren;
	}
	
	protected Object lstSettingsChildren = null;
	public <T extends SettingsRow> List<T> loadSettings(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstSettingsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Settings\" WHERE \"Users GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstSettingsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstSettingsChildren;
	}
	
	protected Object lstTimeEntriesChildren = null;
	public <T extends TimeEntriesRow> List<T> loadTimeEntries(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstTimeEntriesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Time Entries\" WHERE \"Users GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstTimeEntriesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstTimeEntriesChildren;
	}
	
	protected Object lstTimeSheetsChildren = null;
	public <T extends TimeSheetsRow> List<T> loadTimeSheets(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstTimeSheetsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Time Sheets\" WHERE \"Users GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstTimeSheetsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstTimeSheetsChildren;
	}
	

	// parent loaders
	
	protected Object rEmployeeParent = null;
	public <T extends EmployeesRow> T loadEmployee(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rEmployeeParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Employees\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getEmployeesGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Employees row by GUID (" + Statement.convertObjectToString(this.getEmployeesGuid(), null) + ")!");
			rEmployeeParent = lst.get(0);
		}
		return (T) rEmployeeParent;
	}
	

	// unique key loaders
	
	public static <T extends UsersRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Users\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Users row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends UsersRow> T loadByDisplayName(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Users\" WHERE \"Display Name\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Users row by 'Display Name': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends UsersRow> T loadByEmailAddress(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Users\" WHERE \"Email Address\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Users row by 'Email Address': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends UsersRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Users\"");
		return (List<T>) adapter.load(model, stmt, true);
	}
}
