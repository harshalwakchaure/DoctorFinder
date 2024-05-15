package com.example.doctorfinder;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText searchEditText;
    private ArrayAdapter<String> adapter;
    private List<String> doctorList;
    private Map<String, Doctor> doctorDetails;
    private PopupWindow popupWindow; // Reference to the currently opened popup window

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        listView = findViewById(R.id.listView);
        searchEditText = findViewById(R.id.searchEditText);

        // Initialize doctor list
        doctorList = new ArrayList<>();
        doctorList.add("Dr. M.S Dhoni (Thala)");
        doctorList.add("Dr. Rohit Sharma");
        doctorList.add("Dr. Virat Kohli");
        doctorList.add("Dr. Pranav Nehe");
        doctorList.add("Dr. Harshal Wakchaure");
        doctorList.add("Dr. Abhay Jhalani");
        doctorList.add("Dr. Shivraj Bhasme");
        doctorList.add("Dr. Tom ");
        doctorList.add("Dr. Jerry");


        // Initialize doctor details
        doctorDetails = new HashMap<>();
        doctorDetails.put("Dr. M.S Dhoni (Thala)", new Doctor("Surgeon", 42, 7, "+91 7777777777"));
        doctorDetails.put("Dr. Rohit Sharma", new Doctor("MBBS", 37, 20, "+91 2642642640"));
        doctorDetails.put("Dr. Virat Kohli", new Doctor("MD", 35, 15, "+91 2642642640"));
        doctorDetails.put("Dr. Pranav Nehe", new Doctor("Nurse", 21, 6, "+91 8789798798"));
        doctorDetails.put("Dr. Harshal Wakchaure", new Doctor("MD", 20, 4, "+91 8768878768"));
        doctorDetails.put("Dr. Abhay Jhalani", new Doctor("PhD", 21, 5, "+91 7265752373"));
        doctorDetails.put("Dr. Shivraj Bhasme", new Doctor("DDS", 45, 45, "+91 2787875858"));
        doctorDetails.put("Dr. Tom", new Doctor("MBBS", 35, 10, "+91 1234567890"));
        doctorDetails.put("Dr. Jerry", new Doctor("MD", 26, 8, "+91 67867868798"));
        // Initialize adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, doctorList);
        listView.setAdapter(adapter);

        // Add text change listener to filter list based on search query
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Add item click listener to show doctor details
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Clear the search text when a doctor's name is clicked
                searchEditText.setText("");

                // Dismiss the soft keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                // Hide the list view containing the doctor names
                listView.setVisibility(View.GONE);

                String selectedDoctor = (String) parent.getItemAtPosition(position);
                Doctor doctor = doctorDetails.get(selectedDoctor);
                showDoctorPopup(selectedDoctor, doctor.getQualification(), doctor.getAge(), doctor.getExperience(), doctor.getPhoneNumber());
            }
        });

        // Add click listener to the search edit text
        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the search text
                searchEditText.setText("");

                // Make the list view visible again
                listView.setVisibility(View.VISIBLE);

                // Dismiss the previous popup if it exists
                dismissPopup();
            }
        });
    }

    private void showDoctorPopup(String doctorName, String qualification, int age, int experience, String phoneNumber) {
        // Dismiss the previously opened popup window if it exists
        dismissPopup();

        // Inflate the popup layout
        View popupView = getLayoutInflater().inflate(R.layout.popup_doctor_details, null);

        // Initialize a new instance of popup window
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        // Set the doctor's name, qualification, age, experience, and phone number in the popup layout
        TextView doctorNameTextView = popupView.findViewById(R.id.doctorNameTextView);
        TextView qualificationTextView = popupView.findViewById(R.id.qualificationTextView);
        TextView ageTextView = popupView.findViewById(R.id.ageTextView);
        TextView experienceTextView = popupView.findViewById(R.id.experienceTextView);
        TextView phoneNumberTextView = popupView.findViewById(R.id.phoneNumberTextView);
        doctorNameTextView.setText(doctorName);
        qualificationTextView.setText("Qualification: " + qualification);
        ageTextView.setText("Age: " + age);
        experienceTextView.setText("Experience: " + experience + " years");
        phoneNumberTextView.setText("Contact: " + phoneNumber);

        // Show the popup window at the center of the screen
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // Add dismiss listener to handle visibility of list view
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // Make the list view visible again when the popup is dismissed
                listView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void dismissPopup() {
        // Dismiss the previously opened popup window if it exists
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
}
