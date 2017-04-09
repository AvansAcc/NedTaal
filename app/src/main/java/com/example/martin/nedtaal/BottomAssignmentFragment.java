package com.example.martin.nedtaal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BottomAssignmentFragment extends Fragment {

    AssignmentActivity activity = null;
    LinearLayout master = null;
    int correctQ = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_assignment, container, false);
        master = (LinearLayout)view.findViewById(R.id.masterLayout);

        activity = (AssignmentActivity) getActivity();
        String[] answers = activity.getPossibleAnswers();

        createAnswers(answers);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void createAnswers(final String[] answers) {
        master.removeAllViews();
        int count = 0;
        int totalCount = 1;

        LinearLayout rowLayout = null;
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // Start loop through all answers given
        for (final String ans : answers) {
            if (count == 3) count = 0;
            // For every 3 answers a new row is created
            if (count == 0) {
                //create row layout container
                rowLayout = new LinearLayout(activity.getBaseContext());
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLayout.setGravity(Gravity.CENTER);
                rowLayout.setTag(totalCount);
                rowLayout.setLayoutParams(rowParams);
                rowLayout.setBaselineAligned(false);
            }

            // Create button
            Button b = new Button(activity.getBaseContext());
            b.setId(totalCount - 1);
            b.setText(ans);
            b.setPadding(5, 0, 5, 5);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            p.weight = 1;
            p.width = 0;
            b.setLayoutParams(p);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == 0) {
                        Toast.makeText(getActivity(), "Correct!", Toast.LENGTH_SHORT).show();
                        correctQ++;
                    } else {
                        Toast.makeText(getActivity(), "Not correct...", Toast.LENGTH_SHORT).show();
                    }
                    activity.nextQuestion();
                }
            });

            rowLayout.addView(b);

            //add to parent view
            if (count == 2) {
                // Add row to master layout
                master.addView(rowLayout);
            }
            count++;
            totalCount++;
        }
    }
    public int getCorrectQ() {
        return correctQ;
    }
}
