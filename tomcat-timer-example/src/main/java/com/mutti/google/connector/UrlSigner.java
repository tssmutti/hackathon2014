/*
 * Copyright © 2012 Daimler TSS. All Rights Reserved.
 *
 * Reproduction or transmission in whole or in part, in any form or by any
 * means, is prohibited without the prior written consent of the copyright
 * owner.
 * 
 * Created on: 05.06.2012
 * Created by: OMAGAZO
 * Last modified on: $Date: 2008/10/01 09:06:27CEST $
 * Last modified by: $Author: Hardt, Dennis (dhardt7) 
 */

package com.mutti.google.connector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.google.api.client.util.Base64;

public class UrlSigner {

    // This variable stores the binary key, which is computed from the string
    // (Base64) key
    private byte[] key = null;
    
    public UrlSigner(String keyString) throws IOException {
        // Convert the key from 'web safe' base 64 to binary
        String stripped  = keyString.replace('-', '+');
        stripped = stripped.replace('_', '/');
        key = Base64.decodeBase64(stripped);
    }

    public String signRequest(String resource) throws NoSuchAlgorithmException, InvalidKeyException,
            UnsupportedEncodingException, URISyntaxException {

        // Get an HMAC-SHA1 signing key from the raw key bytes
        SecretKeySpec sha1Key = new SecretKeySpec(key, "HmacSHA1");

        // Get an HMAC-SHA1 Mac instance and initialize it with the HMAC-SHA1
        // key
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(sha1Key);

        // compute the binary signature for the request
        byte[] sigBytes = mac.doFinal(resource.getBytes());

        // base 64 encode the binary signature
        String signature = Base64.encodeBase64String(sigBytes);

        // convert the signature to 'web safe' base 64
        signature = signature.replace('+', '-');
        signature = signature.replace('/', '_');

        return "&signature=" + signature;
    }
}
