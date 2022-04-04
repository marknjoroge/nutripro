package com.virtualeatery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Constants {

    public String dateToPlain = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());

}