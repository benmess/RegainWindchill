package ext.regain.document;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.HolderToContent;
import wt.doc.WTDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.inf.container.WTContainerRef;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.pdmlink.PDMLinkProduct;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.type.TypeDefinitionReference;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.VersionControlHelper;

public class RegainBackend 
{
		
	
	public static Boolean createWTDoc(String sDocNo, String sDocName, String sProductName, String sDocType) throws Exception
	{
/*		RemoteMethodServer myServer = null;
		myServer = RemoteMethodServer.getDefault();
	    myServer.setUserName("wcadmin");
	    myServer.setPassword("wcadmin");
*/
  		System.out.println("Set doc number");
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    System.out.println("about to execute with doc " + sDocNo + " and name " + sDocName);
	    help.createdoc(sDocNo, sDocName, sProductName, sDocType);
	    System.out.println("success");
	    
//	    WTDocument doc = (WTDocument)myServer.invoke("createDoc", RegainRemoteHelper.class.getName(), null, aClass,aObj);
//	    System.out.println("completed document with doc number = " + doc.getNumber() + ", name = " + doc.getName());
	    
	    return true;

	}
	
	public static Boolean attachDoc(String sDocNo, String sAttachmentPathAndFile, Boolean bSecondary) throws FileNotFoundException, InvocationTargetException, WTException, PropertyVetoException, IOException
	{
  		System.out.println("Set doc number");
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    System.out.println("about to execute attach doc " + sDocNo + " and path and name " + sAttachmentPathAndFile);
	    help.attachToDoc(sDocNo, sAttachmentPathAndFile, bSecondary);
	    System.out.println("success");
		
		return true;
	}



}
