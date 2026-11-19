package com.example.lab5_bai1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lab5_bai1.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Nhan thong tin tu man hinh login
        Bundle args = getArguments();
        String username = "";
        int userId = -1;
        if (args != null) {
            username = args.getString("username", "");
            userId = args.getInt("userId", -1);
        }
        // Neu khong co args (auto-login nho dang nhap) thi doc tu SharedPreferences
        if (username.isEmpty()) {
            username = requireActivity()
                    .getSharedPreferences(FirstFragment.PREFS_NAME, Context.MODE_PRIVATE)
                    .getString(FirstFragment.KEY_USERNAME, "bạn");
            userId = requireActivity()
                    .getSharedPreferences(FirstFragment.PREFS_NAME, Context.MODE_PRIVATE)
                    .getInt(FirstFragment.KEY_USER_ID, -1);
        }

        binding.tvUserInfo.setText("Xin chào: " + username
                + (userId >= 0 ? " (id=" + userId + ")" : ""));

        binding.btnLogout.setOnClickListener(v -> {
            // Dang xuat: xoa trang thai nho dang nhap roi ve man hinh login
            requireActivity()
                    .getSharedPreferences(FirstFragment.PREFS_NAME, Context.MODE_PRIVATE)
                    .edit()
                    .putBoolean(FirstFragment.KEY_IS_LOGGED_IN, false)
                    .apply();
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_FirstFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
