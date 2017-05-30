package ext.regain.document;

import java.beans.PropertyVetoException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.*;
import java.util.*;

import com.ptc.core.lwc.server.LWCNormalizedObject;
/*import org.apache.commons.lang.time.DateUtils;

import com.ibm.icu.util.GregorianCalendar;
import com.ptc.core.logging.Log;
import com.ptc.core.lwc.server.LWCNormalizedObject;
*/
import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.meta.common.CreateOperationIdentifier;
import com.ptc.core.meta.common.UpdateOperationIdentifier;



















import wt.build.BuildReference;
/*import ext.regain.document.Backend.Email;
import com.ptc.windchill.wp.client.UnattendedClientCommands;
import com.sun.xml.fastinfoset.util.StringArray;

import wt.access.AccessPermission;
import wt.admin.AdminDomainRef;
import wt.change2.AffectedActivityData;
import wt.change2.ChangeActivity2;
*/
import wt.change2.ChangeHelper2;
//import wt.change2.ChangeIssue;
import wt.change2.ChangeNoticeComplexity;
//import wt.change2.Changeable2;
//import wt.change2.RelevantRequestData2;
import wt.change2.ReportedAgainst;
import wt.change2.WTChangeIssue;
import wt.change2.WTChangeIssueMaster;
import wt.change2.WTChangeIssueMasterIdentity;
import wt.change2.WTChangeOrder2;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.content.HolderToContent;
//import wt.doc.DocumentMaster;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.doc.WTDocumentMasterIdentity;
import wt.fc.Identified;
import wt.fc.IdentityHelper;
import wt.fc.Persistable;
//import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
/*import wt.fc.WTReference;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTCollection;
import wt.fc.collections.WTHashSet;
import wt.fc.collections.WTKeyedHashMap;
import wt.fc.collections.WTKeyedMap;
import wt.fc.collections.WTList;
import wt.fc.collections.WTSet;
import wt.fc.collections.WTValuedMap;
import wt.folder.Cabinet;
import wt.folder.CabinetBased;
*/
import wt.folder.Folder;
//import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
/*import wt.folder.FolderNotFoundException;
import wt.folder.FolderService;
import wt.folder.Foldered;
*/
import wt.folder.FolderingInfo;
import wt.folder.SubFolder;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.value.AttributeContainer;
import wt.iba.value.DefaultAttributeContainer;
import wt.iba.value.IBAValueUtility;
import wt.iba.value.litevalue.AbstractValueView;
import wt.inf.container.WTContainerRef;
import wt.inf.library.WTLibrary;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.LineNumber;
import wt.part.Quantity;
import wt.part.QuantityUnit;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartMasterIdentity;
import wt.part.WTPartReferenceLink;
import wt.part.WTPartUsageLink;
import wt.pdmlink.PDMLinkProduct;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.series.MultilevelSeries;
//import wt.series.Series;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtility;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.IterationIdentifier;
import wt.vc.VersionControlHelper;
import wt.vc.VersionIdentifier;
//import wt.vc.VersionInfo;
//import wt.vc.struct.IteratedReferenceLink;
import wt.vc.struct.StructHelper;
//import wt.vc.wip.CheckoutInfo;
import wt.vc.wip.CheckoutLink;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;
import wt.workflow.engine.WfActivity;
import wt.pom.*;



@SuppressWarnings({ "rawtypes", "serial", "deprecation" })
public class RegainRemoteHelper implements Serializable, RemoteAccess 
{
	public static class ReturnClassBool
	{
		public boolean bValue;
		public String sRtnMsg;
	}

	//	static String sTransMsg = "";
	public void execute() throws Exception 
	{
        if (!RemoteMethodServer.ServerFlag){

            RemoteMethodServer ms = RemoteMethodServer.getDefault();
			Class[] argTypes = {};
            Object[] argValues = {};
            ms.invoke("execute", null, this, argTypes, argValues);
            return;
        }

        SessionContext.newContext();
        SessionHelper.manager.setAdministrator();
        Transaction trans = new Transaction();
        
        try
        {
        	trans.start();
        	createdoc("XYZ999", "a test name", "116 Tomago Operation", "local.rs.vsrs05.Regain.RegainRefDocument", "Technical Documents 120TD Thermal Treatment Plant", "C", null, null, "", 0);
        	trans.commit();
            trans = null;
        }
        catch(Throwable t) 
        {
            throw new ExceptionInInitializerError(t);
        } 
        finally 
        {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
        }
	}
	
    public static TypeDefinitionReference getTypeDef(String sType)
	{
	  TypeDefinitionReference typeRef = TypedUtility.getTypeDefinitionReference(sType);
	  return typeRef;
	}

	public static ApplicationData getAppData(ApplicationData objAppData, ContentHolder contentHolder, String filePath) throws FileNotFoundException, WTException, PropertyVetoException, IOException
	{
		objAppData = ContentServerHelper.service.updateContent(contentHolder, objAppData, filePath);
//		System.out.println(objAppData.getFileName());
		return objAppData;
	}
	
	public WTDocument createdoc(String sNumber, String sName, String sProductName, String sDocType, String sFolderName, String sRevision,  
								String[] sAttributeName, String[] sAttributeValue, String sCheckInComments, int iProdOrLibrary) throws Exception 
	{
		int i;
        if (!RemoteMethodServer.ServerFlag)
        {

            RemoteMethodServer ms = RemoteMethodServer.getDefault();
/*            ms.setUserName("benmess");
            ms.setPassword("mo9anaapr!");
*/            Class[] argTypes = {String.class, String.class, String.class, String.class, String.class, String.class, String[].class, String[].class, String.class, int.class};
            Object[] argValues = {sNumber, sName, sProductName, sDocType, sFolderName, sRevision, sAttributeName, sAttributeValue, sCheckInComments, iProdOrLibrary};
            WTDocument thisdoc = (WTDocument)ms.invoke("createdoc", null, this, argTypes, argValues);
            return thisdoc;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);
        
        Transaction trans = new Transaction();
        
        try
        {
        	trans.start();
		    WTDocument doc = WTDocument.newWTDocument();
		    doc.setName(sName);
		    if(!sNumber.equals(""))
		    	doc.setNumber(sNumber);
	
		    WTContainerRef prodref = null;
		    
//		    System.out.println("About to get product");
		    if(iProdOrLibrary == 0)
		    	prodref = GetProductRef(sProductName);
		    else
		    	prodref = GetLibraryRef(sProductName);
		    
//		    System.out.println("Setting container");
		    doc.setContainerReference(prodref);
//		    System.out.println("Setting folder");
		    Folder folder = GetFolder2(sFolderName, sProductName, iProdOrLibrary);
//		    System.out.println("got folder");

		    if (folder != null) 
		    {
	            FolderHelper.assignLocation(doc, folder);
	        }

		 // set revision
		    VersionIdentifier vc = VersionIdentifier.newVersionIdentifier(MultilevelSeries.newMultilevelSeries("wt.series.HarvardSeries.RegainStateBased", sRevision));
		    doc.getMaster().setSeries("wt.series.HarvardSeries.RegainStateBased");
		    VersionControlHelper.setVersionIdentifier(doc, vc);
//		    System.out.println("got version");
		    
//		    FolderingInfo folderInf = GetFolder(sFolderName);
//		    doc.setFolderingInfo(folderInf);
	
		    TypeDefinitionReference typeRef = getTypeDef(sDocType);
	//	    TypeDefinitionReference typeRef = (TypeDefinitionReference)myServer.invoke("getTypeDef", RegainRemoteHelper.class.getName(), null, aClass,aObj);
	
		    doc.setTypeDefinitionReference(typeRef);
//		    System.out.println("set type def");
			for(i=0;i<sAttributeName.length;i++)
			{
				PersistableAdapter obj = new PersistableAdapter(doc,null,Locale.US,new CreateOperationIdentifier());
			    obj.load(sAttributeName[i]);
				obj.set(sAttributeName[i], sAttributeValue[i]);
				obj.apply();
//			    System.out.println("set attribute " + i);
			}
			doc = (WTDocument)PersistenceHelper.manager.store(doc);
//		    System.out.println("stored doc");
		    PersistenceHelper.manager.refresh(doc);
//		    System.out.println("refreshed doc");
	    	trans.commit();
	        trans = null;
	        if(sCheckInComments.length() > 0)
	        {
//			    System.out.println("checking in");
				VersionControlHelper.setNote(doc, sCheckInComments);
//			    System.out.println("setting comments");
				PersistenceServerHelper.update(doc);
//			    System.out.println("set comments");
	        }
		    return doc;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }
	}
 
	public static WTContainerRef GetProductRef(String sProdName) throws WTException, WTPropertyVetoException
	{
		
	    QuerySpec qs = new QuerySpec(PDMLinkProduct.class); //WTLibrary for a library
	    SearchCondition condition = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, sProdName);
	    qs.appendWhere(condition, new int[]{0});
	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec)qs);
	    PDMLinkProduct prod = null;
	    while(qr.hasMoreElements())
	    {
	    	prod = (PDMLinkProduct)qr.nextElement();
	    	break;
	    }
	    
	    ReferenceFactory reffac = new ReferenceFactory();
	    return (WTContainerRef)reffac.getReference(prod);
	}

	public static WTContainerRef GetLibraryRef(String sLibraryName) throws WTException, WTPropertyVetoException
	{
		
	    QuerySpec qs = new QuerySpec(WTLibrary.class); //WTLibrary for a library
	    SearchCondition condition = new SearchCondition(WTLibrary.class, WTLibrary.NAME, SearchCondition.EQUAL, sLibraryName);
	    qs.appendWhere(condition, new int[]{0});
	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec)qs);
	    WTLibrary lib = null;
	    while(qr.hasMoreElements())
	    {
	    	lib = (WTLibrary)qr.nextElement();
	    	break;
	    }
	    
	    ReferenceFactory reffac = new ReferenceFactory();
	    return (WTContainerRef)reffac.getReference(lib);
	}

	public static Folder GetFolder2(String sFolderNameAndFullPath, String sProductName, int iProdOrLibrary)
	{
	    Folder  folder = null;
	    try
	    {
		    WTContainerRef prodref = null;
		    
		    if(iProdOrLibrary == 0)
		    	prodref = GetProductRef(sProductName);
		    else
		    	prodref = GetLibraryRef(sProductName);

		    folder = (Folder) FolderHelper.service.getFolder("/Default/" + sFolderNameAndFullPath.toString(), WTContainerRef.newWTContainerRef(prodref));
	    }
	    catch(Exception ex) 
	    {
	        folder = null;
	    } 

	    return folder;
	}

	
	public static FolderingInfo GetFolder(String sFolderName) throws WTException, WTPropertyVetoException
	{
	    QuerySpec qs = new QuerySpec(FolderingInfo.class); //WTLibrary for a library
	    SearchCondition condition = new SearchCondition(SubFolder.class, SubFolder.NAME, SearchCondition.EQUAL, sFolderName);
	    qs.appendWhere(condition, new int[]{0});
	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec)qs);
	    SubFolder fi = null;
		
		//fi = null;
	    while(qr.hasMoreElements())
	    {
	    	fi = (SubFolder)qr.nextElement();
	    	break;
	    }
	    
