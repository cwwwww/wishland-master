package com.wishland.www.wanhaohui2.view.customlayout;

import android.app.Activity;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import cn.addapp.pickers.adapter.ArrayWheelAdapter;
import cn.addapp.pickers.common.LineConfig;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnSingleWheelListener;
import cn.addapp.pickers.picker.WheelPicker;
import cn.addapp.pickers.util.ConvertUtils;
import cn.addapp.pickers.widget.WheelListView;
import cn.addapp.pickers.widget.WheelView;

/**
 * Created by admin on 2017/10/27.
 */

public class MyPicker<T> extends WheelPicker {
    private static final int ITEM_WIDTH_UNKNOWN = -99;
    private List<T> items;
    private List<String> itemStrings;
    private WheelListView wheelListView;
    private WheelView wheelView;
    private float weightWidth;
    private OnSingleWheelListener onSingleWheelListener;
    private OnItemPickListener<T> onItemPickListener;
    private int selectedItemIndex;
    private String selectedItem;
    private String label;
    private int itemWidth;

    public MyPicker(Activity activity, T[] items) {
        this(activity, Arrays.asList(items));
    }

    public MyPicker(Activity activity, List<T> items) {
        super(activity);
        this.items = new ArrayList();
        this.itemStrings = new ArrayList();
        this.weightWidth = 0.0F;
        this.selectedItemIndex = 0;
        this.selectedItem = "";
        this.label = "";
        this.itemWidth = -99;
        this.setItems(items);
    }

    public void addItem(T item) {
        this.items.add(item);
        this.itemStrings.add(this.formatToString(item));
    }

    public void removeItem(T item) {
        this.items.remove(item);
        this.itemStrings.remove(this.formatToString(item));
    }

    public void setItems(T[] items) {
        this.setItems(Arrays.asList(items));
    }

