package com.moutamid.justbee.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.justbee.R;
import com.moutamid.justbee.models.QueenPerformance;
import com.moutamid.justbee.models.QueenSourceModel;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableVH> {
    Context context;
    public ArrayList<QueenPerformance> list;

    public TableAdapter(Context context, ArrayList<QueenPerformance> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TableVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TableVH(LayoutInflater.from(context).inflate(R.layout.table, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TableVH holder, int position) {
        QueenPerformance queenPerformance = list.get(holder.getAdapterPosition());

        holder.column1.setText(queenPerformance.getColumn1());
        holder.column2.setText(queenPerformance.getColumn2());

        if (holder.getAdapterPosition() % 2 != 0) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.table_row2));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.table_row1));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TableVH extends RecyclerView.ViewHolder {
        TextView column1, column2;

        public TableVH(@NonNull View itemView) {
            super(itemView);
            column1 = itemView.findViewById(R.id.column1);
            column2 = itemView.findViewById(R.id.column2);
        }
    }

}
