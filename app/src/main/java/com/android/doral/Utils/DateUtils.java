package com.android.doral.Utils;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.widget.Button;
import android.widget.TimePicker;


import com.android.doral.R;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 09-Apr-16.
 */
public class DateUtils {
    public static final String
            DATE_FORMATE_1 = "MM/dd/yyyy",              // 25/12/2016
            DATE_FORMATE_2 = "E, MMM dd,yyyy",          // Sat, Apr 09,2016
            DATE_FORMATE_3 = "HH:mm:ss",                // 24 hour time format
            DATE_FORMATE_4 = "yyyy-MM-dd HH:mm:ss",     // 24 hour time format
            DATE_FORMATE_5 = "EEEE dd.MM.yyyy",         // Sunday 25.12.2016
            DATE_FORMATE_6 = "E, MMM dd,yyyy hh:mm a",  // Sat, Mar 25,2016 11:30 PM
            DATE_FORMATE_7 = " MMM dd,yyyy EEEE",          // Mar 17,2017 Sunday
            DATE_FORMATE_12 = " MMM dd,yyyy EEE",          // Mar 17,2017 Sun
            DATE_FORMATE_8 = "yyyy-MM-dd",            // 2016-12-25
            DATE_FORMATE_9 = "MMM yyyy", // 2016-12-25
            DATE_FORMATE_10 = "MM",
            DATE_FORMATE_11 = "MMM dd,yyyy hh:mm a",// Mar 25,2016 11:30 PM
            DATE_FORMATE_14 = "dd MMM yyyy hh:mm a",
            DATE_FORMATE_15 = "hh:mm a",
            DATE_FORMATE_13 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String TAG = "DateUtils";

    /**
     * By Bhavesh<br/>
     * <p/>
     * e.g.
     * <br/>Give time like 1:3 0
     * <br/>Return time 01:03 AM in String format
     */
    public static String timeFormat_HH_MM_PM(int hours, int minutes, int amPm) {
        // Format Hours, Minutes and AMPM
        String strHour = (hours >= 0 && hours < 10) ? "0" + hours : "" + hours;
        String strMinute = (minutes >= 0 && minutes < 10) ? "0" + minutes : "" + minutes;
        String strAmPm = (amPm == 0) ? "AM" : "PM";

        // If time is like this : 00:00 PM
        // Then convert it to   : 12:00 PM
        strHour = strHour.equals("00") ? "12" : "" + strHour;

        return strHour + ":" + strMinute + " " + strAmPm; // e.g. 03:09 PM
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * <br/>Give 2 pattern
     * <br/>Return String of second pattern
     * <p/>
     * e.g.
     * <br/>From : 2016-05-31 15:30:00
     * <br/>To   : 31-05-2016 03:30 PM
     */
    public static String converDateToMiliInString(String dateFormate, String strDate) {

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormate, Locale.getDefault());
        formatter.setLenient(false);
        Date d = null;
        long curMillis;
        try {
            d = formatter.parse(strDate);
            curMillis = d.getTime();
        } catch (Exception ex) {
            Log.v("Exception", ex.getLocalizedMessage());
            curMillis = 12345678910L;
        }

        return String.valueOf(curMillis);

    }

