package com.junyu.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Table(name = "T_COMPARE_PHOTO")
public class ComparePhoto extends BasePojo implements Serializable {
    @Id
    @Column(name = "GUID")
    private String guid;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "HISTORY_GUID")
    private String historyGuid;

    @Column(name = "PHOTO_ID")
    private byte[] photoId;

    @Column(name = "PHOTO_USER")
    private byte[] photoUser;

    @Column(name = "PHOTO_CHIP")
    private byte[] photoChip;

    private static final long serialVersionUID = 1L;

    /**
     * @return GUID
     */
    public String getGuid() {
        return guid;
    }

    /**
     * @param guid
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return HISTORY_GUID
     */
    public String getHistoryGuid() {
        return historyGuid;
    }

    /**
     * @param historyGuid
     */
    public void setHistoryGuid(String historyGuid) {
        this.historyGuid = historyGuid;
    }

    /**
     * @return PHOTO_ID
     */
    public byte[] getPhotoId() {
        return photoId;
    }

    /**
     * @param photoId
     */
    public void setPhotoId(byte[] photoId) {
        this.photoId = photoId;
    }

    /**
     * @return PHOTO_USER
     */
    public byte[] getPhotoUser() {
        return photoUser;
    }

    /**
     * @param photoUser
     */
    public void setPhotoUser(byte[] photoUser) {
        this.photoUser = photoUser;
    }

    /**
     * @return PHOTO_CHIP
     */
    public byte[] getPhotoChip() {
        return photoChip;
    }

    /**
     * @param photoChip
     */
    public void setPhotoChip(byte[] photoChip) {
        this.photoChip = photoChip;
    }
}