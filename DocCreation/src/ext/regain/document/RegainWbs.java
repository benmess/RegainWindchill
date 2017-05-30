package ext.regain.document;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.ptc.jws.servlet.JaxWsWebService;

@WebService()
public class RegainWbs  extends JaxWsWebService
{
	@WebMethod(operationName="doccreate")
    public int doccreate ( int a, int b )
    {
      return a-b;
    }
}
