package com.tretsoft.spa.old.modem;

import lombok.Data;

@Data
public class PhoneSms {
    private int id;
    private String storage;
    private String status;
    private String phoneNumber;
    private String phoneName;
    private String date;
    private String time;
    private String content;
}
