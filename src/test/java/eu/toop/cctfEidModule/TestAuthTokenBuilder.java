/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.toop.cctfEidModule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.toop.cctfEidModule.model.pojo.AuthTokenBuilder;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author nikos
 */
public class TestAuthTokenBuilder {

    private Map<String,Object> contents;

    @Before
    public void before() throws JsonProcessingException {
        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("att1", "val1");
        attrs.put("attr2", "val2");

        String[] consent = new String[2];
        consent[0] = "cons1";
        consent[1] = "cons2";

        AuthTokenBuilder builder = new AuthTokenBuilder();
        builder.setConsent(consent);
        builder.setDcId("dcId");
        builder.seteIDASAttr(attrs);
        contents = builder.build();

    }

    @Test
    public void testBuild() {
        
        assertEquals( ((String[])contents.get("consent"))[0], "cons1");
        assertEquals(contents.get("att1"), "val1");
    }

    @Test
    public void testJSONWrapping() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String expected = "{\"dcId\":\"dcId\",\"attr2\":\"val2\",\"consent\":[\"cons1\",\"cons2\"],\"att1\":\"val1\"}";
        String res = mapper.writeValueAsString(contents);
        System.out.println();  
        assertEquals(res,expected);
    }

}
