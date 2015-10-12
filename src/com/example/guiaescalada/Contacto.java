package com.example.guiaescalada;

import java.util.List;
import java.util.Stack;

import com.example.guiaescalada.R;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;

public class Contacto extends ListaLugares{
	
	EditText asunto, mensaje;
	Button send;
	
	@Override  
	protected void onCreate(Bundle savedInstanceState){
	       super.onCreate(savedInstanceState); 
	       setContentView(R.layout.contacto);
	       this.title.setText("Contacto");
    	   send = (Button) findViewById(R.id.enviar);
   	       mensaje  = (EditText) findViewById(R.id.editText2);
   	       asunto  = (EditText) findViewById(R.id.correo);
	   	  
	   	  /* asunto.setOnClickListener(new OnClickListener()
	   	   { //Para limpiar el texto cuando se hace click.
	   		   this.setText("");
		       
	   	   });*/
	       send.setOnClickListener(new OnClickListener() {
	    	   
		       @Override
		       public void onClick(View v) {
		    	   Intent i = new Intent(Intent.ACTION_SEND);
		           i.setType("*/*");
		           i.putExtra(Intent.EXTRA_EMAIL, new String[] {"aplicacionescalada@gmail.com"});
		           i.putExtra(Intent.EXTRA_SUBJECT, asunto.getText().toString());
		           i.putExtra(Intent.EXTRA_TEXT, mensaje.getText().toString());
		           startActivity(createEmailOnlyChooserIntent(i, "Send via email"));
		       }
	       });
	}
	
	public Intent createEmailOnlyChooserIntent(Intent source, CharSequence chooserTitle) {
	        Stack<Intent> intents = new Stack<Intent>();
	        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","aplicacionescalada@gmail.com", null));
	        List<ResolveInfo> activities = getPackageManager().queryIntentActivities(i, 0);
	        for(ResolveInfo ri : activities) {
	            Intent target = new Intent(source);
	            target.setPackage(ri.activityInfo.packageName);
	            intents.add(target);
	        }

	        if(!intents.isEmpty()) {
	            Intent chooserIntent = Intent.createChooser(intents.remove(0), chooserTitle);
	            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toArray(new Parcelable[intents.size()]));
	            return chooserIntent;
	        } else {
	            return Intent.createChooser(source, chooserTitle);
	        }
	    }
}