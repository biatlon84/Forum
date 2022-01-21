package com.biathlon84.forum.user;

public enum Perm {
    DEV_READ("dev:r"),
    DEV_WRITE("dev:w");
    private final String perm;

    Perm(String perm) {
        this.perm = perm;
    }

    public String getPerm() {
        return perm;
    }
}
