/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.toop.cctfEidModule.controllers;

import eu.eidas.sp.SpAuthenticationRequestData;
import eu.eidas.sp.SpEidasSamlTools;
import eu.eidas.sp.metadata.GenerateMetadataAction;
import eu.toop.cctfEidModule.service.EidasPropertiesService;
import eu.toop.cctfEidModule.service.KeyStoreService;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author nikos
 */
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
public class RestControllers {

    @Autowired
    private EidasPropertiesService propServ;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    KeyStoreService keyServ;

    private final static Logger LOG = LoggerFactory.getLogger(RestControllers.class);

//    private final static String SP_BACKEND = System.getenv("SP_BACKEND");
    private final static String SP_COUNTRY = System.getenv("SP_COUNTRY");
    private final static String SP_SUCCESS_PAGE = System.getenv("SP_SUCCESS_PAGE");
    private final static String SP_FAIL_PAGE = System.getenv("SP_FAIL_PAGE");
    private final static String DC_ID = System.getenv("DC_ID");

    @RequestMapping(value = "/metadata", method = {RequestMethod.POST, RequestMethod.GET}, produces = {"application/xml"})
    public @ResponseBody
    String metadata() {
        GenerateMetadataAction metaData = new GenerateMetadataAction();
        return metaData.generateMetadata().trim();
    }

    @RequestMapping(value = "/generateSAMLToken", method = {RequestMethod.GET})
    public ResponseEntity getSAMLToken(@RequestParam(value = "citizenCountry", required = true) String citizenCountry) {

        String serviceProviderCountry = SP_COUNTRY;
        try {
            ArrayList<String> pal = new ArrayList();
            pal.addAll(propServ.getEidasProperties());
            SpAuthenticationRequestData data
                    = SpEidasSamlTools.generateEIDASRequest(pal, citizenCountry, serviceProviderCountry);

            return ResponseEntity.ok(data.getSaml());
        } catch (NullPointerException e) {
            LOG.error("NulPointer Caught", e);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
