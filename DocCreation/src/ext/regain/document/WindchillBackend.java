package ext.regain.document;

/*import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Date;
*/

import java.sql.ResultSet;
import java.sql.SQLException;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;



import java.util.Calendar;
import java.util.GregorianCalendar;
//import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;

//import wt.util.WTException;








//import ext.regain.document.Backend.Database;
/*import com.fronesis.servlets.Backend;
import com.fronesis.servlets.Backend.*;
import com.regain.rest.IService1Proxy;
*/

@SuppressWarnings({"rawtypes", "unchecked"})
public class WindchillBackend
{
	public class ReturnClassStringArray
	{
		ArrayList sStrings = new ArrayList();
		boolean bResult;
		String sMsg = "";		
	}
	
	public class ReturnClassInt
	{
		boolean bValue;
		int iValue;
		String sRtnMsg;
	}

	public class ReturnClassBool
	{
		boolean bValue;
		boolean bSuccess;
		String sRtnMsg;
	}

	public class ReturnClassCount
	{
		boolean bValue;
		boolean bSuccess;
		int iRecordCount;
		String sRtnMsg;
	}

	public class ReturnClassString
	{
		boolean bValue;
		String sRtnMsg;
		String sReturnedString;
	}

	public String GetDocumentName(String sDocNumber, int iWebAppId)
	{
		Backend be = new Backend(iWebAppId);
		Backend.Database DB = be.new Database();
		String[] sParamNames = new String[1];
		Object[] objParamValues = new Object[1];

		sParamNames[0] = "@pvchDocumentNumber";
		
		objParamValues[0] = sDocNumber;

		int iRecordCount = DB.CallStoredProcResultSet("SP_GetWindchillDocumentName", sParamNames, objParamValues);

		if (iRecordCount < 0)
		{			
			return "There is no document with number " + sDocNumber;
		}
		else
		{
			try
			{
				ResultSet rs = DB.getResultSet();
				String sName = rs.getString("name");	

				return sName;
			} 
			catch (SQLException e)
			{
				return e.getMessage();
			}
		}
						
	}

	public ReturnClassStringArray GetParentDocumentsFromChildDocument(String sParentDocType, String sChildDocNumber, int iWebAppId)
	{
		Backend be = new Backend(iWebAppId);
		Backend.Database DB = be.new Database();
		String[] sParamNames = new String[2];
		Object[] objParamValues = new Object[2];
		int i;
		ReturnClassStringArray rtnClass = new ReturnClassStringArray();

		sParamNames[0] = "@pvchChildDocumentNumber";
		sParamNames[1] = "@pvchParentDocumentType";
		
		objParamValues[0] = sChildDocNumber;
		objParamValues[1] = sParentDocType;

		int iRecordCount = DB.CallStoredProcResultSet("SP_GetWindchillParentDocuments", sParamNames, objParamValues);

		if (iRecordCount < 0)
		{
			rtnClass.bResult = false;
			rtnClass.sMsg = "No parent documents of type " + sParentDocType + " for child with document number " + sChildDocNumber;
			return rtnClass;
		}
		else
		{
			try
			{
				ResultSet rs = DB.getResultSet();

				for(i=0;i<iRecordCount;i++)
				{
					if(rs.wasNull())
					{
						rtnClass.sStrings.add("");					
					}
					else
					{
						rtnClass.sStrings.add(rs.getString("WTDocumentNumber"));					
					}
					rs.next();
				}

				rtnClass.bResult = true;
				return rtnClass;
			} 
			catch (SQLException e)
			{
				rtnClass.bResult = false;
				rtnClass.sMsg = e.getMessage();
				return rtnClass;
			}
		}
						
	}

