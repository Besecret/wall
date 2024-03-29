package com.wanding.model;

import java.util.Calendar;
import java.util.Date;

public class UserCardInfo {
    private Integer id;

    private Integer cardname;

    private Integer userid;
    
    /**
     * user entity
     */
    private UserInfo user;

    private Date firstusetime;

    private Date createdtime;

    private Date invalidtime;

    private Integer num;

    private String cardtype;

    private String addition1;

    private String addition2;

    private String addition3;
    
    public UserCardInfo() {
        
    }
    
    public UserCardInfo(int userId,String cardType) {
        this.cardname =  new Double(Math.random()*100).intValue();
        this.userid = userId;
        this.createdtime = new Date();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(this.createdtime);
        rightNow.add(Calendar.YEAR,-1);
        this.invalidtime = rightNow.getTime() ;
        this.cardtype =cardType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCardname() {
        return cardname;
    }

    public void setCardname(Integer cardname) {
        this.cardname = cardname;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    
    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Date getFirstusetime() {
        if(firstusetime ==null){
            return new Date(0L);
        }
        return firstusetime;
    }

    public void setFirstusetime(Date firstusetime) {
        this.firstusetime = firstusetime;
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public Date getInvalidtime() {
        return invalidtime;
    }

    public void setInvalidtime(Date invalidtime) {
        this.invalidtime = invalidtime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype == null ? null : cardtype.trim();
    }

    public String getAddition1() {
        return addition1;
    }

    public void setAddition1(String addition1) {
        this.addition1 = addition1 == null ? null : addition1.trim();
    }

    public String getAddition2() {
        return addition2;
    }

    public void setAddition2(String addition2) {
        this.addition2 = addition2 == null ? null : addition2.trim();
    }

    public String getAddition3() {
        return addition3;
    }

    public void setAddition3(String addition3) {
        this.addition3 = addition3 == null ? null : addition3.trim();
    }
}