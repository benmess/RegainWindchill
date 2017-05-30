package ext.regain.document;

import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;




import java.sql.Timestamp;

import wt.change2.WTChangeIssue;
import wt.change2.WTChangeOrder2;
import wt.doc.WTDocument;
import wt.part.WTPart;
import wt.util.WTException;

public class RegainBackend 
{
		
	
	@SuppressWarnings("unused")
	public static Boolean createWTDoc(String sDocNo, String sDocName, String sProductName, String sDocType, String sFolderName, String sRevision, 
									  String[] sAttributeName, String[] sAttributeValue, String sCheckInComments, int iProdOrLibrary) throws Exception
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    WTDocument doc = help.createdoc(sDocNo, sDocName, sProductName, sDocType, sFolderName, sRevision, sAttributeName, sAttributeValue, sCheckInComments, iProdOrLibrary);	    
	    return true;
	}
	
	@SuppressWarnings("unused")
	public static String createWTDoc2(String sDocNo, String sDocName, String sProductName, String sDocType, String sFolderName, String sRevision, 
									  String[] sAttributeName, String[] sAttributeValue, String sCheckInComments, int iProdOrLibrary) throws Exception
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    WTDocument doc = help.createdoc(sDocNo, sDocName, sProductName, sDocType, sFolderName, sRevision, sAttributeName, sAttributeValue, sCheckInComments, iProdOrLibrary);	    
	    return "Success^" + doc.getNumber() + "^";	
	}

	public static Boolean attachDoc(String sUser, String sDocNo, String sAttachDesc, String sAttachmentPathAndFile, Boolean bSecondary, String sCheckInComments) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.attachToDoc(sUser, sDocNo, sAttachDesc, sAttachmentPathAndFile, bSecondary, sCheckInComments);		
		return true;
	}
	
	public static Boolean setDocStringAttributes(String sDocNo, String sDocName, String[]sAttributeName, String[] sAttributeValue, String sCheckInComments) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.setDocumentAttributeStrings(sDocNo, sDocName, sAttributeName, sAttributeValue, sCheckInComments);		
		return true;
	}
	
	public static Boolean setDocAttributes(String sDocNo, String sDocName, String[]sAttributeName, String[] sAttributeValue, String[] sAttributeType, String sCheckInComments) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.setDocumentAttributes(sDocNo, sDocName, sAttributeName, sAttributeValue, sAttributeType, sCheckInComments);		
		return true;
	}

	public static Boolean setDocRevision(String sDocNo, String sRevision) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.setDocumentRevision(sDocNo, sRevision);		
		return true;
	}

	public static Boolean deleteAttachment(String sUser, String sDocNo, String sFileName, Boolean bSecondary) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.deleteAttachment(sUser, sDocNo, sFileName, bSecondary);		
		return true;
	}

	public static Boolean setDocToPartRef(String sUser, String sDocNo, String sPartNo, String sCheckingComments) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.setReferencedByLink(sUser, sDocNo, sPartNo, sCheckingComments);		
		return true;
	}
	
	public static Boolean setDocToPartRefs(String sUser, String sDocNo, String[] sPartNos, String sCheckingComments) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    int i;
	    
	    for(i=0;i<sPartNos.length;i++)
	    {
	    	help.setReferencedByLink(sUser, sDocNo, sPartNos[i], sCheckingComments);
	    }
		return true;
	}

	public static Boolean deleteDocToPartRef(String sUser, String sDocNo, String sPartNo, String sCheckingComments) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.deleteReferencedByLink(sUser, sDocNo, sPartNo, sCheckingComments);		
		return true;
	}

	public static Boolean deleteDocToPartRefs(String sUser, String sDocNo, String[] sPartNos, String sCheckingComments) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    int i;
	    
	    for(i=0;i<sPartNos.length;i++)
	    {
	    	help.deleteReferencedByLink(sUser, sDocNo, sPartNos[i], sCheckingComments);
	    }
		return true;
	}	

	public static Boolean setPartUsageAttributes(String sParentPart, String sChildPart, String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.setPartUsageLinkAttributes(sParentPart, sChildPart, sAttributeName, sAttributeValue, sAttributeType);		
		return true;
	}

	public static Boolean setPartAttributes(String sPartNumber, String sPartName, String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType, String sCheckInComments) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.setPartAttributes(sPartNumber, sPartName, sAttributeName, sAttributeValue, sAttributeType, sCheckInComments);		
		return true;
	}
	
	public static Boolean setPartPartLink(String sUser, String sParentPartNo, String sChildPartNumber, double dQty, String sCheckInComments, String sPartUsageType, String sUnit) throws WTException, RemoteException, InvocationTargetException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.setPartPartLink(sUser, sParentPartNo, sChildPartNumber, dQty, sCheckInComments, sPartUsageType, sUnit);		
		return true;
	}
	
	public static Boolean setPartPartLinkWithAttributes(String sUser, String sParentPartNo, String sChildPartNumber, double dQty, String sCheckInComments, String sPartUsageType, String sUnit, long lLineNumber, String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType) throws WTException, RemoteException, InvocationTargetException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.setPartPartLinkWithAttributes(sUser, sParentPartNo, sChildPartNumber, dQty, sCheckInComments, sPartUsageType, sUnit, lLineNumber, sAttributeName, sAttributeValue, sAttributeType);		
		return true;
	}

	public static Boolean updateDispatchDocketPartPartLinkWithAttributes(String sUser, String sParentPartNo, String sChildPartNumber, double dQty, String sDispatchDocketNo,
																	     String sCheckInComments, String sPartUsageType, String sUnit, long lOldLineNumber, long lNewLineNumber,
																	     String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType) throws WTException, RemoteException, InvocationTargetException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.updateDispatchDocketPartPartLinkWithAttributes(sUser, sParentPartNo, sChildPartNumber, dQty, sDispatchDocketNo, sCheckInComments, sPartUsageType, sUnit, lOldLineNumber, lNewLineNumber, sAttributeName, sAttributeValue, sAttributeType);		
		return true;
	}

	public static Boolean updateProdOrderPartPartLinkWithAttributes(String sUser, String sParentPartNo, String sChildPartNumber, double dQty, String sProdOrderNo,
		     String sCheckInComments, String sPartUsageType, String sUnit, long lOldLineNumber, long lNewLineNumber,
		     String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType) throws WTException, RemoteException, InvocationTargetException
	{
		RegainRemoteHelper help = new RegainRemoteHelper();
		help.updateProdOrderPartPartLinkWithAttributes(sUser, sParentPartNo, sChildPartNumber, dQty, sProdOrderNo, sCheckInComments, sPartUsageType, sUnit, lOldLineNumber, lNewLineNumber, sAttributeName, sAttributeValue, sAttributeType);		
		return true;
	}
	public static Boolean deletePartPartLink(String sUser, String sParentPart, String sChildPart, String sCheckingComments) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.deletePartPartLink(sUser, sParentPart, sChildPart, sCheckingComments);		
		return true;
	}

	public static Boolean deletePartPartLinkByDispatchDocket(String sUser, String sDispatchDocketNo, long iLineNumber, String sParentPart, String sChildPart, String sCheckingComments) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.deletePartPartLinkOfDispatchDocket(sUser, sDispatchDocketNo, iLineNumber, sParentPart, sChildPart, sCheckingComments);		
		return true;
	}

	public static Boolean deletePartPartLinkByProductionOrder(String sUser, String sProductionOrderNo, long iLineNumber, String sParentPart, String sChildPart, String sCheckingComments) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.deletePartPartLinkOfProductionOrder(sUser, sProductionOrderNo, iLineNumber, sParentPart, sChildPart, sCheckingComments);		
		return true;
	}

	public static String createWTPart(String sPartNo, String sPartName, String sProductName, String sPartType, String sFolderName, 
			  						   String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType, String sCheckInComments, int iProdOrLibrary) throws Exception
	{
		RegainRemoteHelper help = new RegainRemoteHelper();
		WTPart part = help.createpart(sPartNo, sPartName, sProductName, sPartType, sFolderName, sAttributeName, sAttributeValue, sAttributeType, sCheckInComments, iProdOrLibrary);
		return "Success^" + part.getNumber() + "^";
	}

	public static String createChangeNotice(String sCNNo, String sCNName, String sProductName, String sCNType, String sFolderName, 
			   String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType, int iProdOrLibrary) throws Exception
	{
		RegainRemoteHelper help = new RegainRemoteHelper();
		WTChangeOrder2 cNotice = help.createChangeNotice(sCNNo, sCNName, sProductName, sCNType, sFolderName, sAttributeName, sAttributeValue, sAttributeType, iProdOrLibrary);
		return "Success^" + cNotice.getNumber() + "^";
	}

	public static Boolean attachCNDoc(String sCNNo, String sAttachDesc, String sAttachmentPathAndFile) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.attachToChangeNotice(sCNNo, sAttachDesc, sAttachmentPathAndFile);		
		return true;
	}

	public static Boolean deleteCNAttachment(String sCNNo, String sAttachmentPathAndFile) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.deleteChangeNoticeAttachment(sCNNo, sAttachmentPathAndFile);		
		return true;
	}

	public static Boolean setCNAffectedParts(String sCNNo, String[] sAffectedParts) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.setChangeNoticeAffectedObjects(sCNNo, sAffectedParts);		
		return true;
	}

	public static String createProblemReport(String sPRNo, String sPRName, String sProductName, String sPRType, String sFolderName, 
			   String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType, int iProdOrLibrary, String sNeedDate) throws Exception
	{
		try
		{
			RegainRemoteHelper help = new RegainRemoteHelper();
			Backend be = new Backend();
			Backend.DateClass dte = be.new DateClass();
			Timestamp dtNeedDate = null;
			if(!sNeedDate.equals(""))
			{
				dtNeedDate = dte.GetDateFromString(sNeedDate, "yyyymmdd");
			}
			WTChangeIssue pr = help.createProblemReport(sPRNo, sPRName, sProductName, sPRType, sFolderName, sAttributeName, sAttributeValue, sAttributeType, iProdOrLibrary, dtNeedDate);
			return "Success^" + pr.getNumber() + "^";
		}
		catch(Exception e)
		{
			return "Failure^" + e.getMessage();
		}
	}
	
	public static Boolean attachPRDoc(String sPRNo, String sAttachDesc, String sAttachmentPathAndFile) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.attachToProblemReport(sPRNo, sAttachDesc, sAttachmentPathAndFile);		
		return true;
	}

	public static Boolean deleteProbReport(String sPRNo) throws InvocationTargetException, WTException, RemoteException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.deleteProblemReport(sPRNo);		
		return true;
	}

	public static Boolean deletePRAttachment(String sPRNo, String sAttachmentPathAndFile) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.deleteProblemReportAttachment(sPRNo, sAttachmentPathAndFile);		
		return true;
	}

	public static Boolean setPRAffectedParts(String sPRNo, String[] sAffectedParts) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.setProblemReportAffectedObjects(sPRNo, sAffectedParts);		
		return true;
	}

	public static Boolean deletePRAffectedParts(String sPRNo, String[] sAffectedParts) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    help.deleteProblemReportAffectedObjects(sPRNo, sAffectedParts);		
		return true;
	}

	public static Boolean setProblemReportAttributes(String sPRNumber, String sPRName, String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType, String sNeedDate) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
		Backend be = new Backend();
		Backend.DateClass dte = be.new DateClass();
		Timestamp dtNeedDate = null;
		if(!sNeedDate.equals(""))
		{
			dtNeedDate = dte.GetDateFromString(sNeedDate, "yyyymmdd");
		}
	    help.setProblemReportAttributes(sPRNumber, sPRName, sAttributeName, sAttributeValue, sAttributeType, dtNeedDate);		
		return true;
	}

	public static Boolean setPersonCompliancy(String sPersonNumber, int iWebAppId) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    Boolean bSet = help.setPersonCompliancy(sPersonNumber, iWebAppId);		
		return bSet;
	}
}
