package com.junyu.server.Client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="strXmlHead" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "strXmlHead"
})
@XmlRootElement(name = "ToIngestInvokeHead")
public class ToIngestInvokeHead {

    protected String strXmlHead;

    /**
     * Gets the value of the strXmlHead property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrXmlHead() {
        return strXmlHead;
    }

    /**
     * Sets the value of the strXmlHead property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrXmlHead(String value) {
        this.strXmlHead = value;
    }

}
