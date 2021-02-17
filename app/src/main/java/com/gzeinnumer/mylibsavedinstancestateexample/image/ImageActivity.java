package com.gzeinnumer.mylibsavedinstancestateexample.image;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gzeinnumer.kus.StateUI;
import com.gzeinnumer.kus.StateUIBuilder;
import com.gzeinnumer.mylibsavedinstancestateexample.MenuActivity;
import com.gzeinnumer.mylibsavedinstancestateexample.R;
import com.gzeinnumer.mylibsavedinstancestateexample.databinding.ActivityImageBinding;
import com.gzeinnumer.mylibsavedinstancestateexample.utils.CustomToastDown;
import com.gzeinnumer.mylibsavedinstancestateexample.utils.CustomToastUp;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {
    private StateUI stateUI;
    private ActivityImageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        stateUI = StateUIBuilder.Build(ImageActivity.class, getApplicationContext());

        binding.btnLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });

        binding.btnClearBack.setOnClickListener(v -> {
            stateUI.clearState();
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
        });

        binding.btnSaveBack.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            finish();
        });
    }

    private void loadImage() {
        String imgUrl = "https://avatars3.githubusercontent.com/u/45892408?s=460&u=94158c6479290600dcc39bc0a52c74e4971320fc&v=4";
        Glide.with(this).load(imgUrl).error(R.mipmap.ic_launcher).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                try {
                    stateUI.addViewBitmap("binding.img", (BitmapDrawable) resource);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }).into(binding.img);

        Picasso.get().load(imgUrl).into(binding.img, new Callback() {
            @Override
            public void onSuccess() {
                try {
                    stateUI.addViewBitmap("binding.img", (BitmapDrawable) binding.img.getDrawable());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        new CustomToastUp(getApplicationContext(), "Image_onPause\nData Save To State");

        stateUI.saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stateUI.getState()) {
            new CustomToastDown(getApplicationContext(), "Image_onResume\nData Loaded From State");

            Bitmap bitmap = stateUI.getValueBitmap("binding.img");
            if (bitmap != null) {
                binding.img.setImageBitmap(bitmap);
            }
        }
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        stateUI.clearState();
//    }
}