    public static String changeDateFormat(String fromPattern, String toPattern, String date, boolean isUTC) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(fromPattern, Locale.getDefault());
            if (isUTC) {
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            }
            Date d = dateFormat.parse(date);
            return new SimpleDateFormat(toPattern).format(d);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * e.g.
     * <br/>Give date value in integer
     * <br/>Return date in String format
     * <br/>e.g. Sat, Apr 09,2016
     */
    public static String dateFormat(int day, int month, int year) {
        String strMonths = "";

        // Set month
        strMonths = (month >= 0 && month < 9) ? "0" + (month + 1) : (month + 1) + ""; //

        String selected_date = day + "/" + strMonths + "/" + year;

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE_1, Locale.US);
        Date date = null;
        try {
            date = sdf.parse(selected_date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMATE_2, Locale.US);
        return formatter.format(date);
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * e.g.
     * <br/>Give time in 24 hour. For e.g. 14:30:00
     * <br/>Return time in String format for e.g. 02:30:00
     */
    public static String convertFrom24To12Hours(String _24HourTime) {
        String _12HourTime = "";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATE_3, Locale.US); // 24 hour time format

        try {
            Date d = dateFormat.parse(_24HourTime); // 14:30:00
            calendar.setTime(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int hours = calendar.get(Calendar.HOUR); // 12 hour time
        int minutes = calendar.get(Calendar.MINUTE);
        int am_pm = calendar.get(Calendar.AM_PM);

        _12HourTime = timeFormat_HH_MM_PM(hours, minutes, am_pm); // 02:30 PM

        return _12HourTime;
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * Calculate time between two dates
     * <p/>
     * For e.g.
     * <p/>
     * Return 1 day ago, 1 minute ago, 2 years ago
     */
    public static String calculateTimeBetweenTwoDates(String startDate, String endDate, boolean shortDescription,boolean isUTC) {
        /**
         * **** Easy Use ****
         * String currDate = new SimpleDateFormat(DateUtils.DATE_FORMATE_4).format(Calendar.getInstance().getTime());
         * String finalTime = DateUtils.calculateTimeBetweenTwoDates(list.get(position).getDatetime(), currDate);
         */

        String finalTimeString = "";

        String ago = "ago";
        String returnMin = "minute";
        String returnHour = "hour";
        String returnDay = "day";
        String returnMonth = "month";
        String returnYear = "year";

        if (shortDescription) {
            // Need short description
            ago = "";
            returnMin = "min";
            returnHour = "hour";
            returnDay = "day";
            returnMonth = "month";
            returnYear = "year";
        }


        SimpleDateFormat myFormat = new SimpleDateFormat(DATE_FORMATE_4, Locale.US); // 24 hours format

        if (isUTC) {
            myFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        try {
            Date date1 = myFormat.parse(startDate);
            Date date2 = myFormat.parse(endDate);
            long diff = date2.getTime() - date1.getTime();

            long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            long hours = TimeUnit.MILLISECONDS.toHours(diff);
            long days = TimeUnit.MILLISECONDS.toDays(diff);
            long months = days / 30;
            long year = months / 12;

            if (seconds < 60) {
                finalTimeString = "Now";
            } else if (minutes < 60) {
                if (minutes == 1) {
                    finalTimeString = minutes + " " + returnMin + " " + ago;
                } else {
                    finalTimeString = minutes + " " + returnMin + "s " + ago;
                }
            } else if (hours < 24) {
                if (hours == 1) {
                    finalTimeString = hours + " " + returnHour + " " + ago;
                } else {
                    finalTimeString = hours + " " + returnHour + "s " + ago;
                }
            } else if (days < 30) {
                if (days == 1) {
                    finalTimeString = days + " " + returnDay + " " + ago;
                } else {
                    finalTimeString = days + " " + returnDay + "s " + ago;
                }
            } else if (months < 12) {
                if (months == 1) {
                    finalTimeString = months + " " + returnMonth + " " + ago;
                } else {
                    finalTimeString = months + " " + returnMonth + "s " + ago;
                }
            } else {
                if (year == 1) {
                    finalTimeString = year + " " + returnYear + " " + ago;
                } else {
                    finalTimeString = year + " " + returnYear + "s " + ago;
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return finalTimeString;
    }

    public static String printDifference(String startDate, String endDate) {

        String finalTimeString = "";
        String returnMin = "min";
        String returnHour = "hour";
        String returnDay = "day";
        String returnMonth = "month";

        //milliseconds

        SimpleDateFormat myFormat = new SimpleDateFormat(DATE_FORMATE_4, Locale.US); // 24 hours format


        try {
            Date date1 = myFormat.parse(startDate);
            Date date2 = myFormat.parse(endDate);
            long different = date2.getTime() - date1.getTime();

            System.out.println("startDate : " + startDate);
            System.out.println("endDate : " + endDate);
            System.out.println("different : " + different);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;
            long monthsInMilli = daysInMilli * 30;

            long elapsedmonths = different / monthsInMilli;
            different = different % monthsInMilli;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            System.out.printf(
                    "%dmonths %d days, %d hours, %d minutes, %d seconds%n",
                    elapsedmonths,
                    elapsedDays,
                    elapsedHours, elapsedMinutes, elapsedSeconds);

            if (elapsedmonths >= 1) {
                if (elapsedmonths == 1) {
                    finalTimeString = elapsedmonths + " " + returnMonth + " ";
                } else {
                    finalTimeString = elapsedmonths + " " + returnMonth + "s ";
                }

            } else {
                /*elese of months*/
                if (elapsedDays >= 1) {

                    if (elapsedDays == 1) {
                        finalTimeString = elapsedDays + " " + returnDay + " ";
                    } else {
                        finalTimeString = elapsedDays + " " + returnDay + "s ";
                    }
                } else {
                    /*===== else of days ====*/
                    if (elapsedHours == 23 && elapsedMinutes > 30) {
                        finalTimeString = 1 + " " + returnDay + " ";
                    } else {
                        /*else of 23 and 30*/
                        /************ hours 1 and minutes less tehn 30*/
                        if (elapsedHours >= 1) {

                            if (elapsedHours == 1 && elapsedMinutes > 30) {
                                finalTimeString = 2 + " " + returnHour + "s ";
                            } else {
                                if (elapsedHours == 1) {
                                    finalTimeString = elapsedHours + " " + returnHour + " ";
                                } else {
                                    finalTimeString = elapsedHours + " " + returnHour + "s ";
                                }

                            }


                        } else {
                            /*==== else of hours ===*/
                            if (elapsedMinutes >= 1) {
                                if (elapsedMinutes == 1) {
                                    finalTimeString = elapsedMinutes + " " + returnMin;
                                } else {
                                    finalTimeString = elapsedMinutes + " " + returnMin + "s ";
                                }

                            } else {
                                finalTimeString = 1 + " " + returnMin;
                            }

                        }
                    }


                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return finalTimeString;
    }


    public static String calculateTimeBetweenTwoDatesInPlusRoundOrder(String startDate, String endDate, boolean shortDescription) {
        /**
         * **** Easy Use ****
         * String currDate = new SimpleDateFormat(DateUtils.DATE_FORMATE_4).format(Calendar.getInstance().getTime());
         * String finalTime = DateUtils.calculateTimeBetweenTwoDates(list.get(position).getDatetime(), currDate);
         */

        String finalTimeString = "";

        String ago = "ago";
        String returnMin = "minute";
        String returnHour = "hour";
        String returnDay = "day";
        String returnMonth = "month";
        String returnYear = "year";

        if (shortDescription) {
            // Need short description
            ago = "";
            returnMin = "min";
            returnHour = "hour";
            returnDay = "day";
            returnMonth = "month";
            returnYear = "year";
        }


        SimpleDateFormat myFormat = new SimpleDateFormat(DATE_FORMATE_4, Locale.US); // 24 hours format

        try {
            Date date1 = myFormat.parse(startDate);
            Date date2 = myFormat.parse(endDate);
            long diff = date2.getTime() - date1.getTime();

            long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            long hours = TimeUnit.MILLISECONDS.toHours(diff);
            long days = TimeUnit.MILLISECONDS.toDays(diff);
            long months = days / 30;
            long year = months / 12;

            if (seconds < 60) {
                finalTimeString = "Now";
            } else if (minutes < 60) {
                if (minutes == 1) {
                    finalTimeString = minutes + " " + returnMin + " " + ago;
                } else {
                    finalTimeString = minutes + " " + returnMin + "s " + ago;
                }
            } else if (hours < 24) {
                if (hours == 1) {
                    finalTimeString = hours + " " + returnHour + " " + ago;
                } else {
                    finalTimeString = hours + " " + returnHour + "s " + ago;
                }
            } else if (days < 30) {
                if (days == 1) {
                    finalTimeString = days + " " + returnDay + " " + ago;
                } else {
                    finalTimeString = days + " " + returnDay + "s " + ago;
                }
            } else if (months < 12) {
                if (months == 1) {
                    finalTimeString = months + " " + returnMonth + " " + ago;
                } else {
                    finalTimeString = months + " " + returnMonth + "s " + ago;
                }
            } else {
                if (year == 1) {
                    finalTimeString = year + " " + returnYear + " " + ago;
                } else {
                    finalTimeString = year + " " + returnYear + "s " + ago;
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return finalTimeString;
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * e.g.
     * <br/>Give date value in integer
     * <br/>Return ArrayList of Week dates
     * <p/>e.g.<br/>
     * <t/>day e.g. SUNDAY, MONDAY etc<br/>
     * <t/>date e.g. 02, 31, 25 etc<br/>
     * <t/>month e.g. 01, 12, 05 etc<br/>
     * <t/>year e.g. 2016, 2018 etc<br/>
     * <t/>fromToday Date is before today's date or after or equals<br/>
     * <p/>
     * <p/>NOTE: This is not consider time for check date is before or after or equals
     */
    public static ArrayList<WeekDatesModel> getWeekDates(int date, int month, int year) {
        Calendar todayCal = Calendar.getInstance();

        Calendar weekCal = Calendar.getInstance();
        weekCal.set(Calendar.DAY_OF_MONTH, date);
        weekCal.set(Calendar.MONTH, month);
        weekCal.set(Calendar.YEAR, year);

        int weekNo = weekCal.get(Calendar.WEEK_OF_YEAR);
        weekCal.set(Calendar.WEEK_OF_YEAR, weekNo);

        weekCal.clear();

        weekCal.set(Calendar.WEEK_OF_YEAR, weekNo);
        weekCal.set(Calendar.YEAR, year);

        weekCal.set(Calendar.DAY_OF_WEEK, weekCal.getFirstDayOfWeek());
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE_5, Locale.US);

        ArrayList<WeekDatesModel> weekList = new ArrayList<WeekDatesModel>();

        for (int i = 0; i < 7; i++) {
            String day1 = "", date1 = "", month1 = "", year1 = "", fromToday1 = "";

            // Check date is before, after or equals
            if (weekCal.getTime().before(todayCal.getTime())) {
                if (todayCal.get(Calendar.DATE) == weekCal.get(Calendar.DATE) && todayCal.get(Calendar.MONTH) == weekCal.get(Calendar.MONTH) && todayCal.get(Calendar.YEAR) == weekCal.get(Calendar.YEAR)) {
                    fromToday1 = "equal"; // This is Today's Date
                } else {
                    fromToday1 = "before"; // This is Before today's Date
                }
            } else {
                fromToday1 = "after"; // This is After today's Date
            }

            day1 = String.valueOf(weekCal.get(Calendar.DAY_OF_WEEK));
            date1 = (weekCal.get(Calendar.DATE) > 0 && weekCal.get(Calendar.DATE) < 10) ? "0" + (weekCal.get(Calendar.DATE)) : (weekCal.get(Calendar.DATE)) + "";
            month1 = (weekCal.get(Calendar.MONTH) >= 0 && weekCal.get(Calendar.MONTH) < 10) ? "0" + (weekCal.get(Calendar.MONTH)) : (weekCal.get(Calendar.MONTH)) + "";
            year1 = String.valueOf(weekCal.get(Calendar.YEAR));

            //Logger.d("FULL DATE : --- " + sdf.format(weekCal.getTime()));

            weekList.add(new WeekDatesModel(day1, date1, month1, year1, fromToday1));

            // Plus 1 date
            weekCal.add(Calendar.DAY_OF_WEEK, 1);
        }

        return weekList;
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * <br/>Need pattern e.g. dd/MM/yyyy h:mm:ss
     * <br/>If pattern is empty then it will return below format date
     * <br/>E, MMM dd,yyyy hh:mm a
     * <p/>
     *
     * @param pattern
     * @return Return String as per your given pattern
     */
    public static String getCurrentDateTime(String pattern, boolean isUTC) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        if (isUTC) {
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        return dateFormat.format(new Date());
    }

    /**
     * Convert UTC time to Local time zone
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String convertUTCtoLocalTimeZone(String date, String date_formate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(date_formate, Locale.US);
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date myDate = null;
        try {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            myDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new SimpleDateFormat(date_formate, Locale.US).format(myDate); // Note: Use new DateFormat
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * Calculate time between two dates
     * <p/>
     * For e.g.
     * <p/>
     * Return 1 day ago, 1 minute ago, 2 years ago
     */
    public static String calculateTimeBetweenTwoDate(Context context, String startDate, String endDate) {
        /**
         *** Easy Use ****
         * String currDate = new SimpleDateFormat(DateUtils.DATE_FORMATE_4, Locale.US).format(Calendar.getInstance().getTime());
         * String finalTime = DateUtils.calculateTimeBetweenTwoDates(context,list.get(position).getDatetime(), currDate);
         */

        String finalTimeString = "";

        SimpleDateFormat myFormat = new SimpleDateFormat(DATE_FORMATE_4, Locale.US); // 24 hours format

        try {
            Date date1 = myFormat.parse(startDate);
            Date date2 = myFormat.parse(endDate);
            long diff = date2.getTime() - date1.getTime();

            long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            long hours = TimeUnit.MILLISECONDS.toHours(diff);
            long days = TimeUnit.MILLISECONDS.toDays(diff);
            long months = days / 30;
            long year = months / 12;

            if (seconds < 60) {
                finalTimeString = "Now";
            } else if (minutes < 60) {
                if (minutes == 1) {
                    finalTimeString = minutes + " " + context.getResources().getString(R.string.minute_ago);
                } else {
                    finalTimeString = minutes + " " + context.getResources().getString(R.string.minutes_ago);
                }
            } else if (hours < 24) {
                if (hours == 1) {
                    finalTimeString = hours + " " + context.getResources().getString(R.string.hour_ago);
                } else {
                    finalTimeString = hours + " " + context.getResources().getString(R.string.hours_ago);
                }
            } else if (days < 30) {
                if (days == 1) {
                    finalTimeString = days + " " + context.getResources().getString(R.string.day_ago);
                } else {
                    finalTimeString = days + " " + context.getResources().getString(R.string.days_ago);
                }
            } else if (months < 12) {
                if (months == 1) {
                    finalTimeString = months + " " + context.getResources().getString(R.string.month_ago);
                } else {
                    finalTimeString = months + " " + context.getResources().getString(R.string.months_ago);
                }
            } else {
                if (year == 1) {
                    finalTimeString = year + " " + context.getResources().getString(R.string.year_ago);
                } else {
                    finalTimeString = year + " " + context.getResources().getString(R.string.years_ago);
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return finalTimeString;
    }

    public static String convertLocalTimeZoneToUTC(String inputPattern, String date) {
        SimpleDateFormat utcDateFormat = new SimpleDateFormat(inputPattern);
        Date localDate = null;
        try {
            localDate = new SimpleDateFormat(inputPattern).parse(date); // Local Date Format (By default)
            utcDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return utcDateFormat.format(localDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Date getDateFromString(String dateStr, String formatStr) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.getDefault());
        format.setLenient(false);
        try {
            date = format.parse(dateStr);
            System.out.println(date);
            Log.e("date_millies", String.valueOf(date.getTime()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /*
     *  convert string to date
     */

    /*
     * current date in string format
     */
    public static Date getCurrentDate(String formatStr) {
        Date return_date = null;
        String datetime = "";
        SimpleDateFormat dateformat = new SimpleDateFormat(formatStr);

        Date date = new Date();
        datetime = dateformat.format(date);
        System.out.println("Current Date Time : " + datetime);
        return_date = getDateFromString(datetime, formatStr);
        return return_date;
    }

    /*
     * compare date with current date
     */
    public static int compareDateWithCurrent(String date) {
        Date now = new Date();
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE_8);
        now = getCurrentDate(DATE_FORMATE_8);
        Date theOtherDate = null;
        try {
            theOtherDate = sdf.parse(date);
            result = now.compareTo(theOtherDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "compareDateWithCurrent" + result);

        /* result will be an int < 0 if now Date is less than the theOtherDate Date, 0 if they are equal, and an int > 0 if this Date is greater.*/
        return result;
    }

    /*
     * compare two dates
     * result will be an int < 0 if theFirstDate Date is less than the theSecondDate Date,
     * 0 if they are equal,
     * and an int > 0 if this Date is greater.
     */
    public static int compareTwoDates(String startDate, String endDate, String dateFormat) {
        int result = 0;
        Date theFirstDate = getDateFromString(startDate, dateFormat);
        Date theSecondDate = getDateFromString(endDate, dateFormat);

        result = theFirstDate.compareTo(theSecondDate);
        Log.e(TAG, "compareDateWithCurrent" + result);

        /* result will be an int < 0 if theFirstDate Date is less than the theSecondDate Date, 0 if they are equal, and an int > 0 if this Date is greater.*/
        return result;
    }

    /*
     * isTimeNotExpired
     */
    public static boolean isTimeNotExpired(String date) {
        int condition_var = compareDateWithCurrent(date);
        return condition_var < 0;
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     *
     * @param date
     * @param inputPattern
     * @param field          The {@code Calendar} field to modify.
     * @param incrementValue
     * @return
     */
    public static String addFieldIntoDate(String date, String inputPattern, int field, int incrementValue) {
        try {
            Date date1 = new SimpleDateFormat(inputPattern, Locale.US).parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(field, incrementValue); // Add here
            return new SimpleDateFormat(inputPattern, Locale.US).format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String calculateTimeBetweenTwoDates(Context context, String startDate, String endDate, boolean isShort) {

        String finalTimeString = "";

        SimpleDateFormat myFormat = new SimpleDateFormat(DATE_FORMATE_4, Locale.US); // 24 hours format

        try {
            Date date1 = myFormat.parse(startDate);
            Date date2 = myFormat.parse(endDate);
            long diff = date2.getTime() - date1.getTime();

            long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            long hours = TimeUnit.MILLISECONDS.toHours(diff);
            long days = TimeUnit.MILLISECONDS.toDays(diff);
            long months = days / 30;
            long year = months / 12;

            if (seconds < 60) {
                finalTimeString = context.getResources().getString(R.string.Now);
            } else if (minutes < 60) {
                if (!isShort) {
                    if (minutes == 1) {
                        finalTimeString = minutes + " " + context.getResources().getString(R.string.minute_ago);
                    } else {
                        finalTimeString = minutes + " " + context.getResources().getString(R.string.minutes_ago);
                    }
                } else {
                    finalTimeString = minutes + " " + context.getResources().getString(R.string.m_short);
                }
            } else if (hours < 24) {
                if (!isShort) {
                    if (hours == 1) {
                        finalTimeString = hours + " " + context.getResources().getString(R.string.hour_ago);
                    } else {
                        finalTimeString = hours + " " + context.getResources().getString(R.string.hours_ago);
                    }
                } else {
                    finalTimeString = hours + " " + context.getResources().getString(R.string.h_short);
                }
            } else if (days < 30) {
                if (!isShort) {
                    if (days == 1) {
                        finalTimeString = days + " " + context.getResources().getString(R.string.day_ago);
                    } else {
                        finalTimeString = days + " " + context.getResources().getString(R.string.days_ago);
                    }
                } else {
                    finalTimeString = days + " " + context.getResources().getString(R.string.d_short);
                }
            } else if (months < 12) {

                if (months == 1) {
                    if (!isShort) {
                        finalTimeString = months + " " + context.getResources().getString(R.string.month_ago);
                    } else {
                        finalTimeString = months + " " + context.getResources().getString(R.string.mth_short);
                    }
                } else {
                    if (!isShort) {
                        finalTimeString = months + " " + context.getResources().getString(R.string.months_ago);
                    } else {
                        finalTimeString = months + " " + context.getResources().getString(R.string.mths_short);
                    }
                }
            } else {
                if (year == 1) {
                    if (!isShort) {
                        finalTimeString = year + " " + context.getResources().getString(R.string.year_ago);
                    } else {
                        finalTimeString = year + " " + context.getResources().getString(R.string.yr_short);
                    }
                } else {
                    if (!isShort) {
                        finalTimeString = year + " " + context.getResources().getString(R.string.years_ago);
                    } else {
                        finalTimeString = year + " " + context.getResources().getString(R.string.yrs_short);
                    }
                }
            }

        } catch (
                ParseException e) {
            e.printStackTrace();
        }

        return finalTimeString;
    }

    /**
     * Week Dates list Model class
     */
    public static class WeekDatesModel {
        private String
                day = "", // e.g. SUNDAY, MONDAY etc
                date = "", // e.g. 02, 31, 25 etc
                month = "", // e.g. 01, 12, 05 etc
                year = "", // e.g. 2016, 2018 etc
                fromToday = ""; // Date is before today's date or after or equals

        public WeekDatesModel(String day, String date, String month, String year, String fromToday) {
            this.day = day;
            this.date = date;
            this.month = month;
            this.year = year;
            this.fromToday = fromToday;
        }

        public String getDay() {
            return day;
        }

        public String getDate() {
            return date;
        }

        public String getMonth() {
            return month;
        }

        public String getYear() {
            return year;
        }

        public String getFromToday() {
            return fromToday;
        }
    }

    public static class RangeTimePickerDialog extends TimePickerDialog {

        /*
        **************
        * How to USE *
        **************

            int currHours = Integer.parseInt(DateUtils.getCurrentDateTime("HH")); // Need 24 hours format
            int currMinutes = Integer.parseInt(DateUtils.getCurrentDateTime("mm"));

            DateUtils.RangeTimePickerDialog timePicker = new DateUtils.RangeTimePickerDialog(
                    BookTaxiActivity.this,
                    R.style.dateTimePickerDialog,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String formattedDate = DateUtils.changeDateFormat("HH:mm", "hh:mm a", hourOfDay + ":" + minute);
                            tv_BookTaxiActivity_rideLaterTime.setText(formattedDate);
                        }
                    },
                    currHours,  // Hours
                    currMinutes,// Minutes
                    false
            );
            timePicker.setMin(currHours, currMinutes);
            timePicker.setMax(currHours + 12, currMinutes - 1); // Add 12 hours
            timePicker.setDialogTitle(getResources().getString(R.string.preBookingMessage)); // Must before show(); dialog
            timePicker.show();

            timePicker.setButtonTextTypefaceAndColor(
                    DialogInterface.BUTTON_POSITIVE,
                    false,
                    getResources().getString(R.string.done),
                    AppClass.typefaceBold,
                    ContextCompat.getColor(BookTaxiActivity.this, R.color.colorPrimary)
            );
            timePicker.setButtonTextTypefaceAndColor(
                    DialogInterface.BUTTON_NEGATIVE,
                    false,
                    getResources().getString(R.string.cancel),
                    AppClass.typefaceRegular,
                    ContextCompat.getColor(BookTaxiActivity.this, R.color.colorAccent)
            );
        */

        private int minHour = -1;
        private int minMinute = -1;

        private int maxHour = 25;
        private int maxMinute = 25;

        private int currentHour = 0;
        private int currentMinute = 0;

        private Calendar calendar = Calendar.getInstance();
        private DateFormat dateFormat;

        /**
         * Creates a new time picker dialog with the specified theme.
         *
         * @param context      the parent context
         * @param themeResId   Can be ZERO. The resource ID of the theme to apply to this dialog
         * @param hourOfDay    the initial hour
         * @param minute       the initial minute
         * @param is24HourView Whether this is a 24 hour view, or AM/PM.
         */
        public RangeTimePickerDialog(Context context, int themeResId, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView) {
            super(context, themeResId, callBack, hourOfDay, minute, is24HourView);
            currentHour = hourOfDay;
            currentMinute = minute;
            dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

            try {
                Class<?> superclass = getClass().getSuperclass();
                Field mTimePickerField = superclass.getDeclaredField("mTimePicker");
                mTimePickerField.setAccessible(true);
                TimePicker mTimePicker = (TimePicker) mTimePickerField.get(this);
                mTimePicker.setOnTimeChangedListener(this);
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            boolean validTime = true;
            if (hourOfDay < minHour || (hourOfDay == minHour && minute < minMinute)) {
                validTime = false;
            }

            if (hourOfDay > maxHour || (hourOfDay == maxHour && minute > maxMinute)) {
                validTime = false;
            }

            if (validTime) {
                currentHour = hourOfDay;
                currentMinute = minute;
            }

            updateTime(currentHour, currentMinute);
            // updateDialogTitle(view, currentHour, currentMinute);
        }

        public void setMin(int hour, int minute) {
            minHour = hour;
            minMinute = minute;
        }

        public void setMax(int hour, int minute) {
            maxHour = hour;
            maxMinute = minute;
        }

        /**
         * Some of devices (E.g. Moto G4 plus) are showing text in Non-Readable format
         * So, set it manually OK, CANCEL Button
         */
        private void setDefaultButtonText() {
            Button positionButton = getButton(DialogInterface.BUTTON_POSITIVE);
            positionButton.setText(getContext().getResources().getString(R.string.ok));

            Button negativeButton = getButton(DialogInterface.BUTTON_NEGATIVE);
            negativeButton.setText(getContext().getResources().getString(R.string.cancel));
        }

        /**
         * @param whichButton The identifier of the button that should be returned.
         *                    For example, this can be
         *                    {@link DialogInterface#BUTTON_POSITIVE}.
         * @param buttonText
         * @param typeface    can be null (If null then it will set default font of android)
         */
        public void setButtonTextTypefaceAndColor(int whichButton, boolean allCaps, String buttonText, Typeface typeface, int color) {
            Button positionButton = getButton(whichButton);
            positionButton.setText(buttonText);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                positionButton.setAllCaps(allCaps);
            }
            if (typeface != null) {
                positionButton.setTypeface(typeface);
            }
            positionButton.setTextColor(color);
        }

        /**
         * Intentionally created this method
         */
        public void show() {
            super.show();
            setDefaultButtonText(); // Set text (MUST Call after super.show() )
        }

        /**
         * Call Must before show(); dialog
         *
         * @param title
         */
        public void setDialogTitle(String title) {
            setTitle(title);
        }
    }

    public static String calculateHoursBetWeenTwoTimes(String timePattern, String startTime, String endTime) {

        try {

            SimpleDateFormat sdf = new SimpleDateFormat(timePattern, Locale.getDefault());
            Date d1 = sdf.parse(startTime);
            Date d2 = sdf.parse(endTime);

            long mills = d2.getTime() - d1.getTime();
            long hours = (mills / (1000 * 60 * 60));
            long mins = (mills / (1000 * 60)) % 60;


            return "" + hours;

        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }

    }

    public static long differentbetweenTwodate(String timePattern, String startTime, String endTime) {

        try {

            SimpleDateFormat sdf = new SimpleDateFormat(timePattern, Locale.getDefault());
            Date d1 = sdf.parse(startTime);
            Date d2 = sdf.parse(endTime);

            long mills = d2.getTime() - d1.getTime();
            long hours = (mills / (1000 * 60 * 60));
            long mins = (mills / (1000 * 60)) % 60;


            return mills;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * strDate1 is befor to strDate2 or not
     *
     * @param datePattern
     * @param strDate1
     * @param strDate2
     * @return
     */
    public static boolean isDateBefor(String datePattern, String strDate1, String strDate2) {

        boolean flag = false;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern, Locale.getDefault());
            Date d1 = sdf.parse(strDate1);
            Date d2 = sdf.parse(strDate2);

            long date = d1.getTime() - d2.getTime();

            System.out.println("1. " + sdf.format(d1).toUpperCase());
            System.out.println("2. " + sdf.format(d2).toUpperCase());

            if (date < 0) {
                System.out.println("d1 is before d2");
                flag = true;
            } else if (date > 0) {
                System.out.println("d1 is after d2");
            } else {
                System.out.println("d1 is equal to d2");
                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * startTime is befor to endTime or not
     *
     * @param timePattern
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isTimeBefor(String timePattern, String startTime, String endTime) {

        boolean flag = false;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(timePattern, Locale.getDefault());
            Date d1 = sdf.parse(startTime);
            Date d2 = sdf.parse(endTime);

            long date = d1.getTime() - d2.getTime();

            if (date < 0) {
                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    public static long getDaysBetweenDates(String DATE_FORMAT, String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Date startDate, endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays;
    }

    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    /**
     * checking is selected time is between two times or not
     * all input time pattern will be same
     *
     * @param pattern           - input string pattern
     * @param selectedTime      - seleted time
     * @param selectedStartTime - selected start time
     * @param selectedEndTime   - selected end time
     * @return true(if time between two times) / false(if time is not between two times)
     */
    public static boolean isNotTimeBetweenTwoTimes(String pattern, String selectedTime, String selectedStartTime, String selectedEndTime) {

        try {

            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());

            Date time = sdf.parse(selectedTime);
            Date startTime = sdf.parse(selectedStartTime);
            Date endTime = sdf.parse(selectedEndTime);

            return (time.before(startTime)) || (time.after(endTime));

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static long converDateToMiliSecondLong(String dateFormate, String strDate) {

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormate, Locale.getDefault());
        formatter.setLenient(false);
        Date d = null;
        long curMillis;
        try {
            d = formatter.parse(strDate);
            curMillis = d.getTime();
        } catch (Exception ex) {
            Log.v("Exception", ex.getLocalizedMessage());
            curMillis = 12345678910L;
        }

        return curMillis;

    }
}
