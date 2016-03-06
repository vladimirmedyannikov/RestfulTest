package ru.medyannikov.restfultest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.medyannikov.restfultest.CupboardTest.User;

/**
 * Created by Vladimir on 18.09.2015.
 */
public class RecycleAdapterUser extends RecyclerView.Adapter<RecycleAdapterUser.ViewHolder> {
    private List<User> userList;

    public RecycleAdapterUser(List<User> userList, RecyclerView recyclerView) {
        this.userList = userList;

    }

    @Override
    public RecycleAdapterUser.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_user_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecycleAdapterUser.ViewHolder holder, int position) {
        holder.name.setText(userList.get(position).getName());
        holder.email.setText(userList.get(position).getEmail());
        holder.token.setText(userList.get(position).getToken());
    }

    @Override
    public int getItemCount(){
        if (userList != null){
            return userList.size();
        }
        else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView email;
        public TextView token;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.userName);
            email = (TextView) itemView.findViewById(R.id.userEmail);
            token = (TextView) itemView.findViewById(R.id.userToken);
        }
    }
}