//	    ReferenceFactory reffac = new ReferenceFactory();
	    return (FolderingInfo)fi.getFolderingInfo();
	}

	/**
	 * Function to add the given file as the primary or secondary content of the given ContentHolder
	 * 
	  * @param filePath
	 * @param contentHolder
	 * @return
	 * @throws PropertyVetoException
	 * @throws WTException
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws InvocationTargetException 
	 */
	 public boolean attachToDoc(String sUser, String sDocNo, String sAttachDesc, String filePath, Boolean bSecondary, String sCheckInComments) throws WTException, PropertyVetoException, FileNotFoundException, IOException, InvocationTargetException
	 {
        if (!RemoteMethodServer.ServerFlag)
        {

            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class, String.class, String.class, Boolean.class, String.class};
            Object[] argValues = {sUser, sDocNo, sAttachDesc, filePath, bSecondary, sCheckInComments};
            ms.invoke("attachToDoc", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
        
        try
        {
        	trans.start();
        	WTDocument doc = getDocumentByNumber(sDocNo);

			Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
			CheckoutLink col = null;
			col = WorkInProgressHelper.service.checkout((WTDocument)doc,checkoutFolder,"");
			WTDocument doc2 = (WTDocument)col.getWorkingCopy();

			ContentHolder contentHolder = (ContentHolder)doc2;
		     contentHolder = ContentHelper.service.getContents(contentHolder);

	       // START -- Delete contents if they have same name as the file name to be uploaded
	       ContentRoleType roleType = ContentRoleType.PRIMARY;
	       
	       if(bSecondary)
	    	roleType =  ContentRoleType.SECONDARY;
	       
	       QueryResult qrContents = ContentHelper.service.getContentsByRole(contentHolder, roleType); //ContentRoleType.SECONDARY

	       // Get file name of current file to be attached as secondary content
	       File objFile = new File(filePath);
	       String nameOfFileToBeAttached = objFile.getName();

	       ApplicationData fileContent = null;
	       String nameOfExistingSecContent = null;
	       while (qrContents.hasMoreElements())
	       {
	             fileContent = (ApplicationData) qrContents.nextElement();
	             nameOfExistingSecContent = fileContent.getFileName();
	             if(nameOfExistingSecContent.equals(nameOfFileToBeAttached) || !bSecondary)
	            	 deleteExistingContent(contentHolder, fileContent);
	       }
	       // END -- Delete contents if they have same name as the file name to be uploaded

	       ApplicationData objAppData = ApplicationData.newApplicationData(contentHolder);
	       if(contentHolder instanceof WTDocument)
	       {
	             //WTDocument wtDoc = (WTDocument)contentHolder;
	    	   objAppData.setDescription(sAttachDesc);
	             if(bSecondary)
	            	 objAppData.setRole(ContentRoleType.toContentRoleType("SECONDARY"));
	             else
	            	 objAppData.setRole(ContentRoleType.toContentRoleType("PRIMARY"));
	       }

/*	       RemoteMethodServer myServer = RemoteMethodServer.getDefault();
	       myServer.setUserName("wcadmin");
	       myServer.setPassword("wcadmin!");
*/	       //Class aClass[] = {ApplicationData.class, ContentHolder.class, String.class};
	       //Object aObj[] = {objAppData,contentHolder, filePath };
	       
//	       objAppData = (ApplicationData)myServer.invoke("getAppData", RegainRemoteHelper.class.getName(), null, aClass,aObj);
	       
	        objAppData = ContentServerHelper.service.updateContent(contentHolder, objAppData, filePath);
			PersistableAdapter obj = new PersistableAdapter(doc2,null,Locale.US,new UpdateOperationIdentifier());
		    obj.load("Originator");
			obj.set("Originator", sUser);
			obj.apply();
			PersistenceHelper.manager.modify(doc2);				
			doc2 = (WTDocument)WorkInProgressHelper.service.checkin(doc2,sCheckInComments);
	       trans.commit();
	        trans = null;
		    return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }

	 public boolean deleteAttachment(String sUser, String sDocNo, String filePath, Boolean bSecondary) throws WTException, PropertyVetoException, FileNotFoundException, IOException, InvocationTargetException
	 {
		Boolean bDeleted = false;
        if (!RemoteMethodServer.ServerFlag)
        {

            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class, String.class,Boolean.class};
            Object[] argValues = {sUser, sDocNo, filePath, bSecondary};
            ms.invoke("deleteAttachment", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
        
        try
        {
        	trans.start();
        	WTDocument doc = getDocumentByNumber(sDocNo);

			Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
			CheckoutLink col = null;
			col = WorkInProgressHelper.service.checkout((WTDocument)doc,checkoutFolder,"");
			WTDocument doc2 = (WTDocument)col.getWorkingCopy();

			ContentHolder contentHolder = (ContentHolder)doc2;
    		contentHolder = ContentHelper.service.getContents(contentHolder);

	       // START -- Delete contents if they have same name as the file name to be uploaded
	       ContentRoleType roleType = ContentRoleType.PRIMARY;
	       
	       if(bSecondary)
	    	roleType =  ContentRoleType.SECONDARY;
	       
	        QueryResult qrContents = ContentHelper.service.getContentsByRole(contentHolder, roleType); 

	       // Get file name of current file to be attached as secondary content
	       File objFile = new File(filePath);
	       String nameOfFileToBeAttached = objFile.getName();

	       ApplicationData fileContent = null;
	       String nameOfExistingSecContent = null;
	       while (qrContents.hasMoreElements())
	       {
	             fileContent = (ApplicationData) qrContents.nextElement();
	             nameOfExistingSecContent = fileContent.getFileName();
	             if(nameOfExistingSecContent.equals(nameOfFileToBeAttached))
	             {
	            	 deleteExistingContent(contentHolder, fileContent);
	            	 bDeleted = true;
	             }
	       }
	       // END -- Delete contents if they have same name as the file name to be uploaded

	       if(bDeleted)
	       {
		       	String sCheckInComments = "Deleted attachment " + nameOfFileToBeAttached;
				PersistableAdapter obj = new PersistableAdapter(doc2,null,Locale.US,new UpdateOperationIdentifier());
			    obj.load("Originator");
				obj.set("Originator", sUser);
				obj.apply();
				PersistenceHelper.manager.modify(doc2);				
				doc2 = (WTDocument)WorkInProgressHelper.service.checkin(doc2,sCheckInComments);
	       }
	       else
	       {
	    	    doc2 = (WTDocument)WorkInProgressHelper.service.undoCheckout(doc2);	       
	       }

  	        trans.commit();
	        trans = null;
		    return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }

	 /**
	 * Function to delete a secondary content, if one with the given name exists.
	 * 
	  * @param contentholder
	 * @param contentitem
	 * @throws WTException
	 * @throws WTPropertyVetoException
	 */
	 private static void deleteExistingContent(ContentHolder contentholder, ContentItem contentitem) throws WTException, WTPropertyVetoException
	 {
	       if (PersistenceHelper.isPersistent(contentitem))
	       {
	             if (contentitem.getHolderLink() == null)
	             {
	                   QueryResult queryresult = PersistenceHelper.manager.navigate(contentitem, HolderToContent.CONTENT_ITEM_ROLE, wt.content.HolderToContent.class, false);
	                   if (queryresult.hasMoreElements())
	                         contentitem.setHolderLink((HolderToContent) queryresult.nextElement());
	             }

	             if (contentitem.getHolderLink() != null)
	                   PersistenceHelper.manager.delete(contentitem.getHolderLink());
	       }
	 }
	 
	 public static WTDocument getDocumentByNumber(String sDocNumber) throws WTException 
	{
		// TODO Auto-generated method stub
	    QuerySpec qs = new QuerySpec(WTDocument.class);
	    SearchCondition condition = new SearchCondition(WTDocument.class, WTDocument.NUMBER, SearchCondition.EQUAL, sDocNumber);
	    qs.appendWhere(condition, new int[]{0});
	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec)qs);
	    WTDocument doc = null;
	    WTDocument doc2 = null;
		while(qr.hasMoreElements())
		{
			doc = (WTDocument)qr.nextElement();
			break;
		}
		if(doc != null)
		{
			QueryResult qr2 = VersionControlHelper.service.allVersionsOf(doc);
			doc2= (WTDocument)qr2.nextElement();
		}
		
		return doc2;
	}

	 public static String getDocumentNameByNumber(String sDocNumber) throws WTException 
	{
		WTDocument doc = getDocumentByNumber(sDocNumber);		
		return doc.getName();
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

	 public static TypeDefinitionReference getTypeDefRef(String sType) throws WTException 
	{
		// TODO Auto-generated method stub
	    QuerySpec qs = new QuerySpec(WTPart.class);
	    SearchCondition condition = new SearchCondition(TypeDefinitionReference.class, TypeDefinitionReference.KEY, SearchCondition.EQUAL, sType);
	    qs.appendWhere(condition, new int[]{0});
	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec)qs);
	    TypeDefinitionReference typedef = null;
		while(qr.hasMoreElements())
		{
			typedef = (TypeDefinitionReference)qr.nextElement();
			break;
		}
		
		return typedef;
	}

	 public Boolean setDocumentAttributeStrings(String sDocNumber, String sDocName, String[] sAttributeName, String[] sAttributeValue, String sCheckInComments) throws WTException, RemoteException, InvocationTargetException
	 {
		int i;
        if (!RemoteMethodServer.ServerFlag)
        {
            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class, String[].class, String[].class, String.class};
            Object[] argValues = {sDocNumber, sDocName, sAttributeName, sAttributeValue, sCheckInComments};
            ms.invoke("setDocumentAttributeStrings", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
	        
        try
        {
	        trans.start();
			WTDocument doc = getDocumentByNumber(sDocNumber);
			if(doc != null)
			{
				WTDocumentMaster master = (WTDocumentMaster)doc.getMaster();
				WTDocumentMasterIdentity masterId = (WTDocumentMasterIdentity)master.getIdentificationObject();
				masterId.setName(sDocName);
				IdentityHelper.service.changeIdentity((Identified) master, masterId);

				master = (WTDocumentMaster) PersistenceHelper.manager.refresh(master);
//				doc.setName(sDocName);
				Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
				CheckoutLink col = null;
				col = WorkInProgressHelper.service.checkout((WTDocument)doc,checkoutFolder,"");
				WTDocument doc2 = (WTDocument)col.getWorkingCopy();
				//LWCNormalizedObject obj = new LWCNormalizedObject(doc2, null, null, new UpdateOperationIdentifier());
				for(i=0;i<sAttributeName.length;i++)
				{
					PersistableAdapter obj = new PersistableAdapter(doc2,null,Locale.US,new UpdateOperationIdentifier());
				    obj.load(sAttributeName[i]);
					obj.set(sAttributeName[i], sAttributeValue[i]);
					obj.apply();
				}
				PersistenceHelper.manager.modify(doc2);				
				doc2 = (WTDocument)WorkInProgressHelper.service.checkin(doc2,sCheckInComments);
			}
		 
			trans.commit();
			trans = null;
			return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }
	 
	 // sAttributeType can have values
	 //		Boolean - bool or boolean
	 //		Date & Time - date or datetime
	 //		Integer Number - int or integer
	 //		Real Number - real or doub or double or float
	 //		String - string or the default
	 public Boolean setDocumentAttributes(String sDocNumber, String sDocName, String[] sAttributeName, String[] sAttributeValue, 
			 						  String[] sAttributeType, String sCheckInComments) throws WTException, RemoteException, InvocationTargetException
	 {
		int i;
        if (!RemoteMethodServer.ServerFlag)
        {
//        	System.out.println("About to call setDocumentAttributes");
            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class, String[].class, String[].class, String[].class, String.class};
            Object[] argValues = {sDocNumber, sDocName, sAttributeName, sAttributeValue, sAttributeType, sCheckInComments};
//        	System.out.println("Invoking setDocumentAttributes");
            ms.invoke("setDocumentAttributes", null, this, argTypes, argValues);
            return true;
        }

//    	System.out.println("Getting session");
        SessionContext.newContext();
        String sUsername = getBackendUser();
//    	System.out.println("Got sUsername = " + sUsername);
        SessionHelper.manager.setPrincipal(sUsername);
//    	System.out.println("Set sUsername = " + sUsername);

        Transaction trans = new Transaction();
	        
        try
        {
	        trans.start();
			WTDocument doc = getDocumentByNumber(sDocNumber);
//	    	System.out.println("Got document = " + doc.getName());

			if(doc != null)
			{
				WTDocumentMaster master = (WTDocumentMaster)doc.getMaster();
				WTDocumentMasterIdentity masterId = (WTDocumentMasterIdentity)master.getIdentificationObject();
				masterId.setName(sDocName);
				IdentityHelper.service.changeIdentity((Identified) master, masterId);

				master = (WTDocumentMaster) PersistenceHelper.manager.refresh(master);
				Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
				CheckoutLink col = null;
				col = WorkInProgressHelper.service.checkout((WTDocument)doc,checkoutFolder,"");
				WTDocument doc2 = (WTDocument)col.getWorkingCopy();
				for(i=0;i<sAttributeName.length;i++)
				{
					PersistableAdapter obj = new PersistableAdapter(doc2,null,Locale.US,new UpdateOperationIdentifier());
				    obj.load(sAttributeName[i]);
				    Object objAttributeValue = null;
				    switch(sAttributeType[i])
				    {
					    case "string":
					    default:
					    	if(sAttributeValue[i].equals("null"))
					    		objAttributeValue = null;
					    	else
					    		objAttributeValue = sAttributeValue[i];
					    	break;
					    case "date":
					    case "datetime":
					    	objAttributeValue = GetDateFromString(sAttributeValue[i], "dd/MM/yyyy hh:mm:ss a");
					    	break;
					    case "bool":
					    case "boolean":
//System.out.println("About to set boolean for sAttributeValue[i] = " + sAttributeValue[i]);
					    	objAttributeValue = Boolean.valueOf(sAttributeValue[i]);
					    	break;
					    case "int":
					    case "integer":
					    	objAttributeValue = Integer.valueOf(sAttributeValue[i]);
					    	break;
					    case "doub":
					    case "double":
					    case "real":
					    case "float":
					    	objAttributeValue = com.ptc.core.meta.common.FloatingPoint.valueOf(sAttributeValue[i]);
					    	break;
					    	
				    }
//				    System.out.println("About to set attribute");
				    obj.set(sAttributeName[i], objAttributeValue);
					obj.apply();
				}
				PersistenceHelper.manager.modify(doc2);				
//			    System.out.println("Checking in");
				doc2 = (WTDocument)WorkInProgressHelper.service.checkin(doc2,sCheckInComments);
//			    System.out.println("Completed");
			}
		 
			trans.commit();
			trans = null;
			return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }
	 public Boolean setDocumentRevision(String sDocNumber, String sRevision) throws WTException, RemoteException, InvocationTargetException
	 {
        if (!RemoteMethodServer.ServerFlag)
        {
            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class};
            Object[] argValues = {sDocNumber, sRevision};
            ms.invoke("setDocumentRevision", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
	        
        try
        {
	        trans.start();
			WTDocument doc = getDocumentByNumber(sDocNumber);
			if(doc != null)
			{
//				System.out.println("about to version document");
				MultilevelSeries mls = MultilevelSeries.newMultilevelSeries("wt.series.HarvardSeries.RegainStateBased", sRevision);
				VersionIdentifier version_id = VersionIdentifier.newVersionIdentifier(mls);
				IterationIdentifier iteration_id = VersionControlHelper.firstIterationId(doc);

//				System.out.println("about to store document");
				WTDocument newVersion = (WTDocument)VersionControlHelper.service.newVersion(doc, version_id, iteration_id);
				newVersion = (WTDocument)PersistenceHelper.manager.store(newVersion);

//				System.out.println("got document with revision ");						
			}
		 
			trans.commit();
			trans = null;
			return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }

	 public Boolean setReferencedByLink(String sUser, String sDocNumber, String sPartNumber, String sCheckInComments) throws WTException, RemoteException, InvocationTargetException
	 {
        if (!RemoteMethodServer.ServerFlag)
        {
            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class, String.class, String.class};
            Object[] argValues = {sUser, sDocNumber, sPartNumber, sCheckInComments};
            ms.invoke("setReferencedByLink", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
	        
        try
        {
	        trans.start();
			WTDocument doc = getDocumentByNumber(sDocNumber);
			if(doc != null)
			{
				WTDocumentMaster master = (WTDocumentMaster)doc.getMaster();
				WTPart part = getPartByNumber(sPartNumber);
				WTPartReferenceLink link = new WTPartReferenceLink();

				Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
				CheckoutLink col = null;
				col = WorkInProgressHelper.service.checkout((WTPart)part,checkoutFolder,"");
				WTPart part2 = (WTPart)col.getWorkingCopy();
				PersistableAdapter obj = new PersistableAdapter(part2,null,Locale.US,new UpdateOperationIdentifier());
			    obj.load("Originator");
				obj.set("Originator", sUser);
				obj.apply();
				PersistenceHelper.manager.modify(part2);				

				link.setRoleAObject(part2);
				link.setRoleBObject(master);
				link = (WTPartReferenceLink)PersistenceHelper.manager.store(link);
				PersistenceHelper.manager.refresh(link);
				part2 = (WTPart)WorkInProgressHelper.service.checkin(part2,sCheckInComments);
			}
		 
			trans.commit();
			trans = null;
			return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }
	 
	 public WTPartReferenceLink getPartDocRefLink(String sPartNo, String sDocNumber) throws WTException
	 {
		WTPart part = getPartByNumber(sPartNo);
		QueryResult queryReferences = StructHelper.service.navigateReferences(part, WTPartReferenceLink.class, false);
		while (queryReferences.hasMoreElements()) 
		{
			WTPartReferenceLink link = (WTPartReferenceLink) queryReferences.nextElement();
			if(link.getRoleBObject() instanceof WTDocumentMaster)
			{
				WTDocumentMaster docMaster2 = (WTDocumentMaster)link.getRoleBObject();
				if(sDocNumber.equals(docMaster2.getNumber()))
				{
					return link;
				}
			}
		}
		
	    return null;
	 }

	 public WTPartUsageLink getPartPartLink(String sParentPart, String sChildPart) throws WTException
	 {
		WTPart partParent = getPartByNumber(sParentPart);
		QueryResult queryReferences = StructHelper.service.navigateUses(partParent, WTPartUsageLink.class, false);
		while (queryReferences.hasMoreElements()) 
		{
			WTPartUsageLink link = (WTPartUsageLink) queryReferences.nextElement();
			if(link.getRoleBObject() instanceof WTPartMaster)
			{
				WTPartMaster partMaster2 = (WTPartMaster)link.getRoleBObject();
				if(sChildPart.equals(partMaster2.getNumber()))
				{
					return link;
				}
			}
		}
		
	    return null;
	 }

	 public WTPartUsageLink getPartPartLinkFromId(String sParentPart, String sChildPart, int iPartUsageId) throws WTException
	 {
		WTPart partParent = getPartByNumber(sParentPart);
		System.out.println("Getting query result for parent part  = " + sParentPart);
		QueryResult queryReferences = StructHelper.service.navigateUses(partParent, WTPartUsageLink.class, false);
		while (queryReferences.hasMoreElements()) 
		{
			System.out.println("Got some results for parent part  = " + sParentPart);
			WTPartUsageLink link = (WTPartUsageLink) queryReferences.nextElement();
			System.out.println("Got link with identity from link.getIdentity()  = " + link.getIdentity());
			if(link.getIdentity().equals("wt.part.WTPartUsageLink:" + Integer.toString(iPartUsageId)))
			{
				BuildReference WTRef = link.getSourceIdentification();
				System.out.println("Found actual link  = " + link.getIdentity() + " - " + WTRef.UNIQUE_ID);
//				WTRef.UNIQUE_ID
				return link;
			}
		}
		
	    return null;
	 }

	 @SuppressWarnings("static-access")
	 public static WTPartUsageLink getPartPartLinkFromDispatchDocketNo(String sParentPart, String sChildPart, long iLineNumber, String sDispatchDocketNo) throws WTException
	 {
	 	WTPart partParent = getPartByNumber(sParentPart);
	 	WTPartUsageLink linkrtn = null;
//	 	System.out.println("Getting query result for parent part  = " + sParentPart);
	 	QueryResult queryReferences = StructHelper.service.navigateUses(partParent, WTPartUsageLink.class, false);
	 	while (queryReferences.hasMoreElements()) 
	 	{
//	 		System.out.println("Got some results for parent part  = " + sParentPart);
	 		WTPartUsageLink link = (WTPartUsageLink) queryReferences.nextElement();
//	 		System.out.println("Got link with identity from link.getIdentity()  = " + link.getIdentity());
	 		LineNumber  linkAtts = link.getLineNumber();
			PersistableAdapter obj = new PersistableAdapter(link,null,Locale.US,new UpdateOperationIdentifier());
//			System.out.println("About to load attribute for dispatch docket");
		    obj.load("DispatchDocketNo");
//			System.out.println("Loaded attribute for dispatch docket");
/*	 		LWCNormalizedObject obj = new LWCNormalizedObject(link, null, java.util.Locale.US, new com.ptc.core.meta.common.DisplayOperationIdentifier());

	 		obj.load("DispatchDocketNo");
*/
	 		String sDispatchDocketNoAtt = (java.lang.String) obj.get("DispatchDocketNo");
			System.out.println("Got sDispatchDocketNoAtt = " + sDispatchDocketNoAtt);
	 		if(linkAtts != null)
	 		{
	 			long iLineNumberAtt = linkAtts.getValue();
//				System.out.println("Got sDispatchDocketNoAtt = " + sDispatchDocketNoAtt);
	 			if(iLineNumberAtt == iLineNumber && sDispatchDocketNoAtt.equals(sDispatchDocketNo))
	 			{
	 				linkrtn = link;
	 				break;
	 			}
	 		}
	 	}
	 	
	    return linkrtn;
	 }

	 @SuppressWarnings("static-access")
	 public static WTPartUsageLink getPartPartLinkFromProductionOrderNo(String sParentPart, String sChildPart, long iLineNumber, String sProductionOrderNo) throws WTException
	 {
	 	WTPart partParent = getPartByNumber(sParentPart);
	 	WTPartUsageLink linkrtn = null;
//	 	System.out.println("Getting query result for parent part  = " + sParentPart);
	 	QueryResult queryReferences = StructHelper.service.navigateUses(partParent, WTPartUsageLink.class, false);
	 	while (queryReferences.hasMoreElements()) 
	 	{
//	 		System.out.println("Got some results for parent part  = " + sParentPart);
	 		WTPartUsageLink link = (WTPartUsageLink) queryReferences.nextElement();
//	 		System.out.println("Got link with identity from link.getIdentity()  = " + link.getIdentity());
	 		LineNumber  linkAtts = link.getLineNumber();
			PersistableAdapter obj = new PersistableAdapter(link,null,Locale.US,new UpdateOperationIdentifier());
			System.out.println("About to load attribute for prod order");
		    obj.load("ProdOrderNo");
			System.out.println("Loaded attribute for prod order");
/*	 		LWCNormalizedObject obj = new LWCNormalizedObject(link, null, java.util.Locale.US, new com.ptc.core.meta.common.DisplayOperationIdentifier());

	 		obj.load("DispatchDocketNo");
*/
	 		String sProdOrderNoAtt = (java.lang.String) obj.get("ProdOrderNo");
			System.out.println("Got sProdOrderNoAtt = " + sProdOrderNoAtt);
	 		if(linkAtts != null)
	 		{
	 			long iLineNumberAtt = linkAtts.getValue();
				System.out.println("Got iLineNumberAtt = " + iLineNumberAtt);
	 			if(iLineNumberAtt == iLineNumber && sProdOrderNoAtt.equals(sProductionOrderNo))
	 			{
	 				System.out.println("Got link from prod order = " + sProdOrderNoAtt);
	 				
	 				linkrtn = link;
	 				break;
	 			}
	 		}
	 	}
	 	
	    return linkrtn;
	 }

	 public Boolean setPartUsageLinkAttributes(String sParentPart, String sChildPart, String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType) throws WTException, RemoteException, InvocationTargetException
	 {
			int i;
	        if (!RemoteMethodServer.ServerFlag)
	        {
	            RemoteMethodServer ms = RemoteMethodServer.getDefault();
	            Class[] argTypes = {String.class, String.class, String[].class, String[].class, String[].class};
	            Object[] argValues = {sParentPart, sChildPart, sAttributeName, sAttributeValue, sAttributeType};
	            ms.invoke("setPartUsageLinkAttributes", null, this, argTypes, argValues);
	            return true;
	        }

	        SessionContext.newContext();
	        String sUsername = getBackendUser();
	        SessionHelper.manager.setPrincipal(sUsername);

	        Transaction trans = new Transaction();
		        
	        try
	        {
		        trans.start();
				WTPart part = getPartByNumber(sParentPart);
	    		if(part != null)
				{
/*					WTPartMaster master = (WTPartMaster)part.getMaster();
					WTPartMasterIdentity masterId = (WTPartMasterIdentity)master.getIdentificationObject();
					masterId.setName(sPartName);
					IdentityHelper.service.changeIdentity((Identified) master, masterId);

					master = (WTPartMaster) PersistenceHelper.manager.refresh(master);
*/					
					Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
					CheckoutLink col = null;
					col = WorkInProgressHelper.service.checkout((WTPart)part,checkoutFolder,"");
					WTPart part2 = (WTPart)col.getWorkingCopy();
//		    		System.out.println("Got parent part working copy");
					WTPartUsageLink link = getPartPartLink(sParentPart, sChildPart);
//		    		System.out.println("Got link");

//					Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
//					CheckoutLink col = null;
//					col = WorkInProgressHelper.service.checkout((WTPartUsageLink)link,checkoutFolder,"");
//					WTPart part2 = (WTPart)col.getWorkingCopy();
					for(i=0;i<sAttributeName.length;i++)
					{
						PersistableAdapter obj = new PersistableAdapter(link,null,Locale.US,new UpdateOperationIdentifier());
					    obj.load(sAttributeName[i]);
					    Object objAttributeValue = null;
					    switch(sAttributeType[i])
					    {
						    case "string":
						    default:
						    	if(sAttributeValue[i].equals("null"))
						    		objAttributeValue = null;
						    	else
						    		objAttributeValue = sAttributeValue[i];
						    	break;
						    case "date":
						    case "datetime":
						    	objAttributeValue = GetDateFromString(sAttributeValue[i], "dd/MM/yyyy hh:mm:ss a");
						    	break;
						    case "bool":
						    case "boolean":
						    	objAttributeValue = Boolean.valueOf(sAttributeValue[i]);
						    	break;
						    case "int":
						    case "integer":
						    	objAttributeValue = Integer.valueOf(sAttributeValue[i]);
						    	break;
						    case "doub":
						    case "double":
						    case "real":
						    case "float":
						    	objAttributeValue = com.ptc.core.meta.common.FloatingPoint.valueOf(sAttributeValue[i]);
						    	break;
						    	
					    }
//			    		System.out.println("about to set link attribute");
						obj.set(sAttributeName[i], objAttributeValue);
//			    		System.out.println("set link attribute");
						obj.apply();
//			    		System.out.println("applied link attribute");
					}
					PersistenceHelper.manager.modify(link);				
//		    		System.out.println("Modified link");
					part2 = (WTPart)WorkInProgressHelper.service.checkin(part2,"Added attribute values to the child part " + sChildPart);
//		    		System.out.println("checked the part back in");

				}
			 
				trans.commit();
				trans = null;
				return true;
		    }
		    catch(Throwable t) 
		    {
		        throw new ExceptionInInitializerError(t);
		    } 
		    finally 
		    {
		        if(trans != null) 
		        {
		        	trans.rollback();
		        }
		    }	        		 
	 }
	 
	 public Boolean deleteReferencedByLink(String sUser, String sDocNumber, String sPartNumber, String sCheckInComments) throws WTException, RemoteException, InvocationTargetException
	 {
		Boolean bAtLeastOneDelete = false;
        if (!RemoteMethodServer.ServerFlag)
        {
            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class,String.class, String.class};
            Object[] argValues = {sUser, sDocNumber, sPartNumber, sCheckInComments};
            ms.invoke("deleteReferencedByLink", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
	        
        try
        {
	        	trans.start();
				WTPart part = getPartByNumber(sPartNumber);

				Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
				CheckoutLink col = null;
				col = WorkInProgressHelper.service.checkout((WTPart)part,checkoutFolder,"");
				WTPart part2 = (WTPart)col.getWorkingCopy();
				PersistableAdapter obj = new PersistableAdapter(part2,null,Locale.US,new UpdateOperationIdentifier());
			    obj.load("Originator");
				obj.set("Originator", sUser);
				obj.apply();
				PersistenceHelper.manager.modify(part2);				

		        WTPartReferenceLink link = getPartDocRefLink(sPartNumber, sDocNumber);
				while(link != null)
				{
					PersistenceHelper.manager.delete(link);
				    bAtLeastOneDelete = true;
				    link = getPartDocRefLink(sPartNumber, sDocNumber);
				}
				
				if(bAtLeastOneDelete)
				{
					part2 = (WTPart)WorkInProgressHelper.service.checkin(part2,sCheckInComments);
				}
				else
				{
					part2 = (WTPart)WorkInProgressHelper.service.undoCheckout(part2);
				}
		 
			trans.commit();
			trans = null;
			return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }

	public WTPart createpart(String sNumber, String sName, String sProductName, String sPartType, String sFolderName, 
								String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType, String sCheckInComments, int iProdOrLibrary) throws Exception 
	{
		int i;
        if (!RemoteMethodServer.ServerFlag)
        {

            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class, String.class, String.class, String.class, String[].class, String[].class, String[].class, String.class, int.class};
            Object[] argValues = {sNumber, sName, sProductName, sPartType, sFolderName, sAttributeName, sAttributeValue, sAttributeType, sCheckInComments, iProdOrLibrary};
            WTPart rtnPart = (WTPart)ms.invoke("createpart", null, this, argTypes, argValues);
            return rtnPart;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);
        
        Transaction trans = new Transaction();
        
        try
        {
        	trans.start();
		    WTPart part = WTPart.newWTPart();
		    part.setName(sName);
		    if(!sNumber.equals(""))
		    	part.setNumber(sNumber);
		    WTContainerRef prodref = null;
		    
		    if(iProdOrLibrary == 0)
		    	prodref = GetProductRef(sProductName);
		    else
		    	prodref = GetLibraryRef(sProductName);

		    part.setContainerReference(prodref);
		    Folder folder = GetFolder2(sFolderName, sProductName, iProdOrLibrary);

		    if (folder != null) 
		    {
	            FolderHelper.assignLocation(part, folder);
	        }
	
		    TypeDefinitionReference typeRef = getTypeDef(sPartType);

		    part.setTypeDefinitionReference(typeRef);
			for(i=0;i<sAttributeName.length;i++)
			{
				PersistableAdapter obj = new PersistableAdapter(part,null,Locale.US,new CreateOperationIdentifier());
			    obj.load(sAttributeName[i]);
			    Object objAttributeValue = null;
			    switch(sAttributeType[i])
			    {
				    case "string":
				    default:
				    	if(sAttributeValue[i].equals("null"))
				    		objAttributeValue = null;
				    	else
				    		objAttributeValue = sAttributeValue[i];
				    	break;
				    case "date":
				    case "datetime":
				    	objAttributeValue = GetDateFromString(sAttributeValue[i], "dd/MM/yyyy hh:mm:ss a");
				    	break;
				    case "bool":
				    case "boolean":
				    	objAttributeValue = Boolean.valueOf(sAttributeValue[i]);
				    	break;
				    case "int":
				    case "integer":
				    	objAttributeValue = Integer.valueOf(sAttributeValue[i]);
				    	break;
				    case "doub":
				    case "double":
				    case "real":
				    case "float":
				    	objAttributeValue = com.ptc.core.meta.common.FloatingPoint.valueOf(sAttributeValue[i]);
				    	break;
				    	
			    }
				obj.set(sAttributeName[i], objAttributeValue);
				obj.apply();
			}
			part = (WTPart)PersistenceHelper.manager.store(part);
	    	trans.commit();
	        trans = null;
	        if(sCheckInComments.length() > 0)
	        {
				VersionControlHelper.setNote(part, sCheckInComments);
				PersistenceServerHelper.update(part);
	        }

	        PersistenceHelper.manager.refresh(part);
		    return part;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }
	}
	 
	 // sAttributeType can have values
	 //		Boolean - bool or boolean
	 //		Date & Time - date or datetime
	 //		Integer Number - int or integer
	 //		Real Number - real or doub or double or float
	 //		String - string or the default
	 public Boolean setPartAttributes(String sPartNumber, String sPartName, String[] sAttributeName, String[] sAttributeValue, 
			 						  String[] sAttributeType, String sCheckInComments) throws WTException, RemoteException, InvocationTargetException
	 {
		int i;
        if (!RemoteMethodServer.ServerFlag)
        {
            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class, String[].class, String[].class, String[].class, String.class};
            Object[] argValues = {sPartNumber, sPartName, sAttributeName, sAttributeValue, sAttributeType, sCheckInComments};
            ms.invoke("setPartAttributes", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
	        
        try
        {
	        trans.start();
			WTPart part = getPartByNumber(sPartNumber);
			if(part != null)
			{
				WTPartMaster master = (WTPartMaster)part.getMaster();
				WTPartMasterIdentity masterId = (WTPartMasterIdentity)master.getIdentificationObject();
				if(!sPartName.equals(""))
					masterId.setName(sPartName);
				IdentityHelper.service.changeIdentity((Identified) master, masterId);

				master = (WTPartMaster) PersistenceHelper.manager.refresh(master);
				Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
				CheckoutLink col = null;
				col = WorkInProgressHelper.service.checkout((WTPart)part,checkoutFolder,"");
				WTPart part2 = (WTPart)col.getWorkingCopy();
				for(i=0;i<sAttributeName.length;i++)
				{
					PersistableAdapter obj = new PersistableAdapter(part2,null,Locale.US,new UpdateOperationIdentifier());
				    obj.load(sAttributeName[i]);
				    Object objAttributeValue = null;
				    switch(sAttributeType[i])
				    {
					    case "string":
					    default:
					    	if(sAttributeValue[i].equals("null"))
					    		objAttributeValue = null;
					    	else
					    		objAttributeValue = sAttributeValue[i];
					    	break;
					    case "date":
					    case "datetime":
					    	objAttributeValue = GetDateFromString(sAttributeValue[i], "dd/MM/yyyy hh:mm:ss a");
					    	break;
					    case "bool":
					    case "boolean":
					    	objAttributeValue = Boolean.valueOf(sAttributeValue[i]);
					    	break;
					    case "int":
					    case "integer":
					    	objAttributeValue = Integer.valueOf(sAttributeValue[i]);
					    	break;
					    case "doub":
					    case "double":
					    case "real":
					    case "float":
					    	objAttributeValue = com.ptc.core.meta.common.FloatingPoint.valueOf(sAttributeValue[i]);
					    	break;
					    	
				    }
					obj.set(sAttributeName[i], objAttributeValue);
					obj.apply();
				}
				PersistenceHelper.manager.modify(part2);				
				part2 = (WTPart)WorkInProgressHelper.service.checkin(part2,sCheckInComments);
			}
		 
			trans.commit();
			trans = null;
			return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }
	 
	 public Boolean setPartPartLink(String sUser, String sParentPartNo, String sChildPartNumber, double dQty, String sCheckInComments, String sPartUsageType, String sUnit) throws WTException, RemoteException, InvocationTargetException
	 {
        if (!RemoteMethodServer.ServerFlag)
        {
            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class, String.class, double.class, String.class, String.class, String.class};
            Object[] argValues = {sUser, sParentPartNo, sChildPartNumber, dQty, sCheckInComments, sPartUsageType, sUnit};
            ms.invoke("setPartPartLink", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
	        
        try
        {
	        trans.start();
	        WTPart partChild = getPartByNumber(sChildPartNumber);
			if(partChild != null)
			{
				WTPartMaster masterChild = (WTPartMaster)partChild.getMaster();
				WTPart partParent = getPartByNumber(sParentPartNo);
				WTPartUsageLink link = new WTPartUsageLink();
				Quantity partQty = new Quantity();
				partQty.setAmount(dQty);
				if(sUnit.equals(""))
					partQty.setUnit(QuantityUnit.EA);
				else
					partQty.setUnit(QuantityUnit.toQuantityUnit(sUnit));
				link.setQuantity(partQty);
				Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
				CheckoutLink col = null;
				col = WorkInProgressHelper.service.checkout((WTPart)partParent,checkoutFolder,"");
				WTPart partParent2 = (WTPart)col.getWorkingCopy();
				PersistableAdapter obj = new PersistableAdapter(partParent2,null,Locale.US,new UpdateOperationIdentifier());
			    obj.load("Originator");
				obj.set("Originator", sUser);
				obj.apply();
				PersistenceHelper.manager.modify(partParent2);				

				link.setTypeDefinitionReference(getTypeDef(sPartUsageType));
				link.setRoleAObject(partParent2);
				link.setRoleBObject(masterChild);
				link = (WTPartUsageLink)PersistenceHelper.manager.store(link);
				PersistenceHelper.manager.refresh(link);
				partParent2 = (WTPart)WorkInProgressHelper.service.checkin(partParent2,sCheckInComments);
			}
		 
			trans.commit();
			trans = null;
			return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }

	 public Boolean setPartPartLinkWithAttributes(String sUser, String sParentPartNo, String sChildPartNumber, double dQty, 
			 									  String sCheckInComments, String sPartUsageType, String sUnit, long lLineNumber,
			 									  String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType) throws WTException, RemoteException, InvocationTargetException
	 {
		int i;
		
        if (!RemoteMethodServer.ServerFlag)
        {
            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class, String.class, double.class, String.class, String.class, String.class, long.class, String[].class, String[].class, String[].class};
            Object[] argValues = {sUser, sParentPartNo, sChildPartNumber, dQty, sCheckInComments, sPartUsageType, sUnit, lLineNumber, sAttributeName, sAttributeValue, sAttributeType};
            ms.invoke("setPartPartLinkWithAttributes", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
	        
        try
        {
	        trans.start();
	        WTPart partChild = getPartByNumber(sChildPartNumber);
			if(partChild != null)
			{
				WTPartMaster masterChild = (WTPartMaster)partChild.getMaster();
				WTPart partParent = getPartByNumber(sParentPartNo);
				WTPartUsageLink link = new WTPartUsageLink();
				Quantity partQty = new Quantity();
				partQty.setAmount(dQty);
				if(sUnit.equals(""))
					partQty.setUnit(QuantityUnit.EA);
				else
					partQty.setUnit(QuantityUnit.toQuantityUnit(sUnit));
				link.setQuantity(partQty);
				if(lLineNumber != -1)
				{
					LineNumber ln = new LineNumber();
					ln.setValue(lLineNumber);
					link.setLineNumber(ln);
				}
				Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
				CheckoutLink col = null;
				col = WorkInProgressHelper.service.checkout((WTPart)partParent,checkoutFolder,"");
				WTPart partParent2 = (WTPart)col.getWorkingCopy();
				for(i=0;i<sAttributeName.length;i++)
				{
					PersistableAdapter obj = new PersistableAdapter(link,null,Locale.US,new UpdateOperationIdentifier());
				    obj.load(sAttributeName[i]);
				    Object objAttributeValue = null;
				    switch(sAttributeType[i])
				    {
					    case "string":
					    default:
					    	if(sAttributeValue[i].equals("null"))
					    		objAttributeValue = null;
					    	else
					    		objAttributeValue = sAttributeValue[i];
					    	break;
					    case "date":
					    case "datetime":
					    	objAttributeValue = GetDateFromString(sAttributeValue[i], "dd/MM/yyyy hh:mm:ss a");
					    	System.out.println("Got date attribute with value " + objAttributeValue.toString());
					    	break;
					    case "bool":
					    case "boolean":
					    	objAttributeValue = Boolean.valueOf(sAttributeValue[i]);
					    	break;
					    case "int":
					    case "integer":
					    	objAttributeValue = Integer.valueOf(sAttributeValue[i]);
					    	break;
					    case "doub":
					    case "double":
					    case "real":
					    case "float":
					    	objAttributeValue = com.ptc.core.meta.common.FloatingPoint.valueOf(sAttributeValue[i]);
					    	break;
					    	
				    }
					obj.set(sAttributeName[i], objAttributeValue);
					obj.apply();
				}
				PersistableAdapter obj2 = new PersistableAdapter(partParent2,null,Locale.US,new UpdateOperationIdentifier());
			    obj2.load("Originator");
				obj2.set("Originator", sUser);
				obj2.apply();
				PersistenceHelper.manager.modify(partParent2);				

				link.setTypeDefinitionReference(getTypeDef(sPartUsageType));
				link.setRoleAObject(partParent2);
				link.setRoleBObject(masterChild);
				link = (WTPartUsageLink)PersistenceHelper.manager.store(link);
				PersistenceHelper.manager.refresh(link);
				partParent2 = (WTPart)WorkInProgressHelper.service.checkin(partParent2,sCheckInComments);
			}
		 
			trans.commit();
			trans = null;
			return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }


	 
	 public Boolean updateDispatchDocketPartPartLinkWithAttributes(String sUser, String sParentPartNo, String sChildPartNumber, double dQty, String sDispatchDocketNo,
																   String sCheckInComments, String sPartUsageType, String sUnit, long lOldLineNumber, long lNewLineNumber, 
																   String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType) throws WTException, RemoteException, InvocationTargetException
	{
		int i;
		
		if (!RemoteMethodServer.ServerFlag)
		{
			RemoteMethodServer ms = RemoteMethodServer.getDefault();
			Class[] argTypes = {String.class, String.class, String.class, double.class, String.class, String.class, String.class, String.class, long.class,  long.class, String[].class, String[].class, String[].class};
			Object[] argValues = {sUser, sParentPartNo, sChildPartNumber, dQty, sDispatchDocketNo, sCheckInComments, sPartUsageType, sUnit, lOldLineNumber, lNewLineNumber, sAttributeName, sAttributeValue, sAttributeType};
			ms.invoke("updateDispatchDocketPartPartLinkWithAttributes", null, this, argTypes, argValues);
			return true;
		}
	
		SessionContext.newContext();
		String sUsername = getBackendUser();
		SessionHelper.manager.setPrincipal(sUsername);
		
		Transaction trans = new Transaction();
		
		try
		{
			trans.start();
			WTPart partChild = getPartByNumber(sChildPartNumber);
			if(partChild != null)
			{
//				WTPartMaster masterChild = (WTPartMaster)partChild.getMaster();
				WTPart partParent = getPartByNumber(sParentPartNo);
				Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
				CheckoutLink col = null;
				col = WorkInProgressHelper.service.checkout((WTPart)partParent,checkoutFolder,"");
				WTPart partParent2 = (WTPart)col.getWorkingCopy();
				

				//Ensure you get the link AFTER checking out the parent part otherwsie you cannot update it.
				WTPartUsageLink link = getPartPartLinkFromDispatchDocketNo(sParentPartNo, sChildPartNumber, lOldLineNumber, sDispatchDocketNo);
				Quantity partQty = new Quantity();
				partQty.setAmount(dQty);
				if(sUnit.equals(""))
					partQty.setUnit(QuantityUnit.EA);
				else
					partQty.setUnit(QuantityUnit.toQuantityUnit(sUnit));
				
				link.setQuantity(partQty);
				if(lNewLineNumber != -1 && lNewLineNumber != lOldLineNumber)
				{
					LineNumber ln = new LineNumber();
					ln.setValue(lNewLineNumber);
					link.setLineNumber(ln);
				}

				for(i=0;i<sAttributeName.length;i++)
				{
					PersistableAdapter obj = new PersistableAdapter(link,null,Locale.US,new UpdateOperationIdentifier());
					obj.load(sAttributeName[i]);
					Object objAttributeValue = null;
					switch(sAttributeType[i])
					{
						case "string":
						default:
							if(sAttributeValue[i].equals("null"))
								objAttributeValue = null;
							else
								objAttributeValue = sAttributeValue[i];
							break;
						case "date":
						case "datetime":
							objAttributeValue = GetDateFromString(sAttributeValue[i], "dd/MM/yyyy hh:mm:ss a");
							break;
						case "bool":
						case "boolean":
							objAttributeValue = Boolean.valueOf(sAttributeValue[i]);
							break;
						case "int":
						case "integer":
							objAttributeValue = Integer.valueOf(sAttributeValue[i]);
							break;
						case "doub":
						case "double":
						case "real":
						case "float":
							objAttributeValue = com.ptc.core.meta.common.FloatingPoint.valueOf(sAttributeValue[i]);
							break;					
					}
					obj.set(sAttributeName[i], objAttributeValue);
					obj.apply();
				}
				PersistableAdapter obj2 = new PersistableAdapter(partParent2,null,Locale.US,new UpdateOperationIdentifier());
				obj2.load("Originator");
				obj2.set("Originator", sUser);
				obj2.apply();
				PersistenceHelper.manager.modify(partParent2);				
				
				PersistenceHelper.manager.modify(link);
				PersistenceHelper.manager.refresh(link);

				partParent2 = (WTPart)WorkInProgressHelper.service.checkin(partParent2,sCheckInComments);
			}
			
			trans.commit();
			trans = null;
			return true;
		}
		catch(Throwable t) 
		{
			throw new ExceptionInInitializerError(t);
		} 
		finally 
		{
			if(trans != null) 
			{
				trans.rollback();
			}
		}	        
	}
	 
	 public Boolean updateProdOrderPartPartLinkWithAttributes(String sUser, String sParentPartNo, String sChildPartNumber, double dQty, String sProdOrderNo,
			   												  String sCheckInComments, String sPartUsageType, String sUnit, long lOldLineNumber, long lNewLineNumber, 
			   												  String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType) throws WTException, RemoteException, InvocationTargetException
	{
		int i;
		
		if (!RemoteMethodServer.ServerFlag)
		{
			RemoteMethodServer ms = RemoteMethodServer.getDefault();
			Class[] argTypes = {String.class, String.class, String.class, double.class, String.class, String.class, String.class, String.class, long.class,  long.class, String[].class, String[].class, String[].class};
			Object[] argValues = {sUser, sParentPartNo, sChildPartNumber, dQty, sProdOrderNo, sCheckInComments, sPartUsageType, sUnit, lOldLineNumber, lNewLineNumber, sAttributeName, sAttributeValue, sAttributeType};
			ms.invoke("updateProdOrderPartPartLinkWithAttributes", null, this, argTypes, argValues);
			return true;
		}
		
		SessionContext.newContext();
		String sUsername = getBackendUser();
		SessionHelper.manager.setPrincipal(sUsername);
		
		Transaction trans = new Transaction();
		
		try
		{
			trans.start();
			WTPart partChild = getPartByNumber(sChildPartNumber);
			if(partChild != null)
			{
				//WTPartMaster masterChild = (WTPartMaster)partChild.getMaster();
				WTPart partParent = getPartByNumber(sParentPartNo);
				Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
				CheckoutLink col = null;
				col = WorkInProgressHelper.service.checkout((WTPart)partParent,checkoutFolder,"");
				WTPart partParent2 = (WTPart)col.getWorkingCopy();
				
				
				//Ensure you get the link AFTER checking out the parent part otherwise you cannot update it.
				WTPartUsageLink link = getPartPartLinkFromProductionOrderNo(sParentPartNo, sChildPartNumber, lOldLineNumber, sProdOrderNo);
				Quantity partQty = new Quantity();
				partQty.setAmount(dQty);
				if(sUnit.equals(""))
					partQty.setUnit(QuantityUnit.EA);
				else
					partQty.setUnit(QuantityUnit.toQuantityUnit(sUnit));
				
				System.out.println("ABout to set qty Prod Order part part link");
				link.setQuantity(partQty);
				System.out.println("Set qty Prod Order part part link");
				if(lNewLineNumber != -1 && lNewLineNumber != lOldLineNumber)
				{
					System.out.println("About to set line number Prod Order part part link");
					LineNumber ln = new LineNumber();
					ln.setValue(lNewLineNumber);
					link.setLineNumber(ln);
					System.out.println("Set line number Prod Order part part link");
				}
				
				for(i=0;i<sAttributeName.length;i++)
				{
					System.out.println("Doing attribute i=" + i);
					PersistableAdapter obj = new PersistableAdapter(link,null,Locale.US,new UpdateOperationIdentifier());
					obj.load(sAttributeName[i]);
					Object objAttributeValue = null;
					switch(sAttributeType[i])
					{
						case "string":
						default:
						if(sAttributeValue[i].equals("null"))
							objAttributeValue = null;
						else
							objAttributeValue = sAttributeValue[i];
						break;
						case "date":
						case "datetime":
							objAttributeValue = GetDateFromString(sAttributeValue[i], "dd/MM/yyyy hh:mm:ss a");
							break;
						case "bool":
						case "boolean":
							objAttributeValue = Boolean.valueOf(sAttributeValue[i]);
							break;
						case "int":
						case "integer":
							objAttributeValue = Integer.valueOf(sAttributeValue[i]);
							break;
						case "doub":
						case "double":
						case "real":
						case "float":
							objAttributeValue = com.ptc.core.meta.common.FloatingPoint.valueOf(sAttributeValue[i]);
							break;					
					}
					System.out.println("About to set attribute i=" + i);
					obj.set(sAttributeName[i], objAttributeValue);
					obj.apply();
					System.out.println("Set attribute i=" + i);
				}
				System.out.println("About to set originator on parent");
				PersistableAdapter obj2 = new PersistableAdapter(partParent2,null,Locale.US,new UpdateOperationIdentifier());
				obj2.load("Originator");
				obj2.set("Originator", sUser);
				obj2.apply();
				System.out.println("Set originator on parent");
				PersistenceHelper.manager.modify(partParent2);				
				
				PersistenceHelper.manager.modify(link);
				PersistenceHelper.manager.refresh(link);
				
				System.out.println("About to checkin parent");
				partParent2 = (WTPart)WorkInProgressHelper.service.checkin(partParent2,sCheckInComments);
				System.out.println("Done checkin parent");
			}
			
			trans.commit();
			trans = null;
			return true;
		}
		catch(Throwable t) 
		{
			throw new ExceptionInInitializerError(t);
		} 
		finally 
		{
			if(trans != null) 
			{
				trans.rollback();
			}
		}	        
	}
	 
	 public Boolean deletePartPartLink(String sUser, String sParentPart, String sChildPart, String sCheckInComments) throws WTException, RemoteException, InvocationTargetException
	 {
		Boolean bAtLeastOneDelete = false;
        if (!RemoteMethodServer.ServerFlag)
        {
            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class,String.class, String.class};
            Object[] argValues = {sUser, sParentPart, sChildPart, sCheckInComments};
            ms.invoke("deletePartPartLink", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
	        
        try
        {
	        	trans.start();
				WTPart partParent = getPartByNumber(sParentPart);

				Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
				CheckoutLink col = null;
				col = WorkInProgressHelper.service.checkout((WTPart)partParent,checkoutFolder,"");
				WTPart partParent2 = (WTPart)col.getWorkingCopy();
				PersistableAdapter obj = new PersistableAdapter(partParent2,null,Locale.US,new UpdateOperationIdentifier());
			    obj.load("Originator");
				obj.set("Originator", sUser);
				obj.apply();
				PersistenceHelper.manager.modify(partParent2);				

		        WTPartUsageLink link = getPartPartLink(sParentPart, sChildPart);
				while(link != null)
				{
					PersistenceHelper.manager.delete(link);
				    bAtLeastOneDelete = true;
				    link = getPartPartLink(sParentPart, sChildPart);
				}
								
				if(bAtLeastOneDelete)
				{
					partParent2 = (WTPart)WorkInProgressHelper.service.checkin(partParent2,sCheckInComments);
				}
				else
				{
					partParent2 = (WTPart)WorkInProgressHelper.service.undoCheckout(partParent2);
				}
		 
			trans.commit();
			trans = null;
			return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }

	 public Boolean deletePartPartLinkOfDispatchDocket(String sUser, String sDispatchDocket, long iLineNumber, String sParentPart, String sChildPart, String sCheckInComments) throws WTException, RemoteException, InvocationTargetException
	 {
		Boolean bAtLeastOneDelete = false;
        if (!RemoteMethodServer.ServerFlag)
        {
            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class, long.class, String.class, String.class, String.class};
            Object[] argValues = {sUser, sDispatchDocket, iLineNumber, sParentPart, sChildPart, sCheckInComments};
            ms.invoke("deletePartPartLinkOfDispatchDocket", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
	        
        try
        {
	        	trans.start();
	        	

		        WTPart partParent = getPartByNumber(sParentPart);

				Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
				CheckoutLink col = null;
				col = WorkInProgressHelper.service.checkout((WTPart)partParent,checkoutFolder,"");
				WTPart partParent2 = (WTPart)col.getWorkingCopy();
				PersistableAdapter obj = new PersistableAdapter(partParent2,null,Locale.US,new UpdateOperationIdentifier());
			    obj.load("Originator");
				obj.set("Originator", sUser);
				obj.apply();
				PersistenceHelper.manager.modify(partParent2);				

		        WTPartUsageLink link = getPartPartLinkFromDispatchDocketNo(sParentPart, sChildPart, iLineNumber, sDispatchDocket);
		        System.out.println("Got link with id = " + sDispatchDocket);

		        while(link != null)
				{
			        System.out.println("Got link not null with id = " + link.getIdentity());
					PersistenceHelper.manager.delete(link);
			        System.out.println("deleted link");
				    bAtLeastOneDelete = true;
				    
				    //This line of code is very very important. Without it you cannot delete the link.
				    //I don't know why it is required because even PTC knowledge base CS201190 does not require it
				    link = getPartPartLinkFromDispatchDocketNo(sParentPart, sChildPart, iLineNumber, sDispatchDocket);
				    
				}
								
				if(bAtLeastOneDelete)
				{
					System.out.println("Checking in");
					partParent2 = (WTPart)WorkInProgressHelper.service.checkin(partParent2,sCheckInComments);
					System.out.println("Checked in");
				}
				else
				{
					partParent2 = (WTPart)WorkInProgressHelper.service.undoCheckout(partParent2);
				}
		 
			trans.commit();
			trans = null;
			return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }

	 public Boolean deletePartPartLinkOfProductionOrder(String sUser, String sProdOrderNo, long iLineNumber, String sParentPart, String sChildPart, String sCheckInComments) throws WTException, RemoteException, InvocationTargetException
	 {
		Boolean bAtLeastOneDelete = false;
        if (!RemoteMethodServer.ServerFlag)
        {
            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class, long.class, String.class, String.class, String.class};
            Object[] argValues = {sUser, sProdOrderNo, iLineNumber, sParentPart, sChildPart, sCheckInComments};
            ms.invoke("deletePartPartLinkOfProductionOrder", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
	        
        try
        {
	        	trans.start();
	        	

		        WTPart partParent = getPartByNumber(sParentPart);

				Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
				CheckoutLink col = null;
				col = WorkInProgressHelper.service.checkout((WTPart)partParent,checkoutFolder,"");
				WTPart partParent2 = (WTPart)col.getWorkingCopy();
				PersistableAdapter obj = new PersistableAdapter(partParent2,null,Locale.US,new UpdateOperationIdentifier());
			    obj.load("Originator");
				obj.set("Originator", sUser);
				obj.apply();
				PersistenceHelper.manager.modify(partParent2);				

		        WTPartUsageLink link = getPartPartLinkFromProductionOrderNo(sParentPart, sChildPart, iLineNumber, sProdOrderNo);
		        System.out.println("Got link with id = " + sProdOrderNo);

		        while(link != null)
				{
			        System.out.println("Got link not null with id = " + link.getIdentity());
					PersistenceHelper.manager.delete(link);
			        System.out.println("deleted link");
				    bAtLeastOneDelete = true;
				    
				    //This line of code is very very important. Without it you cannot delete the link.
				    //I don't know why it is required because even PTC knowledge base CS201190 does not require it
				    link = getPartPartLinkFromProductionOrderNo(sParentPart, sChildPart, iLineNumber, sProdOrderNo);
				    
				}
								
				if(bAtLeastOneDelete)
				{
					System.out.println("Checking in");
					partParent2 = (WTPart)WorkInProgressHelper.service.checkin(partParent2,sCheckInComments);
					System.out.println("Checked in");
				}
				else
				{
					partParent2 = (WTPart)WorkInProgressHelper.service.undoCheckout(partParent2);
				}
		 
			trans.commit();
			trans = null;
			return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }

	 public WTChangeOrder2 createChangeNotice(String sNumber, String sName, String sProductName, String sCNType, String sFolderName, 
				String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType, int iProdOrLibrary) throws Exception 
		{
			int i;
			if (!RemoteMethodServer.ServerFlag)
			{			
				RemoteMethodServer ms = RemoteMethodServer.getDefault();
				Class[] argTypes = {String.class, String.class, String.class, String.class, String.class, String[].class, String[].class, String[].class, int.class};
				Object[] argValues = {sNumber, sName, sProductName, sCNType, sFolderName, sAttributeName, sAttributeValue, sAttributeType, iProdOrLibrary};
				WTChangeOrder2 rtnCN = (WTChangeOrder2)ms.invoke("createChangeNotice", null, this, argTypes, argValues);
				return rtnCN;
			}
			
			SessionContext.newContext();
			String sUsername = getBackendUser();
			SessionHelper.manager.setPrincipal(sUsername);
			
			Transaction trans = new Transaction();
			
			
			try
			{
				trans.start();
				WTChangeOrder2 chgnotice = WTChangeOrder2.newWTChangeOrder2();
				chgnotice.setName(sName);
				chgnotice.setChangeNoticeComplexity(ChangeNoticeComplexity.BASIC);
				if(!sNumber.equals(""))
					chgnotice.setNumber(sNumber);
			    WTContainerRef prodref = null;
			    
			    if(iProdOrLibrary == 0)
			    	prodref = GetProductRef(sProductName);
			    else
			    	prodref = GetLibraryRef(sProductName);

			    chgnotice.setContainerReference(prodref);
				Folder folder = GetFolder2(sFolderName, sProductName, iProdOrLibrary);
				
//				ContentHolder contentHolder = (ContentHolder)chgnotice;

				if (folder != null) 
				{
					FolderHelper.assignLocation(chgnotice, folder);
				}
	
				TypeDefinitionReference typeRef = getTypeDef(sCNType);
				
				chgnotice.setTypeDefinitionReference(typeRef);
				for(i=0;i<sAttributeName.length;i++)
				{
					PersistableAdapter obj = new PersistableAdapter(chgnotice,null,Locale.US,new CreateOperationIdentifier());
					obj.load(sAttributeName[i]);
					Object objAttributeValue = null;
					switch(sAttributeType[i])
					{
					    case "string":
					    default:
					    	if(sAttributeValue[i].equals("null"))
					    		objAttributeValue = null;
					    	else
					    		objAttributeValue = sAttributeValue[i];
					    	break;
					    case "date":
					    case "datetime":
					    	objAttributeValue = GetDateFromString(sAttributeValue[i], "dd/MM/yyyy hh:mm:ss a");
					    	break;
					    case "bool":
					    case "boolean":
					    	objAttributeValue = Boolean.valueOf(sAttributeValue[i]);
					    	break;
					    case "int":
					    case "integer":
					    	objAttributeValue = Integer.valueOf(sAttributeValue[i]);
					    	break;
					    case "doub":
					    case "double":
					    case "real":
					    case "float":
					    	objAttributeValue = com.ptc.core.meta.common.FloatingPoint.valueOf(sAttributeValue[i]);
					    	break;
					    	
					}
					obj.set(sAttributeName[i], objAttributeValue);
					obj.apply();
				}
				
			    String[] sAffectedParts = new String[2];

			    sAffectedParts[0] = "P121D001";
			    sAffectedParts[1] = "P121D002";
//			    ChangeActivity2 ca = wt.change2.WTChangeActivity2.newWTChangeActivity2();
				//				setChangeNoticeAffectedObjects(sCNNo, sAffectedParts)
			    //AffectedActivityData accData = AffectedActivityData.newAffectedActivityData((Changeable2) chgnotice, paramChangeActivity2);  //chgnotice, paramChangeActivity2);
	        	Vector<WTPart> links = new Vector<WTPart>();
//	    		System.out.println("Set WTCollection");
	        	for(int ii = 0; ii< sAffectedParts.length;ii++)
	        	{
	        		WTPart part = getPartByNumber(sAffectedParts[ii]);        		
//	        		System.out.println("Got part " + sAffectedParts[ii]);
		        	links.addElement(part);
//	        		System.out.println("Added part to link " + sAffectedParts[ii]);
		        	
	        	}
	        	
	    		
//	    		System.out.println("About to create change notice");
				chgnotice = (WTChangeOrder2)PersistenceHelper.manager.store(chgnotice);
//	    		ca = (wt.change2.WTChangeActivity2) wt.change2.ChangeHelper2.service.saveChangeActivity(chgnotice, ca);
//	    		System.out.println("About to store links");
	        	ChangeHelper2.service.storeAssociations(wt.change2.IncludedIn2.class,chgnotice, links);	
				trans.commit();
				trans = null;
				
/*				if(sCheckInComments.length() > 0)
				{
					VersionControlHelper.setNote(chgnotice, sCheckInComments);
					PersistenceServerHelper.update(chgnotice);
				}
*/	
				PersistenceHelper.manager.refresh(chgnotice);
			return chgnotice;
		}
		catch(Throwable t) 
		{
			throw new ExceptionInInitializerError(t);
		} 
		finally 
		{
			if(trans != null) 
			{
				trans.rollback();
			}
		}
	}

	 public boolean attachToChangeNotice(String sCNNo, String sAttachDesc, String filePath) throws WTException, PropertyVetoException, FileNotFoundException, IOException, InvocationTargetException
	 {
        if (!RemoteMethodServer.ServerFlag)
        {

            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class, String.class};
            Object[] argValues = {sCNNo, sAttachDesc, filePath};
            ms.invoke("attachToChangeNotice", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
        
        try
        {
        	trans.start();
        	WTChangeOrder2 cn = getChangeNoticeByNumber(sCNNo);

			ContentHolder contentHolder = (ContentHolder)cn;
			contentHolder = ContentHelper.service.getContents(contentHolder);

	       // START -- Delete contents if they have same name as the file name to be uploaded
	       ContentRoleType roleType = ContentRoleType.SECONDARY;
	       
	       QueryResult qrContents = ContentHelper.service.getContentsByRole(contentHolder, roleType); //ContentRoleType.SECONDARY

	       // Get file name of current file to be attached as secondary content
	       File objFile = new File(filePath);
	       String nameOfFileToBeAttached = objFile.getName();

	       ApplicationData fileContent = null;
	       String nameOfExistingSecContent = null;
	       while (qrContents.hasMoreElements())
	       {
	             fileContent = (ApplicationData) qrContents.nextElement();
	             nameOfExistingSecContent = fileContent.getFileName();
	             if(nameOfExistingSecContent.equals(nameOfFileToBeAttached))
	            	 deleteExistingContent(contentHolder, fileContent);
	       }
	       // END -- Delete contents if they have same name as the file name to be uploaded

	       ApplicationData objAppData = ApplicationData.newApplicationData(contentHolder);
	       if(contentHolder instanceof WTChangeOrder2)
	       {
	    	   objAppData.setDescription(sAttachDesc);
	       }

	       
	        objAppData = ContentServerHelper.service.updateContent(contentHolder, objAppData, filePath);
/*			PersistableAdapter obj = new PersistableAdapter(cn,null,Locale.US,new UpdateOperationIdentifier());
		    obj.load("Originator");
			obj.set("Originator", sUser);
			obj.apply();
*/			PersistenceHelper.manager.modify(cn);				
	        trans.commit();
	        trans = null;
		    return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }

	 public boolean deleteChangeNoticeAttachment(String sCNNo, String filePath) throws WTException, PropertyVetoException, FileNotFoundException, IOException, InvocationTargetException
	 {
		Boolean bDeleted = false;
        if (!RemoteMethodServer.ServerFlag)
        {

            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class};
            Object[] argValues = {sCNNo, filePath};
            ms.invoke("deleteChangeNoticeAttachment", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
        
        try
        {
        	trans.start();
        	WTChangeOrder2 cn = getChangeNoticeByNumber(sCNNo);

			ContentHolder contentHolder = (ContentHolder)cn;
    		contentHolder = ContentHelper.service.getContents(contentHolder);

	       // START -- Delete contents if they have same name as the file name to be uploaded
	       ContentRoleType roleType = ContentRoleType.SECONDARY;
	       
	       
	       QueryResult qrContents = ContentHelper.service.getContentsByRole(contentHolder, roleType); 

	       // Get file name of current file to be attached as secondary content
	       File objFile = new File(filePath);
	       String nameOfFileToBeAttached = objFile.getName();

	       ApplicationData fileContent = null;
	       String nameOfExistingSecContent = null;
	       while (qrContents.hasMoreElements())
	       {
	             fileContent = (ApplicationData) qrContents.nextElement();
	             nameOfExistingSecContent = fileContent.getFileName();
	             if(nameOfExistingSecContent.equals(nameOfFileToBeAttached))
	             {
	            	 deleteExistingContent(contentHolder, fileContent);
	            	 bDeleted = true;
	             }
	       }
	       // END -- Delete contents if they have same name as the file name to be uploaded

	       if(bDeleted)
	       {
				PersistenceHelper.manager.modify(cn);				
	       }

  	        trans.commit();
	        trans = null;
		    return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }

	 public static WTChangeOrder2 getChangeNoticeByNumber(String sCNNumber) throws WTException 
	{
		// TODO Auto-generated method stub
	    QuerySpec qs = new QuerySpec(WTChangeOrder2.class);
	    SearchCondition condition = new SearchCondition(WTChangeOrder2.class, WTChangeOrder2.NUMBER, SearchCondition.EQUAL, sCNNumber);
	    qs.appendWhere(condition, new int[]{0});
	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec)qs);
	    WTChangeOrder2 cn = null;
	    WTChangeOrder2 cn2 = null;
		while(qr.hasMoreElements())
		{
			cn = (WTChangeOrder2)qr.nextElement();
			break;
		}
		if(cn != null)
		{
			QueryResult qr2 = VersionControlHelper.service.allVersionsOf(cn);
			cn2= (WTChangeOrder2)qr2.nextElement();
		}
		
		
		return cn2;
	}

	public boolean setChangeNoticeAffectedObjects(String sCNNo, String[] sAffectedObjectsNos) throws WTException, PropertyVetoException, FileNotFoundException, IOException, InvocationTargetException
	 {
        if (!RemoteMethodServer.ServerFlag)
        {

            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String[].class};
            Object[] argValues = {sCNNo, sAffectedObjectsNos};
            ms.invoke("setChangeNoticeAffectedObjects", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

//        Transaction trans = new Transaction();
        
        try
        {
//        	trans.start();
        	Vector<WTPart> links = new Vector<WTPart>();
//    		System.out.println("Set WTCollection");
        	WTChangeOrder2 cn = getChangeNoticeByNumber(sCNNo);
        	for(int i = 0; i< sAffectedObjectsNos.length;i++)
        	{
        		WTPart part = getPartByNumber(sAffectedObjectsNos[i]);        		
//        		System.out.println("Got part " + sAffectedObjectsNos[i]);
	        	links.addElement(part);
//        		System.out.println("Added part to link " + sAffectedObjectsNos[i]);
	        	
        	}
        	
//    		System.out.println("About to store links");
    		
        	ChangeHelper2.service.storeAssociations(wt.change2.ChangeOrder2.class,cn, links);	
//    		System.out.println("Stored links");
//        	trans.commit();
//	        trans = null;
		    return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
/*	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
*/	    }	        
	 }

	public Boolean deleteProblemReport(String sPRNumber) throws RemoteException, InvocationTargetException, WTException
	{
		int i;
		if (!RemoteMethodServer.ServerFlag)
		{			
			RemoteMethodServer ms = RemoteMethodServer.getDefault();
			Class[] argTypes = {String.class};
			Object[] argValues = {sPRNumber};
			Boolean rtnPR = (Boolean)ms.invoke("deleteProblemReport", null, this, argTypes, argValues);
			return rtnPR;
		}
		
		SessionContext.newContext();
		String sUsername = getBackendUser();
		SessionHelper.manager.setPrincipal(sUsername);
		
		Transaction trans = new Transaction();
		
		
		try
		{
			trans.start();
			WTChangeIssue probrpt = getProblemReportByNumber(sPRNumber);
			
			
			probrpt = (WTChangeIssue)PersistenceHelper.manager.delete(probrpt);

			trans.commit();
			trans = null;
			
//			PersistenceHelper.manager.refresh(probrpt);
//    		System.out.println("refreshed prob report");


			return true;
		}
		catch(Throwable t) 
		{
			throw new ExceptionInInitializerError(t);
		} 
		finally 
		{
			if(trans != null) 
			{
				trans.rollback();
			}
		}

	}
	public WTChangeIssue createProblemReport(String sNumber, String sName, String sProductName, String sPRType, String sFolderName, 
											 String[] sAttributeName, String[] sAttributeValue, String[] sAttributeType, int iProdOrLibrary,
											 Timestamp dtNeedDate) throws Exception 
	{
		int i;
		if (!RemoteMethodServer.ServerFlag)
		{			
			RemoteMethodServer ms = RemoteMethodServer.getDefault();
			Class[] argTypes = {String.class, String.class, String.class, String.class, String.class, String[].class, String[].class, String[].class, int.class, Timestamp.class};
			Object[] argValues = {sNumber, sName, sProductName, sPRType, sFolderName, sAttributeName, sAttributeValue, sAttributeType, iProdOrLibrary, dtNeedDate};
			WTChangeIssue rtnPR = (WTChangeIssue)ms.invoke("createProblemReport", null, this, argTypes, argValues);
			return rtnPR;
		}
		
		SessionContext.newContext();
		String sUsername = getBackendUser();
		SessionHelper.manager.setPrincipal(sUsername);
		
		Transaction trans = new Transaction();
		
		
		try
		{
			trans.start();
			WTChangeIssue probrpt = WTChangeIssue.newWTChangeIssue();
			probrpt.setName(sName);
//probrpt.
			//We may not want this or maybe only for issue reports. Maybe send a flag across.
/*			LifeCycleState LCState = LifeCycleState.newLifeCycleState();
			LCState.setState(wt.lifecycle.State.toState("UNDERREVIEW"));
			probrpt.setState(LCState);
*/			
			if(!sNumber.equals(""))
				probrpt.setNumber(sNumber);
			
			if(dtNeedDate != null)
				probrpt.setNeedDate(dtNeedDate);
			
		    WTContainerRef prodref = null;
		    
		    if(iProdOrLibrary == 0)
		    	prodref = GetProductRef(sProductName);
		    else
		    	prodref = GetLibraryRef(sProductName);
		    
			probrpt.setContainerReference(prodref);
			Folder folder = GetFolder2(sFolderName, sProductName, iProdOrLibrary);
			
//			ContentHolder contentHolder = (ContentHolder)probrpt;

			if (folder != null) 
			{
				FolderHelper.assignLocation(probrpt, folder);
			}

			TypeDefinitionReference typeRef = getTypeDef(sPRType);
			
			probrpt.setTypeDefinitionReference(typeRef);
			for(i=0;i<sAttributeName.length;i++)
			{
				PersistableAdapter obj = new PersistableAdapter(probrpt,null,Locale.US,new CreateOperationIdentifier());
				obj.load(sAttributeName[i]);
				Object objAttributeValue = null;
				switch(sAttributeType[i])
				{
				    case "string":
				    default:
				    	if(sAttributeValue[i].equals("null"))
				    		objAttributeValue = null;
				    	else
				    		objAttributeValue = sAttributeValue[i];
				    	break;
				    case "date":
				    case "datetime":
				    	objAttributeValue = GetDateFromString(sAttributeValue[i], "dd/MM/yyyy hh:mm:ss a");
				    	break;
				    case "bool":
				    case "boolean":
				    	objAttributeValue = Boolean.valueOf(sAttributeValue[i]);
				    	break;
				    case "int":
				    case "integer":
				    	objAttributeValue = Integer.valueOf(sAttributeValue[i]);
				    	break;
				    case "doub":
				    case "double":
				    case "real":
				    case "float":
				    	objAttributeValue = com.ptc.core.meta.common.FloatingPoint.valueOf(sAttributeValue[i]);
				    	break;
				    	
				}
				obj.set(sAttributeName[i], objAttributeValue);
				obj.apply();
			}
			
			probrpt = (WTChangeIssue)PersistenceHelper.manager.store(probrpt);

			trans.commit();
			trans = null;
			
			PersistenceHelper.manager.refresh(probrpt);
//    		System.out.println("refreshed prob report");


			return probrpt;
		}
		catch(Throwable t) 
		{
			throw new ExceptionInInitializerError(t);
		} 
		finally 
		{
			if(trans != null) 
			{
				trans.rollback();
			}
		}
	}

		 public boolean attachToProblemReport(String sPRNo, String sAttachDesc, String filePath) throws WTException, PropertyVetoException, FileNotFoundException, IOException, InvocationTargetException
		 {
	        if (!RemoteMethodServer.ServerFlag)
	        {

	            RemoteMethodServer ms = RemoteMethodServer.getDefault();
	            Class[] argTypes = {String.class, String.class, String.class};
	            Object[] argValues = {sPRNo, sAttachDesc, filePath};
	            ms.invoke("attachToProblemReport", null, this, argTypes, argValues);
	            return true;
	        }

	        SessionContext.newContext();
	        String sUsername = getBackendUser();
	        SessionHelper.manager.setPrincipal(sUsername);

	        Transaction trans = new Transaction();
	        
	        try
	        {
	        	trans.start();
	        	WTChangeIssue pr = getProblemReportByNumber(sPRNo);

				ContentHolder contentHolder = (ContentHolder)pr;
				contentHolder = ContentHelper.service.getContents(contentHolder);

		       // START -- Delete contents if they have same name as the file name to be uploaded
		       ContentRoleType roleType = ContentRoleType.SECONDARY;
		       
		       QueryResult qrContents = ContentHelper.service.getContentsByRole(contentHolder, roleType); //ContentRoleType.SECONDARY

		       // Get file name of current file to be attached as secondary content
		       File objFile = new File(filePath);
		       String nameOfFileToBeAttached = objFile.getName();

		       ApplicationData fileContent = null;
		       String nameOfExistingSecContent = null;
		       while (qrContents.hasMoreElements())
		       {
		             fileContent = (ApplicationData) qrContents.nextElement();
		             nameOfExistingSecContent = fileContent.getFileName();
		             if(nameOfExistingSecContent.equals(nameOfFileToBeAttached))
		            	 deleteExistingContent(contentHolder, fileContent);
		       }
		       // END -- Delete contents if they have same name as the file name to be uploaded

		       ApplicationData objAppData = ApplicationData.newApplicationData(contentHolder);
		       if(contentHolder instanceof WTChangeIssue)
		       {
		    	   objAppData.setDescription(sAttachDesc);
		       }

		       
		        objAppData = ContentServerHelper.service.updateContent(contentHolder, objAppData, filePath);
				PersistenceHelper.manager.modify(pr);				
		        trans.commit();
		        trans = null;
			    return true;
		    }
		    catch(Throwable t) 
		    {
		        throw new ExceptionInInitializerError(t);
		    } 
		    finally 
		    {
		        if(trans != null) 
		        {
		        	trans.rollback();
		        }
		    }	        
		 }

		 public boolean deleteProblemReportAttachment(String sPRNo, String filePath) throws WTException, PropertyVetoException, FileNotFoundException, IOException, InvocationTargetException
		 {
			Boolean bDeleted = false;
	        if (!RemoteMethodServer.ServerFlag)
	        {

	            RemoteMethodServer ms = RemoteMethodServer.getDefault();
	            Class[] argTypes = {String.class, String.class};
	            Object[] argValues = {sPRNo, filePath};
	            ms.invoke("deleteProblemReportAttachment", null, this, argTypes, argValues);
	            return true;
	        }

	        SessionContext.newContext();
	        String sUsername = getBackendUser();
	        SessionHelper.manager.setPrincipal(sUsername);

	        Transaction trans = new Transaction();
	        
	        try
	        {
	        	trans.start();
	        	WTChangeIssue probrpt = getProblemReportByNumber(sPRNo);

				ContentHolder contentHolder = (ContentHolder)probrpt;
	    		contentHolder = ContentHelper.service.getContents(contentHolder);

		       // START -- Delete contents if they have same name as the file name to be uploaded
		       ContentRoleType roleType = ContentRoleType.SECONDARY;
		       
		       
		        QueryResult qrContents = ContentHelper.service.getContentsByRole(contentHolder, roleType); 

		       // Get file name of current file to be attached as secondary content
		       File objFile = new File(filePath);
		       String nameOfFileToBeAttached = objFile.getName();

		       ApplicationData fileContent = null;
		       String nameOfExistingSecContent = null;
		       while (qrContents.hasMoreElements())
		       {
		             fileContent = (ApplicationData) qrContents.nextElement();
		             nameOfExistingSecContent = fileContent.getFileName();
		             if(nameOfExistingSecContent.equals(nameOfFileToBeAttached))
		             {
		            	 deleteExistingContent(contentHolder, fileContent);
		            	 bDeleted = true;
		             }
		       }
		       // END -- Delete contents if they have same name as the file name to be uploaded

		       if(bDeleted)
		       {
					PersistenceHelper.manager.modify(probrpt);				
		       }

	  	        trans.commit();
		        trans = null;
			    return true;
		    }
		    catch(Throwable t) 
		    {
		        throw new ExceptionInInitializerError(t);
		    } 
		    finally 
		    {
		        if(trans != null) 
		        {
		        	trans.rollback();
		        }
		    }	        
		 }

		 public static WTChangeIssue getProblemReportByNumber(String sPRNumber) throws WTException 
		{
		    QuerySpec qs = new QuerySpec(WTChangeIssue.class);
		    SearchCondition condition = new SearchCondition(WTChangeIssue.class, WTChangeIssue.NUMBER, SearchCondition.EQUAL, sPRNumber);
		    qs.appendWhere(condition, new int[]{0});
		    QueryResult qr = PersistenceHelper.manager.find((StatementSpec)qs);
		    WTChangeIssue pr = null;
			while(qr.hasMoreElements())
			{
				pr = (WTChangeIssue)qr.nextElement();
				break;
			}
						
			return pr;
		}

	 public boolean setProblemReportAffectedObjects(String sPRNo, String[] sAffectedObjectsNos) throws WTException, PropertyVetoException, FileNotFoundException, IOException, InvocationTargetException
	 {
        if (!RemoteMethodServer.ServerFlag)
        {

            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String[].class};
            Object[] argValues = {sPRNo, sAffectedObjectsNos};
            ms.invoke("setProblemReportAffectedObjects", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
        
        try
        {
        	trans.start();
        	WTChangeIssue probrpt = getProblemReportByNumber(sPRNo);
        	for(int ii = 0; ii< sAffectedObjectsNos.length;ii++)
        	{
	    		wt.fc.collections.WTSet links =   new wt.fc.collections.WTHashSet();
        		WTPart part = getPartByNumber(sAffectedObjectsNos[ii]);        		
	    		ReportedAgainst rptAgainst = ReportedAgainst.newReportedAgainst(part, probrpt);
	        	links.add(rptAgainst);
	        	ChangeHelper2.service.storeAssociations(probrpt, links);	
		        	
        	}
        	trans.commit();
	        trans = null;
		    return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }

	 public boolean deleteProblemReportAffectedObjects(String sPRNo, String[] sAffectedObjectsNos) throws WTException, PropertyVetoException, FileNotFoundException, IOException, InvocationTargetException
	 {
        if (!RemoteMethodServer.ServerFlag)
        {

            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String[].class};
            Object[] argValues = {sPRNo, sAffectedObjectsNos};
            ms.invoke("deleteProblemReportAffectedObjects", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
        
        try
        {
        	trans.start();
        	WTChangeIssue probrpt = getProblemReportByNumber(sPRNo);
        	for(int ii = 0; ii< sAffectedObjectsNos.length;ii++)
        	{
        		WTPart part = getPartByNumber(sAffectedObjectsNos[ii]);        		
	        	ChangeHelper2.service.unattachChangeable(part, probrpt, wt.change2.ReportedAgainst.class, wt.change2.ReportedAgainst.ROLE_AOBJECT_ROLE);		        	
        	}
        	trans.commit();
	        trans = null;
		    return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }

	 // sAttributeType can have values
	 //		Boolean - bool or boolean
	 //		Date & Time - date or datetime
	 //		Integer Number - int or integer
	 //		Real Number - real or doub or double or float
	 //		String - string or the default
	 public Boolean setProblemReportAttributes(String sPRNumber, String sPRName, String[] sAttributeName, String[] sAttributeValue, 
			 						  		   String[] sAttributeType, Timestamp dtNeedDate) throws WTException, RemoteException, InvocationTargetException
	 {
		int i;
        if (!RemoteMethodServer.ServerFlag)
        {
            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class, String[].class, String[].class, String[].class, Timestamp.class};
            Object[] argValues = {sPRNumber, sPRName, sAttributeName, sAttributeValue, sAttributeType, dtNeedDate};
            ms.invoke("setProblemReportAttributes", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
	        
        try
        {
	        trans.start();
			WTChangeIssue probrpt = getProblemReportByNumber(sPRNumber);
			if(dtNeedDate != null)
				probrpt.setNeedDate(dtNeedDate);

			if(probrpt != null)
			{
				WTChangeIssueMaster master = (WTChangeIssueMaster)probrpt.getMaster();
				WTChangeIssueMasterIdentity masterId = (WTChangeIssueMasterIdentity)master.getIdentificationObject();
				masterId.setName(sPRName);
				IdentityHelper.service.changeIdentity((Identified) master, masterId);

				master = (WTChangeIssueMaster) PersistenceHelper.manager.refresh(master);

//				Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
//				CheckoutLink col = null;
//				col = WorkInProgressHelper.service.checkout((WTPart)part,checkoutFolder,"");
//				WTPart part2 = (WTPart)col.getWorkingCopy();
				for(i=0;i<sAttributeName.length;i++)
				{
					PersistableAdapter obj = new PersistableAdapter(probrpt,null,Locale.US,new UpdateOperationIdentifier());
				    obj.load(sAttributeName[i]);
				    Object objAttributeValue = null;
				    switch(sAttributeType[i])
				    {
					    case "string":
					    default:
					    	if(sAttributeValue[i].equals("null"))
					    		objAttributeValue = null;
					    	else
					    		objAttributeValue = sAttributeValue[i];
					    	break;
					    case "date":
					    case "datetime":
					    	objAttributeValue = GetDateFromString(sAttributeValue[i], "dd/MM/yyyy hh:mm:ss a");
					    	break;
					    case "bool":
					    case "boolean":
					    	objAttributeValue = Boolean.valueOf(sAttributeValue[i]);
					    	break;
					    case "int":
					    case "integer":
					    	objAttributeValue = Integer.valueOf(sAttributeValue[i]);
					    	break;
					    case "doub":
					    case "double":
					    case "real":
					    case "float":
					    	objAttributeValue = com.ptc.core.meta.common.FloatingPoint.valueOf(sAttributeValue[i]);
					    	break;
					    	
				    }
					obj.set(sAttributeName[i], objAttributeValue);
					obj.apply();
				}
				PersistenceHelper.manager.modify(probrpt);				
			}
		 
			trans.commit();
			trans = null;
			return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
	 }

	 //The sPersonNumber is the code (or number) for the person. It should be GBnnnnn
	 //The WebAppId is whether we are using the Regain or the pims DB.
	 //pims = 1
	 //Regain = 2
	 public Boolean setPersonCompliancy(String sPersonNumber, int iWebAppId) throws WTException, RemoteException, InvocationTargetException
	 {
        if (!RemoteMethodServer.ServerFlag)
        {
            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, int.class};
            Object[] argValues = {sPersonNumber, iWebAppId};
            ms.invoke("setPersonCompliancy", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        String sUsername = getBackendUser();
        SessionHelper.manager.setPrincipal(sUsername);

        Transaction trans = new Transaction();
	        
        try
        {
	        trans.start();
			WindchillBackend wbe = new WindchillBackend();
			Backend be = new Backend();
//			be.email_message(sSubject, sBody, sAttachments, sRecipients, sCCRecipients, sBCCRecipients)
//			System.out.println("Processing setPersonCompliancy");
			wbe.SetPersonCompliancy(sPersonNumber, iWebAppId);
//			System.out.println("Completed setPersonCompliancy");
			trans.commit();
			trans = null;
			return true;
	    }
	    catch(Throwable t) 
	    {
	        throw new ExceptionInInitializerError(t);
	    } 
	    finally 
	    {
	        if(trans != null) 
	        {
	        	trans.rollback();
	        }
	    }	        
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
	    	java.sql.Timestamp dtsql = new java.sql.Timestamp(startDate.getTime());
	        return dtsql;
	     } 
	     catch (Exception e) 
	     {
	        e.printStackTrace();
	        return null;
	     }		 
	 }
	 
/*	 private static void logToFile(String sMsg)
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
*/	 
	 
	public static String getBackendUser()
	{
		Backend be = new Backend(5);
		Backend.Environment env = be.new Environment();
        String sUsername = env.GetEnvironmentVariable("WindchillBackendUser");
        return sUsername;

	}
	

	public final static String Get_FilenameOnly_From_FullPath(String sPathAndFilename)
	{
		String sFileNameOnly = null;
		int iStart = 0;
		int iLength = 0;

		iStart = sPathAndFilename.lastIndexOf("\\") + 1;
		iLength = (sPathAndFilename == null ? 0 : sPathAndFilename.length()) - iStart;
		sFileNameOnly = sPathAndFilename.substring(iStart, iStart + iLength);

		return sFileNameOnly;
	}

	public class RegainBackend
	{
		public Backend.ReturnClassBool email_message(String sSubject, String sBody, String[] sAttachments, String sRecipients, String sCCRecipients, String sBCCRecipients, int iWebAppId)
		{
			Backend be = new Backend(iWebAppId);
			Backend.Email mail = be.new Email();
			Backend.ReturnClassBool rtn = be.new ReturnClassBool();
			rtn = mail.email_message(sSubject, sBody, sAttachments,sRecipients, sCCRecipients, sBCCRecipients);
			return rtn;
		}
		
		//Get the competency unit lead time from the own accreditation item. Return it in days regardless of the unit
		public int GetCompetencyUnitLeadTimeFromOwnAccreditation(String sOwnAccreditationNumber, int iWebAppId)
		{
			WindchillBackend wbe = new WindchillBackend();
			int[] iCompUnits = wbe.GetChildDocumentIdsFromParentDocument("local.rs.vsrs05.Regain.CompetencyUnit", sOwnAccreditationNumber, iWebAppId);
			if(iCompUnits.length > 1)
			{
				return -99;
			}
			else
			{
				if(iCompUnits.length == 0)
				{
					wbe.GetLeadTimeInDays(sOwnAccreditationNumber, iWebAppId);
				}
				return iCompUnits[0];
			}
		}
		
		public void CheckOwnAccreditationExpiry(WTDocument WTOwnAccreditation, int iWebAppId)
		{
//			System.out.println("Inside CheckOwnAccreditationExpiry");
			WindchillBackend wbe = new WindchillBackend();
//			System.out.println("Established wbe");
			wbe.OwnAccreditationSendWarningsOnExpiry(WTOwnAccreditation.getNumber(), iWebAppId);
//			System.out.println("Completed CheckOwnAccreditationExpiry");
			
		}
		
	}
	/*	 private static void logToFileInsideTrans(String sMsg)
	 {
		 sTransMsg += sMsg + "\r\n";
	 }
*/	 

}