	public int[] GetParentDocumentFromChildDocument(String sParentDocType, String sChildDocNumber, int iWebAppId)
	{
		Backend be = new Backend(iWebAppId);
		Backend.Database DB = be.new Database();
		String[] sParamNames = new String[2];
		Object[] objParamValues = new Object[2];
		int i;

		sParamNames[0] = "@pvchChildDocumentNumber";
		sParamNames[1] = "@pvchParentDocumentType";
		
		objParamValues[0] = sChildDocNumber;
		objParamValues[1] = sParentDocType;

		int iRecordCount = DB.CallStoredProcResultSet("SP_GetWindchillParentDocumentIds", sParamNames, objParamValues);

		if (iRecordCount < 0)
		{
			int[] sRtn = new int[1];
			sRtn[0] = -1;
			return sRtn;
		}
		else
		{
			try
			{
				ResultSet rs = DB.getResultSet();
				int[] sRtn = new int[iRecordCount];

				for(i=0;i<iRecordCount;i++)
				{
					if(rs.wasNull())
					{
						sRtn[i] = 0;					
					}
					else
					{
						sRtn[i] = rs.getInt("DocumentId");	
					}
					rs.next();
				}

				return sRtn;
			} 
			catch (SQLException e)
			{
				int[] sRtn = new int[1];
				sRtn[0] = -99;
				return sRtn;
			}
		}
						
	}

	public ReturnClassStringArray GetChildDocumentsFromParentDocument(String sChildDocType, String sParentDocNumber, int iWebAppId)
	{
		Backend be = new Backend(iWebAppId);
		Backend.Database DB = be.new Database();
		String[] sParamNames = new String[2];
		Object[] objParamValues = new Object[2];
		int i;
		ReturnClassStringArray rtnClass = new ReturnClassStringArray();
		
		sParamNames[0] = "@pvchParentDocumentNumber";
		sParamNames[1] = "@pvchChildDocumentType";
		
		objParamValues[0] = sParentDocNumber;
		objParamValues[1] = sChildDocType;

		int iRecordCount = DB.CallStoredProcResultSet("SP_GetWindchillChildDocuments", sParamNames, objParamValues);

		if (iRecordCount < 0)
		{
			rtnClass.bResult = false;
			rtnClass.sMsg = "No child documents of type " + sChildDocType + " for parent with document number " + sParentDocNumber;
			return rtnClass;
		}
		else
		{
			try
			{
				ResultSet rs = DB.getResultSet();

				for(i=0;i<iRecordCount;i++)
				{
					if(rs.wasNull())
					{
						rtnClass.sStrings.add("");					
					}
					else
					{
						rtnClass.sStrings.add(rs.getString("WTDocumentNumber"));					
					}
					rs.next();
				}

				rtnClass.bResult = true;
				return rtnClass;
			} 
			catch (SQLException e)
			{
				rtnClass.bResult = false;
				rtnClass.sMsg = e.getMessage();
				return rtnClass;
			}
		}
						
	}

	public int[] GetChildDocumentIdsFromParentDocument(String sChildDocType, String sParentDocNumber, int iWebAppId)
	{
		Backend be = new Backend(iWebAppId);
		Backend.Database DB = be.new Database();
		String[] sParamNames = new String[2];
		Object[] objParamValues = new Object[2];
		int i;

		sParamNames[0] = "@pvchParentDocumentNumber";
		sParamNames[1] = "@pvchChildDocumentType";
		
		objParamValues[0] = sParentDocNumber;
		objParamValues[1] = sChildDocType;

		int iRecordCount = DB.CallStoredProcResultSet("SP_GetWindchillChildDocumentIds", sParamNames, objParamValues);

		if (iRecordCount < 0)
		{
			int[] sRtn = new int[1];
			sRtn[0] = -1;
			return sRtn;
		}
		else
		{
			try
			{
				ResultSet rs = DB.getResultSet();
				int[] sRtn = new int[iRecordCount];

				for(i=0;i<iRecordCount;i++)
				{
					if(rs.wasNull())
					{
						sRtn[i] = 0;					
					}
					else
					{
						sRtn[i] = rs.getInt("DocumentId");	
					}
					rs.next();
				}

				return sRtn;
			} 
			catch (SQLException e)
			{
				int[] sRtn = new int[1];
				sRtn[0] = -99;
				return sRtn;
			}
		}
						
	}

