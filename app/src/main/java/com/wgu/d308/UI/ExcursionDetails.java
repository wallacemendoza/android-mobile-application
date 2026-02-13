package com.wgu.d308.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    String title;
    double price;
    int excursionID;
    int vacaID;
    String startVacationDate;
    String endVacationDate;
    EditText editName;
    EditText editPrice;
    EditText editNote;
    TextView editDate;
    String excursionDate;
    Repository repository;
    Date startStartDate = null;
    Date endEndDate = null;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);
        repository = new Repository(getApplication());
        title = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price", 0.0);
        excursionDate = getIntent().getStringExtra("excursionDate");
        editName = findViewById(R.id.excursionTitle);
        editPrice = findViewById(R.id.excursionPrice);
        editName.setText(title);
        editPrice.setText(String.valueOf(price));
        excursionID = getIntent().getIntExtra("id", -1);
        vacaID = getIntent().getIntExtra("vacationID", -1);
        startVacationDate = getIntent().getStringExtra("startVacationDate");
        endVacationDate = getIntent().getStringExtra("endVacationDate");
        editNote = findViewById(R.id.excursionnote);
        editDate = findViewById(R.id.excursiondate);
        editDate.setText(excursionDate);

        startDate = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthOfYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            showTimePickerDialog();
        };

        startStartDate = parseDate(startVacationDate);
        endEndDate = parseDate(endVacationDate);

        editDate.setOnClickListener(v -> {
            String info = editDate.getText().toString();
            Date initialDate = parseDate(info);
            if (initialDate != null) {
                myCalendarStart.setTime(initialDate);
            }

            DatePickerDialog datePickerDialog = new DatePickerDialog(ExcursionDetails.this, startDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH));

            if (startStartDate != null) {
                datePickerDialog.getDatePicker().setMinDate(startStartDate.getTime());
            }
            if (endEndDate != null) {
                datePickerDialog.getDatePicker().setMaxDate(endEndDate.getTime());
            }
            datePickerDialog.show();
        });

        MaterialButton saveButton = findViewById(R.id.saveExcursionButton);
        saveButton.setOnClickListener(v -> saveExcursion());

        MaterialButton deleteButton = findViewById(R.id.deleteExcursionButton);
        deleteButton.setOnClickListener(v -> deleteExcursion());

        if (excursionID == -1) {
            deleteButton.setVisibility(View.GONE);
        }
    }

    private void deleteExcursion() {
        if (excursionID != -1) {
            Excursion currentExcursion = new Excursion(excursionID, title, price, vacaID, excursionDate);
            repository.delete(currentExcursion);
            Toast.makeText(ExcursionDetails.this, title + " was deleted", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    private Date parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        SimpleDateFormat formatWithTime = new SimpleDateFormat("MM/dd/yy HH:mm", Locale.US);
        SimpleDateFormat formatWithoutTime = new SimpleDateFormat("MM/dd/yy", Locale.US);
        try {
            return formatWithTime.parse(dateString);
        } catch (ParseException e) {
            try {
                return formatWithoutTime.parse(dateString);
            } catch (ParseException e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }

    private void showTimePickerDialog() {
        int hour = myCalendarStart.get(Calendar.HOUR_OF_DAY);
        int minute = myCalendarStart.get(Calendar.MINUTE);
        new TimePickerDialog(ExcursionDetails.this, (view, hourOfDay, minuteOfHour) -> {
            myCalendarStart.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendarStart.set(Calendar.MINUTE, minuteOfHour);
            updateLabelStart();
        }, hour, minute, false).show();
    }

    private void saveExcursion() {
        String excursionName = editName.getText().toString();
        String priceString = editPrice.getText().toString();
        String date = editDate.getText().toString();
        if (excursionName.isEmpty() || date.isEmpty() || priceString.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double excursionPrice = Double.parseDouble(priceString);

        Excursion excursion;
        if (excursionID == -1) {
            int newExcursionID = repository.getAllExcursions().isEmpty() ? 1 : repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
            excursion = new Excursion(newExcursionID, excursionName, excursionPrice, vacaID, date);
            repository.insert(excursion);
        } else {
            excursion = new Excursion(excursionID, excursionName, excursionPrice, vacaID, date);
            repository.update(excursion);
        }
        finish();
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        // Hide the old delete menu item
        menu.findItem(R.id.excursiondelete).setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.excursionsave) {
            saveExcursion();
            return true;
        }

        if (item.getItemId() == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, editName.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Excursion Details");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, null));
            return true;
        }
        if (item.getItemId() == R.id.notify) {
            Date myDate = parseDate(editDate.getText().toString());
            if (myDate != null) {
                try {
                    long trigger = myDate.getTime() - 30 * 60 * 1000;
                    Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
                    intent.putExtra("key", "Excursion Starting: " + editName.getText().toString() + " in 30 minutes!");
                    PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                    Toast.makeText(this, "Notification set for 30 minutes before " + editDate.getText().toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}