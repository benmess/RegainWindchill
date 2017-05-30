package ext.regain.document;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.security.SecureRandom;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Types;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;



















import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
//Import for the excel stuff
import org.apache.poi.xssf.usermodel.*;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.poi.;
//import org.eclipse.jdt.internal.compiler.ast.ReturnStatement;


//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers..cocumentBuilder;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//
//import org.w3c.dom.Attr;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;

public class Backend
{
	int iWebApp = 1;
	
	public Backend() {}
	public Backend(int iWebAppSetup)
	{
		iWebApp = iWebAppSetup;
	}
	public class ReturnClassBool
	{
		public boolean bValue;
		public String sRtnMsg;
	}
	
	public class ReturnClassString
	{
		public boolean bValue;
		public String sRtnMsg;
		public String sReturnedString;
	}

	public class ReturnClassInt
	{
		boolean bValue;
		int iValue;
		String sRtnMsg;
	}

	public class ReturnNextId
	{
		public long iNextId;
		public String sPrefix;
		public int iSequenceLength;
	}
	
	public class ReturnClassExcel
	{
		public boolean bValue;
		public String sRtnMsg;
		public String sFile;
	}

	public class Environment
	{
		public String GetEnvironment()
		{
			String sFile = GetBaseWebpath() + "\\Environment\\Environment.txt";
			String sLine = "";
			try
			{
				BufferedReader FileRead = new BufferedReader(new FileReader(sFile));
				String sRtn = "Dev";
				while((sLine = FileRead.readLine()) != null)
				{		
					if(sLine.startsWith("Environment"))
					{
						int iStart = sLine.indexOf("Environment=")+12;
						sRtn = sLine.substring(iStart);
						break;
					}
				}
				FileRead.close();
				return sRtn;
				
			}
			catch(Exception ex)
			{
				return "Dev";
			}
		}
		
		public String GetEnvironmentVariable(String sVariableName)
		{
			String sFile = GetBaseWebpath() + "\\Environment\\Environment.txt";
			String sLine = "";
			try
			{
				BufferedReader FileRead = new BufferedReader(new FileReader(sFile));
				String sRtn = "";
				while((sLine = FileRead.readLine()) != null)
				{		
					if(sLine.startsWith(sVariableName))
					{
						int iStart = sLine.indexOf(sVariableName + "=")+sVariableName.length() + 1;
						sRtn = sLine.substring(iStart);
						break;
					}
				}
				FileRead.close();
				return sRtn;
				
			}
			catch(Exception ex)
			{
				return "";
			}
		}

		public String GetDeploymentPath()
		{
			return GetEnvironmentVariable("DeploymentPath");
		}


		public String GetBaseWebpath()
		{
			String sReturn = "";
			switch(iWebApp)
			{
				case 1:
				default:
					sReturn = "C:\\WebRoot\\PIMS";
					break;
				case 2:
					sReturn = "C:\\WebRoot\\Regain";
					break;
				case 5:
					sReturn = "C:\\WebRoot\\WindchillInterface";
					break;
			}
			return sReturn;
		}

		public String GetWebserverRootPath()
		{
			return "C:\\WebRoot\\";
		}
}
	
	public class Database 
	{
		private ResultSet m_rs = null;
		private int m_iResultSetRowCount = -1;
		private ResultSetMetaData m_rsmd = null;
		private int m_iResultSetColumnCount = -1;
		private CallableStatement  m_cstmt;
		private Connection m_conn;
		
		public Connection ConnectToDB()
		{
			Environment env = new Environment();					
			String sEnvironment = env.GetEnvironment();
			Connection conn = null;
			String sSAUser = "sa";
			String sSAPass = "mogana";
	        String dbName = "pims";
	        String serverip="VSRS06";
//	        String serverport="1433";
	        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        String dbURL;
	        Log log = new Log();
			
	        log.LogMsg("WebApp = " + String.valueOf(iWebApp));
			if(iWebApp == 3)
			{
				switch(sEnvironment.toUpperCase())
				{
					case "DEV":
					case "DEVWC":
					case "DEVWCREGAIN":
					case "DEVREGAIN":
					default:
						sSAUser = "pimsadmin";
						sSAPass = "N33dt0kn0w";
				        dbName = "wcadmin";
				        serverip="VSRS12";
				        dbURL = "jdbc:sqlserver://"+serverip+";integratedSecurity=false;databaseName="+dbName+"";
				        break;				
					case "PRODUCTION":
					case "PRODUCTIONREGAIN":
						sSAUser = "pimsadmin";
						sSAPass = "N33dt0kn0w";
				        dbName = "wcadmin";
				        serverip="VSRS06";
				        dbURL = "jdbc:sqlserver://"+serverip+";integratedSecurity=false;databaseName="+dbName+"";
				        break;				
				}				
			}
			else if(iWebApp == 4) //THis is for views etc that are in the Regain DB but are rading from wcadmin or other DBs on the same server
			{
				switch(sEnvironment.toUpperCase())
				{
					case "DEV":
					case "DEVREGAIN":
					case "DEVWC":
					case "DEVWCREGAIN":
					default:
						sSAUser = "pimsadmin";
						sSAPass = "N33dt0kn0w";
				        dbName = "Regain";
				        serverip="VSRS12";
				        dbURL = "jdbc:sqlserver://"+serverip+";integratedSecurity=false;databaseName="+dbName+"";
				        break;				
					case "PRODUCTION":
					case "PRODUCTIONREGAIN":
						sSAUser = "pimsadmin";
						sSAPass = "N33dt0kn0w";
				        dbName = "Regain";
				        serverip="VSRS06";
				        dbURL = "jdbc:sqlserver://"+serverip+";integratedSecurity=false;databaseName="+dbName+"";
				        break;				
				}				
			}
			else
			{
		        log.LogMsg("sEnvironment = " + sEnvironment);
				switch(sEnvironment.toUpperCase())
				{
					case "DEV":
					default:
						sSAUser = "sa";
						sSAPass = "mogana";
				        dbName = "pims";
				        dbURL = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=false;databaseName="+dbName+"";
				        break;				
					case "DEVREGAIN":
						sSAUser = "sa";
						sSAPass = "mogana";
				        dbName = "Regain";
				        dbURL = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=false;databaseName="+dbName+"";
				        break;				
					case "DEVWC":
						sSAUser = "pimsadmin";
						sSAPass = "N33dt0kn0w";
				        dbName = "pims";
				        serverip="VSRS12";
				        dbURL = "jdbc:sqlserver://"+serverip+";integratedSecurity=false;databaseName="+dbName+"";
				        break;				
					case "DEVWCREGAIN":
						sSAUser = "pimsadmin";
						sSAPass = "N33dt0kn0w";
				        dbName = "Regain";
				        serverip="VSRS12";
				        dbURL = "jdbc:sqlserver://"+serverip+";integratedSecurity=false;databaseName="+dbName+"";
				        break;				
					case "PRODUCTION":
						sSAUser = "pimsadmin";
						sSAPass = "N33dt0kn0w";
				        dbName = "pims";
				        serverip="VSRS06";
				        dbURL = "jdbc:sqlserver://"+serverip+";integratedSecurity=false;databaseName="+dbName+"";
				        break;				
					case "PRODUCTIONREGAIN":
						sSAUser = "pimsadmin";
						sSAPass = "N33dt0kn0w";
				        dbName = "Regain";
				        serverip="VSRS06";
				        dbURL = "jdbc:sqlserver://"+serverip+";integratedSecurity=false;databaseName="+dbName+"";
				        break;				
					case "PRODUCTIONMES":
						sSAUser = "pimsadmin";
						sSAPass = "N33dt0kn0w";
				        dbName = "pims";
				        serverip="MES-SQL01";
				        dbURL = "jdbc:sqlserver://"+serverip+";integratedSecurity=false;databaseName="+dbName+"";
				        break;				
				}
			}
	        
			try
			{
		        Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(dbURL, sSAUser, sSAPass);
				return conn;
			} 
			catch (Exception ex) 
			{
				ex.getMessage();
		        ex.printStackTrace();
		        log.LogMsg(ex.getMessage());
		        return null;
		    } 
		}
		
		
	    public ResultSet OpenRecordset(String sSQL) 
	    { 
	        Log log = new Log();
		   try 
		   { 
			    Connection Conn = ConnectToDB();
		        log.LogMsg("Got connection sSQL = " + sSQL);
				Statement stat = Conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				Conn.setHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT);
				ResultSet ds = stat.executeQuery(sSQL);
				//stat.close();
				Conn.commit();
				return ds;
			}
			catch (SQLException ex) 
			{
		        log.LogMsg("OpenRecordset error = " + ex.getMessage());
//		        ex.printStackTrace();
		        return null;
		    } 
		}
		
	    public Boolean ExecuteSQL(String sSQL)
	    {
	        Connection conn = ConnectToDB();
	        try
			{
				Statement stmt = conn.createStatement();
				int iRowsAffected = stmt.executeUpdate(sSQL);
				if(iRowsAffected < 0)
				{
					return false;
				}
				else
				{
					return true;
				}
			} 
	        catch (SQLException e)
			{
				return false;
			}
	    	
	    }
	    
	    public int getRowCount(ResultSet set) throws Exception
	    {
	       int rowCount;
	       int currentRow = set.getRow();            // Get current row
	       rowCount = set.last() ? set.getRow() : 0; // Determine number of rows
	       if (currentRow == 0)                      // If there was no current row
	          set.first();                     		// We want next() to go to first row
	       else                                      // If there WAS a current row
	          set.absolute(currentRow);              // Restore it
	       return rowCount;
	    }

