package fanzone.datacollection;

/**
 * Created by jayasowmya on 25/8/16.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
    Dialog dialog;
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

        holder.iv_display.setTag(position);
        holder.iv_delete.setTag(position);
        holder.tv_name.setText(dataList.get(position).mname);

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                LayoutInflater inflater = LayoutInflater.from(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("TNPL");
                builder.setMessage("Do you want to delete this record?");
                // builder.setView(subView);
                AlertDialog alertDialog = builder.create();

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        listener.nameToChnge(dataList.get((Integer) view.getTag()).mphone);

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
        holder.iv_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(context,android.R.style.Theme_Translucent);
                dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
                dialog.setContentView(R.layout.profile_detail);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW;
                window.setAttributes(wlp);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                TextView surveyorName = (TextView) dialog.findViewById(R.id.tv_surver_name);
                TextView location = (TextView) dialog.findViewById(R.id.tv_location);
                TextView match = (TextView) dialog.findViewById(R.id.tv_match);
                TextView date = (TextView) dialog.findViewById(R.id.tv_surver_date);
                TextView name = (TextView) dialog.findViewById(R.id.tv_name);
                TextView gender = (TextView) dialog.findViewById(R.id.tv_gender);
                TextView age = (TextView) dialog.findViewById(R.id.tv_age);
                TextView from_loc = (TextView) dialog.findViewById(R.id.tv_from_loc);
                TextView phone = (TextView) dialog.findViewById(R.id.tv_phone);
                TextView email = (TextView) dialog.findViewById(R.id.tv_email);
                TextView fev_team = (TextView) dialog.findViewById(R.id.tv_fev_team);
                TextView profession = (TextView) dialog.findViewById(R.id.tv_profession);
                TextView education = (TextView) dialog.findViewById(R.id.tv_education);

                surveyorName.setText(dataList.get((Integer) view.getTag()).msurveyor);
                location.setText(dataList.get((Integer) view.getTag()).mlocation);
                match.setText(dataList.get((Integer) view.getTag()).mmatch);
                date.setText(dataList.get((Integer) view.getTag()).msys_time);
                name.setText(dataList.get((Integer) view.getTag()).mname);
                gender.setText(dataList.get((Integer) view.getTag()).mgender);
                age.setText(dataList.get((Integer) view.getTag()).mage);

                from_loc.setText(dataList.get((Integer) view.getTag()).mfromloc);
                phone.setText(dataList.get((Integer) view.getTag()).mphone);
                email.setText(dataList.get((Integer) view.getTag()).memail);
                fev_team.setText(dataList.get((Integer) view.getTag()).mfevteam);
                profession.setText(dataList.get((Integer) view.getTag()).mprofession);
                education.setText(dataList.get((Integer) view.getTag()).meducation);

               // Button back  =(Button) dialog.findViewById(R.id.btn_back);
                Button list  =(Button) dialog.findViewById(R.id.btn_list);

               /* back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent back = new Intent(context,Profile.class);
                        context.startActivity(back);
                        dialog.dismiss();
                    }
                });*/

                list.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent list = new Intent(context,UserDetail.class);
                        context.startActivity(list);
                        dialog.dismiss();
                    }
                });
                // dialog.dismiss();

                dialog.show();


            }
        });

    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView iv_delete,iv_display;

        public ListViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_delete= (ImageView) itemView.findViewById(R.id.iv_delete);
            iv_display= (ImageView) itemView.findViewById(R.id.iv_display);

        }
    }


}
