package com.harrywhewell.scrolldatepicker;



import androidx.annotation.Nullable;

import org.joda.time.LocalDate;

public interface OnChildDateSelectedListener {
    void onDateSelectedChild(@Nullable LocalDate date);
}
