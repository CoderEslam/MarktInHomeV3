package com.doubleclick.downloader;



public interface OnDownloadListener {

    void onDownloadComplete();

    void onError(Error error);

}
