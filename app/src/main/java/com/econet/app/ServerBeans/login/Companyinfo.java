package com.econet.app.ServerBeans.login;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2020-06-02 15:57:8
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Companyinfo {

    @JsonProperty("companyId")
    private String companyid;
    @JsonProperty("companyName")
    private String companyname;
    @JsonProperty("companyAddress")
    private String companyaddress;
    public void setCompanyid(String companyid) {
         this.companyid = companyid;
     }
     public String getCompanyid() {
         return companyid;
     }

    public void setCompanyname(String companyname) {
         this.companyname = companyname;
     }
     public String getCompanyname() {
         return companyname;
     }

    public void setCompanyaddress(String companyaddress) {
         this.companyaddress = companyaddress;
     }
     public String getCompanyaddress() {
         return companyaddress;
     }

}