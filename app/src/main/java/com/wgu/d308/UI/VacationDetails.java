package com.wgu.d308.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
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
    double price;
    static int notificationID;
    String hotel;
    int vacationID;
    String startVacationDate;
    String endVacationDate;
    EditText editName;
    EditText editHotel;
    EditText editPrice;
    TextView editStartVacaDate;
    TextView editEndVacaDate;
    Vacation currentVacation;
    DatePickerDialog.OnDateSetListener startVacaDateListener;
    DatePickerDialog.OnDateSetListener endVacaDateListener;
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
        editPrice = findViewById(R.id.pricetext);
        editStartVacaDate = findViewById(R.id.startvacationdate);
        editEndVacaDate = findViewById(R.id.endvacationdate);

        vacationID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price", 0.0);
        hotel = getIntent().getStringExtra("hotel");
        startVacationDate = getIntent().getStringExtra("startVacationDate");
        endVacationDate = getIntent().getStringExtra("endVacationDate");

        editName.setText(name);
        editHotel.setText(hotel);
        editPrice.setText(String.valueOf(price));
        editStartVacaDate.setText(startVacationDate);
        editEndVacaDate.setText(endVacationDate);

        String myFormat = "MM/dd/yy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startVacaDateListener = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthOfYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            showStartTimePickerDialog();
        };

        endVacaDateListener = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarEnd.set(Calendar.YEAR, year);
            myCalendarEnd.set(Calendar.MONTH, monthOfYear);
            myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            showEndTimePickerDialog();
        };

        editStartVacaDate.setOnClickListener(v -> {
            String info = editStartVacaDate.getText().toString();
            if (info.isEmpty()) info = "02/10/24 12:00";
            try {
                Date date = sdf.parse(info);
                if (date != null) {
                    myCalendarStart.setTime(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(VacationDetails.this, startVacaDateListener, myCalendarStart
                    .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                    myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
        });

        editEndVacaDate.setOnClickListener(v -> {
            String info = editEndVacaDate.getText().toString();
            if (info.isEmpty()) info = "02/10/24 12:00";
            try {
                Date date = sdf.parse(info);
                if (date != null) {
                    myCalendarEnd.setTime(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DatePickerDialog datePickerDialog = new DatePickerDialog(VacationDetails.this, endVacaDateListener, myCalendarEnd
                    .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                    myCalendarEnd.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.getDatePicker().setMinDate(myCalendarStart.getTimeInMillis());

            datePickerDialog.show();
        });

        MaterialButton addExcursionButton = findViewById(R.id.addExcursionButton);
        MaterialButton deleteVacationButton = findViewById(R.id.deleteVacationButton);
        MaterialButton addVacationButton = findViewById(R.id.addVacationButton);

        if (vacationID == -1) {
            addExcursionButton.setVisibility(View.GONE);
            deleteVacationButton.setVisibility(View.GONE);
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

        deleteVacationButton.setOnClickListener(v -> deleteVacation());
        addVacationButton.setOnClickListener(v -> saveVacation());
    }

    private void showStartTimePickerDialog() {
        int hour = myCalendarStart.get(Calendar.HOUR_OF_DAY);
        int minute = myCalendarStart.get(Calendar.MINUTE);
        new TimePickerDialog(VacationDetails.this, (view, hourOfDay, minuteOfHour) -> {
            myCalendarStart.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendarStart.set(Calendar.MINUTE, minuteOfHour);
            updateLabelStart();
        }, hour, minute, false).show();
    }

    private void showEndTimePickerDialog() {
        int hour = myCalendarEnd.get(Calendar.HOUR_OF_DAY);
        int minute = myCalendarEnd.get(Calendar.MINUTE);
        new TimePickerDialog(VacationDetails.this, (view, hourOfDay, minuteOfHour) -> {
            myCalendarEnd.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendarEnd.set(Calendar.MINUTE, minuteOfHour);
            updateLabelEnd();
        }, hour, minute, false).show();
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
        String priceString = editPrice.getText().toString();
        String startDate = editStartVacaDate.getText().toString();
        String endDate = editEndVacaDate.getText().toString();

        if (vacationName.isEmpty() || hotelName.isEmpty() || priceString.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double vacationPrice = Double.parseDouble(priceString);

        if (vacationID == -1) {
            Vacation newVacation = new Vacation(0, vacationName, vacationPrice, hotelName, startDate, endDate);
            repository.insert(newVacation);
        } else {
            Vacation updatedVacation = new Vacation(vacationID, vacationName, vacationPrice, hotelName, startDate, endDate);
            repository.update(updatedVacation);
        }

        finish();
    }

    private void deleteVacation() {
        for (Vacation vaca : repository.getAllVacations()) {
            if (vaca.getVacationID() == vacationID) currentVacation = vaca;
        }

        List<Excursion> excursionsToDelete = new ArrayList<>();
        for (Excursion excursion : repository.getAllExcursions()) {
            if (excursion.getVacationID() == vacationID) {
                excursionsToDelete.add(excursion);
            }
        }

        for (Excursion excursion : excursionsToDelete) {
            repository.delete(excursion);
        }

        repository.delete(currentVacation);
        Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " and its excursions were deleted", Toast.LENGTH_LONG).show();
        finish();
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartVacaDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEndVacaDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    private void scheduleAlarm(AlarmManager alarmManager, long triggerTime, String message, int notificationId) {
        Intent intent = new Intent(VacationDetails.this, MyVacationReceiver.class);
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
            deleteVacation();
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
                Excursion excursion = new Excursion(excursionID, "Snorkeling", 0.0, vacationID, "02/26/24 12:00");
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
            String myFormat = "MM/dd/yy HH:mm";
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
                    long trigger = myStartDate.getTime() - 30 * 60 * 1000;
                    scheduleAlarm(alarmManager, trigger, "Vacation Start: " + name + " in 30 minutes!", ++notificationID);
                }
                if (myEndDate != null) {
                    long trigger = myEndDate.getTime() - 30 * 60 * 1000;
                    scheduleAlarm(alarmManager, trigger, "Vacation End: " + name + " in 30 minutes!", ++notificationID);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
