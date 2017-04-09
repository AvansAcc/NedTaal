package com.example.martin.nedtaal;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.Locale;

public class TopAssignmentFragment extends Fragment implements AdapterView.OnClickListener {

    TextView mTextView = null;
    AssignmentActivity activity = null;
    TextToSpeech ttobj = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_assignment, container, false);

        mTextView = (TextView) view.findViewById(R.id.assignment_no);
        activity = (AssignmentActivity) getActivity();

        ImageButton btn = (ImageButton)view.findViewById(R.id.button_play);
        btn.setOnClickListener(this);

        setQuestionNo();

        // Create TTS module
        ttobj = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    ttobj.setLanguage(new Locale("de_NL"));
                    float rate = (float)0.8;
                    ttobj.setSpeechRate(rate);
                }
                else {
                    Toast.makeText(getActivity(),"Error occurred while initializing Text-To-Speech engine", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_play) {
            String toSpeak = activity.getPossibleAnswers()[0];
            ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    public void setQuestionNo() {
        String assignmentsLeft = activity.getWordsLeft();
        mTextView.setText(assignmentsLeft);
    }
}