package com.mutti.directions;

import com.daimler.moovel.domain.ProviderServiceType;
import com.daimler.moovel.domain.provider.BaseProviderConfig;
import com.daimler.moovel.domain.provider.ConnectorProperties;


/**
 * The config class for the provider google.
 *
 * @author Anatoli Trockmann
 */
public class GoogleDirectionsProviderConfig extends BaseProviderConfig {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4535545328151307801L;

    /** The Constant CLIENT_ID. */
    private static final String CLIENT_ID = "clientId";

    /** The Constant CRYPTO_KEY. */
    private static final String CRYPTO_KEY = "cryptoKey";

    /**
     * the constructor.
     *
     * @param baseConfig            the {@link BaseProviderConfig}
     */
    public GoogleDirectionsProviderConfig(BaseProviderConfig baseConfig) {
        super(baseConfig);
    }

    /**
     * Get client id based on the {@link ConnectorProperties}.
     *
     * @param serviceType            the connectorProperties with the id of serviceType
     * @return the ClientID
     */
    public String getClientId(ProviderServiceType serviceType) {
        return getPropertyMap(serviceType).get(CLIENT_ID);
    }

    /**
     * Gets the crypto key.
     *
     * @param serviceType the service type
     * @return the crypto key
     */
    public String getCryptoKey(ProviderServiceType serviceType) {
        return getPropertyMap(serviceType).get(CRYPTO_KEY);
    }
}
