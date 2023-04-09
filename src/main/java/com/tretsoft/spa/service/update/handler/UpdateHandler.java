package com.tretsoft.spa.service.update.handler;

public abstract class UpdateHandler {

    public final String oldVersion;
    public final String newVersion;

    public UpdateHandler(String oldVersion, String newVersion) {
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
    }

    public abstract void run();

}