	    public int CallStoredProcReturnValue(String sSP, String[] sParamNames, Object[] objParamValues)
	    {
	    	return CallStoredProcBase(sSP, sParamNames, objParamValues, 2);
	    }

	    public int CallStoredProcResultSet(String sSP, String[] sParamNames, Object[] objParamValues)
	    {
	    	return CallStoredProcBase(sSP, sParamNames, objParamValues, 1);
	    }

	    public int CallStoredProcUpdateAndResultSet(String sSP, String[] sParamNames, Object[] objParamValues)
	    {
	    	return CallStoredProcBase(sSP, sParamNames, objParamValues, 3);
	    }

	    public int CallStoredProc(String sSP, String[] sParamNames, Object[] objParamValues)
	    {
	    	return CallStoredProcBase(sSP, sParamNames, objParamValues, 0);
	    }
	    
	    public int CallStoredProcBase(String sSP, String[] sParamNames, Object[] objParamValues, int iResultSetReturned)
	    {
	    	CallableStatement cstmt = null;
	        ResultSet rs = null;
	        Connection conn = ConnectToDB();
	        int iRowCount = -1;
	        String sParamString = "";
	        
	        try 
	        {
	        	if(sParamNames != null)
	        	{
	        		sParamString = "( ";
		        	for(int i=0;i<sParamNames.length;i++)
		        	{
		        		//sParamString += sParamNames[i] + " => ? ,";
		        		sParamString += " ? ,";
		        	}
		        	
		        	if(iResultSetReturned == 2)
		        		sParamString += " ? ,"; //Add one more for the output parameter
		        	
		        	sParamString = sParamString.substring(0, sParamString.length()-1) + " )";
	        	}
		        conn.setHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT);
		        if(iResultSetReturned == 2)
		        {
		            cstmt = conn.prepareCall("{call " + sSP + sParamString + "}", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		        }
		        else
		        {
		            cstmt = conn.prepareCall("{call " + sSP + sParamString + "}", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);		        	
		        }

	        	if(objParamValues != null)
	        	{	        		
		        	for(int i=0;i<objParamValues.length;i++)
		        	{
		        		if(objParamValues[i] == null)
		        		{
		        			cstmt.setNull(sParamNames[i].replace("@",""), java.sql.Types.NULL);		        			
		        		}
		        		else
		        		{
			        		switch(objParamValues[i].getClass().toString().replace("java.lang.", "").replace("java.sql.", "").replace("class ","").trim())
			        		{
				        		case "Integer":
				        			cstmt.setInt(sParamNames[i].replace("@",""), (int)objParamValues[i]);
				        			break;
			        			
				        		case "Boolean":
				        			int iBool = ((Boolean)objParamValues[i]) ? 1 : 0;
				        			cstmt.setInt(sParamNames[i].replace("@",""), iBool);
				        			break;

				        		case "Long":
				        			cstmt.setLong(sParamNames[i].replace("@",""), (long)objParamValues[i]);
				        			break;

				        		case "String":
				        			cstmt.setString(sParamNames[i].replace("@",""), (String)objParamValues[i]);
				        			break;

				        		case "Double":
				        			cstmt.setDouble(sParamNames[i].replace("@",""), (double)objParamValues[i]);
				        			break;
				        		//Note: this object must be of the type java.sql.date (Often it is easier to send the date as a string and allow SQL to implicitly cast it)
				        		// eg send a date as yyyyMMdd
				        		case "Date":
				        			cstmt.setDate(sParamNames[i].replace("@",""), (Date)objParamValues[i]);
				        			break;				        			
					        		//Note: this object must be of the type java.sql.Timestamp (Often it is easier to send the datetime as a string and allow SQL to implicitly cast it)
					        		// eg send a datetime as yyyyMMdd hh:mm:ss.ss
				        		case "Timestamp":
				        			cstmt.setTimestamp(sParamNames[i].replace("@",""), (Timestamp)objParamValues[i]);
				        			break;				        			
			        		}
		        			
		        		}
		        	}
		        	
	        	}

		        if(iResultSetReturned == 2)
		        {
		            cstmt.registerOutParameter("iReturn", Types.INTEGER);
		        }
	        	
	        	boolean bRtn = cstmt.execute();

	        	switch(iResultSetReturned)
	        	{
		        	case 1:
			            rs = cstmt.getResultSet();
			            break;
		        	case 2:
	        			iRowCount = cstmt.getInt("iReturn");
	        			break;
		        	case 3:
		        		//Bloody ridiculous that you have to do this but you need to loop through every
		        		//update/insert result (including triggers) to get to the result set
		        		iRowCount = 1;
		        		while(bRtn || iRowCount > 0)
		        		{
		        			iRowCount = cstmt.getUpdateCount();
		        			if(bRtn)
		        			{
		        				//This will get the last result set. Dunno how to get earlier ones but possibly build them up in an array if necessary
		        				//Actually discovered this is impossible because the JDBC driver does not support the CallableStatement.KEEP_CURRENT_RESULT flag.
					            rs = cstmt.getResultSet();
					            break; 
		        			}
		        			bRtn = cstmt.getMoreResults();		        				
		        		}
		            	break;
		            default:
	        			iRowCount = cstmt.getUpdateCount();
		            	break;

	        	}
//	        	if(iResultSetReturned == 1)
//	        	{	        		
//	            	boolean bRtn1 = cstmt.getMoreResults();
//	            	if(bRtn || bRtn1)
//	            	{
//			            rs = cstmt.getResultSet();			            		
//	            	}
//	        	}
//	        	else
//	        	{
//	        		if(iResultSetReturned == 2)
//	        		{
////			            rs = cstmt.getResultSet();	        	
//	        			iRowCount = cstmt.getInt("iReturn");
//	        		}
//	        		else
//	        		{
//	        			iRowCount = cstmt.getUpdateCount();
//	        		}
//	        	}
	        } 
	        catch (SQLException ex) 
	        {
	        	return -1;	        	
	        } 
	        finally 
	        {
	            if (rs != null) 
	            {
	                try 
	                {
	        	        m_rs = rs;
	        	        m_rsmd = m_rs.getMetaData();
	        	        m_iResultSetColumnCount = m_rsmd.getColumnCount();
			        	if(iResultSetReturned == 1 || iResultSetReturned == 3)
			        	{
			            	iRowCount = getRowCount(rs);
			        	}
	                    //rs.close();
	                } 
	                catch (SQLException ex) 
	                {
	    	        	return -1;	        	
	                } 
	                catch (Exception e)
					{
	    	        	return -1;	        	
					}
	            }
	            
	            m_cstmt = cstmt;
	            m_conn = conn;
//	            if (cstmt != null) 
//	            {
//	                try 
//	                {
//	                    cstmt.close();
//	                } 
//	                catch (SQLException ex) 
//	                {
//	    	        	return -1;	        	
//	                }
//	            }	            
	        }
	        
	        m_iResultSetRowCount = iRowCount;
	        return iRowCount;
	    }
	    
	    public int CallStoredProcInsertFile(String sSP, String[] sParamNames, Object[] objParamValues, String sFullPathFilename, int iActualFileSkipped)
	    {
	    	CallableStatement cstmt = null;
	        ResultSet rs = null;
	        Connection conn = ConnectToDB();
	        int iRowCount = -1;
	        String sParamString = "";
	        InputStream inputStream;

	        try 
	        {
	        	if(sParamNames != null)
	        	{
	        		sParamString = "( ";
		        	for(int i=0;i<sParamNames.length;i++)
		        	{
		        		//sParamString += sParamNames[i] + " => ? ,";
		        		sParamString += " ? ,";
		        	}
		        	
	        		sParamString += " ? , ? ,"; //Add one more for the file stream parameter and one for the output parameter
		        	
		        	sParamString = sParamString.substring(0, sParamString.length()-1) + " )";
	        	}
	        	
		        conn.setHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT);
	            cstmt = conn.prepareCall("{call " + sSP + sParamString + "}", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

	        	if(objParamValues != null)
	        	{	        		
		        	for(int i=0;i<objParamValues.length;i++)
		        	{
		        		if(objParamValues[i] == null)
		        		{
		        			cstmt.setNull(sParamNames[i].replace("@",""), java.sql.Types.NULL);		        			
		        		}
		        		else
		        		{
			        		switch(objParamValues[i].getClass().toString().replace("java.lang.", "").replace("class ","").trim())
			        		{
				        		case "Integer":
				        			cstmt.setInt(sParamNames[i].replace("@",""), (int)objParamValues[i]);
				        			break;
			        			
				        		case "Long":
				        			cstmt.setLong(sParamNames[i].replace("@",""), (long)objParamValues[i]);
				        			break;

				        		case "String":
				        			cstmt.setString(sParamNames[i].replace("@",""), (String)objParamValues[i]);
				        			break;

				        		case "Double":
				        			cstmt.setDouble(sParamNames[i].replace("@",""), (double)objParamValues[i]);
				        			break;
				        		//Note: this object must be of the type java.sql.date (Often it is easier to send the date as a string and allow SQL to implicitly cast it)
				        		// eg send a date as yyyyMMdd
				        		case "Date":
				        			cstmt.setDate(sParamNames[i].replace("@",""), (Date)objParamValues[i]);
				        			break;				        			
					        		//Note: this object must be of the type java.sql.Timestamp (Often it is easier to send the datetime as a string and allow SQL to implicitly cast it)
					        		// eg send a datetime as yyyyMMdd hh:mm:ss.ss
				        		case "Timestamp":
				        			cstmt.setTimestamp(sParamNames[i].replace("@",""), (Timestamp)objParamValues[i]);
				        			break;				        			
			        		}
		        			
		        		}
		        	}
		        	
	        	}

        		//For the last parameter this is the file stream
	        	if(iActualFileSkipped == 1)
	        	{
        			cstmt.setNull("filedata", java.sql.Types.NULL);		        			
	        	}
	        	else
	        	{
	        		inputStream = new FileInputStream(new File(sFullPathFilename));
	        		cstmt.setBlob("filedata", inputStream);
	        	}
	            cstmt.registerOutParameter("iReturn", Types.INTEGER);
	        	cstmt.execute();

    			iRowCount = cstmt.getInt("iReturn");
	        } 
	        catch (Exception ex) 
	        {
	        	return -1;	        	
	        } 
	        finally 
	        {
	            if (rs != null) 
	            {
	                try 
	                {
	        	        m_rs = rs;
	        	        m_rsmd = m_rs.getMetaData();
	        	        m_iResultSetColumnCount = m_rsmd.getColumnCount();
	                } 
	                catch (SQLException ex) 
	                {
	    	        	return -1;	        	
	                } 
	                catch (Exception e)
					{
	    	        	return -1;	        	
					}
	            }
	            
	            m_cstmt = cstmt;
	            m_conn = conn;
	        }
	        
	        m_iResultSetRowCount = iRowCount;
	        return iRowCount;
	    }

