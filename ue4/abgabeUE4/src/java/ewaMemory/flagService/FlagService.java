
package ewaMemory.flagService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "FlagService", targetNamespace = "http://flagservice.big.tuwien.ac.at/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface FlagService {


    /**
     * 
     * @return
     *     returns ewaMemory.flagService.StringArray
     * @throws FlagServiceException
     */
    @WebMethod
    @WebResult(name = "supportedGameSize", partName = "supportedGameSize")
    public StringArray getSupportedGameSize()
        throws FlagServiceException
    ;

    /**
     * 
     * @return
     *     returns ewaMemory.flagService.StringArray
     * @throws FlagServiceException
     */
    @WebMethod
    @WebResult(name = "supportedContinents", partName = "supportedContinents")
    public StringArray getSupportedContinents()
        throws FlagServiceException
    ;

    /**
     * 
     * @param flagRequest
     * @return
     *     returns ewaMemory.flagService.FlagResponse
     * @throws FlagServiceException
     */
    @WebMethod
    @WebResult(name = "flags", partName = "flags")
    public FlagResponse getFlags(
        @WebParam(name = "FlagRequest", partName = "FlagRequest")
        FlagRequest flagRequest)
        throws FlagServiceException
    ;

}
