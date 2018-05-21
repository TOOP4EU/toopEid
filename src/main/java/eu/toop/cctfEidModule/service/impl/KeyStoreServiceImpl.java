/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.toop.cctfEidModule.service.impl;

import eu.toop.cctfEidModule.service.KeyStoreService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author nikos
 */
public class KeyStoreServiceImpl implements KeyStoreService{

    private final String jwtCertPath;
    private final String keyPass;
    private final String storePass;
    private final String keyAlias;
    
    private KeyStore keystore;

    public KeyStoreServiceImpl() throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        jwtCertPath = System.getenv("SP_JWT_CERT");
        keyPass = System.getenv("KEY_PASS");
        storePass = System.getenv("STORE_PASS");
        keyAlias = System.getenv("CERT_ALIAS");
        
        keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        if (!StringUtils.isEmpty(jwtCertPath) && !StringUtils.isEmpty(keyPass) 
                && !StringUtils.isEmpty(storePass) && !StringUtils.isEmpty(keyAlias)) {
            File jwtCertFile = new File(jwtCertPath);
            InputStream certIS = new FileInputStream(jwtCertFile);
            keystore.load(certIS, "jkspassword".toCharArray());
//            Key key = keystore.getKey("jwtkey", "keypassword".toCharArray());
        }

    }
    
    public Key getJWTSigningKey() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException{
        //"jwtkey"
        return keystore.getKey(keyAlias, "keypassword".toCharArray());
    }
    
    public PublicKey getJWTPublicKey() throws KeyStoreException{
        //"jwtkey"
        Certificate cert = keystore.getCertificate(keyAlias);
        return cert.getPublicKey();
    }
    

}