	    public ResultSet getResultSet()
	    {
	    	return m_rs;
	    }
	    
	    public ResultSetMetaData getResultSetMetaData()
	    {
	    	return m_rsmd;
	    }

	    public int getResultSetRowCount()
	    {
	    	return m_iResultSetRowCount;
	    }
		
	    public int getResultSetColumnCount()
	    {
	    	return m_iResultSetColumnCount;
	    }
	    
	    public void CloseResultSet()
	    {
	    	try
			{
				m_cstmt.close();
		    	m_conn.commit();	    
			} 
	    	catch (SQLException e)
			{
	    		return;
			}
    	}
	    
	    public ReturnClassBool InsertFile(String sUser, int iId, int iRelatedTableUniqueId, int iAttachmentType, int iCounter, String sDescription, String sComments, String sPathAndFilename, int iDeleted, int iActualFileSkipped)
	    {
			Database DB = new Database();
			Utilities util = new Utilities();
			String[] sParamNames = new String[10];
			Object[] objParamValues = new Object[10];
			ReturnClassBool rtn = new ReturnClassBool();
			String sFileNameOnly = util.Get_FilenameOnly_From_FullPath(sPathAndFilename);
			String sExt = util.GetFileExtension(sPathAndFilename);
			
			
			sParamNames[0] = "@piId"; 
			sParamNames[1] = "@piRelatedTableUniqueId"; 
			sParamNames[2] = "@piAttachmentType"; 
			sParamNames[3] = "@piCounter"; 
			sParamNames[4] = "@pvchFileName"; 
			sParamNames[5] = "@pvchFileNameExtension"; 
			sParamNames[6] = "@pvchDescription"; 
			sParamNames[7] = "@pvchComments"; 
			sParamNames[8] = "@pvchActiveUser"; 
			sParamNames[9] = "@piDeleted"; 
			
			objParamValues[0] = iId; 
			objParamValues[1] = iRelatedTableUniqueId; 
			objParamValues[2] = iAttachmentType; 
			objParamValues[3] = iCounter; 
			objParamValues[4] = sFileNameOnly; 
			objParamValues[5] = sExt; 
			objParamValues[6] = sDescription; 
			objParamValues[7] = sComments; 
			objParamValues[8] = sUser; 
			objParamValues[9] = iDeleted; 

			int iReturn = DB.CallStoredProcInsertFile("SP_InsertFile", sParamNames, objParamValues, sPathAndFilename, iActualFileSkipped);
			
			if(iReturn < 0)
			{
				rtn.sRtnMsg = "Cannot insert file " + sFileNameOnly + " into the database item with Id " + iId; 
				rtn.bValue = false;
				return rtn;		
			}
			else
			{
				rtn.sRtnMsg = ""; 
				rtn.bValue = true;
				return rtn;		
			}
	    	
	    	
	    }
	    
	}

	
	public class FileUtilities
	{
		public ArrayList<String> Get_VirtualFolder_Contents(String sFolderURL)
		{
			File f = new File(sFolderURL);
			ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
			return names;
		}
		
		public final String Get_Random_FileName()
		{
				Utilities util = new Utilities();
				ReturnNextId rtnId = new ReturnNextId();
				long i = 0;

				rtnId = util.Get_Next_Id("FileCounter");
				i= rtnId.iNextId;

				return "RandomFile_" + i;
		}
		
		public final boolean RetrieveAttachment(int iId, String sReturnFileName)
		{
			return RetrieveAttachment(iId, sReturnFileName, false);
		}

		public final boolean RetrieveAttachment(int iId, String sReturnFileName, boolean bReturnFileNameFromDB)
		{
			Database DB = new Database();
			Environment env =  new Environment();
			Utilities util = new Utilities();
			ResultSet rst = null;
			String sSQL = null;
			boolean bReturn;
			int iRecords = -1;

			if (bReturnFileNameFromDB)
			{
				//sReturnFileName = Get_FileName_From_DB(sID, iAttachmentType, iAttachmentNumber);
			}

			sSQL = "Select Attachment from tblAttachments WHERE ID = " + iId;

			rst = DB.OpenRecordset(sSQL);

			try 
			{
				iRecords = DB.getRowCount(rst);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			if (iRecords > 0)
			{
				try
				{
					String sPath = env.GetDeploymentPath();
					String sFullFile = sPath + "\\" + sReturnFileName;
					sFullFile = util.StripMultiBackslashes(sFullFile);
					File fle = new File(sFullFile);
					FileOutputStream fos = new FileOutputStream(fle);
					
					byte[] buffer = new byte[1];
					InputStream is = rst.getBinaryStream("Attachment");
					while (is.read(buffer) > 0) 
					{
					  fos.write(buffer);
					}
					fos.close();
					bReturn = true;
				}
				catch(Exception e) 
				{
					bReturn = false;
				}
			}
			else
			{
				bReturn = false;
			}
			
			return bReturn;
		}

		public final boolean RetrieveWindchillAttachment(int iId, String sReturnFileName)
		{
			Database DB = new Database();
			Environment env =  new Environment();
			Utilities util = new Utilities();
			ResultSet rst = null;
			String sSQL = null;
			boolean bReturn;
			int iRecords = -1;

			sSQL = "select SD.lobLoc as Attachment from wcadmin.wcadmin.StreamData SD where SD.idA2A2 = " + iId;

			rst = DB.OpenRecordset(sSQL);

			try 
			{
				iRecords = DB.getRowCount(rst);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			if (iRecords > 0)
			{
				try
				{
					String sPath = env.GetDeploymentPath();
					String sFullFile = sPath + "\\" + sReturnFileName;
					sFullFile = util.StripMultiBackslashes(sFullFile);
					File fle = new File(sFullFile);
					FileOutputStream fos = new FileOutputStream(fle);
					
					byte[] buffer = new byte[1];
					InputStream is = rst.getBinaryStream("Attachment");
					while (is.read(buffer) > 0) 
					{
					  fos.write(buffer);
					}
					fos.close();
					bReturn = true;
				}
				catch(Exception e) 
				{
					bReturn = false;
				}
			}
			else
			{
				bReturn = false;
			}
			
			return bReturn;
		}

		public final String GetWindchillFileSearchResults(int iWebAppId, String sSearchCriteriaDocNo, String sSearchCriteriaDocName, 
				  										  String sSearchCriteriaFileName, String sSearchCriteriaFileDesc, String sSearchCriteriaOriginator, 
				  										  Boolean bHistoric, int iDocNumberOnly, String sUserId)
		{
			Backend be = new Backend(iWebAppId);
			Database DB = be.new Database();
			String[] sParamNames = new String[8];
			Object[] objParamValues = new Object[8];
			String sReturn = "Success||";
			String sReturnDetails = "";
			int i;
			int iCols;
//			String sThisSearchString;
			

/*			if(!sPartDocNo.equals(""))
			{
				sThisSearchString = sPartDocNo;				
			}
			else
			{
				sThisSearchString = sSearchString;
			}
*/
			sParamNames[0] = "@pvchSearchCriteriaDocNo";
			sParamNames[1] = "@pvchSearchCriteriaDocName";
			sParamNames[2] = "@pvchSearchCriteriaFileName";
			sParamNames[3] = "@pvchSearchCriteriaFileDesc";
			sParamNames[4] = "@pvchSearchCriteriaOriginator";
			sParamNames[5] = "@pbHistoric";
			sParamNames[6] = "@piDocumentNumberOnly";
			sParamNames[7] = "@pvchUser";
			
			objParamValues[0] = sSearchCriteriaDocNo;
			objParamValues[1] = sSearchCriteriaDocName;
			objParamValues[2] = sSearchCriteriaFileName;
			objParamValues[3] = sSearchCriteriaFileDesc;
			objParamValues[4] = sSearchCriteriaOriginator;
			objParamValues[5] = bHistoric;
			objParamValues[6] = iDocNumberOnly;
			objParamValues[7] = sUserId;
			
			int iRecordCount = DB.CallStoredProcResultSet("SP_GetWindchillFileSearchResults", sParamNames, objParamValues);

			if (iRecordCount < 0)
			{
				return null;
			}
			else if(iRecordCount > 100 && iDocNumberOnly != 1)
			{
				sReturn = "Failure||There are more than 100 files. Please refine the search criteria and try again";
			}
			else
			{
				try
				{
					ResultSet rs = DB.getResultSet();
					if(iDocNumberOnly == 1)
					{
						iCols = 2;
					}
					else
					{
						iCols = 7;
					}
					for(i=0;i<iRecordCount;i++)
					{
						String sDocNumber = rs.getString("DocNumber");
						String sName = rs.getString("Name");
						
						if(iDocNumberOnly != 1)
						{
							String sFileName = rs.getString("FileName");
							String sStatus = rs.getString("Status");
							int iAttachmentId = rs.getInt("AttachmentId");
							String sDescription = rs.getString("description");
							String sOriginator = rs.getString("Originator");
							
							sReturnDetails += "DocNumber" + i + "=" + sDocNumber + "^" + "Name" + i + "=" + sName + "^" + 
									   "Filename" + i + "=" + sFileName + "^" + "AttachmentId" + i + "=" + iAttachmentId + "^" + 
									   "Status" + i + "=" + sStatus + "^" + "AttachDesc" + i + "=" + sDescription + "^" + "Originator" + i + "=" + sOriginator + "^||";
						}
						else
						{
							sReturnDetails += "DocNumber" + i + "=" + sDocNumber + "^" + "Name" + i + "=" + sName + "^||";
							
						}
						rs.next();
					}
					
					sReturn += "Rows=" + iRecordCount + "^Columns=" + iCols + "^||" + sReturnDetails;
					
				} 
				catch (SQLException e)
				{
					sReturn = "Failure||" + e.getMessage();
					return sReturn;
				}
			}
							
			return sReturn;
		}

		public final String GetWindchillFilesFromFuncLocation(int iWebAppId, String sFuncLocCode, Boolean bHistoric, String sUserId, Boolean bReturnXML)
		{
			Backend be = new Backend(iWebAppId);
			Database DB = be.new Database();
			String[] sParamNames = new String[3];
			Object[] objParamValues = new Object[3];
			String sReturn = "Success||";
			String sReturnDetails = "";
			int i;
			int iCols;
			String sSPName;
			

			sParamNames[0] = "@pvchFunctionLocation";
			sParamNames[1] = "@pbHistoric";
			sParamNames[2] = "@pvchUser";
			objParamValues[0] = sFuncLocCode;
			objParamValues[1] = bHistoric;
			objParamValues[2] = sUserId;
			
			if(bReturnXML)
			{
				sSPName = "SP_GetWindchillDocumentsFromFunctionalLocationXML";
			}
			else
			{
				sSPName = "SP_GetWindchillDocumentsFromFunctionalLocation";
				
			}
			int iRecordCount = DB.CallStoredProcResultSet(sSPName, sParamNames, objParamValues);

			if (iRecordCount < 0)
			{
				return "";
			}
			else
			{
				try
				{
					ResultSet rs = DB.getResultSet();
					if(bReturnXML)
					{
			            sReturn = "<Root><DataSet><Header><Status>Success</Status><TableName>tblFuncLocDocs</TableName>" + rs.getString("XMLOutput");
			            rs.next();
			            sReturn += "</Header><Data>" + rs.getString("XMLOutput") + "</Data></DataSet></Root>";
					}
					else
					{
						iCols = 7;
						for(i=0;i<iRecordCount;i++)
						{
							String sDocNumber = rs.getString("DocNumber");
							String sName = rs.getString("Name");
							String sFileName = rs.getString("FileName");
							String sStatus = rs.getString("Status");
							int iAttachmentId = rs.getInt("AttachmentId");
							String sDesc = rs.getString("description");
							String sOriginator = rs.getString("Originator");

							sReturnDetails += "DocNumber" + i + "=" + sDocNumber + "^" + "Name" + i + "=" + sName + "^" + 
									   "Filename" + i + "=" + sFileName + "^" + "AttachmentId" + i + "=" + iAttachmentId + "^" + 
									   "Status" + i + "=" + sStatus + "^" + "AttachDesc" + i + "=" + sDesc + "^" + "Originator" + i + "=" + sOriginator + "^||";
							rs.next();
						}
						
						sReturn += "Rows=" + iRecordCount + "^Columns=" + iCols + "^||" + sReturnDetails;
					}
					
				} 
				catch (SQLException e)
				{
					if(bReturnXML)
					{
			            sReturn = "<Root><Header><Status>Failure</Status><Message>" + e.getMessage() + "</Message></Header></Root>";
					}
					else
					{

						sReturn = "Failure||" + e.getMessage();
					}
					return sReturn;
				}
			}
							
			return sReturn;
		}

		public final String GetWindchillDocDetails(int iWebAppId, String sDocNo)
		{
			Backend be = new Backend(iWebAppId);
			Database DB = be.new Database();
			String[] sParamNames = new String[1];
			Object[] objParamValues = new Object[1];
			String sReturn = "Success||";
			String sReturnDetails = "";
			

			sParamNames[0] = "@pvchDocumentNumber";
			objParamValues[0] = sDocNo;
			
			int iRecordCount = DB.CallStoredProcResultSet("SP_GetWindchillDocumentDetails", sParamNames, objParamValues);

			if (iRecordCount < 0)
			{
				return null;
			}
			else
			{
				try
				{
					ResultSet rs = DB.getResultSet();
					String sDocNumber = rs.getString("DocNumber");
					String sName = rs.getString("Name");
					String sStatus = rs.getString("Status");
					String sLongDesc = rs.getString("LongDescription");
					String sModifiedBy = rs.getString("ModifiedBy");
					String sOrigDocId = rs.getString("OrigDocId");
					String sJobCode = rs.getString("JobCode");
					String sDocType = rs.getString("DocType");
					String sRevision = rs.getString("Revision");
					int iDocCategory = rs.getInt("DocCategory");
						
						sReturnDetails += "DocNumber" + "=" + sDocNumber + "^" + "Name" + "=" + sName + "^" + 
								   "LongDesc" + "=" + sLongDesc + "^" + "ModifiedBy" + "=" + sModifiedBy + "^" + 
								   "OrigDocId" + "=" + sOrigDocId + "^" + "JobCode" + "=" + sJobCode + "^"+ 
								   "DocType" + "=" + sDocType + "^" + "Revision" + "=" + sRevision + "^" + 
								   "Status" + "=" + sStatus +  "^" + "DocCategory" + "=" + iDocCategory + "^||";
					
					sReturn += "Rows=" + iRecordCount + "^Columns=10^||" + sReturnDetails;
					
				} 
				catch (SQLException e)
				{
					sReturn = "Failure||" + e.getMessage();
					return sReturn;
				}
			}
							
			return sReturn;
		}

		public final Boolean GetWindchillDocExists(int iWebAppId, String sDocNo)
		{
			Backend be = new Backend(iWebAppId);
			Database DB = be.new Database();
			String[] sParamNames = new String[1];
			Object[] objParamValues = new Object[1];
			

			sParamNames[0] = "@pvchDocumentNumber";
			objParamValues[0] = sDocNo;
			
			int iRecordCount = DB.CallStoredProcResultSet("SP_GetWindchillDocumentExists", sParamNames, objParamValues);

			if (iRecordCount <= 0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}

		public String GetAttachments(int iRelatedTableUniqueId, int iAttachmentType)
		{
			Backend be = new Backend();
			Database DB = be.new Database();
			String[] sParamNames = new String[2];
			Object[] objParamValues = new Object[2];
			String sReturn = "Success||";
			String sReturnDetails = "";
			int i;
					
			sParamNames[0] = "@piRelatedTableUniqueId";
			sParamNames[1] = "@piAttachmentType";

			objParamValues[0] = iRelatedTableUniqueId;
			objParamValues[1] = iAttachmentType;
			
			int iRecordCount = DB.CallStoredProcResultSet("SP_GetAttachments", sParamNames, objParamValues);

			if (iRecordCount < 0)
			{
				return null;
			}
			else
			{
				try
				{
					ResultSet rs = DB.getResultSet();
					for(i=0;i<iRecordCount;i++)
					{
						int iId = rs.getInt("Id");
						int iCounter = rs.getInt("Counter");
						String sFilename = rs.getString("Filename");
						String sAttachmentExtension = rs.getString("AttachmentExtension");
						String sDescription = rs.getString("Description");
						String sComments = rs.getString("Comments");
						
						sReturnDetails += "Id" + i + "=" + iId + "^" +  "Counter" + i + "=" + iCounter + "^" +  "Filename" + i + "=" + sFilename + "^" +  
										   "AttachmentExtension" + i + "=" + sAttachmentExtension + "^" +  "Description" + i + "=" + sDescription + "^" +  "Comments" + i + "=" + sComments +"^||";
						rs.next();
					}
					
					sReturn += "Rows=" + iRecordCount + "^Columns=6^||" + sReturnDetails;
					
				} 
				catch (SQLException e)
				{
					sReturn = "Failure||" + e.getMessage();
					return sReturn;
				}
			}
							
			return sReturn;
		}
		
		public FileOutputStream Get_File_For_Writing(String sReturnFileName)
		{
			try
			{
			Environment env =  new Environment();
			Utilities util = new Utilities();
			String sPath = env.GetDeploymentPath();
			String sFullFile = sPath + "\\" + sReturnFileName;
			sFullFile = util.StripMultiBackslashes(sFullFile);
			File fle = new File(sFullFile);
			if(!fle.exists())
			{
				fle.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(fle, false);

			return fos;
			}
			catch(Exception ex)
			{
				return null;
			}
		}
	
	}
	
	public class Utilities
	{
		public boolean isNumeric(String str)
		{
		  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
		}
		
		public double RoundDouble(double dUnrounded, int iDecPlaces)
		{
			if(iDecPlaces >= 0 && iDecPlaces <=10)
			{
				String sFormat = "#0.";
				switch(iDecPlaces)
				{
					case 0:
					default:
						sFormat = "#0.";
						break;
					case 1:
						sFormat = "#0.0";
						break;
					case 2:
						sFormat = "#0.00";
						break;
					case 3:
						sFormat = "#0.000";
						break;
					case 4:
						sFormat = "#0.0000";
						break;
					case 5:
						sFormat = "#0.00000";
						break;
					case 6:
						sFormat = "#0.000000";
						break;
					case 7:
						sFormat = "#0.0000000";
						break;
					case 8:
						sFormat = "#0.00000000";
						break;
					case 9:
						sFormat = "#0.000000000";
						break;
					case 10:
						sFormat = "#0.0000000000";
						break;
				}
				DecimalFormat decFormat = new DecimalFormat(sFormat);
				String result = decFormat.format(dUnrounded);
				double dResult = Double.parseDouble(result);
				return  dResult;				
			}
			else
			{
				return (double)((int)dUnrounded / (int)(10.0 * iDecPlaces) * iDecPlaces);
			}
		}
		
		public final String Get_FilenameOnly_From_FullPath(String sPathAndFilename)
		{
			String sFileNameOnly = null;
			int iStart = 0;
			int iLength = 0;

			iStart = sPathAndFilename.lastIndexOf("\\") + 1;
			iLength = (sPathAndFilename == null ? 0 : sPathAndFilename.length()) - iStart;
			sFileNameOnly = sPathAndFilename.substring(iStart, iStart + iLength);

			return sFileNameOnly;
		}
		
		public final String GetFileExtension(String sFilenameOrPathandFileName)
		{
			String sExt = null;
			sExt = sFilenameOrPathandFileName.substring(sFilenameOrPathandFileName.lastIndexOf(".") + 1);
			return sExt;
		}
		
		public final String GetFileWithoutExtension(String sFilenameOrPathandFileName)
		{
			String sExt = null;
			sExt = sFilenameOrPathandFileName.substring(0, sFilenameOrPathandFileName.lastIndexOf("."));
			return sExt;
		}
		
		public final String StripMultiSlashes(String sInputString)
		{
			String sReturn = null;
			
			sInputString = sInputString.replace("\\", "/");

			sReturn = sInputString.replace("http://", "http:!!");
			sReturn = sReturn.replace("https://", "https:!!");
			sReturn = sReturn.replace("///////", "/");
			sReturn = sReturn.replace("//////", "/");
			sReturn = sReturn.replace("/////", "/");
			sReturn = sReturn.replace("////", "/");
			sReturn = sReturn.replace("///", "/");
			sReturn = sReturn.replace("//", "/");
			sReturn = sReturn.replace("http:!!", "http://");
			sReturn = sReturn.replace("https:!!", "https://");

			return sReturn;
		}

		public final String StripMultiBackslashes(String sInputString)
		{
			String sReturn = null;

			sInputString = sInputString.replace("/","\\");

			if (sInputString.startsWith("\\\\"))
			{
				sReturn = "!!" + sInputString.substring(2);
			}
			else
			{
				sReturn = sInputString;
			}
			sReturn = sReturn.replace("\\\\\\\\\\\\\\", "\\");
			sReturn = sReturn.replace("\\\\\\\\\\\\", "\\");
			sReturn = sReturn.replace("\\\\\\\\\\", "\\");
			sReturn = sReturn.replace("\\\\\\\\", "\\");
			sReturn = sReturn.replace("\\\\\\", "\\");
			sReturn = sReturn.replace("\\\\", "\\");

			return sReturn;
		}
		
		public final ArrayList<ArrayList<String>> Extract_Paired_Values(String sPairedValues)
		{
			int iStart = 0;
			int iEnd = 0;
			int iLength = 0;
			String sRow = null;
			String sName = null;
			String sValue = null;
			ArrayList<String> sNameArray = new ArrayList<String>();
			ArrayList<String> sValueArray = new ArrayList<String>();
			ArrayList<ArrayList<String>> sLocalArray = new ArrayList<ArrayList<String>>();

			//Find the 1st ^ symbol
			iEnd = 0;
			if ((sPairedValues == null ? 0 : sPairedValues.length()) > 0)
			{
				iEnd = sPairedValues.indexOf("^") + 1;
			}

			iStart = 1;

			while (iEnd > 0)
			{
				iEnd = sPairedValues.indexOf("^", iStart - 1) + 1;
				iLength = iEnd - iStart + 1;

				sRow = sPairedValues.substring(iStart - 1, iStart - 1 + iLength);

				iEnd = sPairedValues.indexOf("=") + 1;
				iLength = iEnd - iStart;
				sName = sRow.substring(iStart - 1, iStart - 1 + iLength);

				iStart = sPairedValues.indexOf("=") + 1 + 1;
				iEnd = sRow == null ? 0 : sRow.length();
				iLength = iEnd - iStart;
				sValue = sRow.substring(iStart - 1, iStart - 1 + iLength);

				sNameArray.add(sName);
				sValueArray.add(sValue);

				iStart = sPairedValues.indexOf("^") + 1 + 1;
				iLength = (sPairedValues == null ? 0 : sPairedValues.length()) - iStart + 1; //The extra 1 is to get the last ^ symbol

				if (iLength > 0)
				{
					sPairedValues = sPairedValues.substring(iStart - 1, iStart - 1 + iLength);
					iEnd = sPairedValues.indexOf("^") + 1;
				}
				else
				{
					iEnd = 0;
				}

				iStart = 1;
			}

			sLocalArray.add(sNameArray);
			sLocalArray.add(sValueArray);

			return sLocalArray;
		}

		public final String[] Extract_Values(String sValues)
		{
			String[] sLocalArray = sValues.split(Pattern.quote("^"));
			return sLocalArray;
		}

		public final ReturnNextId Get_Next_Id(String sColumnName)
		{
			ResultSet rst = null;
			String sSQL = null;
			long iNextID = 0;
			int iSequenceLength = 0;
			String sPrefix = "";
			Database DB = new Database();
			int iRecords = -1;
			ReturnNextId rtn = new ReturnNextId();

			sSQL = "SELECT Counter, isnull(PrefixCode,'') as PrefixCode, isnull(SequenceLength,0) as SequenceLength FROM tblNextId WHERE ColumnName = '" + sColumnName + "'";
			rst = DB.OpenRecordset(sSQL);

			try 
			{
				iRecords = DB.getRowCount(rst);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			if (iRecords > 0)
			{
				try
				{
					iNextID = rst.getInt("Counter");	
					if(rst.wasNull())
					{
						iNextID = -1;
					}
					
					sPrefix = rst.getString("PrefixCode");	
					if(rst.wasNull())
					{
						sPrefix = "";
					}

					iSequenceLength = rst.getInt("SequenceLength");	
					if(rst.wasNull())
					{
						iSequenceLength = -1;
					}
}
				catch(Exception e) 
				{
					iNextID = -1;
					iSequenceLength = -1;
				}
			}
			else
			{
				iNextID = -1;
			}

			if (iNextID != -1)
			{
				//Now increment for the next time we select
				sSQL = "UPDATE tblNextId SET Counter = Counter+1 WHERE ColumnName = '" + sColumnName + "'";
				DB.ExecuteSQL(sSQL);
			}

			try 
			{
				rst.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}

			rtn.iNextId = iNextID;
			rtn.iSequenceLength = iSequenceLength;
			rtn.sPrefix = sPrefix;
			
			return rtn;

		}

		public ReturnClassString GetConstantValue(String sConstantName)
		{
			ResultSet rst = null;
			String sSQL = null;
			String sValue = "";
			Database DB = new Database();
			int iRecords = -1;
			ReturnClassString rtn = new ReturnClassString();

			sSQL = "SELECT isnull(Value,'') as Value FROM tblConstants WHERE Name = '" + sConstantName + "'";
			rst = DB.OpenRecordset(sSQL);

			try 
			{
				iRecords = DB.getRowCount(rst);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			if (iRecords > 0)
			{
				try
				{
					sValue = rst.getString("Value");
					rtn.sReturnedString = sValue;
					rtn.sRtnMsg = "";
					rtn.bValue = true;
				}
				catch(Exception e) 
				{
					sValue = e.getMessage();
					rtn.bValue = false;
					rtn.sRtnMsg = sValue;
					rtn.sReturnedString = "";
					
				}
			}
			else
			{
				rtn.bValue = false;
				rtn.sRtnMsg = "The constant name " + sConstantName + " does not exist in tblConstants";
				rtn.sReturnedString = "";
			}


			try 
			{
				rst.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			
			return rtn;

		}

		public String generateRandomPassword()
		{
		      // Pick from some letters that won't be easily mistaken for each
		      // other. So, for example, omit o O and 0, 1 l and L.
		      String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";
		      Random RANDOM = new SecureRandom();
		      
		      String pw = "";
		      for (int i=0; i<8; i++)
		      {
		          int index = (int)(RANDOM.nextDouble()*letters.length());
		          pw += letters.substring(index, index+1);
		      }
		      return pw;
		}
		
		public byte[] hexToBytes(String hexString) {
		     HexBinaryAdapter adapter = new HexBinaryAdapter();
		     byte[] bytes = adapter.unmarshal(hexString);
		     return bytes;
		}
		
		public String convertHexToString(String hex)
		{
//			  byte[] hexasbyte = hexToBytes(hex);
			  StringBuilder sb = new StringBuilder();
			  StringBuilder temp = new StringBuilder();
//			  String sOUt2 = new String(hexasbyte);
			  
			  //49204c6f7665204a617661 split into two characters 49, 20, 4c...
			  for( int i=0; i<hex.length()-1; i+=2 ){
				  
			      //grab the hex in pairs
			      String output = hex.substring(i, (i + 2));
			      //convert hex to decimal
			      int decimal = Integer.parseInt(output, 16);
			      //convert the decimal to character
			      if(decimal >= 32 && decimal <=126)
			    	  sb.append((char)decimal);
				  
			      temp.append(decimal);
			  }
			  
			  return sb.toString();
		}
		
		public int GetJobFromDoc(String sDocNo)
		{
			String sJob = sDocNo.substring(0, 4);
			if(isNumeric(sJob))
				return Integer.valueOf(sJob);
			else
			{
				sJob = sJob.substring(0,3);
				if(isNumeric(sJob))
					return Integer.valueOf(sJob);
				else
				{
					sJob = sJob.substring(0,2);
					if(isNumeric(sJob))
						return Integer.valueOf(sJob);
					else
						return -1;
				}
			}
		}
		
		public String removeDigits(String sString)
		{
			return sString.replaceAll("\\d","");
		}
}

	public class User
	{
		public class UserDetails
		{
			public String sUsername;
			public String sFullname;
			public String sPassword;
			public String sEmail;
			public String sErrorMsg;
			
		}
		
		public ReturnClassInt AddUser(String sUsername, String sFullname, String sPassword, String sEmail, int iWebAppId)
		{
			iWebApp = iWebAppId;
			Database DB = new Database();
			String[] sParamNames = new String[4];
			Object[] objParamValues = new Object[4];
			String sHashedPW = BCrypt.hashpw(sPassword, BCrypt.gensalt());
			ReturnClassInt rtnClass = new ReturnClassInt();
			
			sParamNames[0] = "@pvchUser";
			sParamNames[1] = "@pvchPassword";
			sParamNames[2] = "@pvchEmail";
			sParamNames[3] = "@pvchFullname";
			
			objParamValues[0] = sUsername;
			objParamValues[1] = sHashedPW;
			objParamValues[2] = sEmail;
			objParamValues[3] = sFullname;
			
			
			int iRecords = DB.CallStoredProcReturnValue("SP_SetUser", sParamNames, objParamValues);
			
			if(iRecords > 0)
			{
				rtnClass.bValue = true;
				rtnClass.iValue = iRecords;
				return rtnClass;
			}
			
			rtnClass.bValue = false;
			rtnClass.iValue = iRecords;
			return rtnClass;
			
		}
		
		public String GetUserFullName(String sUsername, int iWebAppId)
		{
			UserDetails rtnUD = GetUser(sUsername, iWebAppId);
			return rtnUD.sFullname;
			
		}
		
		public UserDetails GetUser(String sUsername, int iWebAppId)
		{
			Backend be = new Backend(iWebAppId);
			Database DB = be.new Database();
			UserDetails rtnUD = new UserDetails();
			String[] sParamNames = new String[1];
			Object[] objParamValues = new Object[1];
			Log log = new Log();

			sParamNames[0] = "@pvchUser";
			objParamValues[0] = sUsername;
			
			try
			{
				log.LogMsg("Got to A");
				int iRecordCount = DB.CallStoredProcResultSet("SP_GetUserDetails", sParamNames, objParamValues);
				log.LogMsg("Got to B");

				if (iRecordCount <= 0)
				{
					rtnUD.sErrorMsg = "User " + sUsername + " does not exist.";
					return rtnUD;
				}
				else
				{					
					log.LogMsg("Got to C");
					ResultSet rs = DB.getResultSet();
					log.LogMsg("Got to D");
					rtnUD.sUsername = rs.getString("Username");
					rtnUD.sFullname = rs.getString("Fullname");
					log.LogMsg("Got to E");
					rtnUD.sPassword = rs.getString("Password");
					rtnUD.sEmail = rs.getString("Email");
					rtnUD.sErrorMsg = "";
					log.LogMsg("Got to F");
					return rtnUD;
				}
				
			}
			catch(Exception ex)
			{
				rtnUD.sErrorMsg = ex.getMessage();
				return rtnUD;
			}

		}
		
		public String GetAllUsers(int iWebAppId)
		{
			Backend be = new Backend(iWebAppId);
			Database DB = be.new Database();
			String[] sParamNames = new String[0];
			Object[] objParamValues = new Object[0];
			String sReturn = "Success||";
			String sReturnDetails = "";
			int i;
					
			int iRecordCount = DB.CallStoredProcResultSet("SP_GetUsers", sParamNames, objParamValues);

			if (iRecordCount < 0)
			{
				return null;
			}
			else
			{
				try
				{
					ResultSet rs = DB.getResultSet();
					for(i=0;i<iRecordCount;i++)
					{
						String sUsername = rs.getString("Username");
						int iUserId = rs.getInt("UserId");
						
						sReturnDetails += "Username" + i + "=" + sUsername + "^" +  "UserId" + i + "=" + iUserId +"^||";
						rs.next();
					}
					
					sReturn += "Rows=" + iRecordCount + "^Columns=2^||" + sReturnDetails;
					
				} 
				catch (SQLException e)
				{
					sReturn = "Failure||" + e.getMessage();
					return sReturn;
				}
			}
							
			return sReturn;
		}

		public String GetAllRoles(int iWebAppId)
		{
			Backend be = new Backend(iWebAppId);
			Database DB = be.new Database();
			String[] sParamNames = new String[0];
			Object[] objParamValues = new Object[0];
			String sReturn = "Success||";
			String sReturnDetails = "";
			int i;
					
			int iRecordCount = DB.CallStoredProcResultSet("SP_GetRoles", sParamNames, objParamValues);

			if (iRecordCount < 0)
			{
				return null;
			}
			else
			{
				try
				{
					ResultSet rs = DB.getResultSet();
					for(i=0;i<iRecordCount;i++)
					{
						String sRolename = rs.getString("RoleDescription");
						int iRoleId = rs.getInt("RoleId");
						
						sReturnDetails += "RoleDescription" + i + "=" + sRolename + "^" +  "RoleId" + i + "=" + iRoleId +"^||";
						rs.next();
					}
					
					sReturn += "Rows=" + iRecordCount + "^Columns=2^||" + sReturnDetails;
					
				} 
				catch (SQLException e)
				{
					sReturn = "Failure||" + e.getMessage();
					return sReturn;
				}
			}
							
			return sReturn;
		}
		
		public ReturnClassInt SetRole(int iRole, String sRoleDescription, String sRoleLongDescription, int iWebAppId)
		{		 
				Backend be = new Backend(iWebAppId);
				Database DB = be.new Database();
				String[] sParamNames = new String[3];
				Object[] objParamValues = new Object[3];
				ReturnClassInt rtn = new ReturnClassInt();

				sParamNames[0] = "@piRoleId"; 
				sParamNames[1] = "@pvchRole"; 
				sParamNames[2] = "@pvchRoleDesc"; 

				objParamValues[0] = iRole; 
				objParamValues[1] = sRoleDescription; 
				objParamValues[2] = sRoleLongDescription; 

				int iReturn = DB.CallStoredProcReturnValue("SP_SetRole", sParamNames, objParamValues);
				
				if(iReturn < 0)
				{
					rtn.iValue = iReturn;
					rtn.sRtnMsg = "Cannot save role " + sRoleDescription; 
					rtn.bValue = false;
					return rtn;		
				}
				else
				{
					rtn.iValue = iReturn;
					rtn.sRtnMsg = ""; 
					rtn.bValue = true;
					return rtn;		
				}
		}

		public ReturnClassInt DeleteRole(int iRoleId, int iWebAppId)
		{		 
			Backend be = new Backend(iWebAppId);
			Database DB = be.new Database();
			String[] sParamNames = new String[1];
			Object[] objParamValues = new Object[1];
			ReturnClassInt rtn = new ReturnClassInt();

			
			sParamNames[0] = "@piRoleId"; 
			objParamValues[0] = iRoleId; 

			int iReturn = DB.CallStoredProc("SP_DeleteRole", sParamNames, objParamValues);
			
			if(iReturn < 0)
			{
				rtn.iValue = iReturn;
				rtn.sRtnMsg = "Cannot delete role with id " + iRoleId; 
				rtn.bValue = false;
				return rtn;		
			}

			//If we get to here the delete has been successful
			rtn.iValue = 0;
			rtn.sRtnMsg = ""; 
			rtn.bValue = true;
			return rtn;		
		 }

		public ReturnClassInt DeleteUser(String sUser, int iWebAppId)
		{		 
			Backend be = new Backend(iWebAppId);
			Database DB = be.new Database();
			String[] sParamNames = new String[1];
			Object[] objParamValues = new Object[1];
			ReturnClassInt rtn = new ReturnClassInt();

			
			sParamNames[0] = "@pvchUserId"; 
			objParamValues[0] = sUser; 

			int iReturn = DB.CallStoredProc("SP_DeleteUser", sParamNames, objParamValues);
			
			if(iReturn < 0)
			{
				rtn.iValue = iReturn;
				rtn.sRtnMsg = "Cannot delete user " + sUser; 
				rtn.bValue = false;
				return rtn;		
			}

			//If we get to here the delete has been successful
			rtn.iValue = 0;
			rtn.sRtnMsg = ""; 
			rtn.bValue = true;
			return rtn;		
		}

		public ReturnClassInt DeleteUserRoles(String sUsername, int iWebAppId)
		{		 
			Backend be = new Backend(iWebAppId);
			Database DB = be.new Database();
			String[] sParamNames = new String[1];
			Object[] objParamValues = new Object[1];
			ReturnClassInt rtn = new ReturnClassInt();

			
			sParamNames[0] = "@pvchUser"; 
			objParamValues[0] = sUsername; 

			int iReturn = DB.CallStoredProc("SP_DeleteUserRoles", sParamNames, objParamValues);
			
			if(iReturn < 0)
			{
				rtn.iValue = iReturn;
				rtn.sRtnMsg = "Cannot delete user roles for user " + sUsername; 
				rtn.bValue = false;
				return rtn;		
			}

			//If we get to here the delete has been successful
			rtn.iValue = 0;
			rtn.sRtnMsg = ""; 
			rtn.bValue = true;
			return rtn;		
		}

		public ReturnClassInt DeleteRoleUsers(int iRoleId, String sRoleDesc, int iWebAppId)
		{		 
			Backend be = new Backend(iWebAppId);
			Database DB = be.new Database();
			String[] sParamNames = new String[1];
			Object[] objParamValues = new Object[1];
			ReturnClassInt rtn = new ReturnClassInt();

			
			sParamNames[0] = "@piRoleId"; 
			objParamValues[0] = iRoleId; 

			int iReturn = DB.CallStoredProc("SP_DeleteRoleUsers", sParamNames, objParamValues);
			
			if(iReturn < 0)
			{
				rtn.iValue = iReturn;
				rtn.sRtnMsg = "Cannot delete users for role " + sRoleDesc; 
				rtn.bValue = false;
				return rtn;		
			}

			//If we get to here the delete has been successful
			rtn.iValue = 0;
			rtn.sRtnMsg = ""; 
			rtn.bValue = true;
			return rtn;		
		}

		public ReturnClassInt SetUserRole(int iUserId, int iRole, String sUsername, String sRoleDescription, int iWebAppId)
		{		 
				Backend be = new Backend(iWebAppId);
				Database DB = be.new Database();
				String[] sParamNames = new String[2];
				Object[] objParamValues = new Object[2];
				ReturnClassInt rtn = new ReturnClassInt();

				sParamNames[0] = "@piUserId"; 
				sParamNames[1] = "@piRoleId"; 

				objParamValues[0] = iUserId; 
				objParamValues[1] = iRole; 

				int iReturn = DB.CallStoredProc("SP_SetUserRole", sParamNames, objParamValues);
				
				if(iReturn < 0)
				{
					rtn.iValue = iReturn;
					rtn.sRtnMsg = "Cannot save role " + sRoleDescription + " for user " + sUsername; 
					rtn.bValue = false;
					return rtn;		
				}
				else
				{
					rtn.iValue = iReturn;
					rtn.sRtnMsg = ""; 
					rtn.bValue = true;
					return rtn;		
				}
		}

		public String GetUserRoles(String sUserId, boolean bFull, int iWebAppId)
		{
			Backend be = new Backend(iWebAppId);
			Database DB = be.new Database();
			String[] sParamNames = new String[1];
			Object[] objParamValues = new Object[1];
			String sReturn = "Success^";
			int i;
					
			sParamNames[0] = "@pvchUsername"; 

			objParamValues[0] = sUserId; 

			int iRecordCount = DB.CallStoredProcResultSet("SP_GetUserRoles", sParamNames, objParamValues);

			if (iRecordCount < 0)
			{
				return null;
			}
			else
			{
				try
				{
					ResultSet rs = DB.getResultSet();
					for(i=0;i<iRecordCount;i++)
					{
						int iRoleId = rs.getInt("RoleId");
						String sRoleDesc = rs.getString("RoleDescription");
						
						sReturn += "RoleId" + i + "=" + iRoleId +"^RoleDescription" + i + "=" + sRoleDesc +"^";
						rs.next();
					}
										
				} 
				catch (SQLException e)
				{
					sReturn = "Failure^" + e.getMessage() + "^";
					return sReturn;
				}
			}
							
			return sReturn;
		}
		
		public String GetRoleUsers(int iRoleId, boolean bFull, int iWebAppId)
		{
			Backend be = new Backend(iWebAppId);
			Database DB = be.new Database();
			String[] sParamNames = new String[1];
			Object[] objParamValues = new Object[1];
			String sReturn = "Success^";
			int i;
					
			sParamNames[0] = "@piRoleId"; 

			objParamValues[0] = iRoleId; 

			int iRecordCount = DB.CallStoredProcResultSet("SP_GetRoleUsers", sParamNames, objParamValues);

			if (iRecordCount < 0)
			{
				return null;
			}
			else
			{
				try
				{
					ResultSet rs = DB.getResultSet();
					for(i=0;i<iRecordCount;i++)
					{
						int iUserId = rs.getInt("UserId");
						String sUsername = rs.getString("Username");
						
						sReturn += "UserId" + i + "=" + iUserId +"^Username" + i + "=" + sUsername +"^";
						rs.next();
					}
										
				} 
				catch (SQLException e)
				{
					sReturn = "Failure^" + e.getMessage() + "^";
					return sReturn;
				}
			}
							
			return sReturn;
		}
		
		public String[] GetUserRolesArray(String sUserId, boolean bFull, int iWebAppId)
		{
			Backend be = new Backend(iWebAppId);
			Database DB = be.new Database();
			String[] sParamNames = new String[1];
			Object[] objParamValues = new Object[1];
			int i;
					
			sParamNames[0] = "@pvchUsername"; 

			objParamValues[0] = sUserId; 

			int iRecordCount = DB.CallStoredProcResultSet("SP_GetUserRoles", sParamNames, objParamValues);

			if (iRecordCount < 0)
			{
				return null;
			}
			else
			{
				try
				{
					String[] sReturn = new String[iRecordCount];
					ResultSet rs = DB.getResultSet();
					for(i=0;i<iRecordCount;i++)
					{
//						int iRoleId = rs.getInt("RoleId");
						String sRoleDesc = rs.getString("RoleDescription");
						
						sReturn[i] = sRoleDesc;
						rs.next();
					}
					return sReturn;
										
				} 
				catch (SQLException e)
				{
					String[] sReturn = new String[1];
					sReturn[0] = "Failure^" + e.getMessage() + "^";
					return sReturn;
				}
			}
							
		}

		public boolean IsUserInRole(String sUserId, String sRoleId, int iWebAppId)
		{
			String[] arrRoles = GetUserRolesArray(sUserId, true, iWebAppId);
			
			for(int i = 0; i< arrRoles.length; i++)
			{
				if(sRoleId.compareTo(arrRoles[i]) == 0)
					return true;
			}
			
			return false;
		}
	}

	public class XML
	{
		public ReturnClassString CreateXmlString(String sSQL, String sRowIdentifierName, String sResultsetName) 
		{
			ReturnClassString clsRtn = new ReturnClassString();
			
			try 
			{
				ResultSet rst = null;
				Database DB = new Database();
				int iRecords = 0;
				String sOutput = "";

				if(sResultsetName== null || sResultsetName.equals(""))
				{
					sResultsetName = "ResultSet";
				}

				if(sRowIdentifierName== null || sRowIdentifierName.equals(""))
				{
					sRowIdentifierName = "Row";
				}
				sSQL = "select(" + sSQL + " for xml raw('" + sRowIdentifierName + "'), root('" + sResultsetName + "'), elements ) as XMLOutput";
				rst = DB.OpenRecordset(sSQL);
				iRecords = DB.getRowCount(rst);

//		        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//		        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//		        Document doc = dBuilder.newDocument();

		        if (iRecords > 0)
				{
					sOutput = rst.getString("XMLOutput");
				}
				
//		        // root element
//		        Element rootElement = doc.createElement("cars");
//		        doc.appendChild(rootElement);
//	
//		        //  supercars element
//		        Element supercar = doc.createElement("supercars");
//		        rootElement.appendChild(supercar);
//	
//		        // setting attribute to element
//		        Attr attr = doc.createAttribute("company");
//		        attr.setValue("Ferrari");
//		        supercar.setAttributeNode(attr);
//	
//		        // carname element
//		        Element carname = doc.createElement("carname");
//		        Attr attrType = doc.createAttribute("type");
//		        attrType.setValue("formula one");
//		        carname.setAttributeNode(attrType);
//		        carname.appendChild(
//		        doc.createTextNode("Ferrari 101"));
//		        supercar.appendChild(carname);
//	
//		         Element carname1 = doc.createElement("carname");
//		         Attr attrType1 = doc.createAttribute("type");
//		         attrType1.setValue("sports");
//		         carname1.setAttributeNode(attrType1);
//		         carname1.appendChild(
//		         doc.createTextNode("Ferrari 202"));
//		         supercar.appendChild(carname1);
//	
//		         // write the content into xml file
//		         TransformerFactory transformerFactory = TransformerFactory.newInstance();
//		         Transformer transformer = transformerFactory.newTransformer();
//		         DOMSource source = new DOMSource(doc);
//		         StreamResult result =
//		         new StreamResult(new File(sFilename));
//		         transformer.transform(source, result);

		         clsRtn.bValue = true;
		         clsRtn.sRtnMsg = "";
		         clsRtn.sReturnedString = sOutput;
		         return clsRtn;
		    } 
			catch (Exception ex) 
			{
		         clsRtn.bValue = false;
		         clsRtn.sRtnMsg = ex.getMessage().toString();
		         clsRtn.sReturnedString = "";
		         return clsRtn;
		    }
		}
	}
	
	public class Excel
	{
		public ReturnClassExcel WriteExcelFile(int iWebAppId, String sSPName, String[] sParamNames, Object[] objParamValues, boolean bIncludeHeader, 
											  String sFileNameOnly, String sExistingTemplate, int iSheet, int iStartRow, int iStartColumn)
		{
			iWebApp = iWebAppId;
			Database DB = new Database();
			ReturnClassExcel rtn = new ReturnClassExcel();
			Environment env = new Environment();
			Utilities utils = new Utilities();
			boolean bExisting = false;
			
            try 
            {
				int iRecordCount = DB.CallStoredProcResultSet(sSPName, sParamNames, objParamValues);
				ResultSet rs = DB.getResultSet();
	
				//Blank workbook
				String sFileName = env.GetBaseWebpath() + "\\temp\\" + sFileNameOnly;
		        sFileName = utils.StripMultiBackslashes(sFileName);
		        File xlfile = new File(sFileName);
		        if(xlfile.exists())
		        	xlfile.delete();
		        
		        XSSFWorkbook workbook = null;
		        XSSFSheet sheet = null;
		        if(sExistingTemplate != null)
		        {
		        	if(!sExistingTemplate.equals(""))
		        	{
		        		sExistingTemplate = env.GetBaseWebpath() + "\\templates\\" + sExistingTemplate;
		        		File xlnewfile = new File(sFileName);
		        		File xltemplate = new File(sExistingTemplate);
		        		FileUtils.copyFile(xltemplate, xlnewfile);
			        	workbook = new XSSFWorkbook(new FileInputStream(xlnewfile));
			        	sheet = workbook.getSheetAt(iSheet - 1);
			        	bExisting = true;
		        	}
		        	else
		        	{
			        	workbook = new XSSFWorkbook();		        		
			        	sheet = workbook.createSheet();
		        	}
		        }
		        else
		        {
		        	workbook = new XSSFWorkbook();
		        	sheet = workbook.createSheet();
		        }
		        		         		        
		        //Iterate over data and write to sheet
		        int iColCount = DB.getResultSetColumnCount();
		        for (int i = 0; i<iRecordCount; i++)
		        {
		        	XSSFRow row = sheet.createRow(i + iStartRow -1);
		            for (int j =0 ; j< iColCount; j++)
		            {
		            	XSSFCell cell = row.createCell(j + iStartColumn - 1);
		            	Object obj = rs.getObject(j+1);
		            	if(obj instanceof String)
		                    cell.setCellValue((String)obj);
		                else if(obj instanceof Integer)
		                    cell.setCellValue((Integer)obj);
		                else if (obj instanceof Double)
		                    cell.setCellValue((Double)obj);
		                else if (obj instanceof Date)
		                    cell.setCellValue((Date)obj);
		                else if (obj instanceof Boolean)
		                    cell.setCellValue((Boolean)obj);
		                else
		                    cell.setCellValue(obj.toString());		                	
		            }
						rs.next();
		        }

		        //Write the workbook in file system
	            FileOutputStream out = null;
	            if(bExisting)
	            	out = new FileOutputStream(new File(sFileName));
	            else
	            	out = new FileOutputStream(new File(sFileName));
	            workbook.write(out);
	            out.close();
	            workbook.close();
	            rtn.sFile = sFileName;
	            rtn.bValue = true;
	        }
	        catch (Exception e)
	        {
	            rtn.sFile = "";
            	rtn.bValue = false;
            	rtn.sRtnMsg = e.getMessage();
	        }
            
        	return rtn;
		}
		
	}
	
	public class Email
	{
		public ReturnClassBool email_message(String sSubject, String sBody, String[] sAttachments, String sRecipients, String sCCRecipients, String sBCCRecipients)
		{
			ReturnClassBool rtn = new ReturnClassBool();
			Utilities utils = new Utilities();
			// Recipient's email ID needs to be mentioned.
			String[] sRecipientList = sRecipients.split(";");
			String[] sCCRecipientList = sCCRecipients.split(";");
			String[] sBCCRecipientList = sBCCRecipients.split(";");

			// Sender's email ID needs to be mentioned
			String from = "ben.messenger@fronesis.com";

//		      String host = "filter.regain-1.mailguard.com.au";
			String host = "RSSBS11.rs.local";

		      // Get system properties
		    Properties properties = System.getProperties();

		      // Setup mail server
		    properties.setProperty("mail.smtp.host", host);

		      // Get the default Session object.
		    Session session = Session.getDefaultInstance(properties);

		      try
		      {
		         // Create a default MimeMessage object.
		         MimeMessage message = new MimeMessage(session);
	
		         // Set From: header field of the header.
		         message.setFrom(new InternetAddress(from));
	
		         // Set To: header field of the header.
		         if(sRecipients.length() > 0)
		         {
			         for(int i = 0; i < sRecipientList.length; i++)
			         {
			        	 if(sRecipientList[i].compareTo("") != 0)
			        		 message.addRecipient(Message.RecipientType.TO, new InternetAddress(sRecipientList[i]));
			         }
	
			         if(sCCRecipients.length() > 0)
			         {
				         for(int i = 0; i < sCCRecipientList.length; i++)
				         {
				        	 if(sCCRecipientList[i].compareTo("") != 0)
				        		 message.addRecipient(Message.RecipientType.CC, new InternetAddress(sCCRecipientList[i]));
				         }
			         }
			         
			         if(sBCCRecipients.length() > 0)
			         {
				         for(int i = 0; i < sBCCRecipientList.length; i++)
				         {
				        	 if(sBCCRecipientList[i].compareTo("") != 0)
				        		 message.addRecipient(Message.RecipientType.BCC, new InternetAddress(sBCCRecipientList[i]));
				         }
			         }
	
			         // Set Subject: header field
			         message.setSubject(sSubject);
		
			      // Create the message part 
			         BodyPart messageBodyPart = new MimeBodyPart();
		
			         // Fill the message
			         messageBodyPart.setText(sBody);
			         
			         // Create a multipart message
			         Multipart multipart = new MimeMultipart();
		
			         // Set text message part
			         multipart.addBodyPart(messageBodyPart);
		
			         // Part two is attachment
			         if(sAttachments != null)
			         {
				         for(int j = 0; j< sAttachments.length; j++)
				         {
					         messageBodyPart = new MimeBodyPart();
					         String filename = sAttachments[j];
					         String sFileNameOnly = utils.Get_FilenameOnly_From_FullPath(filename);
					         DataSource source = new FileDataSource(filename);
					         messageBodyPart.setDataHandler(new DataHandler(source));
					         messageBodyPart.setFileName(sFileNameOnly);
					         multipart.addBodyPart(messageBodyPart);
				         }
			         }
			         
			         // Send the actual HTML message, as big as you like
			         message.setContent(multipart);
		
			         // Send message
			         Transport.send(message);
		         }
		         rtn.bValue = true;
		         return rtn;
		      }
		      catch (MessagingException mex) 
		      {
		    	rtn.bValue = false;
		    	rtn.sRtnMsg = mex.getMessage();
		    	return rtn;
		      }
		}
	}
	
	public class DateClass
	{
		public String GetDateTimeStamp(String sFormat)
		{
			SimpleDateFormat sdfDate = new SimpleDateFormat(sFormat);
		    java.util.Date now = new java.util.Date();
		    String strDate = sdfDate.format(now);
		    return strDate;		
		}
		
		public String FormatDate(java.util.Date date, String sFormat)
		{
			SimpleDateFormat sdfDate = new SimpleDateFormat(sFormat);
		    String strDate = sdfDate.format(date);
		    return strDate;		
		}

		public String FormatTimestamp(java.sql.Timestamp date, String sFormat)
		{
			SimpleDateFormat sdfDate = new SimpleDateFormat(sFormat);
		    String strDate = sdfDate.format(date);
		    return strDate;		
		}
		
		public Timestamp ConvertFromGMTToAEST(Timestamp dtInputDateTime)
		{
			Calendar cal = Calendar.getInstance();
	        TimeZone tz2 = cal.getTimeZone();
	        Calendar cal2 = Calendar.getInstance(tz2);
	        int iOffset = tz2.getOffset(cal2.getTime().getTime());
	        java.util.Date startDate2 = DateUtils.addMilliseconds(dtInputDateTime, iOffset * 1);
	        java.sql.Timestamp sq = new java.sql.Timestamp(startDate2.getTime());
	        return sq;
		}
 		public java.sql.Timestamp GetDateFromString(String sDate, String sFormat)
		{
			SimpleDateFormat sdfDate = new SimpleDateFormat(sFormat);
			java.sql.Timestamp datetime =  null;
			java.util.Date date =  null;
			try
			{
				date = sdfDate.parse(sDate);
				datetime = new java.sql.Timestamp(date.getTime());
/*				String sNewDate = datetime.toString();
				System.out.println(sNewDate);
*/			} 
			catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return datetime;		
		}
}
	
	public class Log
	{
		public void LogMsg(String sMsg)
		{
			try
			{
				Environment env = new Environment();
				Utilities utils = new Utilities();
				String sLogging = env.GetEnvironmentVariable("Logging");
				int iLogging = 0;
				if(utils.isNumeric(sLogging))
					iLogging = Integer.parseInt(sLogging);
				if(iLogging > 0)
				{
					DateClass dte = new DateClass();
					String sNow = dte.GetDateTimeStamp("dd/MM/yyyy hh:mm:ss");
					String sOut = sMsg + " - " + sNow;
					String sFileName = env.GetBaseWebpath() + "\\logs\\LogMessages.txt";
					sFileName = utils.StripMultiBackslashes(sFileName);
					PrintWriter out = new PrintWriter(new FileWriter(sFileName, true));
					out.println(sOut);
					out.close();
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
}