	public int GetLeadTimeInDays(String sDocNumber, int iWebAppId)
	{
		Backend be = new Backend(iWebAppId);
		Backend.Database DB = be.new Database();
		String[] sParamNames = new String[1];
		Object[] objParamValues = new Object[1];

		sParamNames[0] = "@pvchDocumentNumber";
		
		objParamValues[0] = sDocNumber;

		int iRecordCount = DB.CallStoredProcResultSet("SP_GetWindchillLeadTimeInDays", sParamNames, objParamValues);

		if (iRecordCount < 0)
		{			
			return -1;
		}
		else
		{
			try
			{
				ResultSet rs = DB.getResultSet();
				int iRtn = -1;

				if(rs.wasNull())
				{
					iRtn = -1;					
				}
				else
				{
					iRtn = rs.getInt("LeadTimeInDays");	
				}
				rs.next();

				return iRtn;
			} 
			catch (SQLException e)
			{
				return -1;
			}
		}
						
	}
	
	public java.util.Date GetExpiryDate(String sDocNumber, int iWebAppId)
	{
		Backend be = new Backend(iWebAppId);
		Backend.Database DB = be.new Database();
		String[] sParamNames = new String[1];
		Object[] objParamValues = new Object[1];

		sParamNames[0] = "@pvchDocumentNumber";
		
		objParamValues[0] = sDocNumber;

		int iRecordCount = DB.CallStoredProcResultSet("SP_GetWindchillDocumntExpiryDate", sParamNames, objParamValues);

		if (iRecordCount < 0)
		{			
			return null;
		}
		else
		{
			try
			{
				ResultSet rs = DB.getResultSet();

				if(rs.wasNull())
				{
					return null;
				}
				else
				{
					java.sql.Timestamp dtExpiry = rs.getTimestamp("ExpiryDate");
					java.util.Date dtExpiry2 = SetWindchillDateToLocalDate((java.util.Date)dtExpiry);
					return dtExpiry2;	
				}

			} 
			catch (SQLException e)
			{
				return null;
			}
		}
						
	}
	
	public ReturnClassString GetPersonEmail(String sDocNumber, int iWebAppId)
	{
		Backend be = new Backend(iWebAppId);
		Backend.Database DB = be.new Database();
		String[] sParamNames = new String[1];
		Object[] objParamValues = new Object[1];
		ReturnClassString rtn = new ReturnClassString();
		
		sParamNames[0] = "@pvchDocumentNumber";
		
		objParamValues[0] = sDocNumber;

		int iRecordCount = DB.CallStoredProcResultSet("SP_GetWindchillPersonEmail", sParamNames, objParamValues);

		if (iRecordCount < 0)
		{		
			rtn.bValue = false;
			rtn.sRtnMsg = "No email address for person " + sDocNumber;
			return rtn;
		}
		else
		{
			try
			{
				ResultSet rs = DB.getResultSet();

				if(rs.wasNull())
				{
					rtn.sReturnedString = "";					
				}
				else
				{
					rtn.sReturnedString = rs.getString("EmailAddress");	
				}
				rs.next();
				rtn.bValue = true;
				return rtn;
			} 
			catch (SQLException e)
			{
				rtn.bValue = false;
				rtn.sRtnMsg = e.getMessage();
				return rtn;
			}
		}
						
	}