    public void setItems(List<T> items) {
        if (null != items && items.size() != 0) {
            this.items = items;
            Iterator var2 = items.iterator();

            while (var2.hasNext()) {
                Object item = var2.next();
                this.itemStrings.add(this.formatToString((T) item));
            }

            if (this.wheelModeEnable) {
                if (null != this.wheelView) {
                    this.wheelView.setAdapter(new ArrayWheelAdapter(this.itemStrings));
                    this.wheelView.setCurrentItem(this.selectedItemIndex);
                }
            } else if (null != this.wheelListView) {
                this.wheelListView.setItems(this.itemStrings, this.selectedItemIndex);
            }

        }
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setSelectedIndex(int index) {
        if (index >= 0 && index < this.items.size()) {
            this.selectedItemIndex = index;
        }

    }

    public void setSelectedItem(@NonNull T item) {
        this.setSelectedIndex(this.itemStrings.indexOf(this.formatToString(item)));
    }

    public void setWeightWidth(@FloatRange(from = 0.0D, to = 1.0D) float weightWidth) {
        if (weightWidth < 0.0F) {
            weightWidth = 0.0F;
        }

        if (!TextUtils.isEmpty(this.label) && weightWidth >= 1.0F) {
            weightWidth = 0.5F;
        }

        this.weightWidth = weightWidth;
    }

    public void setItemWidth(int itemWidth) {
        int width;
        if (this.wheelModeEnable) {
            if (null != this.wheelView) {
                width = ConvertUtils.toPx(this.activity, (float) itemWidth);
                this.wheelView.setLayoutParams(new LinearLayout.LayoutParams(width, this.wheelListView.getLayoutParams().height));
            } else {
                this.itemWidth = itemWidth;
            }
        } else if (null != this.wheelListView) {
            width = ConvertUtils.toPx(this.activity, (float) itemWidth);
            this.wheelListView.setLayoutParams(new LinearLayout.LayoutParams(width, this.wheelListView.getLayoutParams().height));
        } else {
            this.itemWidth = itemWidth;
        }

    }

    public void setOnSingleWheelListener(OnSingleWheelListener onSingleWheelListener) {
        this.onSingleWheelListener = onSingleWheelListener;
    }

    public void setOnItemPickListener(OnItemPickListener<T> listener) {
        this.onItemPickListener = listener;
    }

    @NonNull
    protected View makeCenterView() {
        if (this.items.size() == 0) {
            throw new IllegalArgumentException("please initial items at first, can\'t be empty");
        } else {
            LinearLayout layout = new LinearLayout(this.activity);
            layout.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -2));
            layout.setOrientation(0);
            layout.setGravity(17);
            LinearLayout.LayoutParams wheelParams = new LinearLayout.LayoutParams(-2, -2);
            if (this.weightEnable) {
                layout.setWeightSum(1.0F);
                wheelParams.weight = this.weightWidth;
            }

            TextView width;
            int width1;
            if (this.wheelModeEnable) {
                this.wheelView = new WheelView(this.activity);
                this.wheelView.setAdapter(new ArrayWheelAdapter(this.itemStrings));
                this.wheelView.setCurrentItem(this.selectedItemIndex);
                this.wheelView.setCanLoop(this.canLoop);
                this.wheelView.setLineConfig(this.lineConfig);
                this.wheelView.setDividerType(LineConfig.DividerType.FILL);
                this.wheelView.setOnItemPickListener(new OnItemPickListener() {
                    @Override
                    public void onItemPicked(int i, Object item) {
                        MyPicker.this.selectedItem = (String) item;
                        MyPicker.this.selectedItemIndex = i;
                        if (MyPicker.this.onSingleWheelListener != null) {
                            MyPicker.this.onSingleWheelListener.onWheeled(MyPicker.this.selectedItemIndex, MyPicker.this.selectedItem);
                        }

                    }
                });
                if (TextUtils.isEmpty(this.label)) {
                    this.wheelView.setLayoutParams(wheelParams);
                    layout.addView(this.wheelView);
                } else {
                    this.wheelView.setLayoutParams(wheelParams);
                    layout.addView(this.wheelView);
                    width = new TextView(this.activity);
                    width.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
                    width.setTextColor(this.textColorFocus);
                    width.setTextSize((float) this.textSize);
                    width.setText(this.label);
                    layout.addView(width);
                }

                if (this.itemWidth != -99) {
                    width1 = ConvertUtils.toPx(this.activity, (float) this.itemWidth);
                    this.wheelView.setLayoutParams(new LinearLayout.LayoutParams(width1, this.wheelView.getLayoutParams().height));
                }
            } else {
                this.wheelListView = new WheelListView(this.activity);
                this.wheelListView.setTextSize(this.textSize);
                this.wheelListView.setSelectedTextColor(this.textColorFocus);
                this.wheelListView.setUnSelectedTextColor(this.textColorNormal);
                this.wheelListView.setLineConfig(this.lineConfig);
                this.wheelListView.setOffset(this.offset);
                this.wheelListView.setCanLoop(this.canLoop);
                this.wheelListView.setItems(this.itemStrings, this.selectedItemIndex);
                this.wheelListView.setOnWheelChangeListener(new WheelListView.OnWheelChangeListener() {
                    public void onItemSelected(boolean isUserScroll, int index, String item) {
                        MyPicker.this.selectedItemIndex = index;
                        MyPicker.this.selectedItem = item;
                        if (MyPicker.this.onSingleWheelListener != null) {
                            MyPicker.this.onSingleWheelListener.onWheeled(MyPicker.this.selectedItemIndex, item);
                        }

                    }
                });
                if (TextUtils.isEmpty(this.label)) {
                    this.wheelListView.setLayoutParams(wheelParams);
                    layout.addView(this.wheelListView);
                } else {
                    this.wheelListView.setLayoutParams(wheelParams);
                    layout.addView(this.wheelListView);
                    width = new TextView(this.activity);
                    width.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
                    width.setTextColor(this.textColorFocus);
                    width.setTextSize((float) this.textSize);
                    width.setText(this.label);
                    layout.addView(width);
                }

                if (this.itemWidth != -99) {
                    width1 = ConvertUtils.toPx(this.activity, (float) this.itemWidth);
                    this.wheelListView.setLayoutParams(new LinearLayout.LayoutParams(width1, this.wheelListView.getLayoutParams().height));
                }
            }

            return layout;
        }
    }

    private String formatToString(T item) {
        return !(item instanceof Float) && !(item instanceof Double) ? item.toString() : (new DecimalFormat("0.00")).format(item);
    }

    public void onSubmit() {
        if (this.onItemPickListener != null) {
            this.onItemPickListener.onItemPicked(this.getSelectedIndex(), this.getSelectedItem());
        }

    }

    private T getSelectedItem() {
        return this.items.get(this.selectedItemIndex);
    }

    public int getSelectedIndex() {
        return this.selectedItemIndex;
    }

    public void removeAllItem() {
        this.items.clear();
        this.items.add((T) "暂无");
//        this.formatToString((T) this.items);
        this.itemStrings.clear();
        this.itemStrings.add("暂无");
//        if (this.wheelListView != null) {
//            this.wheelListView.setItems(this.itemStrings, 0);
//        }
        this.selectedItemIndex = 0;
        if (this.wheelModeEnable) {
            if (null != this.wheelView) {
                this.wheelView.setAdapter(new ArrayWheelAdapter(this.itemStrings));
                this.wheelView.setCurrentItem(0);
            }
        } else if (null != this.wheelListView) {
            this.wheelListView.setItems(this.itemStrings, 0);
        }
    }
}
