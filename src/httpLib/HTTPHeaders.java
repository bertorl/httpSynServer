/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpLib;

import java.util.HashMap;

/**
 *
 * @author berti
 */
public class HTTPHeaders extends HTTPUtils {

    protected static final String  OK = "HTTP/1.1 200 OK";
    protected static final String PARTIAL_CONTENT = "HTTP/1.1 206 Partial Content";
    protected static final String REDIR = "HTTP/1.1 301 Moved Permanently";
    protected static final String NOTMODIFIED = "HTTP/1.1 304 Not Modified";
    protected static final String BADREQUEST = "HTTP/1.1 400 Bad Request";
    protected static final String UNAUTHORIZED = "HTTP/1.1 401 Authorization Required";
    protected static final String FORBIDDEN = "HTTP/1.1 403 Forbidden";
    protected static final String NOTFOUND = "HTTP/1.1 404 Not Found";
    protected static final String NOTALLOWED = "HTTP/1.1 405 Method Not Allowed";
    protected static final String INTERNALERROR = "HTTP/1.1 500 Internal Server Error";
    protected static final String NOTIMPLEMENTED = "HTTP/1.1 501 Not Implemented";
    protected static final String START_HTML = "<!DOCTYPE html>\n<html>\n<body>\n";
    protected static final String FINAL_HTML = "\n</body>\n</html>";
}
