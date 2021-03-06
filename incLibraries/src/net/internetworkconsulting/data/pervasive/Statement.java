package net.internetworkconsulting.data.pervasive;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.TimeZone;
import net.internetworkconsulting.data.SessionInterface;
import net.internetworkconsulting.data.StatementInterface;

public class Statement implements StatementInterface {
	public Statement() {}
	public Statement(String command) { sCommand = command; }

	private String sCommand = "";
	public void setCommand(String value) { sCommand = value; }
	public String getCommand() { return sCommand; }

	HashMap<String, Object> mapParameters = new HashMap<>();
	public void setParameter(HashMap<String, Object> values) { mapParameters = values; }
	public HashMap<String, Object> getParameters() { return mapParameters; }

	public String lastExecutedSql = "";
	public String generate(SessionInterface session, boolean log_query) throws Exception {
		String ret = sCommand;
		for(String key: mapParameters.keySet())
			ret = ret.replace(key, convertObjectToSql(mapParameters.get(key)));

		return ret;
	}

	public static Class getJavaTypeForSqlType(String value) throws Exception {
		String sqlType = value.toLowerCase();

		if(sqlType.contains("char") || sqlType.contains("text"))
			return String.class;
		if(sqlType.contains("blob") || sqlType.contains("binary"))
			return byte[].class;
		if(sqlType.contains("bit") && sqlType.contains("(") && sqlType.contains(")"))
			return byte[].class;
		if(sqlType.contains("small") && sqlType.contains("int"))
			return Integer.class;
		if(sqlType.contains("tiny") && sqlType.contains("int"))
			return Integer.class;
		if(sqlType.contains("medium") && sqlType.contains("int"))
			return Integer.class;
		if(!sqlType.contains("unsigned") && !sqlType.contains("big") && sqlType.contains("int"))
			return Integer.class;
		if(sqlType.contains("big") && sqlType.contains("int") && !sqlType.contains("unsigned"))
			return Long.class;
		if(sqlType.contains("int") &&sqlType.contains("unsigned"))
			return Long.class;
		if(sqlType.contains("decimal"))
			return BigDecimal.class;
		if(sqlType.contains("bit") || sqlType.contains("boolean"))
			return Boolean.class;
		if(sqlType.contains("big") && sqlType.contains("int") && sqlType.contains("unsigned"))
			return BigInteger.class;
		if(sqlType.contains("date") || sqlType.contains("time"))
			return Date.class;
		else
			throw new Exception("Unsupported type: " + sqlType + "!");
	}
	public static String convertObjectToSql(Object value) throws Exception {
		/*
		 * Binary 10 byte[]
		 * Meduim Int Unsigned java.lang.Integer
		 * Char 10 java.lang.String
		 * Decimal Unsigned java.math.BigDecimal
		 * Year java.util.Date
		 * Time java.sql.Time
		 * Vachar 10 java.lang.String
		 * Medium Text java.lang.String
		 * Decimal java.math.BigDecimal
		 * 64 Bits byte[]
		 * Date java.util.Date
		 * DateTime java.sql.Timestamp
		 * Long BLob byte[]
		 * Int Unsigned java.lang.Long
		 * Meduim Int java.lang.Integer
		 * Int java.lang.Integer
		 * Signle Bit java.lang.Boolean
		 * Tiny Text java.lang.String
		 * Small Int Unsigned java.lang.Integer
		 * Small Int java.lang.Integer
		 * Integer Unsigned java.lang.Long
		 * Medium Blob byte[]
		 * Long Text java.lang.String
		 * Boolean java.lang.Boolean
		 * Timestamp java.sql.Timestamp
		 * Tiny Blob byte[]
		 * Text java.lang.String
		 * Varbinary 10 byte[]
		 * TinyInt Unsigned java.lang.Integer
		 * Integer java.lang.Integer
		 * TinyInt java.lang.Integer
		 * BigInt Unsigned java.math.BigInteger
		 * BigInt java.lang.Long
		 */
		SimpleDateFormat sdf;
		if(value == null)
			return "NULL";
		switch(value.getClass().getCanonicalName()) {
			case "boolean":
			case "java.lang.Boolean":
				if((boolean) value)
					return "1";
				else
					return "0";
			case "byte[]":
				String ret = "";
				for(byte b: (byte[]) value)
					ret += "0x" + String.format("%2x", b);
				return ret;
			case "byte":
			case "short":
			case "int":
			case "long":
			case "java.lang.Integer":
			case "java.lang.Long":
				return value.toString();
			case "char":
			case "java.lang.String":
				if(((String) value).length() == 0)
					return "NULL";
				else
					return "'" + value.toString().replace("'", "''") + "'";
			case "float":
			case "double":
			case "java.math.BigDecimal":
				return String.format("%f", value);
			case "java.util.Date":
				java.util.Date objDate = (java.util.Date) value;
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				return "'" + sdf.format(objDate) + "'";
		}

		throw new Exception("Not a valid type (" + value.getClass().getCanonicalName() + ")!");
	}
	public static String convertObjectToString(Object value, String format) throws Exception {
		if(value == null)
			return "";

		SimpleDateFormat sdf;
		switch(value.getClass().getCanonicalName()) {
			case "boolean":
			case "java.lang.Boolean":
				if((boolean) value)
					return "true";
				else
					return "false";
			case "byte[]":
				return javax.xml.bind.DatatypeConverter.printHexBinary(((byte[]) value)).toLowerCase().replace("0x", "");
			case "byte":
			case "short":
			case "int":
			case "long":
			case "java.lang.Integer":
			case "java.lang.Long":
				if(format != null && format.length() > 0)
					return String.format(format, value);
				else
					return value.toString();
			case "char":
			case "java.lang.String":
				return value.toString();
			case "float":
			case "double":
			case "java.math.BigDecimal":
				if(format != null && format.length() > 0)
					return String.format(format, value);
				else
					return ((BigDecimal) value).toString();
			case "java.util.Date":
//				java.util.Date objDate = (java.util.Date) value;
//				sdf = new SimpleDateFormat("yyyy-MM-dd");
//				return sdf.format(objDate);
//			case "java.sql.Time":
//				java.sql.Time objTime = (java.sql.Time) value;
//				sdf = new SimpleDateFormat("HH:mm:ss");
//				return sdf.format(objTime);
//			case "java.sql.Timestamp":
				java.util.Date objDate = (java.util.Date) value;
				sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
				sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
				return sdf.format(objDate);
		}

		throw new Exception("Not a valid type (" + value.getClass().getCanonicalName() + ")!");
	}
	public static Object parseStringToValue(Class type, String value) throws Exception { return parseStringToValue(type.getCanonicalName(), value); }
	public static Object parseStringToValue(String type, String value) throws Exception {
		switch(type) {
			case "byte[]":
				try {
					return javax.xml.bind.DatatypeConverter.parseHexBinary(value.toLowerCase().replace("0x", ""));
				}
				catch(Exception ex) {
					return null;
				}
			case "java.lang.Integer":
				try {
					return new Integer(value);
				}
				catch(Exception ex) {
					return null;
				}
			case "java.lang.String":
				if(value != null && value.equals(""))
					return null;
				else
					return value;
			case "java.math.BigDecimal":
				try {
					return new BigDecimal(value);
				}
				catch(Exception ex) {
					return null;
				}
			case "java.util.Date":
			case "java.sql.Time":
			case "java.sql.Timestamp":
				try {
					if(value.length() == ("yyyy-MM-dd HH:mm:ss").length())
						return new Date((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(value).getTime());
					else if(value.length() == ("yyyy-MM-dd HH:mm").length())
						return new Date((new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(value).getTime());
					else if(value.length() == ("yyyy-MM-dd").length())
						return new Date((new SimpleDateFormat("yyyy-MM-dd")).parse(value).getTime());
					else if(value.length() == ("HH:mm:ss").length())
						return new Date((new SimpleDateFormat("HH:mm:ss")).parse(value).getTime());
					else if(value.length() == "2018-01-01T06:00:00.000Z".length())
						return new Date(Instant.parse(value).toEpochMilli());
				}
				catch(Exception ex) {
					return null;
				}
			case "java.lang.Long":
				try {
					return new Long(value);
				}
				catch(Exception ex) {
					return null;
				}
			case "boolean":
			case "java.lang.Boolean":
				try {
					if(value.length() > 0 && value.toLowerCase().toCharArray()[0] == 't')
						return true;
					if(value.length() > 0 && value.toLowerCase().toCharArray()[0] == 'f')
						return false;
					if(value.length() > 0 && value.toLowerCase().toCharArray()[0] == 'y')
						return true;
					if(value.length() > 0 && value.toLowerCase().toCharArray()[0] == 'n')
						return false;
					if(value.length() > 0 && value.toLowerCase().toCharArray()[0] == '1')
						return true;
					if(value.length() > 0 && value.toLowerCase().toCharArray()[0] == '0')
						return false;
					return false;
				}
				catch(Exception ex) { return false; }
			case "java.math.BigInteger":
				try {
					return new BigInteger(value);
				}
				catch(Exception ex) {
					return null;
				}
		}

		throw new Exception("'" + type + "' is not a supported data type!");
	}

}
