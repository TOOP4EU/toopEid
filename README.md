# TOOP eIDAS loginWebApp


     - EIDAS_PROPERTIES=http://eidas.europa.eu/attributes/naturalperson/CurrentFamilyName,http://eidas.europa.eu/attributes/naturalperson/CurrentGivenName,http://eidas.europa.eu/attributes/naturalperson/DateOfBirth,http://eidas.europa.eu/attributes/naturalperson/PersonIdentifier
      - SP_FAIL_PAGE=https://www.google.com
      - SP_SUCCESS_PAGE=http://138.68.103.237/loginSuccess
      - SP_METADATA_URL=http://138.68.103.237:9090/metadata
      - SP_RETURN_URL=http://138.68.103.237:9090/eidasResponse
      - SP_LOGO=http://trc.aiest.org/wp-content/uploads/2013/04/university-of-t$
      - SP_CONFIG_REPOSITORY=/configEidas/
    volumes:
      - /configEidas:/configEidas
# toopEid
