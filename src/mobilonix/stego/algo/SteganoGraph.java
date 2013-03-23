package mobilonix.stego.algo;

import java.io.File;
import java.io.FilenameFilter;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;

public class SteganoGraph extends TabActivity {

	//so basically if we want hide an image 
	//so the folloing applciation will have two options, either to encode an image or decode an image
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stego_nav);
        //so even if depreciation occured you still want to use the tabhost acitivty, so forget abotu tab fragment activity
    
    }

  //In an Activity
    private String[] mFileList;
    private File mPath = new File(Environment.getExternalStorageDirectory() + "//yourdir//"); //so you can use the extergnalgetsotrage direoctye ot get the xternal sotregae direoctyr usign the andoird.ios fucntion enviornemnt
    private String mChosenFile;
    private static final String FTYPE = ".txt";    
    private static final int DIALOG_LOAD_FILE = 1000;
//ok the the last thing i was doing
    String TAG = "Android Tab Acitivty";
    private void loadFileList() {
        try {
            mPath.mkdirs(); //this ist he enviorment
        }
        catch(SecurityException e) {
            Log.e(TAG, "unable to write on the sd card " + e.toString());
        }
        if(mPath.exists()) {
            FilenameFilter filter = new FilenameFilter() {//why is file name filter a separate class?
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    return filename.contains(FTYPE) || sel.isDirectory();
                }
            };//he's anon fucntionign filename picker
            mFileList = mPath.list(filter);// and finaly using the list item, mpath is the absoltue path of but heat fdoes the list function fo what oh so if you pass a filer into the mpath search search you should get a list of all the fiels
        }
        else {
            mFileList= new String[0];
        }
    }
//so basically this what we needt
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new Builder(this);

        switch(id) {
            case DIALOG_LOAD_FILE:
                builder.setTitle("Choose your file");
                if(mFileList == null) {
                    Log.e(TAG, "Showing file picker before loading the file list");
                    dialog = builder.create();
                    return dialog;
                }
                builder.setItems(mFileList, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mChosenFile = mFileList[which];
                        //you can do stuff with the file here too
                    }
                });
                break;
        }
        dialog = builder.show();
        return dialog;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_stegano_graph, menu);
        return true;
    }
}
