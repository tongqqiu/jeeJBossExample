package org.tongqing.jee.security;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.interception.SecurityPrecedence;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.AcceptedByMethod;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.jboss.resteasy.util.HttpResponseCodes;
import org.jboss.seam.solder.logging.Category;
import org.tongqing.jee.rest.error.CredentialsFailedException;
import org.tongqing.jee.util.AuthorizationHeader;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * User: TQiu
 * Date: 3/31/2014
 */
@Provider
@ServerInterceptor
@SecurityPrecedence
@RequestScoped
public class AuthenticationInterceptor
        implements PreProcessInterceptor, AcceptedByMethod
{

    public static final String ERR_UNAUTHORIZED = "Error 401 Unauthorized";
    public static final String ERR_FORBIDDEN = "Error 403 Forbidden";
    public static final String ERR_SERVER = "Error 500 Internal Server Error";
    private static final String ERR_DEFAULT = ERR_SERVER;
    @SuppressWarnings("unused")
    private static final String DECLARING_CLASS = "SecurityService";
    @SuppressWarnings("unused")
    private static final String METHOD_INVOKED = "loginUser";


    private static Map<Integer, String> ResponseMap = new HashMap<Integer, String>();

    static
    {
        ResponseMap.put(HttpResponseCodes.SC_UNAUTHORIZED, ERR_UNAUTHORIZED);
        ResponseMap.put(HttpResponseCodes.SC_FORBIDDEN, ERR_FORBIDDEN);
        ResponseMap.put(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, ERR_SERVER);
    }


    @Inject
    @Category("jeeJBossExample")
    private Logger log;

    public static String getResponse(Integer responseCode)
    {
        String response = null;
        if (responseCode != null)
        {
            response = ResponseMap.get(responseCode);
        }
        if (response == null)
        {
            response = ERR_DEFAULT;
        }
        return response;
    }


    @SuppressWarnings("rawtypes")
    @Override
    public boolean accept(Class declaring, Method method)
    {
        boolean needsAuthentication = false;
        // only need to authenticate if the calling method is @SecureWebService
        if (method.getDeclaringClass().getAnnotation(SecureWebService.class) != null)
        {
            needsAuthentication = true;
        }
        else if (method.getAnnotation(SecureWebService.class) != null)
        {
            needsAuthentication = true;
        }
        return needsAuthentication;
    }

    @Override
    public ServerResponse preProcess(HttpRequest request, ResourceMethod method)
            throws Failure, WebApplicationException
    {
        ServerResponse response = null;
        boolean wasAuthenticated = false;
        int statusCode = HttpResponseCodes.SC_UNAUTHORIZED;
        String responsePrefix = ERR_UNAUTHORIZED;
        try
        {
            // get the user name and password from the HTTP headers
            String userName = null;
            String password = null;
            String[] credentials = AuthorizationHeader.getAuthorizationCredentials(request.getHttpHeaders());
            if (credentials != null)
            {
                userName = (credentials.length > 0 ? credentials[0] : null);
                password = (credentials.length > 1 ? credentials[1] : null);
            }

            if("admin".equals(userName) && "admin".equals(password)) {
                wasAuthenticated = true;
            }
        }
        catch (CredentialsFailedException e)
        {
            this.log.errorv("{} for web service request {}.{}", new Object[] {
                    e.getMessage(),
                    method.getMethod().getDeclaringClass().getSimpleName(),
                    method.getMethod().getName() });
        }
        if (!wasAuthenticated)
        {
            // create authentication failed response
            response = new ServerResponse();
            response.setStatus(statusCode);
            response.setMetadata(AuthorizationHeader.getAuthorizationChallengeHeaders());
            response.setEntity(responsePrefix + ": " + request.getPreprocessedPath());
        }
        return response;
    }

}