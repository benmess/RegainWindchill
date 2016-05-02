package ext.regain.document;


import java.beans.PropertyVetoException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.ptc.core.lwc.server.LWCNormalizedObject;
import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.meta.common.UpdateOperationIdentifier;

import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.Folder;
import wt.folder.FolderHelper;
import wt.folder.FolderingInfo;
import wt.inf.container.WTContainerRef;
import wt.util.WTException;
import wt.vc.struct.StructHelper;
import wt.vc.wip.CheckoutLink;
import wt.vc.wip.WorkInProgressHelper;
import wt.part.WTPart;
import wt.part.WTPartReferenceLink;
import wt.security.*;
import ext.regain.document.RegainBackend;

public class HelloWorld {
public static void main(String[] args) 
{
	
	try {
		System.out.println("abc");
		RegainBackend be = new RegainBackend();
		String sFolderName = "116 Process Plant Tomago/120 Thermal Treatment Plant/Technical Documents 120TD Thermal Treatment Plant/"; //"Technical Documents 120TD Thermal Treatment Plant";   
		String sProductName = "116 Tomago Operation";   
		String sCheckinComments = "These are the check in comments on creation";
		String sAttachName = "Carambola-java.zip";
	    RegainRemoteHelper help = new RegainRemoteHelper();
//	    WTContainerRef ref = help.GetProductRef(sProductName);
	    System.out.println("about to execute folder info with name " + sFolderName); //String.valueOf(ref.getContainer()()
	    String[] sAttName = new String[3];
	    String[] sAttValue = new String[3];
	    
	    sAttName[0] = "LongDescription";
	    sAttName[1] = "Originator";
	    sAttName[2] = "OriginatorDocId";

	    sAttValue[0] = "A new long description by ben changed again";
	    sAttValue[1] = "benmess";
	    sAttValue[2] = "xxxxyyyy";

//	    Folder  folder = (Folder) FolderHelper.service.getFolder(sFolderName.toString(), WTContainerRef.newWTContainerRef(ref)); //, 
//	    Folder folder = help.GetFolder2(sFolderName, sProductName);
	    String[] sPartRefs = new String[2];
	    sPartRefs[0] = "P120P030";
	    sPartRefs[1] = "P120P130";
	    //getPartDocRefLink("P120P030","120TD101");
	    //be.deleteDocToPartRef("benthebest","120TD101", "P120A100","deleted link to doc 120TD101");

	    //Boolean bResult = be.deleteDocToPartRefs("120TD101", sPartRefs, "Linking to document 120TD101");
//	    System.out.println("success with folder " + folder.getLocation() + " description " + folder.getDescription() + " name = " + folder.getName());
		be.deleteAttachment("benmess33myage", "120TD101", "LogMeIn.msi", true);
//		be.attachDoc("benmess33myage","120TD101", "The attach desc", "C:/WebRoot/Regain/Uploads/LogMeIn.msi", true, "Check to see if originator comes across");
//		Boolean doc = be.createWTDoc("120TD116", "Name with non wcadmin user", sProductName, "local.rs.vsrs05.Regain.TD", sFolderName, sAttName, sAttValue, sCheckinComments);//GetDocumentByNumber("120TD266");
	    System.out.println("success for doc creation with doc");
//	    help.deleteAttachment("120TD101", sAttachName, true);
//	    help.setDocumentAttributeStrings("120TD101","The new name", sAttName,sAttValue, "This is an updated check in");
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
