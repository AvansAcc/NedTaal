package com.example.martin.nedtaal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AssignmentActivity extends AppCompatActivity {

    JSONObject JSONAssignment = null;
    int currentQuestion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean inverse = sharedPref.getBoolean("background", true);
        if(inverse) { setTheme(R.style.AppTheme_Inverse); }
        else { setTheme(R.style.AppTheme); }

        Intent intent = getIntent();
        String jsonString = intent.getStringExtra("assignmentJSON");

        try {
            JSONAssignment = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_assignment);
    }

    public String getWordsLeft() {
        String returnString = null;
        try {
            returnString = "Opdracht " + (currentQuestion + 1) + " van de " + JSONAssignment.getJSONArray("questions").length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public String[] getPossibleAnswers() {
        String[] returnString = null;
        try {
            JSONArray possibleAnswersArray = JSONAssignment.getJSONArray("questions").getJSONObject(currentQuestion).getJSONArray("possibleAnswers");
            returnString = new String[possibleAnswersArray.length() + 1];
            returnString[0] = JSONAssignment.getJSONArray("questions").getJSONObject(currentQuestion).getString("correctAnswer");
            for (int i = 1; i <= possibleAnswersArray.length(); i++) {
                returnString[i] = possibleAnswersArray.getString(i - 1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return returnString;
    }

    public void nextQuestion() {
        currentQuestion++;
        int totQuestions = 0;
        try {
            totQuestions = JSONAssignment.getJSONArray("questions").length();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TopAssignmentFragment topFrag = (TopAssignmentFragment)getSupportFragmentManager().findFragmentById(R.id.question_field);
        BottomAssignmentFragment botFrag = (BottomAssignmentFragment)getSupportFragmentManager().findFragmentById(R.id.answer_field);

        if(totQuestions <= currentQuestion) {
            this.finish();
            Toast.makeText(this, "You finished the assignment with " + botFrag.getCorrectQ() + " correct answer(s).", Toast.LENGTH_LONG).show();
        }
        else {

            topFrag.setQuestionNo();
            botFrag.createAnswers(getPossibleAnswers());
        }
    }
}
