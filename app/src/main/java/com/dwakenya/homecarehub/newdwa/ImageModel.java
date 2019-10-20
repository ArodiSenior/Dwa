package com.dwakenya.homecarehub.newdwa;

public class ImageModel {
    String vimage;
    String vname;
    
    public ImageModel() {
    }
    
    public ImageModel(String vimage, String vname) {
        this.vimage = vimage;
        this.vname = vname;
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
}
