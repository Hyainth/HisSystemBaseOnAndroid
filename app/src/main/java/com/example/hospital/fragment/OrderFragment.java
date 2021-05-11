package com.example.hospital.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hospital.R;
import com.example.hospital.adapter.UserOrderAdapter;
import com.example.hospital.dao.OrderDao;
import com.example.hospital.util.GobalVar;
import com.example.hospital.util.RecycleViewDivider;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderFragment extends Fragment {
    private OrderDao orderDao;
    private UserOrderAdapter adapter;
    private RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.order_fragment,null);

        rv=(RecyclerView)view.findViewById(R.id.rv);

        orderDao=new OrderDao(getActivity());
        adapter=new UserOrderAdapter(getActivity(),orderDao.getMemberOrder(GobalVar.login_account));
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        return view;
    }
}
