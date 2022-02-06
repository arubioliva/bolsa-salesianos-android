package com.example.bolsasalesianos.activities;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.bolsasalesianos.R;
import com.example.bolsasalesianos.adapters.VPAdapter;
import com.example.bolsasalesianos.ui.home.register.EnterpriseRegisterFragment;
import com.example.bolsasalesianos.ui.home.register.StudentRegisterFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;

public class RegisterActivity extends GenericActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tabLayout = findViewById(R.id.register_tabs);
        viewPager = findViewById(R.id.view_pager);

        tabLayout.setupWithViewPager(viewPager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new StudentRegisterFragment(), "Registro estudiante");
        vpAdapter.addFragment(new EnterpriseRegisterFragment(), "Registro empresa");
        viewPager.setAdapter(vpAdapter);

    }
}