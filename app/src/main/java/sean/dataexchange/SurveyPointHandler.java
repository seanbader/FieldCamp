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
import java.io.IOException;
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

    public SurveyPointHandler(String filename, Context context) {
        this.filename = filename;
        this.context = context;
        this.path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File dir = new File(this.path, "FieldCamp");
        System.out.print("File Status: ");
        if (!dir.mkdirs()) {
            System.out.println("Already Exists");
        } else {
            System.out.println("Directory Created");
        }

        this.file = new File(dir.getPath(), filename);
        if(!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }
    }

    public void saveSurveyPoint(double latitude,
                                   double longitude,
                                   int flagNumber,
                                   String user,
                                   String method) {
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(this.file, true);
            Date date = new Date();
            String string = Double.toString(latitude);
            string += "," + Double.toString(longitude);
            string += "," + Integer.toString(flagNumber);
            string += "," + Long.toString(date.getTime());
            string += "," + user;
            string += "," + method;
            string += "\n";
            stream.write(string.getBytes());
            System.out.println("Point Saved: " + file);
            stream.close();
        } catch(IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void loadSurveyPoints(String filename) {
        
    }
}
