package sean.dataexchange;

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by g6 on 11/8/15.
 */
public class SurveyPointHandler extends Activity {
    private String filename;
    File path;
    File file;

    public SurveyPointHandler(String filename) {
        path = Environment.getDataDirectory();
        file = new File(path, filename);
    }

    public boolean saveSurveyPoint(double latitude,
                                   double longitude,
                                   int flagNumber,
                                   String user,
                                   String method) {
        try {
            FileOutputStream fos = openFileOutput(this.filename, Context.MODE_APPEND);
            Date date = new Date();
            String string = Double.toString(latitude);
            string += "," + Double.toString(longitude);
            string += "," + Integer.toString(flagNumber);
            string += "," + Long.toString(date.getTime());
            string += "," + user;
            string += "," + method;
            string += "\n";
            fos.write(string.getBytes());
            fos.close();
        } catch (Exception e) {

        }
        return false;
    }
}
