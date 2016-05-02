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
		
	
	public static Boolean createWTDoc(String sDocNo, String sDocName, String sProductName, String sDocType, String sFolderName, 
									  String[] sAttributeName, String[] sAttributeValue, String sCheckInComments) throws Exception
	{
/*		RemoteMethodServer myServer = null;
		myServer = RemoteMethodServer.getDefault();
	    myServer.setUserName("wcadmin");
	    myServer.setPassword("wcadmin");
*/
	    RegainRemoteHelper help = new RegainRemoteHelper();
	    WTDocument doc = help.createdoc(sDocNo, sDocName, sProductName, sDocType, sFolderName, sAttributeName, sAttributeValue, sCheckInComments);	    
	    return true;

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
}
