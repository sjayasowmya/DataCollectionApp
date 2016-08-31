package fanzone.datacollection.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fanzone.datacollection.R;
import fanzone.datacollection.models.Post;
import fanzone.datacollection.models.Posts;


public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.post_title);
        authorView = (TextView) itemView.findViewById(R.id.post_author);
        starView = (ImageView) itemView.findViewById(R.id.star);
        numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);
        bodyView = (TextView) itemView.findViewById(R.id.post_body);
    }

    public void bindToPost(Posts posts, View.OnClickListener starClickListener) {
        titleView.setText(posts.msurveyor);
        authorView.setText(posts.author);
        numStarsView.setText(String.valueOf(posts.starCount));
        bodyView.setText(posts.body);

        starView.setOnClickListener(starClickListener);
    }
}
