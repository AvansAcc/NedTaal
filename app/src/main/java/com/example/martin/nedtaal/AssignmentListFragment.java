package com.example.martin.nedtaal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssignmentListFragment extends ListFragment {

    JSONObject dummyData = null;
    JSONObject realData = null;
    JSONArray dA = null;
    AssignmentListActivity activity = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assignmentlist, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (AssignmentListActivity) getActivity();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity);

        String url ="https://nederlandse-taal-game-website.herokuapp.com/assignments";

        // Request a string response from the provided URL.
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    dA = response;
                    String[] JsonToStringArray = null;
                    JsonToStringArray = new String[dA.length()];

                    try {
                        for (int i = 0; i < dA.length(); i++) {
                            JsonToStringArray[i] = dA.getJSONObject(i).getString("title");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, JsonToStringArray);
                    setListAdapter(adapter);
                    getListView().setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                            Intent intent = new Intent(activity.getBaseContext(), AssignmentActivity.class);
                            String assignmentString = null;
                            try {
                                assignmentString = dA.getJSONObject(position).toString();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            intent.putExtra("assignmentJSON", assignmentString);
                            startActivity(intent);
                        }
                    });

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "Error while request data form server", Toast.LENGTH_LONG).show();
                }
            }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


        // Download data for assignments here
        //dummyData = generateDummydata();
        //dA = dummyData.getJSONArray("assignments");
    }

    public JSONObject generateDummydata() throws JSONException {

        JSONArray possibleAnswerslist = null;
        JSONObject questionObject = null;
        ArrayList<JSONObject> questionList1 = new ArrayList<JSONObject>();

        // Opdracht 1.1
        possibleAnswerslist = new JSONArray();
        possibleAnswerslist.put("Banaan");
        possibleAnswerslist.put("Mangaan");
        possibleAnswerslist.put("Mana");
        possibleAnswerslist.put("Gaap");
        possibleAnswerslist. put("Kraan");

        questionObject = new JSONObject();
        questionObject.put("Maan", possibleAnswerslist);

        questionList1.add(questionObject);

        // Opdracht 1.2
        possibleAnswerslist = new JSONArray();
        possibleAnswerslist.put("Boos");
        possibleAnswerslist.put("Gozer");
        possibleAnswerslist.put("Bloem");
        possibleAnswerslist.put("Roes");
        possibleAnswerslist.put("Rijk");

        questionObject = new JSONObject();
        questionObject.put("Roos", possibleAnswerslist);

        questionList1.add(questionObject);

        // Opdracht 1.3
        possibleAnswerslist = new JSONArray();
        possibleAnswerslist.put("Gist");
        possibleAnswerslist.put("Rijst");
        possibleAnswerslist.put("Haai");
        possibleAnswerslist.put("Dit");
        possibleAnswerslist.put("Mis");

        questionObject = new JSONObject();
        questionObject.put("Vis", possibleAnswerslist);

        questionList1.add(questionObject);

        // Assemble in one assignment
        JSONObject opdracht_1 = new JSONObject();
        opdracht_1.put("id", "1");
        opdracht_1.put("name", "Assignment 1");
        opdracht_1. put("questions", new JSONArray(questionList1));

        // Add to the assignments
        JSONArray assignments = new JSONArray();
        assignments.put(opdracht_1);

        JSONObject assignmentObject = new JSONObject();
        assignmentObject.put("assignments", assignments);

        return assignmentObject;
    }
}
