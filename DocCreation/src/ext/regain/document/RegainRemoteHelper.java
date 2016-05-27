package ext.regain.document;

import java.beans.PropertyVetoException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;





import java.rmi.RemoteException;
import java.text.*;
import java.util.*;

import org.apache.commons.lang.time.DateUtils;

import com.ibm.icu.util.GregorianCalendar;
import com.ptc.core.logging.Log;
import com.ptc.core.lwc.server.LWCNormalizedObject;
import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.meta.common.CreateOperationIdentifier;
import com.ptc.core.meta.common.UpdateOperationIdentifier;
import com.sun.xml.fastinfoset.util.StringArray;

import wt.access.AccessPermission;
import wt.admin.AdminDomainRef;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.content.HolderToContent;
import wt.doc.DocumentMaster;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.doc.WTDocumentMasterIdentity;
import wt.fc.Identified;
import wt.fc.IdentityHelper;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.fc.collections.WTCollection;
import wt.fc.collections.WTKeyedMap;
import wt.fc.collections.WTList;
import wt.fc.collections.WTSet;
import wt.fc.collections.WTValuedMap;
import wt.folder.Cabinet;
import wt.folder.CabinetBased;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.FolderNotFoundException;
import wt.folder.FolderService;
import wt.folder.Foldered;
import wt.folder.FolderingInfo;
import wt.folder.Shortcut;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipal;
import wt.part.Quantity;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartMasterIdentity;
import wt.part.WTPartReferenceLink;
import wt.part.WTPartUsageLink;
import wt.pdmlink.PDMLinkProduct;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtility;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.VersionControlHelper;
import wt.vc.struct.IteratedReferenceLink;
import wt.vc.struct.StructHelper;
import wt.vc.wip.CheckoutInfo;
import wt.vc.wip.CheckoutLink;
import wt.vc.wip.WorkInProgressHelper;
import wt.pom.*;

