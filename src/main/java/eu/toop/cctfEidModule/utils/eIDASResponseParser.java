/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.toop.cctfEidModule.utils;

import eu.toop.cctfEidModule.model.pojo.EidasAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nikos
 */
public class eIDASResponseParser {

    private final static Pattern namePattern = Pattern.compile("friendlyName='(.*?)'");
    private final static Pattern valuePattern = Pattern.compile("=\\[(.*?)\\]");

    public static EidasAttributes[] parse(String eIDASResponse) throws IndexOutOfBoundsException {

        List<EidasAttributes> result = new ArrayList<EidasAttributes>();

        String loaPart = eIDASResponse.split("levelOfAssurance=")[1].split(",")[0].replace("'", "");
        String attributePart = eIDASResponse.split("attributes='")[1];
        String[] attributesStrings = attributePart.split("AttributeDefinition");

        Arrays.stream(attributesStrings).filter(string -> {
            return string.indexOf("=") > 0;
        }).filter(string -> {
            return namePattern.matcher(string).find();
        }).forEach(attrString -> {
//            String value = attrString.split("=\\[(.*?)\\]")[1].replace("[", "").replace("]", "");

            Matcher nameMatcher = namePattern.matcher(attrString);
            Matcher valueMatcher = valuePattern.matcher(attrString);

            if (valueMatcher.find() && nameMatcher.find()) {
                String name = nameMatcher.group(1);

                char c[] = name.toCharArray();
                c[0] = Character.toLowerCase(c[0]);
                name = new String(c);

                String value = valueMatcher.group(1);

                result.add(new EidasAttributes(name, loaPart, value));

            }
        });

        return result.stream().toArray(EidasAttributes[]::new);
    }

    public static String getTimeStamp(String data) {
        return data.split("notBefore='")[1].split(",")[0].replace("'", "");
    }

    public static String getAudience(String data) {
        return data.split("audienceRestriction='")[1].split(",")[0].replace("'", "");
    }

    public static String getIssuer(String data) {
        return data.split("issuer='")[1].split(",")[0].replace("'", "");
    }

    public static String getCountry(String data) {
        return data.split("country='")[1].split(",")[0].replace("'", "");
    }

}
