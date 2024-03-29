package com.doubleclick.marktinhome.Views.audio_record_view;

import com.doubleclick.marktinhome.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Varun John on 12 Feb, 2020
 * Github : https://github.com/varunjohn
 */
public class AttachmentOption implements Serializable {

    private int id;
    private String title;
    private int resourceImage;

    public static final int DOCUMENT_ID = 101;
    public static final int CAMERA_ID = 102;
    public static final int GALLERY_ID = 103;
    public static final int AUDIO_ID = 104;
    public static final int LOCATION_ID = 105;
    public static final int CONTACT_ID = 106;
    public static final int VIDEO_ID = 107;

    public static List<AttachmentOption> getDefaultList() {
        List<AttachmentOption> attachmentOptions = new ArrayList<>();
        attachmentOptions.add(new AttachmentOption(DOCUMENT_ID, "Document", R.drawable.ic_attachment_document));
//        attachmentOptions.add(new AttachmentOption(CAMERA_ID, "Camera", R.drawable.ic_attachment_camera));
        attachmentOptions.add(new AttachmentOption(GALLERY_ID, "Gallery", R.drawable.ic_attachment_gallery));
//        attachmentOptions.add(new AttachmentOption(AUDIO_ID, "Audio", R.drawable.ic_attachment_audio));
        attachmentOptions.add(new AttachmentOption(LOCATION_ID, "Location", R.drawable.ic_attachment_location));
        attachmentOptions.add(new AttachmentOption(CONTACT_ID, "Contact", R.drawable.ic_attachment_contact));
        attachmentOptions.add(new AttachmentOption(VIDEO_ID, "Video", R.drawable.ic_video));
        return attachmentOptions;
    }

    public AttachmentOption(int id, String title, int resourceImage) {
        this.id = id;
        this.title = title;
        this.resourceImage = resourceImage;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getResourceImage() {
        return resourceImage;
    }
}
