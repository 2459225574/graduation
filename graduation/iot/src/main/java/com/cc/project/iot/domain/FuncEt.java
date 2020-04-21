package com.cc.project.iot.domain;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FuncEt implements Serializable {
  private long userId;
  private long roleId;
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
  private String status;
  private String roleName;
  private String roleKey;
  private String createBy;
  private String path;
  private boolean online;
}
