/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.toop.cctfEidModule.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.eidas.sp.SpAuthenticationResponseData;
import eu.eidas.sp.SpEidasSamlTools;
import eu.toop.cctfEidModule.model.pojo.SpResponse;
import eu.toop.cctfEidModule.service.CountryService;
import eu.toop.cctfEidModule.service.EidasPropertiesService;
import eu.toop.cctfEidModule.utils.eIDASResponseParser;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author nikos
 */
@Controller
public class ViewControllers {

    final static String EIDAS_URL = "EIDAS_NODE_URL";
    final static String SP_FAIL_PAGE = "SP_FAIL_PAGE";
    final static String SP_SUCCESS_PAGE = "SP_SUCCESS_PAGE";
    final static String SP_LOGO = System.getenv("SP_LOGO");

    final static Logger LOG = LoggerFactory.getLogger(ViewControllers.class);

    @Autowired
    private CountryService countryServ;

    @Autowired
    private EidasPropertiesService propServ;

    @RequestMapping("/login")
    public ModelAndView loginView(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("login");

        mv.addObject("nodeUrl", SpEidasSamlTools.getNodeUrl());
        mv.addObject("countries", countryServ.getEnabled());
        mv.addObject("spFailPage", System.getenv(SP_FAIL_PAGE));
        mv.addObject("spSuccessPage", System.getenv(SP_SUCCESS_PAGE));
        mv.addObject("logo", SP_LOGO);
        mv.addObject("legal", propServ.getLegalProperties());
        mv.addObject("natural", propServ.getNaturalProperties());

        return mv;
    }

    @RequestMapping(value = "/eidasResponse", method = {RequestMethod.POST, RequestMethod.GET})
    public String eidasResponse(@RequestParam(value = "SAMLResponse", required = false) String samlResponse,
            HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {

        String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRemoteAddr();
        SpAuthenticationResponseData data = SpEidasSamlTools.processResponse(samlResponse, remoteAddress);
        SpResponse resp = new SpResponse();
        resp.setEidasAttributes(eIDASResponseParser.parse(data.getResponseXML()));
        
        ObjectMapper jmap = new ObjectMapper();
        ModelAndView mv = new ModelAndView("success");
        mv.addObject("spUrl", System.getenv(SP_SUCCESS_PAGE));
//        mv.addObject("eidasAttributes", jmap.writeValueAsString(resp));
        return "redirect:"+System.getenv(SP_SUCCESS_PAGE)+"?eidasAttributes="+URLEncoder.encode(jmap.writeValueAsString(resp), "UTF-8");
    }
}
