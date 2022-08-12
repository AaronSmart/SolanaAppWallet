package com.econet.app.ServerBeans.register;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Auto-generated: 2020-06-02 14:44:2
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Userinfo {

    @JsonProperty("certType")
    private String certtype;
    @JsonProperty("certId")
    private String certid;
    @JsonProperty("firstName")
    private String firstname;
    @JsonProperty("lastName")
    private String lastname;
    private String gender;
    private Date birth;
    private String phone;
    private String region;
    private String address;
    @JsonProperty("healthStatus")
    private String healthstatus;
    @JsonProperty("healthStatusTime")
    private String healthstatustime;
    public void setCerttype(String certtype) {
         this.certtype = certtype;
     }
     public String getCerttype() {
         return certtype;
     }

    public void setCertid(String certid) {
         this.certid = certid;
     }
     public String getCertid() {
         return certid;
     }

    public void setFirstname(String firstname) {
         this.firstname = firstname;
     }
     public String getFirstname() {
         return firstname;
     }

    public void setLastname(String lastname) {
         this.lastname = lastname;
     }
     public String getLastname() {
         return lastname;
     }

    public void setGender(String gender) {
         this.gender = gender;
     }
     public String getGender() {
         return gender;
     }

    public void setBirth(Date birth) {
         this.birth = birth;
     }
     public Date getBirth() {
         return birth;
     }

    public void setPhone(String phone) {
         this.phone = phone;
     }
     public String getPhone() {
         return phone;
     }

    public void setRegion(String region) {
         this.region = region;
     }
     public String getRegion() {
         return region;
     }

    public void setAddress(String address) {
         this.address = address;
     }
     public String getAddress() {
         return address;
     }

    public void setHealthstatus(String healthstatus) {
         this.healthstatus = healthstatus;
     }
     public String getHealthstatus() {
         return healthstatus;
     }

    public void setHealthstatustime(String healthstatustime) {
         this.healthstatustime = healthstatustime;
     }
     public String getHealthstatustime() {
         return healthstatustime;
     }

}