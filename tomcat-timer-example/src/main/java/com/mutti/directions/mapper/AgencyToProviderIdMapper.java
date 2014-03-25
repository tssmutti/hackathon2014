package com.mutti.directions.mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Mapping between agnecy names and provider ids.
 * 
 * @author Christian Bergs
 */
public final class AgencyToProviderIdMapper {

      /** The Constant PROVIDER_ID_UNKNOWN. */
    private static final String PROVIDER_ID_UNKNOWN = "unknown";

    /** The Constant AGENCY_TO_PROVIDER_ID_MAP. */
    private static final Map<String, String> AGENCY_TO_PROVIDER_ID_MAP = new HashMap<String, String>();

    static {
        AGENCY_TO_PROVIDER_ID_MAP.put("MVG - Münchner Verkehrsgesellschaft mbH", "mvv");
        AGENCY_TO_PROVIDER_ID_MAP.put("Berliner Verkehrsbetriebe", "vbb");

        AGENCY_TO_PROVIDER_ID_MAP.put("S-Bahn Berlin GmbH", "vbb");
        AGENCY_TO_PROVIDER_ID_MAP.put("Verkehrsverbund Großraum Nürnberg GmbH", "vgn");

        AGENCY_TO_PROVIDER_ID_MAP.put("Deutsche Bahn AG", "db");
        // vgl. http://de.wikipedia.org/wiki/Verkehrsverbund_Rhein-Ruhr#Verkehrsunternehmen
        AGENCY_TO_PROVIDER_ID_MAP.put("Rheinbahn AG", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Abellio Rail NRW GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Bahnen der Stadt Monheim GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Bochum-Gelsenkirchener Straßenbahnen AG", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("DB Regio NRW GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("DSW21", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Duisburger Verkehrsgesellschaft AG", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Keolis S.A.", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Essener Verkehrs-AG", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Hagener Straßenbahn AG", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Kreisverkehrsgesellschaft Mettmann mbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Mülheimer Verkehrs Gesellschaft mbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Niederrheinische Verkehrsbetriebe AG", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Look Busreisen GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("NEW mobil und aktiv Mönchengladbach", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("NEW mobil und aktiv Viersen GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("NordWestBahn GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Regiobahn GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Regionalverkehr Niederrhein GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("StadtBus Dormagen GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Stadtwerke Neuss GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Stadtwerke Oberhausen GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Verkehrsabteilung der Stadtwerke Solingen GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Straßenbahn Herne – Castrop-Rauxel GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("SWK MOBIL GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Stadtwerke Remscheid GmbH Verkehrsbetrieb", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Verkehrsgesellschaft Hilden GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Verkehrsgesellschaft der Stadt Velbert mbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Verkehrsgesellschaft Ennepe-Ruhr mbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("Vestische Straßenbahnen GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("West-Bus GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("WSW mobil GmbH", "vrr");
        AGENCY_TO_PROVIDER_ID_MAP.put("VVS", "vvs");
    }

    /**
     * Returns the appropriate provider id by a given agency name.
     * 
     * @param agency
     *            The agency's name
     * @return The provider id
     */
    public static String getProviderIdByAgency(String agency) {
        String providerId = PROVIDER_ID_UNKNOWN;
        if (AGENCY_TO_PROVIDER_ID_MAP.get(agency) != null) {
            providerId = AGENCY_TO_PROVIDER_ID_MAP.get(agency);
        } 
        return providerId;
    }

}
