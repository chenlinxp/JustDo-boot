package com.justdo.system.file.domain;


import com.justdo.common.domain.BaseBean;

import java.util.Date;

/**
 * 文件上传
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
public class FileDO extends BaseBean {
    //
    private String fileId;
    // 文件类型
    private Integer fileType;
    // URL地址
    private String fileUrl;
    // 创建时间
    private Date createTime;


    public FileDO() {
        super();
    }


    public FileDO(Integer fileType, String fileUrl, Date createTime) {
        super();
        this.fileType = fileType;
        this.fileUrl = fileUrl;
        this.createTime = createTime;
    }


    /**
     * 设置：
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * 获取：
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * 设置：文件类型
     */
    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取：文件类型
     */
    public Integer getFileType() {
        return fileType;
    }

    /**
     * 设置：URL地址
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    /**
     * 获取：URL地址
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "FileDO{" +
                "fileId=" + fileId +
                ", fileType=" + fileType +
                ", fileUrl='" + fileUrl + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
