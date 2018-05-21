/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.toop.cctfEidModule;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.toop.cctfEidModule.model.pojo.AuthTokenBuilder;
import eu.toop.cctfEidModule.service.KeyStoreService;
import eu.toop.cctfEidModule.service.impl.KeyStoreServiceImpl;
import eu.toop.cctfEidModule.utils.JWTHelper;
import io.jsonwebtoken.Jwts;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author nikos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestJWTHelper {

    private Map<String, Object> contents;
    private Key key;

    @Configuration
    static class Config {

        @Bean
        public KeyStoreService jwtKeyStore() throws KeyStoreException, IOException, FileNotFoundException, FileNotFoundException, NoSuchAlgorithmException, NoSuchAlgorithmException, NoSuchAlgorithmException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException {
            return new KeyStoreServiceImpl();
        }
    }

    @Autowired
    KeyStoreService keyServ;

    @Before
    public void before() throws JsonProcessingException, IOException, KeyStoreException, UnsupportedEncodingException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, NoSuchAlgorithmException, CertificateException {
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

        ClassPathResource resource = new ClassPathResource("keys/server.jks");
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(resource.getInputStream(), "jkspassword".toCharArray());
        key = keystore.getKey("jwtkey", "keypassword".toCharArray());
    }

    @Test
    public void testGenerateJWT() throws UnsupportedEncodingException, IOException, JsonProcessingException, JsonProcessingException, KeyStoreException, KeyStoreException, NoSuchAlgorithmException, NoSuchAlgorithmException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException {
        String expectedSubject = "{\"dcId\":\"dcId\",\"attr2\":\"val2\",\"consent\":[\"cons1\",\"cons2\"],\"att1\":\"val1\"}";
        JWTHelper jwtHelper = new JWTHelper(keyServ);
        String compactJws = jwtHelper.generateJWT(contents);
        String decryptedSubject = Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getBody().getSubject();
        System.out.println(compactJws);
        System.out.println(decryptedSubject);
        assertEquals(decryptedSubject, expectedSubject);

    }

}
