package sean.dataexchange;

import android.util.Log;

import java.util.Date;

/**
 * Created by g6 on 11/19/15.
 */
public class SurveyPoint implements Comparable<SurveyPoint> {

    private long time;
    private double latitude;
    private double longitude;
    private int flagNumber;
    private String user;
    private String method;

    public SurveyPoint() {
        Date date = new Date();
        this.time = date.getTime();
    }

    public SurveyPoint(long time) {
        this.time = time;
    }

    public SurveyPoint(String line) {
        this.fromString(line);
    }

    public long getTime() {
        return time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getFlagNumber() {
        return flagNumber;
    }

    public void setFlagNumber(int flagNumber) {
        this.flagNumber = flagNumber;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void fromString(String sp) {
        String[] vals = sp.split(",");

        if (vals.length != 6) {
            Log.e("Survey Point Error", "Invalid number of arguemants");
        } else {
            this.time = Long.valueOf(vals[0]);
            this.setLatitude(Double.valueOf(vals[1]));
            this.setLongitude(Double.valueOf(vals[2]));
            this.setFlagNumber(Integer.valueOf(vals[3]));
            this.setUser(vals[4]);
            this.setMethod(vals[5]);
        }
    }

    @Override
    public String toString() {
        String string = Long.toString(this.getTime());
        string += "," + Double.toString(this.getLatitude());
        string += "," + Double.toString(this.getLongitude());
        string += "," + Integer.toString(this.getFlagNumber());
        string += "," + this.getUser();
        string += "," + this.getMethod();
        string += "\n";
        return string;
    }

    @Override
    public int compareTo(SurveyPoint sp) {
        if(sp.getTime() < this.getTime()) {
            return -1;
        } else if (sp.getTime() == this.getTime()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SurveyPoint that = (SurveyPoint) o;

        return time == that.time;

    }

    @Override
    public int hashCode() {
        return (int) (time ^ (time >>> 32));
    }
}
