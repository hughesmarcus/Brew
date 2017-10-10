
package com.nnc.hughes.brew.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("mailingListUrl")
    @Expose
    private String mailingListUrl;
    @SerializedName("updateDate")
    @Expose
    private String updateDate;
    @SerializedName("images")
    @Expose
    private Images images;
    @SerializedName("established")
    @Expose
    private String established;
    @SerializedName("isOrganic")
    @Expose
    private String isOrganic;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusDisplay")
    @Expose
    private String statusDisplay;
    public final static Parcelable.Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
    ;

    protected Datum(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.createDate = ((String) in.readValue((String.class.getClassLoader())));
        this.mailingListUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.updateDate = ((String) in.readValue((String.class.getClassLoader())));
        this.images = ((Images) in.readValue((Images.class.getClassLoader())));
        this.established = ((String) in.readValue((String.class.getClassLoader())));
        this.isOrganic = ((String) in.readValue((String.class.getClassLoader())));
        this.website = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.statusDisplay = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Datum() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getMailingListUrl() {
        return mailingListUrl;
    }

    public void setMailingListUrl(String mailingListUrl) {
        this.mailingListUrl = mailingListUrl;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getEstablished() {
        return established;
    }

    public void setEstablished(String established) {
        this.established = established;
    }

    public String getIsOrganic() {
        return isOrganic;
    }

    public void setIsOrganic(String isOrganic) {
        this.isOrganic = isOrganic;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDisplay() {
        return statusDisplay;
    }

    public void setStatusDisplay(String statusDisplay) {
        this.statusDisplay = statusDisplay;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(description);
        dest.writeValue(name);
        dest.writeValue(createDate);
        dest.writeValue(mailingListUrl);
        dest.writeValue(updateDate);
        dest.writeValue(images);
        dest.writeValue(established);
        dest.writeValue(isOrganic);
        dest.writeValue(website);
        dest.writeValue(status);
        dest.writeValue(statusDisplay);
    }

    public int describeContents() {
        return  0;
    }

}
