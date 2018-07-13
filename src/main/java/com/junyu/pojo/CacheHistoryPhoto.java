package com.junyu.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Table(name = "F_CACHE_HISTORY_PHOTO")
public class CacheHistoryPhoto extends BasePojo implements Serializable {
    @Id
    @Column(name = "GUID")
    private String guid;

    @Column(name = "HISTORY_GUID")
    private String historyGuid;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "PHOTO_ID")
    private String photoId;

    @Column(name = "PHOTO_USER")
    private String photoUser;

    @Column(name = "PHO_ID")
    private byte[] phoId;

    @Column(name = "PHO_USER")
    private byte[] phoUser;

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
     * @return PHOTO_ID
     */
    public String getPhotoId() {
        return photoId;
    }

    /**
     * @param photoId
     */
    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    /**
     * @return PHOTO_USER
     */
    public String getPhotoUser() {
        return photoUser;
    }

    /**
     * @param photoUser
     */
    public void setPhotoUser(String photoUser) {
        this.photoUser = photoUser;
    }

    /**
     * @return PHO_ID
     */
    public byte[] getPhoId() {
        return phoId;
    }

    /**
     * @param phoId
     */
    public void setPhoId(byte[] phoId) {
        this.phoId = phoId;
    }

    /**
     * @return PHO_USER
     */
    public byte[] getPhoUser() {
        return phoUser;
    }

    /**
     * @param phoUser
     */
    public void setPhoUser(byte[] phoUser) {
        this.phoUser = phoUser;
    }
}