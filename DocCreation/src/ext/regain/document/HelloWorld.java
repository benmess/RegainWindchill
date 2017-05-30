package ext.regain.document;


import java.beans.PropertyVetoException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;









import com.ptc.core.lwc.server.LWCNormalizedObject;
//import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.meta.common.UpdateOperationIdentifier;

import wt.build.BuildReference;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.Folder;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.value.DefaultAttributeContainer;
import wt.iba.value.IBAValueUtility;
import wt.iba.value.litevalue.AbstractValueView;
import wt.session.SessionHelper;
/*import wt.folder.FolderHelper;
import wt.folder.FolderingInfo;
import wt.inf.container.WTContainerRef;
*/import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.struct.StructHelper;
import wt.vc.wip.CheckoutLink;
import wt.vc.wip.WorkInProgressHelper;
import wt.part.LineNumber;
import wt.part.WTPart;
import wt.part.WTPartReferenceLink;
import wt.part.WTPartUsageLink;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
//import wt.security.*;
import ext.regain.document.RegainBackend;

@SuppressWarnings("deprecation")
public class HelloWorld {
public static void main(String[] args) 
{
	
	try {
		System.out.println("abc");
		RegainBackend be = new RegainBackend();
		WindchillBackend wbe = new WindchillBackend();
		String sFolderName = "Material Catalogue/Technical Documents Materials Catalogue/TM - Technical Manuals"; //"Technical Documents 120TD Thermal Treatment Plant";   
/*		String sProductName = "126 Point Henry Operation";   
		String sCheckinComments = "These are the check in comments on creation of a chnage notice";
		String sAttachName = "Carambola-java.zip";
	    RegainRemoteHelper help = new RegainRemoteHelper();
*///	    WTContainerRef ref = help.GetProductRef(sProductName);
	    System.out.println("about to execute folder info with name " + sFolderName); //String.valueOf(ref.getContainer()()

	    String[] sAttNames = new String[1];
	    String[] sAttValues = new String[1];
	    String[] sAttTypes = new String[1];

	    sAttNames[0] = "Compliant";
	    sAttValues[0] = "false";
	    sAttTypes[0] = "bool";
	    
	    String[] sAttributeNames = new String[1];
	    String[] sAttributeValues = new String[1];
	    String[] sAttributeTypes = new String[1];
//	    String[] sAffectedParts = new String[2];
	    
        sAttributeNames[0] = "Originator";
/*        sAttributeNames[1] = "ProdLossCategory";
        sAttributeNames[2] = "ProdLossSubCategory";
        sAttributeNames[3] = "PlantCode";
        sAttributeNames[4] = "StartDate";
        sAttributeNames[5] = "EndDate";
        sAttributeNames[6] = "DurationInHours";
        sAttributeNames[7] = "SuspectedProblem";
        sAttributeNames[8] = "Comments";
*/
        sAttributeValues[0] = "benmess";
/*        sAttributeValues[1] = "Scheduled Downtime";
        sAttributeValues[2] = "Project work";
        sAttributeValues[3] = "126";
        sAttributeValues[4] = "10/08/2016 12:00 am";
        sAttributeValues[5] = "11/08/2016 12:00 am";
        sAttributeValues[6] = "24.6E1";
        sAttributeValues[7] = "2";
        sAttributeValues[8] = "Comments 1";
*/
        sAttributeTypes[0] = "string";
/*        sAttributeTypes[1] = "string";
        sAttributeTypes[2] = "string";
        sAttributeTypes[3] = "string";
        sAttributeTypes[4] = "string";
        sAttributeTypes[5] = "string";
        sAttributeTypes[6] = "double";
        sAttributeTypes[7] = "string";
        sAttributeTypes[8] = "string";
*/
        
	    String[] sAttName = new String[4];
	    String[] sAttValue = new String[4];

	    sAttName[0] = "LongDescription";
	    sAttName[1] = "Originator";
	    sAttName[2] = "OrigDocId";
	    sAttName[3] = "JobCode";

	    sAttValue[0] = "";
	    sAttValue[1] = "Nigel Dev";
	    sAttValue[2] = "";
	    sAttValue[3] = "-99";

	    String[] sAttName2 = new String[3];
	    String[] sAttValue2 = new String[3];
	    String[] sAttType2 = new String[3];

//	    sAttName2[0] = "DispatchDocketDate";
//	    sAttName2[1] = "TransactionDate";
//	    sAttName2[1] = "UsageComments";

//	    sAttValue2[0] = "08/08/2016 12:22:15 pm";
//	    sAttValue2[1] = "08/08/2016 1:00:00 pm";
//	    sAttValue2[1] = "help";

//	    sAttType2[0] = "date";
//	    sAttType2[1] = "date";
//	    sAttType2[1] = "string";

        /*	    sAttName2[0] = "DispatchDocketNo";
	    sAttValue2[0] = "DD89743";
	    sAttType2[0] = "string";
*/
	    //	    sAttName[0] = "StartDate";
//	    sAttName[1] = "EndDate";
//	    sAttName[2] = "OriginatorDocId";
//	    sAttName[3] = "RequestDate";

//	    sAttValue[0] = "24/06/2016 09:22:31 AM";
//	    sAttValue[1] = "06/06/2016 12:22:31 PM";
//	    sAttValue[2] = "xxxxyyyy";
//	    sAttValue[3] = "12/05/2016 3:18:5 PM";
	    
//	    sAttType[0] = "string";
//	    sAttType[1] = "string";
//	    sAttType[2] = "string";
//	    sAttType[3] = "datetime";
	    
	    sAttName[0] = "LongDescription";
	    sAttName[1] = "Originator";
	    sAttName[2] = "OrigDocId";


	    sAttValue[0] = "";
	    sAttValue[1] = "benmess";
	    sAttValue[2] = "";

/*        sAttName[0] = "Originator";
	    sAttName[1] = "ActionCategory";
	    sAttName[2] = "ARCause";
	    sAttName[3] = "LongDescription";
	    sAttName[4] = "Comments";
	    sAttName[5] = "RequestDate";

	    sAttValue[0] = "benmess";
	    sAttValue[1] = "null";
	    sAttValue[2] = "null";
	    sAttValue[3] = "A long desc from outside";
	    sAttValue[4] = "";
	    sAttValue[5] = "15/05/2016 02:44:22 AM";

	    		sAttType[0] = "string";
	    		sAttType[1] = "string";
	    		sAttType[2] = "string";
	    		sAttType[3] = "string";
	    		sAttType[4] = "string";
	    		sAttType[5] = "datetime";
*/
	    
//	    Object obj = GetDateFromString(sAttValue[3], "dd/MM/yyyy hh:mm:ss a");
//	    Folder  folder = (Folder) FolderHelper.service.getFolder(sFolderName.toString(), WTContainerRef.newWTContainerRef(ref)); //, 
//	    Folder folder = help.GetFolder2(sFolderName, sProductName);
	    String[] sPartRefs = new String[2];
	    sPartRefs[0] = "P120P030";
	    sPartRefs[1] = "P120P130";
	    Timestamp dtNeedDate = new Timestamp(2017,0,3,0,0,0,0);
	    
	    
/*	    Boolean bRtn = be.createWTDoc("D027151", "Dispatch Docket 27151","126 Point Henry Operation", "local.rs.vsrs05.Regain.DispatchDocket", 
	    				"126 Production Material/126 Dispatch Dockets",  "A", sAttName, sAttValue, "Created by bulk docket book creation routine", 0);
*/	    
//		String sPR = be.createProblemReport("", "Test 1 from backend with date", "126 Point Henry Operation", "local.rs.vsrs05.Regain.IssueReport", "126 Maintenance Work Management/", 
//											sAttributeNames, sAttributeValues, sAttributeTypes, 0, "20170103" );//GetDocumentByNumber("120TD266");
//	    System.out.println("success for pr creation " + sPR);
//		String sPartNo = be.createChangeNotice("", "A working autonumbered 2 change notice", sProductName, "wt.change2.WTChangeOrder2", sFolderName, sAttName, sAttValue, sAttType, sCheckinComments);//GetDocumentByNumber("120TD266");
//		be.attachCNDoc("benmess33myage","CN00007", "The CN attach desc", "C:/WebRoot/Regain/Uploads/120TD235 - REV 1.pdf");
//		be.attachPRDoc("CH00013", "The PR attach desc", "C:/WebRoot/Regain/Uploads/120TD235 - REV 1.pdf");
/*		be.deletePRAttachment("CH00013", "120TD235 - REV 1.pdf");
	    sAffectedParts[0] = "P121D001";
	    sAffectedParts[1] = "P121D002";
*/	    //be.setPRAffectedParts("CH00013", sAffectedParts);
//	    be.deletePRAffectedParts("CH00013", sAffectedParts);
//	    getPartPartLinkFromId("PH1696", "PH1686", 83760506);
//	    Boolean bRtn =be.deletePartPartLink("benmess", "PH1696", "PH1686", "deleted link from PH1686 to PH1696");
//	    getPartPartLinkFromDispatchDocketNo("PH1696", "PH1686", 20, "D026988");
//	    Boolean bRtn =be.setPartPartLinkWithAttributes("benmess","PH1696", "PH1745", 111.11, "created link from PH1745 to PH1696 first time", 
//	    				"local.rs.vsrs05.Regain.MBAUsageLink", "tonne", 40, sAttName2, sAttValue2, sAttType2);
	    
//	    Boolean bRtn =be.setPartPartLink("benmess","BA020", "BT0002", 111.11, "created link from PH1745 to PH1696 first time", 
//		"local.rs.vsrs05.Regain.MBAUsageLink", "tonne", 40, sAttName2, sAttValue2, sAttType2);

	    //	    Boolean bRtn =be.updateDispatchDocketPartPartLinkWithAttributes("benmess","PH1696", "PH1686", 422.31,  "D026988", "updated link from PH1686 to PH1696 fourth time", 
//				"local.rs.vsrs05.Regain.MBAUsageLink", "tonne", 20, 30, sAttName2, sAttValue2, sAttType2);
	    
//	    Boolean bRtn =be.deletePartPartLinkByDispatchDocket("benmess", "D026988", 20, "PH1696", "PH1686", "deleted link from PH1686 to PH1696 third time");
//	    Boolean bRtn = be.setProblemReportAttributes("CG00029", "Prob Report through backend changed", sAttName2, sAttValue2, sAttType2, "20170107");
	    //be.setCNAffectedParts("CN00007", sAffectedParts);
	    //getPartDocRefLink("P120P030","120TD101");
	    //be.deleteDocToPartRef("benthebest","120TD101", "P120A100","deleted link to doc 120TD101");
	    //Boolean bRtn = be.setPartPartLinkWithAttributes("Ben Messenger", "PH1736", "B126A1", 555.66, "Testing part to part link again", "local.rs.vsrs05.Regain.MBAUsageLink", "tonne", sAttName2, sAttValue2, sAttType2); //"local.rs.vsrs05.Regain.MBAUsageLink"  "wt.part.WTPartUsageLink"
//	    be.deletePartPartLink("benmess", "P215B014", "CA00002", "Testing part to part link delete"); //This will delete all iterations of the part to part
	    
//	    Boolean bPartUpdate = be.setPartAttributes("CA00002", "A new request changed", sAttName, sAttValue, sAttType, "Some checkin comments");
	    //Boolean bResult = be.deleteDocToPartRefs("120TD101", sPartRefs, "Linking to document 120TD101");
//	    System.out.println("success with folder " + folder.getLocation() + " description " + folder.getDescription() + " name = " + folder.getName());
		//be.deleteAttachment("benmess33myage", "120TD101", "LogMeIn.msi", true);
//		be.attachDoc("benmess35myage","D026988", "The attach desc", "C:/WebRoot/Regain/Uploads/2016_Leash_Free.pdf", false, "Check to see if originator comes across");
//		Boolean doc = be.createWTDoc("M00126", "A manual for the dummy part M00125", "Regain Material Catalogue", "local.rs.vsrs05.Regain.TM", sFolderName, "A", sAttName, sAttValue, "", 1);//GetDocumentByNumber("120TD266");
	    //be.setDocAttributes("GF0005", "Electrician A Grade Victoria", sAttNames, sAttValues, sAttTypes, "Updating compliancy flag");
//	    Boolean bRevSet = be.setDocRevision("159_011", "6");
//	    Boolean bRtn = be.setPartUsageAttributes("D028325", "M00481", sAttName2, sAttValue2, sAttType2);
//	    Boolean bRtn = be.setPersonCompliancy("GB00001", 2);
//	    System.out.println("Got part usage attribute set with returned value = " + Boolean.toString(bRtn));
//	    System.out.println("success for doc creation with doc and name " + Boolean.toString(bRtn) ); //Boolean.toString(bRtn)
//	    help.deleteAttachment("120TD101", sAttachName, true);
//	    help.setDocumentAttributeStrings("120TD101","The new name", sAttName,sAttValue, "This is an updated check in");
	    be.deleteProbReport("BE00004");
	    System.out.println("success for attribute update");
		//WTDocument doc = 
		//		getDocumentByNumber("120TD266");
//		be.attachSecToDoc("C:/temp/Test.txt",doc);
	} 
	catch (Exception e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public static WTPartUsageLink getPartPartLinkFromDispatchDocketNo(String sParentPart, String sChildPart, long iLineNumber, String sDispatchDocketNo) throws WTException
{
	String ibaValue;
	String ibaLogicalId;
	WTPart partParent = getPartByNumber(sParentPart);
	System.out.println("Getting query result for parent part  = " + sParentPart);
	QueryResult queryReferences = StructHelper.service.navigateUses(partParent, WTPartUsageLink.class, false);
	while (queryReferences.hasMoreElements()) 
	{
		System.out.println("Got some results for parent part  = " + sParentPart);
		WTPartUsageLink link = (WTPartUsageLink) queryReferences.nextElement();
		System.out.println("Got link with identity from link.getIdentity()  = " + link.getIdentity());
		LineNumber  linkAtts = link.getLineNumber();
		com.ptc.core.lwc.server.LWCNormalizedObject obj = new com.ptc.core.lwc.server.LWCNormalizedObject(link, null,
		        java.util.Locale.US, new com.ptc.core.meta.common.DisplayOperationIdentifier());

		/* Get value of IBAName soft attribute */
		obj.load("DispatchDocketNo");
		String sDispatchDocketNoAtt = (java.lang.String) obj.get("DispatchDocketNo");
		if(linkAtts != null)
		{
			long iLineNumberAtt = linkAtts.getValue();
			if(iLineNumberAtt == iLineNumber && sDispatchDocketNoAtt.equals(sDispatchDocketNo))
				return link;
		}
	}
	
   return null;
}

public static WTPartUsageLink getPartPartLinkFromId(String sParentPart, String sChildPart, int iPartUsageId) throws WTException
{
	WTPart partParent = getPartByNumber(sParentPart);
	System.out.println("Getting query result for parent part  = " + sParentPart);
	QueryResult queryReferences = StructHelper.service.navigateUses(partParent, WTPartUsageLink.class, false);
	while (queryReferences.hasMoreElements()) 
	{
		System.out.println("Got some results for parent part  = " + sParentPart);
		WTPartUsageLink link = (WTPartUsageLink) queryReferences.nextElement();
		System.out.println("Got link with identity from link.getIdentity()  = " + link.getIdentity());
		BuildReference WTRef = link.getSourceIdentification();
		if(link.getIdentity() == Integer.toString(iPartUsageId))
		{
			return link;
		}
	}
	
   return null;
}

public static WTPart getPartByNumber(String sPartNumber) throws WTException 
{
	// TODO Auto-generated method stub
   QuerySpec qs = new QuerySpec(WTPart.class);
   SearchCondition condition = new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL, sPartNumber);
   qs.appendWhere(condition, new int[]{0});
   QueryResult qr = PersistenceHelper.manager.find((StatementSpec)qs);
   WTPart part = null;
   WTPart part2 = null;
	while(qr.hasMoreElements())
	{
		part = (WTPart)qr.nextElement();
		break;
	}
	if(part != null)
	{
		QueryResult qr2 = VersionControlHelper.service.allVersionsOf(part);
		part2= (WTPart)qr2.nextElement();
	}	
	
	return part2;
}

public static WTPartReferenceLink getPartDocRefLink(String sPartNo, String sDocNumber) throws WTException
{
    RegainRemoteHelper help = new RegainRemoteHelper();
	WTPart part = help.getPartByNumber(sPartNo);
    System.out.println("got part");
	QueryResult queryReferences = StructHelper.service.navigateReferences(part, WTPartReferenceLink.class, false);
    System.out.println("got query refs");
	while (queryReferences.hasMoreElements()) 
	{
	    System.out.println("getting link");
		WTPartReferenceLink link = (WTPartReferenceLink) queryReferences.nextElement();
	    System.out.println("got link");
		if(link.getRoleBObject() instanceof WTDocumentMaster)
		{
		    System.out.println("getting link");
			WTDocumentMaster docMaster2 = (WTDocumentMaster)link.getRoleBObject();
		    System.out.println("got doc master" + docMaster2.getNumber());
			if(sDocNumber.equals(docMaster2.getNumber()))
			{
			    System.out.println("returning link");
				return link;
			}
		}
		
		//PersistenceHelper.manager.delete(link);
	}
	
	return null;
}


public static Boolean setDocumentAttributeStringLocal(String sDocNumber, String sAttributeName, String sAttributeValue) throws WTException
{
     RegainRemoteHelper help = new RegainRemoteHelper();
	 WTDocument doc = help.getDocumentByNumber(sDocNumber);
	 
	 if(doc != null)
	 {
	 	try
	 	{
	 		System.out.println("Got doc A");
	 		Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
	 		System.out.println("Checked out the folder");
	 		CheckoutLink col = null;
	 		col = WorkInProgressHelper.service.checkout((WTDocument)doc,checkoutFolder,"");
	 		System.out.println("got col");
	 		WTDocument doc2 = (WTDocument)col.getWorkingCopy();
	 		System.out.println("got doc 2");
	 		LWCNormalizedObject obj = new LWCNormalizedObject(doc2, null, null, new UpdateOperationIdentifier());
	 		//PersistableAdapter obj = new PersistableAdapter(doc2,null,Locale.US,new UpdateOperationIdentifier());
	 		System.out.println("got obj");
	 	    obj.load(sAttributeName);
	 		System.out.println("loaded attribute");
	 		obj.set(sAttributeName, sAttributeValue);
	 		System.out.println("set attribute");
	 		obj.apply();
	 		System.out.println("applied attribute");
	 		PersistenceHelper.manager.modify(doc2);
	 		System.out.println("modified");
	 		doc2 = (WTDocument)WorkInProgressHelper.service.checkin(doc2,"");
	 		System.out.println("checked in");
	 		return true;
	 	}
	 	catch ( Exception e )
	 	{ 
	 		e.printStackTrace();
	        return false;
	 	}
	 }
	 
	 return true;
	 
}

public static java.sql.Timestamp GetDateFromString(String sDateAsString, String sFormat)
{
	DateFormat df = new SimpleDateFormat(sFormat); 
	TimeZone tz = TimeZone.getTimeZone("UTC");
	df.setTimeZone(tz);
	Date startDate;
    try 
    {
       startDate = df.parse(sDateAsString);
       Calendar cal = Calendar.getInstance();
       TimeZone tz2 = cal.getTimeZone();
       Calendar cal2 = Calendar.getInstance(tz2);
       int iOffset = tz2.getOffset(cal2.getTime().getTime());
//       iOffset = TimeZone.getTimeZone("AEST").getOffset(Calendar.getInstance().getTimeInMillis());
       int iOffset2 = TimeZone.getTimeZone("UTC").getOffset(Calendar.getInstance().getTimeInMillis());
  	    System.out.println("offset = " + Integer.toString(iOffset) );
  	    System.out.println("offset2 = " + Integer.toString(iOffset2) );
  	    System.out.println("start date 1 set " + startDate.toString() );
       Date startDate2 = DateUtils.addMilliseconds(startDate, iOffset2 * -1);
  	    System.out.println("start date 2 set " + startDate2.toString() );
   	   java.sql.Timestamp dtsql = new java.sql.Timestamp(startDate2.getTime());
  	    System.out.println("date set " + dtsql.toString() );
       return dtsql;
    } 
    catch (ParseException e) 
    {
       e.printStackTrace();
       return null;
    }		 
}

private static void logToFile(String sMsg)
{
	File logFile = new File("C:/WebRoot/logs/WCDebug.log");
	BufferedWriter writer = null;
	try 
	{
		String timeLog = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
		writer = new BufferedWriter(new FileWriter(logFile, true));
		sMsg += " " + timeLog + "\r\n";
        writer.write(sMsg);
	} 
	catch (IOException e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally 
	{
       try 
       {
           // Close the writer regardless of what happens...
           writer.close();
       } 
       catch (Exception e) 
       {
       }
   }
}




}