public class RegainRemoteHelper implements Serializable, RemoteAccess 
{
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
        	createdoc("XYZ999", "a test name", "116 Tomago Operation", "local.rs.vsrs05.Regain.RegainRefDocument", "Technical Documents 120TD Thermal Treatment Plant", null, null, "");
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
		System.out.println(objAppData.getFileName());
		return objAppData;
	}
	
	@SuppressWarnings("deprecation")
	public WTDocument createdoc(String sNumber, String sName, String sProductName, String sDocType, String sFolderName, 
								String[] sAttributeName, String[] sAttributeValue, String sCheckInComments) throws Exception 
	{
		int i;
        if (!RemoteMethodServer.ServerFlag)
        {

            RemoteMethodServer ms = RemoteMethodServer.getDefault();
/*            ms.setUserName("benmess");
            ms.setPassword("mo9anaapr!");
*/            Class[] argTypes = {String.class, String.class, String.class, String.class, String.class, String[].class, String[].class, String.class};
            Object[] argValues = {sNumber, sName, sProductName, sDocType, sFolderName, sAttributeName, sAttributeValue, sCheckInComments};
            ms.invoke("createdoc", null, this, argTypes, argValues);
            return null;
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
		    doc.setNumber(sNumber);
	
		    WTContainerRef prodref = GetProductRef(sProductName);
		    doc.setContainerReference(prodref);
		    Folder folder = GetFolder2(sFolderName, sProductName);

		    if (folder != null) 
		    {
	            FolderHelper.assignLocation(doc, folder);
	        }

//		    FolderingInfo folderInf = GetFolder(sFolderName);
//		    doc.setFolderingInfo(folderInf);
	
		    TypeDefinitionReference typeRef = getTypeDef(sDocType);
	//	    TypeDefinitionReference typeRef = (TypeDefinitionReference)myServer.invoke("getTypeDef", RegainRemoteHelper.class.getName(), null, aClass,aObj);
	
		    doc.setTypeDefinitionReference(typeRef);
			for(i=0;i<sAttributeName.length;i++)
			{
				PersistableAdapter obj = new PersistableAdapter(doc,null,Locale.US,new CreateOperationIdentifier());
			    obj.load(sAttributeName[i]);
				obj.set(sAttributeName[i], sAttributeValue[i]);
				obj.apply();
			}
			doc = (WTDocument)PersistenceHelper.manager.store(doc);
		    PersistenceHelper.manager.refresh(doc);
	    	trans.commit();
	        trans = null;
	        if(sCheckInComments.length() > 0)
	        {
				VersionControlHelper.setNote(doc, sCheckInComments);
				PersistenceServerHelper.update(doc);
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

	public static Folder GetFolder2(String sFolderNameAndFullPath, String sProductName) throws WTException, WTPropertyVetoException
	{
	    WTContainerRef prodref = GetProductRef(sProductName);
	    Folder  folder = (Folder) FolderHelper.service.getFolder("/Default/" + sFolderNameAndFullPath.toString(), WTContainerRef.newWTContainerRef(prodref));
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
		System.out.println("in attach doc");
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
		System.out.println("opening trans");
        
        try
        {
        	trans.start();
        	WTDocument doc = getDocumentByNumber(sDocNo);
    		System.out.println("got document");

			Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
			CheckoutLink col = null;
			col = WorkInProgressHelper.service.checkout((WTDocument)doc,checkoutFolder,"");
			WTDocument doc2 = (WTDocument)col.getWorkingCopy();

			ContentHolder contentHolder = (ContentHolder)doc2;
		     contentHolder = ContentHelper.service.getContents(contentHolder);
				System.out.println("got content holder");

	       // START -- Delete contents if they have same name as the file name to be uploaded
	       ContentRoleType roleType = ContentRoleType.PRIMARY;
	       
	       if(bSecondary)
	    	roleType =  ContentRoleType.SECONDARY;
	       
			System.out.println("set role type");
	       QueryResult qrContents = ContentHelper.service.getContentsByRole(contentHolder, roleType); //ContentRoleType.SECONDARY
			System.out.println("got query results");

	       // Get file name of current file to be attached as secondary content
	       File objFile = new File(filePath);
	       String nameOfFileToBeAttached = objFile.getName();
			System.out.println("got nameOfFileToBeAttached = " + nameOfFileToBeAttached);

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

			System.out.println("deleted contents");

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
	       
			System.out.println("about to update");
	        objAppData = ContentServerHelper.service.updateContent(contentHolder, objAppData, filePath);
			PersistableAdapter obj = new PersistableAdapter(doc2,null,Locale.US,new UpdateOperationIdentifier());
		    obj.load("Originator");
			obj.set("Originator", sUser);
			obj.apply();
			PersistenceHelper.manager.modify(doc2);				
			doc2 = (WTDocument)WorkInProgressHelper.service.checkin(doc2,sCheckInComments);
	       System.out.println("Upload Secondary content.......");
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
		System.out.println("in attach doc");
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
		System.out.println("opening trans");
        
        try
        {
        	trans.start();
        	WTDocument doc = getDocumentByNumber(sDocNo);
    		System.out.println("got document");

			Folder checkoutFolder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
			CheckoutLink col = null;
			col = WorkInProgressHelper.service.checkout((WTDocument)doc,checkoutFolder,"");
			WTDocument doc2 = (WTDocument)col.getWorkingCopy();

			ContentHolder contentHolder = (ContentHolder)doc2;
    		contentHolder = ContentHelper.service.getContents(contentHolder);
			System.out.println("got content holder");

	       // START -- Delete contents if they have same name as the file name to be uploaded
	       ContentRoleType roleType = ContentRoleType.PRIMARY;
	       
	       if(bSecondary)
	    	roleType =  ContentRoleType.SECONDARY;
	       
			System.out.println("set role type");
	        QueryResult qrContents = ContentHelper.service.getContentsByRole(contentHolder, roleType); 
			System.out.println("got query results");

	       // Get file name of current file to be attached as secondary content
	       File objFile = new File(filePath);
	       String nameOfFileToBeAttached = objFile.getName();
			System.out.println("got nameOfFileToBeAttached = " + nameOfFileToBeAttached);

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
				System.out.println("deleted contents");
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
	             System.out.println("deleted a secondary content.....");
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
//			System.out.println(doc2.getIdentity() + " iteration " + doc2.getIterationIdentifier().getValue());	
		}
		
		return doc2;
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
//			System.out.println(doc2.getIdentity() + " iteration " + doc2.getIterationIdentifier().getValue());	
		}
		
		return part2;
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
	 
	 public Boolean setReferencedByLink(String sUser, String sDocNumber, String sPartNumber, String sCheckInComments) throws WTException, RemoteException, InvocationTargetException
	 {
		int i;
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

	 public Boolean deleteReferencedByLink(String sUser, String sDocNumber, String sPartNumber, String sCheckInComments) throws WTException, RemoteException, InvocationTargetException
	 {
		int i;
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

	 @SuppressWarnings("deprecation")
	public WTPart createpart(String sNumber, String sName, String sProductName, String sPartType, String sFolderName, 
								String[] sAttributeName, String[] sAttributeValue, String sCheckInComments) throws Exception 
	{
		int i;
        if (!RemoteMethodServer.ServerFlag)
        {

            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class, String.class, String.class, String.class, String[].class, String[].class, String.class};
            Object[] argValues = {sNumber, sName, sProductName, sPartType, sFolderName, sAttributeName, sAttributeValue, sCheckInComments};
            ms.invoke("createpart", null, this, argTypes, argValues);
            return null;
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
		    part.setNumber(sNumber);
	
		    WTContainerRef prodref = GetProductRef(sProductName);
		    part.setContainerReference(prodref);
		    Folder folder = GetFolder2(sFolderName, sProductName);

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
				obj.set(sAttributeName[i], sAttributeValue[i]);
				obj.apply();
			}
			part = (WTPart)PersistenceHelper.manager.store(part);
		    PersistenceHelper.manager.refresh(part);
	    	trans.commit();
	        trans = null;
	        if(sCheckInComments.length() > 0)
	        {
				VersionControlHelper.setNote(part, sCheckInComments);
				PersistenceServerHelper.update(part);
	        }
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
					    	objAttributeValue = Double.valueOf(sAttributeValue[i]);
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
	 
	 public Boolean setPartPartLink(String sUser, String sParentPartNo, String sChildPartNumber, double dQty, String sCheckInComments) throws WTException, RemoteException, InvocationTargetException
	 {
        if (!RemoteMethodServer.ServerFlag)
        {
            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class, String.class, double.class, String.class};
            Object[] argValues = {sUser, sParentPartNo, sChildPartNumber, dQty, sCheckInComments};
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

	 public Boolean deletePartPartLink(String sUser, String sParentPart, String sChildPart, String sCheckInComments) throws WTException, RemoteException, InvocationTargetException
	 {
		int i;
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
	 
	private static String getBackendUser()
	{
		Backend be = new Backend(5);
		Backend.Environment env = be.new Environment();
        String sUsername = env.GetEnvironmentVariable("WindchillBackendUser");
        return sUsername;

	}
	 
/*	 private static void logToFileInsideTrans(String sMsg)
	 {
		 sTransMsg += sMsg + "\r\n";
	 }
*/	 

}
