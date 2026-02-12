package com.wgu.d308.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.wgu.d308.Database.Repository;
import com.wgu.d308.R;
import com.wgu.d308.entities.Excursion;
import com.wgu.d308.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {
    String name;
    static int notificationID;
    String hotel;
    int vacationID;
    String startVacationDate;
    String endVacationDate;
    EditText editName;
    EditText editHotel;
    TextView editStartVacaDate;
    TextView editEndVacaDate;
    Vacation currentVacation;
    int numExcursions;
    DatePickerDialog.OnDateSetListener startVacaDate;
    DatePickerDialog.OnDateSetListener endVacaDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);

        repository = new Repository(getApplication());

        editName = findViewById(R.id.titletext);
        editHotel = findViewById(R.id.hoteltext);
        editStartVacaDate = findViewById(R.id.startvacationdate);
        editEndVacaDate = findViewById(R.id.endvacationdate);

        vacationID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        hotel = getIntent().getStringExtra("hotel");
        startVacationDate = getIntent().getStringExtra("startVacationDate");
        endVacationDate = getIntent().getStringExtra("endVacationDate");

        editName.setText(name);
        editHotel.setText(hotel);
        editStartVacaDate.setText(startVacationDate);
        editEndVacaDate.setText(endVacationDate);

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startVacaDate = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthOfYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelStart();
        };

        endVacaDate = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarEnd.set(Calendar.YEAR, year);
            myCalendarEnd.set(Calendar.MONTH, monthOfYear);
            myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelEnd();
        };

        editStartVacaDate.setOnClickListener(v -> {
            String info = editStartVacaDate.getText().toString();
            if (info.isEmpty()) info = "02/10/24";
            try {
                Date date = sdf.parse(info);
                if (date != null) {
                    myCalendarStart.setTime(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(VacationDetails.this, startVacaDate, myCalendarStart
                    .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                    myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
        });

        editEndVacaDate.setOnClickListener(v -> {
            String info = editEndVacaDate.getText().toString();
            if (info.isEmpty()) info = "02/10/24";
            try {
                Date date = sdf.parse(info);
                if (date != null) {
                    myCalendarEnd.setTime(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DatePickerDialog datePickerDialog = new DatePickerDialog(VacationDetails.this, endVacaDate, myCalendarEnd
                    .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                    myCalendarEnd.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.getDatePicker().setMinDate(myCalendarStart.getTimeInMillis());

            datePickerDialog.show();
        });

        MaterialButton addExcursionButton = findViewById(R.id.addExcursionButton);
        MaterialButton addVacationButton = findViewById(R.id.addVacationButton);

        if (vacationID == -1) {
            addExcursionButton.setVisibility(View.GONE);
            addVacationButton.setText("Save Vacation");
        } else {
            addVacationButton.setText("Save Changes");
        }

        addExcursionButton.setOnClickListener(v -> {
            Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
            intent.putExtra("vacationID", vacationID);
            intent.putExtra("startVacationDate", editStartVacaDate.getText().toString());
            intent.putExtra("endVacationDate", editEndVacaDate.getText().toString());
            startActivity(intent);
        });

        addVacationButton.setOnClickListener(v -> saveVacation());
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this, startVacationDate, endVacationDate);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        if (vacationID != -1) {
            for (Excursion p : repository.getAllExcursions()) {
                if (p.getVacationID() == vacationID) filteredExcursions.add(p);
            }
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }

    private void saveVacation() {
        String vacationName = editName.getText().toString();
        String hotelName = editHotel.getText().toString();
        String startDate = editStartVacaDate.getText().toString();
        String endDate = editEndVacaDate.getText().toString();

        if (vacationName.isEmpty() || hotelName.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (vacationID == -1) {
            Vacation newVacation = new Vacation(0, vacationName, 0.0, hotelName, startDate, endDate);
            repository.insert(newVacation);
        } else {
            Vacation updatedVacation = new Vacation(vacationID, vacationName, 0.0, hotelName, startDate, endDate);
            repository.update(updatedVacation);
        }

        finish();
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartVacaDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEndVacaDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    private void scheduleAlarm(AlarmManager alarmManager, long triggerTime, String message, int notificationId) {
        Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
        intent.putExtra("key", message);
        intent.putExtra("notification_id", notificationId);
        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, notificationId, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, sender);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.vacationsave) {
            saveVacation();
            return true;
        }
        if (item.getItemId() == R.id.vacationdelete) {
            for (Vacation vaca : repository.getAllVacations()) {
                if (vaca.getVacationID() == vacationID) currentVacation = vaca;
            }

            numExcursions = 0;
            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getVacationID() == vacationID) ++numExcursions;
            }

            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(VacationDetails.this, "Can\'t delete a Vacation with excursions", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if (item.getItemId() == R.id.addSampleExcursions) {
            if (vacationID == -1) {
                Toast.makeText(VacationDetails.this, "Please save Vacation before adding excursions", Toast.LENGTH_LONG).show();
            } else {
                int excursionID;
                if (repository.getAllExcursions().isEmpty()) excursionID = 1;
                else
                    excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
                Excursion excursion = new Excursion(excursionID, "Snorkeling", 0.0, vacationID, "02/26/24");
                repository.insert(excursion);
                onResume(); // Refresh the list
            }
            return true;
        }
        if (item.getItemId() == R.id.vacationshare) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Vacation Details: " + name);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "My Vacation");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if (item.getItemId() == R.id.vacationnotify) {
            String startdate = editStartVacaDate.getText().toString();
            String enddate = editEndVacaDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myStartDate = null;
            Date myEndDate = null;
            try {
                myStartDate = sdf.parse(startdate);
                myEndDate = sdf.parse(enddate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                if (myStartDate != null) {
                    scheduleAlarm(alarmManager, myStartDate.getTime(), "Vacation Start: " + name, ++notificationID);
                }
                if (myEndDate != null) {
                    scheduleAlarm(alarmManager, myEndDate.getTime(), "Vacation End: " + name, ++notificationID);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
