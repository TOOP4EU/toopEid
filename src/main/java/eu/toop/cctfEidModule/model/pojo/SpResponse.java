/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.toop.cctfEidModule.model.pojo;

/**
 *
 * @author nikos
 */
public class SpResponse {

    private EidasAttributes[] eidasAttributes;
    private String timestamp;
    private String audienceRestriction;
    private String issuer;
    private String country;

    public EidasAttributes[] getEidasAttributes() {
        return eidasAttributes;
    }

    public void setEidasAttributes(EidasAttributes[] eidasAttributes) {
        this.eidasAttributes = eidasAttributes;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAudienceRestriction() {
        return audienceRestriction;
    }

    public void setAudienceRestriction(String audienceRestriction) {
        this.audienceRestriction = audienceRestriction;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
