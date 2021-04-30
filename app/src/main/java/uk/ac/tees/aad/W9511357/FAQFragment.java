package uk.ac.tees.aad.W9511357;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FAQFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_faq, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ListView listView = view.findViewById(R.id.listViewid);

        String[] q = {"How to use app?",
                "is login mandetory?",
                "need to pay to use app?",
                "Does it require GPS?",
                "Do we track location?",
                "What should I do if i get Flat tyre?",
                "What do the warinig lights on my car mean?",
                "what do i do if i put a wrong fuel in my car?"};

        String[] a = {"Login and register with app to use",
                "Yes",
                "no need of any payment",
                "Yes we use Gps to get your location",
                "No, in case of help we get your location",
                "in most cases, aflat tyre will mean a tyre change is required and can be fixed with a puncture repair kit",
                "When a warning light illuminates on your dashboard, it is a sign of a fault with your vechile",
                "You should call recover company, turning on tha car makes loss of engine performence."
        };

        FaqAdapter adapter = new FaqAdapter(getActivity(), q, a);
        listView.setAdapter(adapter);
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

}
