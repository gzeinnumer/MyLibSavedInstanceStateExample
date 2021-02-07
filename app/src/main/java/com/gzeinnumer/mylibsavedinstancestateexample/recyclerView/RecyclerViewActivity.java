package com.gzeinnumer.mylibsavedinstancestateexample.recyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.reflect.TypeToken;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.helper.BindViewHolder;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.singleType.AdapterBuilder;
import com.gzeinnumer.mylibrecyclerviewadapterbuilder.singleType.AdapterCreator;
import com.gzeinnumer.mylibsavedinstancestate.ListStateCallBack;
import com.gzeinnumer.mylibsavedinstancestate.ListStateReceiver;
import com.gzeinnumer.mylibsavedinstancestate.StateUI;
import com.gzeinnumer.mylibsavedinstancestate.StateUIBuilder;
import com.gzeinnumer.mylibsavedinstancestateexample.MenuActivity;
import com.gzeinnumer.mylibsavedinstancestateexample.R;
import com.gzeinnumer.mylibsavedinstancestateexample.databinding.ActivityRecyclerViewBinding;
import com.gzeinnumer.mylibsavedinstancestateexample.databinding.ItemRvBinding;
import com.gzeinnumer.mylibsavedinstancestateexample.utils.CustomToastDown;
import com.gzeinnumer.mylibsavedinstancestateexample.utils.CustomToastUp;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private ActivityRecyclerViewBinding binding;
    private List<MyModel> list = new ArrayList<>();
    private StateUI stateUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecyclerViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        stateUI = StateUIBuilder.Build(RecyclerViewActivity.class, getApplicationContext());

        binding.btnGenerateData.setOnClickListener(view -> {
            generateData();
        });
        binding.btnClearBack.setOnClickListener(view -> {
            stateUI.clearState();
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
        });
        binding.btnSaveBack.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            finish();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        new CustomToastUp(getApplicationContext(), "RecyclerView_onPause\nData Save To State");

        stateUI.addViewList("binding.rv", new ListStateReceiver<MyModel>() {
            @Override
            public List<MyModel> listReceived() {
                return list;
            }
        });
        stateUI.saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stateUI.getState()) {
            new CustomToastDown(getApplicationContext(), "RecyclerView_onResume\nData Loaded From State");

            stateUI.getValueList("binding.rv", new ListStateCallBack<MyModel>() {
                @Override
                public Type setListModel() {
                    return new TypeToken<List<MyModel>>() {
                    }.getType();
                }

                @Override
                public void listCallBack(List<MyModel> listFromState) {
                    list = new ArrayList<>(listFromState);
                    initAdapter();
                }
            });
        }
    }

    private void generateData() {
        for (int i = 0; i < 10; i++) {
            list.add(new MyModel(false, "Data " + i));
        }

        initAdapter();
    }

    private void initAdapter() {
        // I am using MyOwnlibrary to make AdapterRecyclerView
        // ReadMore on https://github.com/gzeinnumer/MyLibRecyclerViewAdapterBuilder
        // and also support MultiType and Filter Data
        AdapterCreator<MyModel> adapter = new AdapterBuilder<MyModel>(R.layout.item_rv)
                .setList(list)
                .onBind(new BindViewHolder<MyModel>() {
                    @Override
                    public void bind(View holder, MyModel data, int position) {
                        ItemRvBinding bindingItem = ItemRvBinding.bind(holder);
                        bindingItem.tv.setText(data.getName());

                        bindingItem.cb.setChecked(data.isChecked());

                        bindingItem.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                list.get(position).setChecked(isChecked);
                            }
                        });
                    }
                });

        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.rv.hasFixedSize();
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        stateUI.clearState();
//    }
}