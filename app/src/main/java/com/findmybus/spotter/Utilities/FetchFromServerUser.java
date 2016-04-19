package com.findmybus.spotter.Utilities;

/**
 * Created by alokk on 21/01/16.
 */
public interface FetchFromServerUser {
    void onPreFetch();
    void onFetchCompletion(String string, int id);
}
