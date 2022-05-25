package com.doubleclick.marktinhome.Views.ReadQRCode.journeyapps.barcodescanner.camera;


import com.doubleclick.marktinhome.Views.ReadQRCode.journeyapps.barcodescanner.SourceData;

/**
 * Callback for camera previews.
 */
public interface PreviewCallback {
    void onPreview(SourceData sourceData);
    void onPreviewError(Exception e);
}
