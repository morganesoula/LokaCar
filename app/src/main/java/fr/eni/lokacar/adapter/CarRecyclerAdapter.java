package fr.eni.lokacar.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.model.Car;

public class CarRecyclerAdapter {

    List<Car> listCars;
    Context context;

    private OnItemClickListener listener;

    public CarRecyclerAdapter(ArrayList<Car> listCars, Context context) {
        this.listCars = listCars;
        this.context = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView carModel;
        public TextView articlePrice;
        public RatingBar articleNote;
        public TextView articleDescription;


        public ViewHolder(View unArticleLigne) {
            super(unArticleLigne);
            articleNom = unArticleLigne.findViewById(R.id.article_name);
            articlePrice = unArticleLigne.findViewById(R.id.article_price);
            articleNote = unArticleLigne.findViewById(R.id.article_rate);
            articleDescription = unArticleLigne.findViewById(R.id.article_description);

            unArticleLigne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(getArticle(getAdapterPosition()));
                    }
                }
            });

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, MainActivity.class);

            Article article = listArticles.get(getAdapterPosition());
            intent.putExtra("article", article);
            context.startActivity(intent);
        }
    }
}
