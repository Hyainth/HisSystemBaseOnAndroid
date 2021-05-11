package com.example.hospital.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.adapter.UserDepAdapter;
import com.example.hospital.dao.DepartmentDao;
import com.example.hospital.table.Department;
import com.example.hospital.util.RecycleViewDivider;

public class DepFragment extends Fragment {
    private RecyclerView rv;
    private UserDepAdapter adapter;
    private DepartmentDao departmentDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dep_fragment,null);

        rv=(RecyclerView)view.findViewById(R.id.rv);


        departmentDao=new DepartmentDao(getActivity());
        adapter=new UserDepAdapter(getActivity(),departmentDao.getAllDepartment());
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        adapter.setUpdateImageListener(new UserDepAdapter.OnUpdateImageClickListener() {
            @Override
            public void onUpdateImageClick(View view, int position) {
                Department department=adapter.getList().get(position);

                Uri uri = Uri.parse("tel:"+department.getPhone());
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });


        return view;
    }
}
