package com.cc.project.iot.domain;


import java.io.Serializable;
import java.util.Date;

public class FuncEt implements Serializable {

  private long id;
  private long driveId;
  private String eId;
  private String name;
  private String description;
  private long type;
  private String location;
  private java.sql.Date time;
  private String deptName;
  private String beginTime;
  private String endTime;

  @Override
  public String toString() {
    return "FuncEt{" +
            "id=" + id +
            ", driveId=" + driveId +
            ", eId='" + eId + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", type=" + type +
            ", location='" + location + '\'' +
            ", time=" + time +
            ", deptName='" + deptName + '\'' +
            ", beginTime='" + beginTime + '\'' +
            ", endTime='" + endTime + '\'' +
            '}';
  }

  public String getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(String beginTime) {
    this.beginTime = beginTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getDriveId() {
    return driveId;
  }

  public void setDriveId(long driveId) {
    this.driveId = driveId;
  }


  public String getEId() {
    return eId;
  }

  public void setEId(String eId) {
    this.eId = eId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public long getType() {
    return type;
  }

  public void setType(long type) {
    this.type = type;
  }


  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }


  public java.sql.Date getTime() {
    return time;
  }

  public void setTime(java.sql.Date time) {
    this.time = time;
  }

}
