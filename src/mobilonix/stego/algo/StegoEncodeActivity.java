package mobilonix.stego.algo;

import java.io.ByteArrayOutputStream;
import java.util.BitSet;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class StegoEncodeActivity extends Activity {

	
	Button encodeImageButton;
	ImageView encodingImage;
	EditText encodingText;
	Button chooseFileButton;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_stego_encoder); //se create a serpate acitivty which inflates all of the individual layouts,
	      
	        encodeImageButton = (Button)findViewById(R.id.encode_text_button);
	        chooseFileButton = (Button)findViewById(R.id.choose_image_button);
	        encodingImage = (ImageView)findViewById(R.id.encoding_image);
	        encodingText = (EditText)findViewById(R.id.encode_text_field);
	        //test
	        
	        encodeImageButton.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					encodeImage();
				}
			});
	        
	        chooseFileButton.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
	        
	        
	        //i want to do some ajax calles in my android sctipts
	        //what can i do ajax calles with in android
	        //i can worry abotu ajax calles later for now I want to
	    }
	//now have to tak the bits ans add it to each msb
	   String convertTextToBits(String s) {
		    
		   char[] cArray=s.toCharArray(); //c to char array  so yiou concert thre string to char arat and then for eahc char in the char aeeay  you keep appending to the stirng

		    StringBuilder sb=new StringBuilder();

		    for(char c:cArray)
		    {
		        String cBinaryString=Integer.toBinaryString((int)c);//then usign th einteger wrapper you can you can use the toBinaryString method to conver a char to a binary string string
		        sb.append(cBinaryString);
		    }
		    	
		    return sb.toString();
		   
	   }
	 
	   Handler handler = new Handler();
	   ByteArrayOutputStream bos = new ByteArrayOutputStream();
	   Bitmap bitmap;
	   byte[] bitmapdata;
	   String binaryEncodedString = "";
	 	public void encodeImage() {
	 		//what do you do with encoding image
	 		Toast.makeText(this, convertTextToBits(encodingText.getText().toString()), Toast.LENGTH_LONG).show();
	 		
	        new Thread(new Runnable() {

				public void run() {
					
					handler.post(new Runnable() {

						public void run() {
							// TODO Auto-generated method stub
							//need to change the status of the button
							encodeImageButton.setText("Encode Image (BUSY)");
						}
						
					});
					
					bos = new ByteArrayOutputStream(); //we use a byte arrayoutput stream to get th ebitmap data
			        bitmap = ((BitmapDrawable)encodingImage.getDrawable()).getBitmap();
			        bitmap.compress(CompressFormat.PNG, 0, bos); //so why do dyou want ot have to use compress for sure, you are writign a compressed verison fo the data can you jsut write to an oautptu stream withotut hte compress fucntion
			        bitmapdata = bos.toByteArray();//bitmap data must finsih
			 	 	int charBitIndex = 0;
			 	 	int charIndex = 0;
			 	 	
					for (int i = 0; i < bitmapdata.length; i++) {
				 	    	String binString = Integer.toBinaryString(Integer.valueOf(Byte.toString(bitmapdata[i])));
						   
				 	    	Log.v("Image Data Byte " + i,binString);
				 	    	if (charBitIndex < 8) {
				 	    		binaryEncodedString += binString.charAt((int)(binString.length() - 1));
				 	    	} else {
				 	    		charBitIndex = 0;
				 	    		binaryEncodedString += " ";
				 	    	}
				 	    	charBitIndex++;
					}
					handler.post(new Runnable() {

						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(StegoEncodeActivity.this, binaryEncodedString, Toast.LENGTH_LONG).show();//this text
							encodeImageButton.setText("Encode Image");
						}
						
					});
					
				}
	        }).start();
	        
	       
	      //  Toast.makeText(this, bitmapdata., Toast.LENGTH_LONG).show();
	       //this has to be achar seqeunce what do I do with this char seqeunce
	        
	        
	        //for each of the bits ibt the byte array array add the  hte each bit to the lsb in timage data or conver the lsb into image data, but how can I teell if somethign is encoded
	      //
	        ///so here we encode text in most significant bytes
	 	}
	 	
	 
}
