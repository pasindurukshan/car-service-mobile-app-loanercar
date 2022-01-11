package com.example.car_service_wecarcare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class lcMainAdapter extends FirebaseRecyclerAdapter<lcMainModel,lcMainAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public lcMainAdapter(@NonNull @NotNull FirebaseRecyclerOptions<lcMainModel> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull @NotNull myViewHolder holder,final int position, @NonNull @NotNull lcMainModel model) {
        holder.sertype.setText(model.getSertype());
        holder.cartype.setText(model.getCartype());
        holder.fuelexpert.setText(model.getFuelexpert());
        holder.picktime.setText(model.getPicktime());
        holder.delitime.setText(model.getDelitime());
        holder.extrachar.setText(model.getExtrachar());

        Glide.with(holder.img.getContext())
                .load(model.getSurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.e)
                .into(holder.img);


        //update Operation
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.lcupdate_popup))
                        .setExpanded(true,1500)
                        .create();

                //dialogPlus.show();

                View view = dialogPlus.getHolderView();

                EditText sertype = view.findViewById(R.id.sertypetext);
                EditText cartype = view.findViewById(R.id.cartypetext);
                EditText fuelexpert = view.findViewById(R.id.fuelexperttext);
                EditText picktime = view.findViewById(R.id.picktimetext);
                EditText delitime = view.findViewById(R.id.delitimetext);
                EditText extrachar = view.findViewById(R.id.extrachartext);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                sertype.setText(model.getSertype());
                cartype.setText(model.getCartype());
                fuelexpert.setText(model.getFuelexpert());
                picktime.setText(model.getPicktime());
                delitime.setText(model.getDelitime());
                extrachar.setText(model.getExtrachar());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("sertype",sertype.getText().toString());
                        map.put("cartype",cartype.getText().toString());
                        map.put("fuelexpert",fuelexpert.getText().toString());
                        map.put("picktime",picktime.getText().toString());
                        map.put("delitime",delitime.getText().toString());
                        map.put("extrachar",extrachar.getText().toString());


                        //Calculation
                        Integer deltimeno = Integer.valueOf(delitime.getText().toString());
                        Integer picktimeno = Integer.valueOf(picktime.getText().toString());

                        String extrachar = String.valueOf((deltimeno - picktimeno) * 500);
                        map.put("extrachar", String.valueOf(extrachar));



                        FirebaseDatabase.getInstance("https://carserviceapp-fb926-default-rtdb.firebaseio.com/")
                                .getReference().child("vehicles")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.sertype.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.sertype.getContext(), "Error Occured On Update Data", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });

            }
        });


        //Delete Operation
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.sertype.getContext());
                builder.setTitle("Are You Sure?");
                builder.setMessage("Deleted data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //After click delete button dlete data
                        FirebaseDatabase.getInstance("https://carserviceapp-fb926-default-rtdb.firebaseio.com/")
                                .getReference().child("vehicles")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.sertype.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        });



    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lcmain_item,parent,false);
        return new myViewHolder(view);
    }



    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView sertype, cartype, fuelexpert, picktime, delitime, extrachar, surl;

        Button btnEdit, btnDelete;

        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            sertype = (TextView)itemView.findViewById(R.id.sertypetext);
            cartype = (TextView)itemView.findViewById(R.id.cartypetext);
            fuelexpert = (TextView)itemView.findViewById(R.id.fuelexperttext);
            picktime = (TextView)itemView.findViewById(R.id.picktimetext);
            delitime = (TextView)itemView.findViewById(R.id.delitimetext);
            extrachar = (TextView)itemView.findViewById(R.id.extrachartext);

            btnEdit = (Button)itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button)itemView.findViewById(R.id.btnDelete);


        }
    }
}




















