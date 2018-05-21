/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.toop.cctfEidModule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.toop.cctfEidModule.model.pojo.EidasAttributes;
import eu.toop.cctfEidModule.utils.eIDASResponseParser;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author nikos
 */
public class testEIDASResponseParser {

    String testResponse = "'AuthenticationResponse{id='_YLc6H3WhE2mjssJZHnJyOIvuRFBPIHsfszeGwVzAipyXS2csl7SlpVbKjUo4UOp', issuer='http://84.205.248.180:80/EidasNode/ConnectorResponderMetadata', status='ResponseStatus{failure='false', statusCode='urn:oasis:names:tc:SAML:2.0:status:Success', statusMessage='urn:oasis:names:tc:SAML:2.0:status:Success', subStatusCode='null'}', ipAddress='null', inResponseToId='_BJK3gNxljIfI.hOeabwBjO5ZFE54BPHQXmG9gEoXNb.BMgIN4LRZRzY18-ZyG6m', levelOfAssurance='http://eidas.europa.eu/LoA/low', attributes='{AttributeDefinition{nameUri='http://eidas.europa.eu/attributes/naturalperson/CurrentFamilyName', friendlyName='FamilyName', personType=NaturalPerson, required=true, transliterationMandatory=true, uniqueIdentifier=false, xmlType='{http://eidas.europa.eu/attributes/naturalperson}CurrentFamilyNameType', attributeValueMarshaller='eu.eidas.auth.commons.attribute.impl.StringAttributeValueMarshaller'}=[cph8], AttributeDefinition{nameUri='http://eidas.europa.eu/attributes/naturalperson/CurrentGivenName', friendlyName='FirstName', personType=NaturalPerson, required=true, transliterationMandatory=true, uniqueIdentifier=false, xmlType='{http://eidas.europa.eu/attributes/naturalperson}CurrentGivenNameType', attributeValueMarshaller='eu.eidas.auth.commons.attribute.impl.StringAttributeValueMarshaller'}=[cph8], AttributeDefinition{nameUri='http://eidas.europa.eu/attributes/naturalperson/DateOfBirth', friendlyName='DateOfBirth', personType=NaturalPerson, required=true, transliterationMandatory=false, uniqueIdentifier=false, xmlType='{http://eidas.europa.eu/attributes/naturalperson}DateOfBirthType', tributeValueMarshaller='eu.eidas.auth.commons.attribute.impl.DateTimeAttributeValueMarshaller'}=[1966-01-01], AttributeDefinition{nameUri='http://eidas.europa.eu/attributes/naturalperson/PersonIdentifier', friendlyName='PersonIdentifier', personType=NaturalPerson, required=true, transliterationMandatory=false, uniqueIdentifier=true, xmlType='{http://eidas.europa.eu/attributes/naturalperson}PersonIdentifierType',attributeValueMarshaller='eu.eidas.auth.commons.attribute.impl.LiteralStringAttributeValueMarshaller'}=[CA/CA/Cph123456]}', audienceRestriction='http://138.68.103.237:8090/metadata', notOnOrAfter='2017-09-16T08:16:21.191Z', notBefore='2017-09-16T08:11:21.191Z', country='CA', encrypted='false'}'";

    @Test
    public void testEidasResponseParser() {
        EidasAttributes[] p = eIDASResponseParser.parse(testResponse);
        for (EidasAttributes att : p) {
            System.out.println("name " + att.getFriendlyName() + " vlaue: " + att.getValue() + " loa " + att.getLoa());
        }

    }

    @Test
    public void testJSONString() throws JsonProcessingException {
        String expected = "[ {\"friendlyName\": \"familyName\", \"loa\" : \"http://eidas.europa.eu/LoA/low\" , \"value\" :\"cph8\"}, {\"friendlyName\": \"firstName\", \"loa\" : \"http://eidas.europa.eu/LoA/low\" , \"value\" :\"cph8\" }, {\"friendlyName\": \"dateOfBirth\", \"loa\" : \"http://eidas.europa.eu/LoA/low\" , \"value\" :\"1966-01-01\"}, {\"friendlyName\": \"personIdentifier\", \"loa\" : \"http://eidas.europa.eu/LoA/low\" , \"value\" :\"CA/CA/Cph123456\"} ] ";
        EidasAttributes[] map = eIDASResponseParser.parse(testResponse);
        ObjectMapper mapper = new ObjectMapper();

        assertEquals(mapper.writeValueAsString(map), expected.replace(" ", ""));
    }

    @Test
    public void testGetAudiencep() {
        assertEquals(eIDASResponseParser.getTimeStamp(testResponse), "2017-09-16T08:11:21.191Z");
    }

    @Test
    public void testGetTimeStamp() {
        assertEquals(eIDASResponseParser.getAudience(testResponse), "http://138.68.103.237:8090/metadata");
    }

    @Test
    public void testGetIssuer() {
        assertEquals(eIDASResponseParser.getIssuer(testResponse), "http://84.205.248.180:80/EidasNode/ConnectorResponderMetadata");
    }

    @Test
    public void testGetCountry() {
        assertEquals(eIDASResponseParser.getCountry(testResponse), "CA");
    }

}