	public ReturnClassCount GetOwnCompetencyCompliance(String sOwnCompetencyNumber, int iWebAppId)
	{
		Backend be = new Backend(iWebAppId);
		Backend.Database DB = be.new Database();
		String[] sParamNames = new String[1];
		Object[] objParamValues = new Object[1];
		ReturnClassCount rtn = new ReturnClassCount();
		
		sParamNames[0] = "@pvchDocumentNumber";
		
		objParamValues[0] = sOwnCompetencyNumber;

		int iRecordCount = DB.CallStoredProcResultSet("SP_GetWindchillOwnCompetencyCompliance", sParamNames, objParamValues);

		if (iRecordCount < 0)
		{		
			rtn.bValue = false;
			rtn.sRtnMsg = "No compliance flag for own competency item " + sOwnCompetencyNumber;
			return rtn;
		}
		else
		{
			try
			{
				if(iRecordCount == 0)
				{
					rtn.iRecordCount = 0;
					rtn.bValue = false;
				}
				else
				{
					ResultSet rs = DB.getResultSet();
	
					if(rs.wasNull())
					{
						rtn.bValue = false;					
					}
					else
					{
						rtn.bValue = rs.getBoolean("value");	
					}
					rs.next();
					rtn.iRecordCount = 1;
				}
				rtn.bSuccess = true;
				return rtn;
			} 
			catch (SQLException e)
			{
				rtn.iRecordCount = -1;
				rtn.bSuccess = false;
				rtn.bValue = false;
				rtn.sRtnMsg = e.getMessage();
				return rtn;
			}
		}
						
	}

	public void OwnAccreditationSendWarningsOnExpiry(String sOwnAccreditationNumber, int iWebAppId)
	{
		java.util.Date dtExpiry = GetExpiryDate(sOwnAccreditationNumber, iWebAppId);
		ReturnClassInt rtnClass = GetCompetencyUnitLeadTimeFromOwnAccreditation(sOwnAccreditationNumber, iWebAppId);
		Backend be = new Backend(iWebAppId);
		Backend.Email mail = be.new Email();
		Backend.Environment env = be.new Environment();
//		Backend.ReturnClassBool rtn = be.new ReturnClassBool();
		ReturnClassStringArray rtnClass2 = new ReturnClassStringArray();
		
		String sRecipients = env.GetEnvironmentVariable("DefaultEmail");
		
		if(!rtnClass.bValue)
		{
			mail.email_message("Error processing own accreditation item " + sOwnAccreditationNumber, "Error processing own accreditation item " + sOwnAccreditationNumber + "\r\n" + rtnClass.sRtnMsg, null,
									  sRecipients, "", "");
			return;
		}
		else
		{
			Calendar cNow = Calendar.getInstance(); 
			cNow.add(Calendar.DATE, rtnClass.iValue);
			Calendar cExpiry = new GregorianCalendar();
			cExpiry.setTime(dtExpiry);
						
			if(cExpiry.before(cNow))
			{
				rtnClass2 = GetParentDocumentsFromChildDocument("local.rs.vsrs05.Regain.Person", sOwnAccreditationNumber, iWebAppId);
				
				if(!rtnClass2.bResult)
				{
					mail.email_message("Error getting person for own accreditation item " + sOwnAccreditationNumber, "Error processing parent of own accreditation item " + sOwnAccreditationNumber + "\r\n" + rtnClass.sRtnMsg, null,
							  sRecipients, "", "");
					return;				
				}
				else
				{
					if(rtnClass2.sStrings.size() > 1)
					{
						mail.email_message("Error own accreditation item " + sOwnAccreditationNumber + " allocatted to multiple people", "Own accreditation item " + sOwnAccreditationNumber + " has been allocated to " + rtnClass2.sStrings.size() + " people.", null,sRecipients, "", "");
						return;										
					}
					else if(rtnClass2.sStrings.size() <= 0)
					{
						mail.email_message("Error own accreditation item " + sOwnAccreditationNumber + " allocatted to nobody", "Own accreditation item " + sOwnAccreditationNumber + " has not been allocated to anybody.", null,sRecipients, "", "");
						return;										
					}
					else
					{
						String sPerson = rtnClass2.sStrings.get(0).toString();
						ReturnClassString sRecipientClass = GetPersonEmail(sPerson, iWebAppId);
						if(!sRecipientClass.bValue)
						{
							mail.email_message("Error person " + sPerson + " has no valid email address", "Person " + sPerson + " has no email address set.", null,sRecipients, "", "");
							return;																	
						}
						else
						{
							String sBody = "Item " + sOwnAccreditationNumber + " is about to expire on " + dtExpiry.toString() +".\r\nPlease forward a renewal copy of the accreditation record by replying to this email.";
							mail.email_message("Accreditation Item " + sOwnAccreditationNumber + " is about to expire", sBody, null, sRecipientClass.sReturnedString, "", "");
							return;
						}
					}
				}
			}
			
			return;
		}
	}

