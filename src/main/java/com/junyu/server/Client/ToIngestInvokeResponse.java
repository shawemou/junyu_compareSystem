package com.junyu.server.Client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="ToIngestInvokeResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "toIngestInvokeResult"
})
@XmlRootElement(name = "ToIngestInvokeResponse")
public class ToIngestInvokeResponse {

    @XmlElement(name = "ToIngestInvokeResult")
    protected String toIngestInvokeResult;

    /**
     * Gets the value of the toIngestInvokeResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToIngestInvokeResult() {
        return toIngestInvokeResult;
    }

    /**
     * Sets the value of the toIngestInvokeResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToIngestInvokeResult(String value) {
        this.toIngestInvokeResult = value;
    }

}
