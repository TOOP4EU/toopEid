/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.toop.cctfEidModule.model.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nikos
 */
public class AuthTokenBuilder {

    private Map<String, String> eIDASAttr;
    private String[] consent;
    private String dcId;

    public AuthTokenBuilder() {
    }

    public void seteIDASAttr(Map<String, String> eIDASAttr) {
        this.eIDASAttr = eIDASAttr;
    }

    public void setConsent(String[] consent) {
        this.consent = consent;
    }

    public void setDcId(String dcId) {
        this.dcId = dcId;
    }

    public Map<String, Object> build() throws JsonProcessingException {
        Map<String, Object> res = new HashMap();
        this.eIDASAttr.entrySet().stream().forEach(e -> {
            res.put(e.getKey(), e.getValue());
        });
        res.put("dcId", dcId);
        res.put("consent", this.consent);
        return res;
    }


}
