
package com.android.doral.retrofit.model.UserApplicationDetails;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class TaxpayerIdNumber {

    @SerializedName("mText")
    @Expose
    private List<String> mText = null;
    @SerializedName("mSpans")
    @Expose
    private List<List<Object>> mSpans = null;
    @SerializedName("mGapStart")
    @Expose
    private int mGapStart;
    @SerializedName("mSpanEnds")
    @Expose
    private List<Integer> mSpanEnds = null;
    @SerializedName("mGapLength")
    @Expose
    private int mGapLength;
    @SerializedName("mSpanCount")
    @Expose
    private int mSpanCount;
    @SerializedName("mSpanFlags")
    @Expose
    private List<Integer> mSpanFlags = null;
    @SerializedName("mSpanStarts")
    @Expose
    private List<Integer> mSpanStarts = null;

    public List<String> getmText() {
        return mText;
    }

    public void setmText(List<String> mText) {
        this.mText = mText;
    }

    public List<List<Object>> getmSpans() {
        return mSpans;
    }

    public void setmSpans(List<List<Object>> mSpans) {
        this.mSpans = mSpans;
    }

    public int getmGapStart() {
        return mGapStart;
    }

    public void setmGapStart(int mGapStart) {
        this.mGapStart = mGapStart;
    }

    public List<Integer> getmSpanEnds() {
        return mSpanEnds;
    }

    public void setmSpanEnds(List<Integer> mSpanEnds) {
        this.mSpanEnds = mSpanEnds;
    }

    public int getmGapLength() {
        return mGapLength;
    }

    public void setmGapLength(int mGapLength) {
        this.mGapLength = mGapLength;
    }

    public int getmSpanCount() {
        return mSpanCount;
    }

    public void setmSpanCount(int mSpanCount) {
        this.mSpanCount = mSpanCount;
    }

    public List<Integer> getmSpanFlags() {
        return mSpanFlags;
    }

    public void setmSpanFlags(List<Integer> mSpanFlags) {
        this.mSpanFlags = mSpanFlags;
    }

    public List<Integer> getmSpanStarts() {
        return mSpanStarts;
    }

    public void setmSpanStarts(List<Integer> mSpanStarts) {
        this.mSpanStarts = mSpanStarts;
    }

}
