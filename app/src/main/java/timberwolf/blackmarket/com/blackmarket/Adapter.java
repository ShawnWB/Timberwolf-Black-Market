package timberwolf.blackmarket.com.blackmarket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Adapter  extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    private LayoutInflater inflater;
    protected List<Store> data = new ArrayList<Store>();

    public void addEntity(int i, Store entity){
        data.add(i, entity);
        notifyItemInserted(i);
    }

    public void deleteEntity(int i) {
        data.remove(i);
        notifyItemRemoved(i);
    }

    public void moveEntity(int i, int loc) {
        move(data, i, loc);
        notifyItemMoved(i, loc);
    }

    private void move(List<Store> dr, int a, int b) {
        Store temp = dr.remove(a);
        dr.add(b, temp);
    }

    public void setData(final List<Store> dr) {
        // Remove all deleted items.
        for (int i = data.size() - 1; i >= 0; --i) {
            if (getLocation(dr, data.get(i)) < 0) {
                deleteEntity(i);
            }
        }
        for (int i = 0; i < dr.size(); ++i) {
            Store entity = dr.get(i);
            int loc = getLocation(data, entity);
            if (loc < 0) {
                addEntity(i, entity);
            } else if (loc != i) {
                moveEntity(i, loc);

            }
        }
    }

    private int getLocation(List<Store> dr, Store entity) {
        for (int j = 0; j < dr.size(); ++j) {
            Store newEntity = dr.get(j);
            if (entity.equals(newEntity)) {
                return j;
            }
        }

        return -1;
    }

    public Adapter(Context context) {
        inflater=LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = inflater.inflate(R.layout.store_row, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Store current=data.get(position);

        holder.test.setText(current.test);
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView test;
        Button testb;

        public MyViewHolder(View itemView) {

            super(itemView);

            test = (TextView) itemView.findViewById(R.id.test);
            testb = (Button) itemView.findViewById(R.id.testb);
        }
    }
}
