package com.atguigu.bos.bean;
        
import javax.validation.constraints.Pattern;

public class Staff {
    private String id;
    @Pattern(regexp="(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})"
    		,message="用户名必须是2-5位中文或者6-16位英文和数字的组合")
    private String name;

    private String telephone;

    private String haspda = "0";//是否有PDA 1：有 0：无
	private String deltag = "0";//删除标识位 1：已删除 0：未删除

    private String station;

    private String standard;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getHaspda() {
        return haspda;
    }

    public void setHaspda(String haspda) {
        this.haspda = haspda == null ? null : haspda.trim();
    }

    public String getDeltag() {
        return deltag;
    }

    public void setDeltag(String deltag) {
		this.deltag = deltag;
	}

	public void staff(String deltag) {
        this.deltag = deltag == null ? null : deltag.trim();
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station == null ? null : station.trim();
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard == null ? null : standard.trim();
    }
}