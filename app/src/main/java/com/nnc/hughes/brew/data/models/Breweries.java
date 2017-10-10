
package com.nnc.hughes.brew.data.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Breweries implements Parcelable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("numberOfPages")
    @Expose
    private Integer numberOfPages;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("currentPage")
    @Expose
    private Integer currentPage;
    public final static Parcelable.Creator<Breweries> CREATOR = new Creator<Breweries>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Breweries createFromParcel(Parcel in) {
            return new Breweries(in);
        }

        public Breweries[] newArray(int size) {
            return (new Breweries[size]);
        }

    }
    ;

    protected Breweries(Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.numberOfPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.data, (Datum.class.getClassLoader()));
        this.currentPage = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Breweries() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(numberOfPages);
        dest.writeList(data);
        dest.writeValue(currentPage);
    }

    public int describeContents() {
        return  0;
    }

}
