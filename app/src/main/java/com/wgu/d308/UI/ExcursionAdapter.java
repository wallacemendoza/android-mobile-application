package com.wgu.d308.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wgu.d308.R;
import com.wgu.d308.entities.Excursion;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;
    private String startVacationDate;
    private String endVacationDate;

    class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionNameTextView;
        private final TextView excursionDateTextView;

        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionNameTextView = itemView.findViewById(R.id.textViewExcursionName);
            excursionDateTextView = itemView.findViewById(R.id.textViewExcursionDate);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Excursion current = mExcursions.get(position);
                Intent intent = new Intent(context, ExcursionDetails.class);
                intent.putExtra("id", current.getExcursionID());
                intent.putExtra("name", current.getExcursionName());
                intent.putExtra("price", current.getPrice());
                intent.putExtra("vacationID", current.getVacationID());
                intent.putExtra("excursionDate", current.getExcursionDate());
                intent.putExtra("startVacationDate", startVacationDate);
                intent.putExtra("endVacationDate", endVacationDate);
                context.startActivity(intent);
            });
        }
    }

    public ExcursionAdapter(Context context, String startVacationDate, String endVacationDate) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.startVacationDate = startVacationDate;
        this.endVacationDate = endVacationDate;
    }

    @NonNull
    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {
        if (mExcursions != null) {
            Excursion current = mExcursions.get(position);
            holder.excursionNameTextView.setText(current.getExcursionName());
            holder.excursionDateTextView.setText(current.getExcursionDate());
        } else {
            holder.excursionNameTextView.setText("No excursion name");
            holder.excursionDateTextView.setText("No date");
        }
    }

    public void setExcursions(List<Excursion> excursions) {
        mExcursions = excursions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mExcursions != null ? mExcursions.size() : 0;
    }
}