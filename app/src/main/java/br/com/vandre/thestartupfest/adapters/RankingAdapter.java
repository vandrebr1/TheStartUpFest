package br.com.vandre.thestartupfest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import br.com.vandre.thestartupfest.R;
import br.com.vandre.thestartupfest.modelo.Ranking;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    List<Ranking> rankings = Collections.emptyList();

    public RankingAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = layoutInflater.inflate(R.layout.layout_item_ranking, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Ranking ranking = rankings.get(position);
        float media = ranking.media();

        Picasso.get()
                .load(ranking.getStartup().getImageUrl())
                .placeholder(R.color.colorAccent)
                .error(R.color.black_overlay)
                .into(holder.ivLogo);

        holder.tvNome.setText(ranking.getStartup().getName());
        holder.tvSegmento.setText(ranking.getStartup().getSegmento().getName());

        holder.tvPosicao.setText(String.valueOf(position + 1) + "º");
        holder.tvNota.setText( String.format("%.01f", media) + " / 5");
        holder.rbVotos.setRating(media);
    }

    @Override
    public int getItemCount() {
        return rankings.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLogo;
        TextView tvNome;
        TextView tvSegmento;
        TextView tvPosicao;
        TextView tvNota;
        RatingBar rbVotos;

        ViewHolder(View itemView) {
            super(itemView);

            ivLogo = itemView.findViewById(R.id.item_ranking_ivLogo);
            tvNome = itemView.findViewById(R.id.item_ranking_tvNome);
            tvSegmento = itemView.findViewById(R.id.item_ranking_tvSegmento);
            tvPosicao = itemView.findViewById(R.id.item_ranking_tvPosicao);
            tvNota = itemView.findViewById(R.id.item_ranking_tvNota);
            rbVotos = itemView.findViewById(R.id.item_ranking_rbVotos);
        }
    }

    public void setRankings(List<Ranking> rankings) {
        this.rankings = rankings;
        this.notifyDataSetChanged();
    }

}
