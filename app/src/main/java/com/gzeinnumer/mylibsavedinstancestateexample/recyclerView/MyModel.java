package com.gzeinnumer.mylibsavedinstancestateexample.recyclerView;

public class MyModel {
    private boolean isChecked;
    private String name;

    public MyModel(boolean isChecked, String name) {
        this.isChecked = isChecked;
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyModel{" +
                "isChecked=" + isChecked +
                ", name='" + name + '\'' +
                '}';
    }
}
