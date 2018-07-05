package com.junyu.pojo;

import java.io.Serializable;
import javax.persistence.*;

public class Z extends BasePojo implements Serializable {
    private String a;

    private byte[] photo;

    private static final long serialVersionUID = 1L;

    /**
     * @return a
     */
    public String getA() {
        return a;
    }

    /**
     * @param a
     */
    public void setA(String a) {
        this.a = a;
    }

    /**
     * @return photo
     */
    public byte[] getPhoto() {
        return photo;
    }

    /**
     * @param photo
     */
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}