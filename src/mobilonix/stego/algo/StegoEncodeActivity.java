package mobilonix.stego.algo;

import java.io.ByteArrayOutputStream;
import java.util.BitSet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
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
		    	if (c == '.' || c == '!' || c == '+' || c == '!' || c == '?' || c == ',' || c == ' ')
		    		c = '_';
		        String cBinaryString=Integer.toBinaryString((int)c);//then usign th einteger wrapper you can you can use the toBinaryString method to conver a char to a binary string string
		        sb.append(cBinaryString);
		    }
		    return sb.toString();
	   }
	   //this function uses a reule for embdeding text into the image
	   String decodeImage(Bitmap bitmap) {
		   String temp = "";
		   String value = "";
		   boolean foundEnd = false;
		   for (int i = 0; ((i < bitmap.getWidth()) && !foundEnd); i++) {
			   for (int j = 0; ((j < bitmap.getHeight()) && !foundEnd); j ++) {
				   
					int rgb = bitmap.getPixel(i, j);
	        		int r = Color.red(rgb) ;
	        		int g = Color.green(rgb) ;
	        		int b = Color.blue(rgb) ;
	        		if (r == 100) {
	        			temp += "1";
	        		} else if (r == 255) {
	        			temp += "0";
	        		} else {
	        			temp += "0";
	        		}
				   
	        		Log.v("R value: ","R: " + r);
	        		if (temp.length() == 7) {
	        			value += (char)Integer.parseInt(temp,2);
	        			Log.v("Word"," " + (char)Integer.parseInt(temp,2));
	        			temp = "";
	        		}
	        		
	        		if (r == 0 && b == 0 && g == 0) {
	        			foundEnd = true;
	        		}
	        		
			   }
		   }
		   Log.v("SENTANCE",value);
		   return value;
		   
	   }
	   
	   int encodingIndex = 0;
	   String encodingString = "";
	   Handler handler = new Handler();
	   ByteArrayOutputStream bos = new ByteArrayOutputStream();
	   Bitmap bitmap;
	   byte[] bitmapdata;
	   String binaryEncodedString = "";
	 	public void encodeImage() {
	 		//what do you do with encoding image
	 		encodingString = convertTextToBits(encodingText.getText().toString());
	 		Toast.makeText(this, encodingString, Toast.LENGTH_LONG).show();
	 		
	        new Thread(new Runnable() {

				@SuppressLint("NewApi")
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
			        bitmap.compress(CompressFormat.PNG, 0, new ByteArrayOutputStream());
			        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true); //make  amutable copy of th ebitmap why isn't it mutable by default
			        
			        bitmap.setHasAlpha(true);
			        for (int i = 0; ( (i < bitmap.getWidth()) && (encodingIndex < encodingString.length())) ; i++) {
			        	for (int j = 0; ( (j < bitmap.getHeight()) && (encodingIndex < encodingString.length())); j++) {
			        		
			        		int rgb = bitmap.getPixel(i, j);
			        		int r = Color.red(rgb);
			        		int g = Color.green(rgb);
			        		int b = Color.blue(rgb);
			        		Log.v("Premodified Bitmap Color ","R "  + r + " B " + b + " G " + g);
			        		
			        		
			        		if (encodingString.charAt(encodingIndex) == '1') {
			        			r = 100;
			        		} else 
			        		{
			        			r = 255;
			        		}
			        		Log.v("R", "R: " + r);
			        		Log.v("ENCODING INDEX: ", "INDEX: " + encodingIndex + " LENGTH: " + encodingString.length());
			        		encodingIndex++;
			        		bitmap.setPixel(i, j, Color.rgb(r, g, b));
			        		Log.v("POSTmodified Bitmap Color ","R "  + Color.red(bitmap.getPixel(i, j)) + " B " + Color.blue(bitmap.getPixel(i, j)) + " G " + Color.green(bitmap.getPixel(i, j)));
				        	
			        		if (encodingIndex == encodingString.length()) {
			        			
			        			Log.v("SYSTEM: ", "SETTING A ZERO VALUE PIXEL");
			        			bitmap.setPixel(i, j, Color.BLACK);
			        			Log.v("GETTING END COLOR ","R "  + Color.red(bitmap.getPixel(i, j)) + " B " + Color.blue(bitmap.getPixel(i, j)) + " G " + Color.green(bitmap.getPixel(i, j)));
					        	
			        		}
			        		
			        		
			        	}	
			        }
			        
					handler.post(new Runnable() {

						public void run() {
							// TODO Auto-generated method stub
							encodingImage.setImageBitmap(bitmap);
							Toast.makeText(StegoEncodeActivity.this, "Original String: " + encodingString, Toast.LENGTH_LONG).show();//this text
							Bitmap toSet = ((BitmapDrawable)encodingImage.getDrawable()).getBitmap();
							Toast.makeText(StegoEncodeActivity.this, "Decoded String: " + decodeImage(toSet), Toast.LENGTH_LONG).show();//this text
							encodeImageButton.setText("Encode Image");
						}
						
					});
					
				}
	        }).start();
	       
	 	}
	 	
	 
}