	public ReturnClassInt GetCompetencyUnitLeadTimeFromOwnAccreditation(String sOwnAccreditationNumber, int iWebAppId)
	{
		ReturnClassInt rtn1 = new ReturnClassInt();
		ReturnClassStringArray rtn = GetChildDocumentsFromParentDocument("local.rs.vsrs05.Regain.CompetencyUnit", sOwnAccreditationNumber, iWebAppId);
		if(rtn.sStrings.size() > 1)
		{
			rtn1.bValue = false;
			rtn1.sRtnMsg = "There is more than 1 competency unit for the own accreditation numbered " + sOwnAccreditationNumber;
			return rtn1;
		}
		else
		{
			if(rtn.sStrings.size() == 0)
			{
				rtn1.iValue = GetLeadTimeInDays(sOwnAccreditationNumber, iWebAppId);
			}
			else
			{
				rtn1.iValue = GetLeadTimeInDays(rtn.sStrings.get(0).toString(), iWebAppId);
				
			}
			rtn1.bValue = true;
			return rtn1;
		}
	}
	
	@SuppressWarnings("static-access")
	public ReturnClassBool SetPersonCompliancy(String sPersonNumber, int iWebAppId)
	{
		ReturnClassStringArray rtnOwnCompetencies = GetChildDocumentsFromParentDocument("local.rs.vsrs05.Regain.OwnCompetency", sPersonNumber, iWebAppId);
		ReturnClassStringArray rtnOwnAccreditationItems = GetChildDocumentsFromParentDocument("local.rs.vsrs05.Regain.OwnAccreditation", sPersonNumber, iWebAppId);
		int i, j, k;
		ArrayList arrOwnCompetencyUnits = new ArrayList();
		ReturnClassBool rtn = new ReturnClassBool();
		Boolean bComplaint = true;
		java.util.Date dtToday	= new java.util.Date();

		//First get the competency unit for each own accreditation and hold in an array to loop over later on
		if(rtnOwnAccreditationItems.bResult)
		{
			for(i=0;i<rtnOwnAccreditationItems.sStrings.size();i++)
			{
				//Get all the requirements for this own Competency by forst getting the competency and then the competency units
				String sOwnAccreditationItem = rtnOwnAccreditationItems.sStrings.get(i).toString();
				java.util.Date dtExpiry = GetExpiryDate(sOwnAccreditationItem, iWebAppId);
				if(dtExpiry.compareTo(dtToday) >= 0)
				{
					ReturnClassStringArray rtnCompetencyUnit = GetChildDocumentsFromParentDocument("local.rs.vsrs05.Regain.CompetencyUnit", sOwnAccreditationItem, iWebAppId);
					if(rtnCompetencyUnit.bResult)
					{
						//You only expect 1 competency unit for each own accreditation
						if(rtnCompetencyUnit.sStrings.size() == 1)
						{
							arrOwnCompetencyUnits.add(rtnCompetencyUnit.sStrings.get(0));
						}
					}
				}
			}
		}
		
		//For each own competency, get all the requirements
		if(rtnOwnCompetencies.bResult)
		{
			for(i=0;i<rtnOwnCompetencies.sStrings.size();i++)
			{
				//Get all the requirements for this own Competency by forst getting the competency and then the competency units
				String sOwnCompetency = rtnOwnCompetencies.sStrings.get(i).toString();
				ReturnClassStringArray rtnCompetency = GetChildDocumentsFromParentDocument("local.rs.vsrs05.Regain.Competency", sOwnCompetency, iWebAppId);
				if(rtnCompetency.bResult)
				{
					//You only expect 1 competency for each own competency
					if(rtnCompetency.sStrings.size() == 1)
					{
						String sCompetency = rtnCompetency.sStrings.get(i).toString();
						ReturnClassStringArray rtnRequirements = GetChildDocumentsFromParentDocument("local.rs.vsrs05.Regain.CompetencyUnit", sCompetency, iWebAppId);
						if(rtnRequirements.bResult)
						{
							//You expect multiple requirements
							bComplaint = true;
							for(j=0;j<rtnRequirements.sStrings.size();j++)
							{
								Boolean bFoundThisItem = false;
								for(k=0; k<arrOwnCompetencyUnits.size(); k++)
								{
									if(arrOwnCompetencyUnits.get(k).toString().compareTo(rtnRequirements.sStrings.get(j).toString()) == 0)
									{
										bFoundThisItem = true;
										break;
									}
								}
								
								if(!bFoundThisItem)
								{
									bComplaint = false;
									break;
								}																
							}													
						}
						
						//Get the existing compliancy flag
						ReturnClassCount bExistingCompliance = GetOwnCompetencyCompliance(sOwnCompetency, iWebAppId);
						
						if(bExistingCompliance.bSuccess)
						{
							if(bComplaint != bExistingCompliance.bValue || bExistingCompliance.iRecordCount == 0)
							{
/*							    IService1Proxy serv1 = new IService1Proxy();
							    String sDocName = GetDocumentName(sOwnCompetency, iWebAppId);
							    String sRtn = "";
								try
								{
									sRtn = serv1.updateDocAttributes(sOwnCompetency, sDocName, 
									  								  "Compliant", String.valueOf(bComplaint), "bool", 
									  								  null, null, null, 
									  								  null, null, null, 
									  								  null, null, null, 
									  								  null, null, null, 
									  								  "Updated compliancy value");
		
								    if (sRtn.equalsIgnoreCase("Success"))
								    {
									    rtn.bValue = true;						
										rtn.sRtnMsg = "";
								    }
								    else
								    {
									    rtn.bValue = false;						
										rtn.sRtnMsg = sRtn;
								    }
		
								} 
								catch (RemoteException e)
								{
								    rtn.bValue = false;						
									rtn.sRtnMsg = e.getMessage();;
								}
								
*/		
								//Update the compliant flag
								try 
								{
								    RegainRemoteHelper help = new RegainRemoteHelper();
								    String[] sAttNames = new String[1];
								    String[] sAttValues = new String[1];
								    String[] sAttTypes = new String[1];
			
								    sAttNames[0] = "Compliant";
								    sAttValues[0] = String.valueOf(bComplaint);
								    sAttTypes[0] = "bool";
								    String sDocName;
										sDocName = help.getDocumentNameByNumber(sOwnCompetency);
									    help.setDocumentAttributes(sOwnCompetency, sDocName, sAttNames, sAttValues, sAttTypes, "Updated compliancy value");		
									    rtn.bValue = true;						
										rtn.sRtnMsg = "";
								} 
								catch (Exception e) 
								{
									// TODO Auto-generated catch block
								    rtn.bValue = false;						
									rtn.sRtnMsg = e.getMessage();
									e.printStackTrace();
								}
								

						
							}
						}
					}
						
				}
			}
		}
		
		return rtn;
	}
	
	public java.util.Date SetWindchillDateToLocalDate(java.util.Date dtInputDate)
	{
/*		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.UK);
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa", Locale.UK);
*/		
		Calendar cal = Calendar.getInstance();
        TimeZone tz2 = cal.getTimeZone();
        Calendar cal2 = Calendar.getInstance(tz2);
        int iOffset = tz2.getOffset(cal2.getTime().getTime());
        java.util.Date startDate2 = DateUtils.addMilliseconds(dtInputDate, iOffset);
        return startDate2;	
	}

}
