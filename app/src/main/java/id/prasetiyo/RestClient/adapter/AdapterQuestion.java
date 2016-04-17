package id.prasetiyo.RestClient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import id.prasetiyo.RestClient.R;
import id.prasetiyo.RestClient.model.Item;

public class AdapterQuestion extends RecyclerView.Adapter<AdapterQuestion.ViewHolder> {

    private final OnItemClickListener listener;
    private List<Item> ques;
    private Context con;

    public AdapterQuestion(Context context, List<Item> ques, OnItemClickListener listener) {
        this.ques = ques;
        this.listener = listener;
        this.con = context;
    }


    @Override
    public AdapterQuestion.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }


    @Override
    public void onBindViewHolder(AdapterQuestion.ViewHolder holder, int position) {
        holder.click(ques.get(position), listener);
        holder.tvQuestionId.setText(String.valueOf(ques.get(position).getQuestionId()));
        holder.tvTitle.setText(ques.get(position).getTitle());
        holder.tvLink.setText(ques.get(position).getLink());
    }


    @Override
    public int getItemCount() {
        return ques.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionId, tvTitle, tvLink;


        public ViewHolder(View itemView) {
            super(itemView);
            tvQuestionId = (TextView) itemView.findViewById(R.id.tv_question_id);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvLink = (TextView) itemView.findViewById(R.id.tv_link);

        }


        public void click(final Item quesModel, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(quesModel);
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onClick(Item Item);
    }
}