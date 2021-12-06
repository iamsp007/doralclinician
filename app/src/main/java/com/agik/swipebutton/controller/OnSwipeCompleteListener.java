package com.agik.swipebutton.controller;

import com.agik.swipebutton.view.Swipe_Button_View;

/**
 * Created by Gratus on 02/10/18.
 */

public interface OnSwipeCompleteListener {
    void onSwipe_Forward(Swipe_Button_View swipe_button_view);
    void onSwipe_Reverse(Swipe_Button_View swipe_button_view);
}
