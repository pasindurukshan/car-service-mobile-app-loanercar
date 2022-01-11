package com.example.car_service_wecarcare;

public class lcMainModel {

    String cartype, delitime, extrachar, fuelexpert, picktime, sertype, surl;

    lcMainModel()
    {

    }

    public lcMainModel(String cartype, String delitime, String extrachar, String fuelexpert, String picktime, String sertype, String surl) {
        this.cartype = cartype;
        this.delitime = delitime;
        this.extrachar = extrachar;
        this.fuelexpert = fuelexpert;
        this.picktime = picktime;
        this.sertype = sertype;
        this.surl = surl;
    }


    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public String getDelitime() {
        return delitime;
    }

    public void setDelitime(String delitime) {
        this.delitime = delitime;
    }

    public String getExtrachar() {
        return extrachar;
    }

    public void setExtrachar(String extrachar) {
        this.extrachar = extrachar;
    }

    public String getFuelexpert() {
        return fuelexpert;
    }

    public void setFuelexpert(String fuelexpert) {
        this.fuelexpert = fuelexpert;
    }

    public String getPicktime() {
        return picktime;
    }

    public void setPicktime(String picktime) {
        this.picktime = picktime;
    }

    public String getSertype() {
        return sertype;
    }

    public void setSertype(String sertype) {
        this.sertype = sertype;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }
}