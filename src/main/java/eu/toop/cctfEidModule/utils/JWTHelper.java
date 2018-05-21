/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.toop.cctfEidModule.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.toop.cctfEidModule.service.KeyStoreService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author nikos
 */
public class JWTHelper {

//    @Autowired
    private KeyStoreService keyStore;
    
    public JWTHelper(KeyStoreService keyStore){
        this.keyStore = keyStore;
    
    }
    
    public String generateJWT(Map<String, Object> content) throws JsonProcessingException, UnsupportedEncodingException, IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {

//        ClassPathResource resource = new ClassPathResource("keys/server.jks");
//        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
//        keystore.load(resource.getInputStream(), "jkspassword".toCharArray());

//        Key key = keystore.getKey("jwtkey", "keypassword".toCharArray());
          Key key = keyStore.getJWTSigningKey();

//        Certificate cert = keystore.getCertificate("jwtkey");
//        PublicKey publicKey = cert.getPublicKey();

        ObjectMapper mapper = new ObjectMapper();
        return Jwts.builder()
                .setSubject(mapper.writeValueAsString(content))
                .setIssuedAt(new Date())
                .setIssuer("TOOP_eID_Module")
                .setHeaderParam("alg", "RS256")
                //                .signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
                .signWith(SignatureAlgorithm.RS256, keyStore.getJWTSigningKey())
                .compact();
        
        
    }
    
    
}
