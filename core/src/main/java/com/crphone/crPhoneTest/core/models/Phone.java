package com.crphone.crPhoneTest.core.models;

public class Phone {
    
    private String name;
    private String processor;
    private String ram;
    private String sd;
    private String camera;
    private String[] networks;
    private String[] images;
    
    public void setName(String name) {
        this.name = name; 
    }
    public String getName() {
        return name;
    }
    
    public void setProcessor(String processor) {
        this.processor = processor; 
    }
    public String getProcessor() {
        return processor;
    }
    
    public void setRam(String ram) {
        this.ram = ram; 
    }
    public String getRam() {
        return ram;
    }
    
    public void setSd(String sd) {
        this.sd = sd; 
    }
    public String getSd() {
        return sd;
    }
    
    public void setCamera(String camera) {
        this.camera = camera; 
    }
    public String getCamera() {
        return camera;
    }
    
    public void setNetworks(String[] networks) {
        this.networks = networks; 
    }
    public String[] getNetworks() {
        return networks;
    }
    
    public void setImages(String[] images) {
        this.images = images; 
    }
    public String[] getImages() {
        return images;
    }
 
}