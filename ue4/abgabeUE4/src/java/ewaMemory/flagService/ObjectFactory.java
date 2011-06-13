
package ewaMemory.flagService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ewaMemory.flagService package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FlagServiceException_QNAME = new QName("http://flagservice.big.tuwien.ac.at/", "FlagServiceException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ewaMemory.flagService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FlagRequest }
     * 
     */
    public FlagRequest createFlagRequest() {
        return new FlagRequest();
    }

    /**
     * Create an instance of {@link FlagResponse }
     * 
     */
    public FlagResponse createFlagResponse() {
        return new FlagResponse();
    }

    /**
     * Create an instance of {@link Flag }
     * 
     */
    public Flag createFlag() {
        return new Flag();
    }

    /**
     * Create an instance of {@link StringArray }
     * 
     */
    public StringArray createStringArray() {
        return new StringArray();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://flagservice.big.tuwien.ac.at/", name = "FlagServiceException")
    public JAXBElement<String> createFlagServiceException(String value) {
        return new JAXBElement<String>(_FlagServiceException_QNAME, String.class, null, value);
    }

}
