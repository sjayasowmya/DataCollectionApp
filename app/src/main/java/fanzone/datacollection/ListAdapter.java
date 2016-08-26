package fanzone.datacollection;

/**
 * Created by jayasowmya on 25/8/16.
 */
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    Context context;
    List<UserData> dataList = new ArrayList<>();
    LayoutInflater inflater;
    Listener listener;

    public ListAdapter(Context context, List<UserData> dataList1) {

        this.context = context;
        this.dataList = dataList1;
        this.listener= (Listener) context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View convertView = inflater.inflate(R.layout.listrow, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {


        holder.iv_delete.setTag(position);
        holder.tv_name.setText(dataList.get(position).name);

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                LayoutInflater inflater = LayoutInflater.from(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Your Record !!!");
                builder.setMessage("Do you want to delete this record?");
                // builder.setView(subView);
                AlertDialog alertDialog = builder.create();

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        listener.nameToChnge(dataList.get((Integer) view.getTag()).number);

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(context, "Cancel", Toast.LENGTH_LONG).show();
                    }
                });

                builder.show();


            }
        });

    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView iv_delete;

        public ListViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_delete= (ImageView) itemView.findViewById(R.id.iv_delete);

        }
    }


}
