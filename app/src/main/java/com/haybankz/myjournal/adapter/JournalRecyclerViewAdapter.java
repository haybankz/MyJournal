package com.haybankz.myjournal.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haybankz.myjournal.R;
import com.haybankz.myjournal.data.DateConverter;
import com.haybankz.myjournal.model.JournalModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class JournalRecyclerViewAdapter extends RecyclerView.Adapter<JournalRecyclerViewAdapter.JournalViewHolder> {

    @NonNull private List<JournalModel> mJournals;

    private Activity mActivity;

    public JournalRecyclerViewAdapter(Activity mActivity, @NonNull List<JournalModel> mJournals) {

        this.mActivity = mActivity;

        this.mJournals = mJournals;

    }

    @Override
    @NonNull
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_list_item, parent, false);



        return new JournalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        final JournalModel journal = mJournals.get(position);

        holder.bindExchange(journal);

    }


    public void addAllItems(List<JournalModel> journals){
        mJournals.clear();
        mJournals.addAll(journals);
        notifyDataSetChanged();
    }

    public void clear (){
        mJournals.clear();
        notifyDataSetChanged();
    }


    public JournalModel getItem(int position){
        return mJournals.get(position);
    }

//    public void remove(Journal journal){
//        mJournals.remove(journal);
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        return mJournals.size();
    }



    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }


    static class JournalViewHolder extends RecyclerView.ViewHolder {

        //bindviews here
        @BindView(R.id.textView_key) TextView keyTv;
        @BindView(R.id.textView_title) TextView titleTv;
        @BindView(R.id.textView_content) TextView contentTv;
        @BindView(R.id.textView_date) TextView datev;



        JournalViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        //bind views to journal data
        private void bindExchange(JournalModel journal) {
            keyTv.setText(String.valueOf(journal.getKey()));
            titleTv.setText(journal.getTitle());
            contentTv.setText(journal.getContent());
            datev.setText(DateConverter.getDateString(journal.getCreatedOn()));


            keyTv.setVisibility(View.GONE);
        }



    }
}
