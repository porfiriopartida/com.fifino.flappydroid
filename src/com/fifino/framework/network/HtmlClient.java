package com.fifino.framework.network;

/**
 * A HTTP get-post interface
 * 
 * @author porfiriopartida
 *
 */
public interface HtmlClient {
    public String post(String url);

    public String get(String url);

    public String post(String url, String[] args);

    public String get(String url, String[] args);
}