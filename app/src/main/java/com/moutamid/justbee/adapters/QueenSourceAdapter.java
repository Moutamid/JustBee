package com.moutamid.justbee.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.justbee.R;
import com.moutamid.justbee.models.QueenSourceModel;

import java.util.ArrayList;

public class QueenSourceAdapter extends RecyclerView.Adapter<QueenSourceAdapter.QueenVH> {

    Context context;
    public ArrayList<QueenSourceModel> list;
    TableAdapter tableAdapter;

    public QueenSourceAdapter(Context context, ArrayList<QueenSourceModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public QueenVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QueenVH(LayoutInflater.from(context).inflate(R.layout.queen_source, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QueenVH holder, int position) {
        QueenSourceModel model = list.get(holder.getAdapterPosition());

        holder.queenSource.setText(model.getQueenSource());
        holder.title1.setText(model.getTitle1());
        holder.title2.setText(model.getTitle2());

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.subRC.getContext());

        layoutManager.setInitialPrefetchItemCount(model.getList().size());

        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        tableAdapter = new TableAdapter(context, model.getList());
        holder.subRC.setLayoutManager(layoutManager);
        holder.subRC.setHasFixedSize(false);
        holder.subRC.setAdapter(tableAdapter);
        holder.subRC.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class QueenVH extends RecyclerView.ViewHolder{
        TextView queenSource, title1, title2;
        RecyclerView subRC;
        public QueenVH(@NonNull View itemView) {
            super(itemView);

            subRC = itemView.findViewById(R.id.subRC);
            queenSource = itemView.findViewById(R.id.queenSource);
            title1 = itemView.findViewById(R.id.title1);
            title2 = itemView.findViewById(R.id.title2);

        }
    }

}
