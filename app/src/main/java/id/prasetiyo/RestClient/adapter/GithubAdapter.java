package id.prasetiyo.RestClient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import id.prasetiyo.RestClient.R;
import id.prasetiyo.RestClient.model.RepoItem;

/**
 * Created by aoktox on 17/04/16.
 */
public class GithubAdapter extends BaseAdapter {
    private static ArrayList<RepoItem> items;
    private LayoutInflater layoutInflater;

    public GithubAdapter(Context context, ArrayList<RepoItem> repoItems) {
        items = repoItems;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_github, null);
            holder = new ViewHolder();
            holder.nama = (TextView) convertView.findViewById(R.id.txt_nama_repo);
            holder.url = (TextView) convertView.findViewById(R.id.txt_url_repo);
            holder.desc = (TextView) convertView.findViewById(R.id.txt_desc_repo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nama.setText(items.get(position).getNama());
        holder.url.setText(items.get(position).getUrl());
        holder.desc.setText(items.get(position).getDesc());

        return convertView;
    }

    static class ViewHolder {
        TextView nama;
        TextView url;
        TextView desc;
    }
}