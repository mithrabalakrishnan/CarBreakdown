package uk.ac.tees.aad.W9511357;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Faq extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        ListView listView = findViewById(R.id.listViewid);

        String[] q ={"How to use app?","is login madetory?","need to pay to use app?","does it require GPS?","Do we track locations"} ;
        String[] a ={"Lgin and register with app to use","Yes","no neen of any payment","Yes we use Gps to get your location","No, in case of help we get your location"} ;

        FaqAdapter adapter = new FaqAdapter(getApplicationContext(), q,  a);
        listView.setAdapter(adapter);


    }
}

class FaqAdapter extends ArrayAdapter<String> {

    Context context;

    String questions[];
    String answers[];



    FaqAdapter (Context c, String questions[], String answers[])
    {
        super(c, R.layout.list_item,R.id.textView1,questions);
        this.context = c;
        this.questions = questions;
        this.answers = answers;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.list_item,parent, false);

        TextView myTitle = row.findViewById(R.id.textView1);
        TextView myDescription = row.findViewById(R.id.textView2);

        myTitle.setText(questions[position]);
        myDescription.setText(answers[position]);

        return row;
    }


}


