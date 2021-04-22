package com.ld.baselibrary.binding.conversion;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.databinding.BindingConversion;

public class Conversion {

    /**
     * 将字符串颜色值转化为ColorDrawable
     *
     * @param colorString 如：#ff0000
     *                    <p>
     *                    <View
     *                    android:layout_marginTop="30dp"
     *                    android:layout_width="50dp"
     *                    android:layout_height="50dp"
     *                    android:background="@{isColor?@string/gray:@string/blue}" />
     *
     *                    <string name="gray">#e6e6e6</string>
     *                    <string name="blue">#00008b</string>
     * @return
     */
    @BindingConversion
    public static ColorDrawable convertColorToDrawable(String colorString) {
        int color = Color.parseColor(colorString);
        return new ColorDrawable(color);
    }

    /**
     * 设置资源颜色
     *
     * @param color @color/white
     *
     * android:textColor="@{@color/grey_666666}"
     *
     * @return
     */
    @BindingConversion
    public static ColorDrawable convertColorToDrawable(int color) {
        return new ColorDrawable(color);
    }
}
