package com.example.hospital.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hospital.R;
import com.example.hospital.adapter.UserProjAdapter;
import com.example.hospital.dao.ProjectDao;
import com.example.hospital.table.Project;
import com.example.hospital.util.GobalVar;
import com.example.hospital.util.RecycleViewDivider;
import com.example.hospital.view.Order_Save;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ProjFragment extends Fragment {
    private RecyclerView rv;
    private UserProjAdapter adapter;
    private ProjectDao projectDao;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.proj_fragment,null);

        rv=(RecyclerView)view.findViewById(R.id.rv);

        projectDao=new ProjectDao(getActivity());
        adapter=new UserProjAdapter(getActivity(),projectDao.getAllProject());
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        adapter.setUpdateImageListener(new UserProjAdapter.OnUpdateImageClickListener() {
            @Override
            public void onUpdateImageClick(View view, int position) {
                Project project=adapter.getList().get(position);
                Intent intent=new Intent(getActivity(), Order_Save.class);
                intent.putExtra("mode","user");
                intent.putExtra("mebID", GobalVar.login_account);
                intent.putExtra("projID",project.getProjID());
                startActivityForResult(intent,1);
            }
        });

        return view;
    }
}
