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
public class EidasAttributes {

    private String friendlyName;
    private String loa;
    private String value;

    public EidasAttributes() {
    }

    public EidasAttributes(String name, String loa, String value) {
        this.friendlyName = name;
        this.loa = loa;
        this.value = value;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getLoa() {
        return loa;
    }

    public void setLoa(String loa) {
        this.loa = loa;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
