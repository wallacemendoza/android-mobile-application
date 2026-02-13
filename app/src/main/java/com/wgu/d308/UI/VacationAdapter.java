package com.wgu.d308.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wgu.d308.Database.Repository;
import com.wgu.d308.R;
import com.wgu.d308.entities.Excursion;
import com.wgu.d308.entities.Vacation;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {
    private List<Vacation> mVacations;
    private final Context context;
    private final LayoutInflater mInflater;
    private final Repository repository;

    public VacationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.repository = new Repository(((VacationList) context).getApplication());
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationItemView;
        private final TextView hotelName;
        private final TextView vacationPrice;
        private final TextView startDateValue;
        private final TextView endDateValue;

        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);
            vacationItemView = itemView.findViewById(R.id.vacationTitle);
            hotelName = itemView.findViewById(R.id.hotelName);
            vacationPrice = itemView.findViewById(R.id.vacationPrice);
            startDateValue = itemView.findViewById(R.id.startDateValue);
            endDateValue = itemView.findViewById(R.id.endDateValue);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Vacation current = mVacations.get(position);
                    Intent intent = new Intent(context, VacationDetails.class);
                    intent.putExtra("id", current.getVacationID());
                    intent.putExtra("name", current.getVacationName());
                    intent.putExtra("price", current.getPrice());
                    intent.putExtra("hotel", current.getHotel());
                    intent.putExtra("startVacationDate", current.getStartVacationDate());
                    intent.putExtra("endVacationDate", current.getEndVacationDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.vacation_list_item, parent, false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if (mVacations != null) {
            Vacation currentVacation = mVacations.get(position);
            double totalExcursionPrice = 0.0;
            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getVacationID() == currentVacation.getVacationID()) {
                    totalExcursionPrice += excursion.getPrice();
                }
            }
            double totalPrice = currentVacation.getPrice() + totalExcursionPrice;
            holder.vacationItemView.setText(currentVacation.getVacationName());
            holder.hotelName.setText(currentVacation.getHotel());
            holder.vacationPrice.setText(String.format("$%.2f", totalPrice));
            holder.startDateValue.setText(currentVacation.getStartVacationDate());
            holder.endDateValue.setText(currentVacation.getEndVacationDate());
        } else {
            holder.vacationItemView.setText("No vacation name");
        }
    }

    @Override
    public int getItemCount() {
        return mVacations != null ? mVacations.size() : 0;
    }

    public void setVacations(List<Vacation> vacations) {
        mVacations = vacations;
        notifyDataSetChanged();
    }
}