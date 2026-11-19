package com.example.lab5_bai1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lab5_bai1.databinding.FragmentFirstBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    // Giong Lab 4: SharedPreferences luu trang thai "Nho dang nhap"
    public static final String PREFS_NAME = "LoginPrefs";
    public static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_USER_ID = "userId";

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = requireActivity()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Da nho dang nhap tu lan truoc -> vao thang man hinh chinh (giong Lab 4)
        if (prefs.getBoolean(KEY_IS_LOGGED_IN, false)) {
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
            return;
        }

        // Hien lai username da nho (neu co)
        String savedUser = prefs.getString(KEY_USERNAME, "");
        if (!savedUser.isEmpty()) {
            binding.edtUsername.setText(savedUser);
            binding.cbRemember.setChecked(true);
        }

        binding.btnLogin.setOnClickListener(v -> doLogin(prefs));
    }

    private void doLogin(SharedPreferences prefs) {
        String username = binding.edtUsername.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            binding.tvStatus.setText("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!");
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.tvStatus.setText("");
        binding.btnLogin.setEnabled(false);

        // Buoc 5 Lab 5: tao instance MyAPI tu Retrofit client va goi API bat dong bo
        MyAPI myAPI = RetrofitClient.getApi();
        Call<LoginResponse> call = myAPI.login(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call,
                                   @NonNull Response<LoginResponse> response) {
                if (binding == null) return;
                binding.progressBar.setVisibility(View.GONE);
                binding.btnLogin.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse result = response.body();
                    if (result.isSuccess()) {
                        // Giong Lab 4: neu tick "Nho dang nhap" thi luu lai
                        SharedPreferences.Editor editor = prefs.edit();
                        if (binding.cbRemember.isChecked()) {
                            editor.putBoolean(KEY_IS_LOGGED_IN, true);
                            editor.putString(KEY_USERNAME, result.getUsername());
                            editor.putInt(KEY_USER_ID, result.getId());
                        } else {
                            editor.clear();
                            // Van giu username de tien dang nhap lan sau? Lab 4 goc thi khong luu.
                            editor.putString(KEY_USERNAME, result.getUsername());
                        }
                        editor.apply();

                        Toast.makeText(getContext(), result.getMessage(),
                                Toast.LENGTH_SHORT).show();

                        // Truyen username sang man hinh 2
                        Bundle bundle = new Bundle();
                        bundle.putString("username", result.getUsername());
                        bundle.putInt("userId", result.getId());
                        NavHostFragment.findNavController(FirstFragment.this)
                                .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
                    } else {
                        binding.tvStatus.setText(result.getMessage());
                        Toast.makeText(getContext(), result.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.tvStatus.setText("Lỗi server! Mã: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                if (binding == null) return;
                binding.progressBar.setVisibility(View.GONE);
                binding.btnLogin.setEnabled(true);
                binding.tvStatus.setText("Lỗi kết nối: " + t.getMessage()
                        + "\n(Kiểm tra XAMPP + URL trong RetrofitClient)");
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
