package org.tongqing.jee.rest.error;

import org.jboss.resteasy.util.HttpResponseCodes;

/**
 * User: TQiu
 * Date: 3/31/2014
 */
public class CredentialsFailedException extends WebServiceException
{

    private static final long serialVersionUID = 4652318930659055692L;

    private static final int DEFAULT_STATUS = HttpResponseCodes.SC_UNAUTHORIZED;


    public CredentialsFailedException()
    {
        super(DEFAULT_STATUS);
    }

    public CredentialsFailedException(String message)
    {
        super(message, DEFAULT_STATUS);
    }

    public CredentialsFailedException(String message, Throwable e)
    {
        super(message, e, DEFAULT_STATUS);
    }


    @Override
    public int getDefaultStatusCode()
    {
        return DEFAULT_STATUS;
    }
}
