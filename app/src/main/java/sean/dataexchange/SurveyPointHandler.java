package sean.dataexchange;

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by g6 on 11/8/15.
 */
public class SurveyPointHandler extends Activity {
    private String filename;
    File path;
    File file;
    Context context;

    public SurveyPointHandler(String filename1, Context context) {
        filename = filename1;
        this.context = context;
        path = Environment.getExternalStorageDirectory();
        file = new File(Environment.DIRECTORY_DOCUMENTS, filename);
        System.out.println("file setup: " + file);
    }

    public void saveSurveyPoint(double latitude,
                                   double longitude,
                                   int flagNumber,
                                   String user,
                                   String method) {
        //FileOutputStream fos = null;
        try {
            FileOutputStream  fos = openFileOutput(this.file.getName(), Context.MODE_APPEND);
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
            System.out.println("Point Saved: " + file);

        } catch (Exception e) {

            System.out.println("Error: " + e);
        }
    }
}
