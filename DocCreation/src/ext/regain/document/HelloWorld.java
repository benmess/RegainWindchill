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

import wt.doc.WTDocument;
import wt.util.WTException;
import wt.security.*;
import ext.regain.document.RegainBackend;

public class HelloWorld {
public static void main(String[] args) 
{
	
	try {
		System.out.println("abc");
		RegainBackend be = new RegainBackend();
		be.attachDoc("120TD101", "C:/WebRoot/Regain/temp/120TD203-Sht 06.pdf", true);
		System.out.println("xyz");
//		Boolean doc = be.createWTDoc("yyy", "some test name", "116 Tomago Operation", "local.rs.vsrs05.Regain.RegainRefDocument");//GetDocumentByNumber("120TD266");
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
