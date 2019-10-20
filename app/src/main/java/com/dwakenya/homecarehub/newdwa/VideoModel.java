package com.dwakenya.homecarehub.newdwa;

public class VideoModel {
    String vimage;
    String vname;
    String video;
    
    public VideoModel() {
    }
    
    public VideoModel(String vimage, String vname, String video) {
        this.vimage = vimage;
        this.vname = vname;
        this.video = video;
    }
    
    public String getVimage() {
        return vimage;
    }
    
    public void setVimage(String vimage) {
        this.vimage = vimage;
    }
    
    public String getVname() {
        return vname;
    }
    
    public void setVname(String vname) {
        this.vname = vname;
    }
    
    public String getVideo() {
        return video;
    }
    
    public void setVideo(String video) {
        this.video = video;
    }
}
