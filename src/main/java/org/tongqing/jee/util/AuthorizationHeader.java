package org.tongqing.jee.util;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.util.Base64;
import org.tongqing.jee.rest.error.CredentialsFailedException;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

/**
 * User: TQiu
 * Date: 3/31/2014
 */
public abstract class AuthorizationHeader
{

    private static final String ERR_MISSING_HEADERS = "no HTTP headers found in web service request";
    private static final String ERR_MISSING_AUTHORIZATION_HEADERS = "no " + HttpHeaders.AUTHORIZATION + " header found in web service request";
    private static final String ERR_DECODING_AUTHORIZATION_HEADER = "error decoding {0} header - {1}";


    public static MultivaluedMap<String, Object> getAuthorizationChallengeHeaders()
    {
        MultivaluedMap<String, Object> headers = new Headers<Object>();
        headers.add("Content-Type", "text/plain");
        headers.add("WWW-Authenticate", "BASIC realm=\"users\"");
        return headers;
    }

    public static String[] getAuthorizationCredentials(HttpHeaders headers)
            throws CredentialsFailedException
    {
        String[] credentials = null;
        if (headers == null)
        {
            throw new CredentialsFailedException(ERR_MISSING_HEADERS);
        }
        String authorization = null;
        List<String> authorizationHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        if ((authorizationHeaders != null) && (!authorizationHeaders.isEmpty()))
        {
            authorization = authorizationHeaders.get(0);
        }
        if (authorization == null)
        {
            throw new CredentialsFailedException(ERR_MISSING_AUTHORIZATION_HEADERS);
        }
        // get the Basic authentication credentials
        int basic = authorization.indexOf("Basic ");
        authorization = authorization.substring(basic + "Basic ".length());
        // get the user name and password from the header
        try
        {
            String decoded = new String(Base64.decode(authorization));
            int colon = decoded.indexOf(":");
            if (colon < 0)
            {
                credentials = new String[] { decoded };		// user name only
            }
            else
            {
                credentials = new String[] {
                        decoded.substring(0, colon),			// user name
                        decoded.substring(colon + 1)			// password
                };
            }
        }
        catch (IOException e)
        {
            throw new CredentialsFailedException(
                    MessageFormat.format(ERR_DECODING_AUTHORIZATION_HEADER, HttpHeaders.AUTHORIZATION,
                            e.getLocalizedMessage()),
                    e);
        }
        return credentials;
    }

    public static String buildAuthorizationHeader(String userName, String password)
    {
        return "Basic " + new String(Base64.encodeBytes((userName + ":" + password).getBytes()));
    }

}
