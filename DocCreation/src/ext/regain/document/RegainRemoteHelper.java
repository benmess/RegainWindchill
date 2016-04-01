package ext.regain.document;

import java.beans.PropertyVetoException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;





import java.text.*;
import java.util.*;

import com.ptc.core.logging.Log;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.content.HolderToContent;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.inf.container.WTContainerRef;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
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
        	createdoc("XYZ999", "a test name", "116 Tomago Operation", "local.rs.vsrs05.Regain.RegainRefDocument");
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
	
	public WTDocument createdoc(String sNumber, String sName, String sProductName, String sDocType) throws Exception 
	{
		System.out.println("help me");
        if (!RemoteMethodServer.ServerFlag)
        {

            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class,String.class, String.class};
            Object[] argValues = {sNumber, sName, sProductName, sDocType};
            ms.invoke("createdoc", null, this, argTypes, argValues);
            return null;
        }

        SessionContext.newContext();
        SessionHelper.manager.setAdministrator();
        Transaction trans = new Transaction();
        
        try
        {
        	trans.start();
		    WTDocument doc = WTDocument.newWTDocument();
		    doc.setName(sName);
		    doc.setNumber(sNumber);
	
		    WTContainerRef prodref = GetProductRef(sProductName);
		    doc.setContainerReference(prodref);
	
		    TypeDefinitionReference typeRef = getTypeDef(sDocType);
	//	    TypeDefinitionReference typeRef = (TypeDefinitionReference)myServer.invoke("getTypeDef", RegainRemoteHelper.class.getName(), null, aClass,aObj);
	
		    doc.setTypeDefinitionReference(typeRef);
		    doc = (WTDocument)PersistenceHelper.manager.store(doc);
	
		    PersistenceHelper.manager.refresh(doc);
	    	trans.commit();
	        trans = null;
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
	 public boolean attachToDoc(String sDocNo, String filePath, Boolean bSecondary) throws WTException, PropertyVetoException, FileNotFoundException, IOException, InvocationTargetException
	 {
		System.out.println("in attach doc");
        if (!RemoteMethodServer.ServerFlag)
        {

            RemoteMethodServer ms = RemoteMethodServer.getDefault();
            Class[] argTypes = {String.class, String.class,Boolean.class};
            Object[] argValues = {sDocNo, filePath, bSecondary};
            ms.invoke("attachToDoc", null, this, argTypes, argValues);
            return true;
        }

        SessionContext.newContext();
        SessionHelper.manager.setAdministrator();
        Transaction trans = new Transaction();
		System.out.println("opening trans");
        
        try
        {
        	trans.start();
        	WTDocument doc = getDocumentByNumber(sDocNo);
    		System.out.println("got document");

			 ContentHolder contentHolder = (ContentHolder)doc;
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
	            	 deleteExistingSecContent(contentHolder, fileContent);
	       }
	       // END -- Delete contents if they have same name as the file name to be uploaded

			System.out.println("deleted contents");

	       ApplicationData objAppData = ApplicationData.newApplicationData(contentHolder);
	       if(contentHolder instanceof WTDocument)
	       {
	             //WTDocument wtDoc = (WTDocument)contentHolder;
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

	 /**
	 * Function to delete a secondary content, if one with the given name exists.
	 * 
	  * @param contentholder
	 * @param contentitem
	 * @throws WTException
	 * @throws WTPropertyVetoException
	 */
	 private static void deleteExistingSecContent(ContentHolder contentholder, ContentItem contentitem) throws WTException, WTPropertyVetoException
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
			System.out.println(doc2.getIdentity() + " iteration " + doc2.getIterationIdentifier().getValue());	
		}
		
		return doc2;
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
	 
/*	 private static void logToFileInsideTrans(String sMsg)
	 {
		 sTransMsg += sMsg + "\r\n";
	 }
*/	 

}
