package sean.dataexchange;

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by g6 on 11/8/15.
 */
public class SurveyPointHandler extends Activity {
    private String filename;
    private Set<SurveyPoint> surveyPoints = new TreeSet<SurveyPoint>();
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

    public void recordSurveyPoint(double latitude,
                                   double longitude,
                                   int flagNumber,
                                   String user,
                                   String method) {
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(this.file, true);

            // Create a new survey point
            SurveyPoint sp = new SurveyPoint();
            sp.setLatitude(latitude);
            sp.setLongitude(longitude);
            sp.setFlagNumber(flagNumber);
            sp.setUser(user);
            sp.setMethod(method);
            //stream.write(sp.toString().getBytes());

            // Add the survey point to the set
            this.surveyPoints.add(sp);

            // Write the new survey point to file
            stream.write(sp.toString().getBytes());
            System.out.println("Point Saved: " + file);
            stream.close();

        } catch(IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void loadSurveyPoints() {
        FileInputStream is;
        BufferedReader reader;

        if (this.file.exists()) {
            try {
                is = new FileInputStream(this.file);
                reader = new BufferedReader(new InputStreamReader(is));
                String line = "Hello World!";
                while(line != null){
                    line = reader.readLine();
                    SurveyPoint sp = new SurveyPoint();
                    sp.fromString(line);
                    this.surveyPoints.add(sp);
                }
            } catch(Exception e) {
                Log.e("File Open Error", "Error opening "+this.file.toString());
            }
        }
    }

    // Added by Sean - need a function to access the data file and return it as a string to be sent
    //
    public String accessSurveyPoints() {
        FileInputStream is;
        BufferedReader reader;
        String out="";

        if (this.file.exists()) {
            try {
                is = new FileInputStream(this.file);
                reader = new BufferedReader(new InputStreamReader(is));
                String line = "Hello World!";
                while(line != null){
                    line = reader.readLine();

                    out = out + line + ":";
                }
            } catch(Exception e) {
                Log.e("File Open Error", "Error opening "+this.file.toString());
            }
        }
        System.out.println(out);
        return out;
    }

    public void saveSurveyPoints() {
        FileOutputStream stream = null;
        try {

            for (SurveyPoint sp : this.surveyPoints) {
                stream = new FileOutputStream(this.file,true);

                Log.e("sp to file: ", sp.toString());
                stream.write(sp.toString().getBytes());

                stream.close();
            }

        } catch(Exception e) {
            Log.e("File Output Error", "Error writing to "+this.file.toString());
        }
    }


    public static TreeSet<SurveyPoint> parseSurveyPoints(String points) {
        //System.out.println(points);
        TreeSet<SurveyPoint> pointSet = new TreeSet<SurveyPoint>();
        String[] lines = points.split("\n");
        for (String line : lines) {
            SurveyPoint sp = new SurveyPoint(line);
            pointSet.add(sp);
        }
        return pointSet;
    }

    public void addTransmittedPoints(String points) {
        String[] lines = points.split(":");

        int min = 0;
        int max = lines.length - 1;

        while ( min < max ) {
            SurveyPoint sp = new SurveyPoint();
            Log.e("sp added: ", lines[min]);

            sp.fromString(lines[min]);
            surveyPoints.add(sp);
            min = min + 1;
        }

    }



    public void mergeSurveyPoints(Set<SurveyPoint> points) {
        this.surveyPoints.addAll(points);
    }
}
