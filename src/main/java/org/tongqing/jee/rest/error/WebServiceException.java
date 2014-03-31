package org.tongqing.jee.rest.error;

import org.jboss.resteasy.util.HttpResponseCodes;

/**
 * User: TQiu
 * Date: 3/31/2014
 */
public class WebServiceException
        extends RuntimeException
{

    private static final long serialVersionUID = 251272607514398240L;

    private static final int DEFAULT_STATUS = HttpResponseCodes.SC_INTERNAL_SERVER_ERROR;


    private int statusCode = DEFAULT_STATUS;


    public WebServiceException()
    {
        this.statusCode = this.getDefaultStatusCode();
    }

    public WebServiceException(String message, int statusCode)
    {
        super(message);
        this.statusCode = statusCode;
    }

    public WebServiceException(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public WebServiceException(String message)
    {
        super(message);
        this.statusCode = this.getDefaultStatusCode();
    }

    public WebServiceException(String message, Throwable e)
    {
        super(message, e);
        this.statusCode = this.getDefaultStatusCode();
    }

    public WebServiceException(String message, Throwable e, int statusCode)
    {
        super(message, e);
        this.statusCode = statusCode;
    }

    public WebServiceException(Throwable e)
    {
        super(e);
        this.statusCode = this.getDefaultStatusCode();
    }


    public int getStatusCode()
    {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public int getDefaultStatusCode()
    {
        return DEFAULT_STATUS;
    }

    public String getEntity()
    {
        // default implementation just uses the exception message
        return this.getMessage();
    }

}
