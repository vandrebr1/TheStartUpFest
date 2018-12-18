package br.com.vandre.thestartupfest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


import br.com.vandre.thestartupfest.R;
import br.com.vandre.thestartupfest.modelo.Startup;

public class StartupsAdapter extends RecyclerView.Adapter<StartupsAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    List<Startup> startups;

    public StartupsAdapter(Context context, List<Startup> startups) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.startups = startups;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = layoutInflater.inflate(R.layout.layout_item_startups, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Startup startup = startups.get(position);

        // holder.ivLogo.(pedidoItem.getProduto().getProduto());
        holder.tvNome.setText(startup.getName());
        holder.tvSegmento.setText(String.valueOf(startup.getSegmento().getName()));

    }

    @Override
    public int getItemCount() {
        return startups.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLogo;
        TextView tvNome;
        TextView tvSegmento;

        ViewHolder(View itemView) {
            super(itemView);

            ivLogo = itemView.findViewById(R.id.item_startup_ivLogo);
            tvNome = itemView.findViewById(R.id.item_startup_tvNome);
            tvSegmento = itemView.findViewById(R.id.item_startup_tvSegmento);
        }
    }
}
