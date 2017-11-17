package com.atguigu.bos.bean;

import java.util.HashSet;
import java.util.Set;

public class Region {
    private String id;

    private String province;

    private String city;

    private String district;

    private String postcode;

	private String shortcode;

    private String citycode;

    public String getId() {
        return id;
    }
    
    private Set subareas = new HashSet(0);
    
    public Region(){
    	
    }
    public String getName(){
		return province+city + district;
	}

    public Region(String id, String province, String city, String district, String postcode, String shortcode,
			String citycode, Set subareas) {
		super();
		this.id = id;
		this.province = province;
		this.city = city;
		this.district = district;
		this.postcode = postcode;
		this.shortcode = shortcode;
		this.citycode = citycode;
		this.subareas = subareas;
	}

	public Region(String id, String province, String city, String district, String postcode, String shortcode,
			String citycode) {
		super();
		this.id = id;
		this.province = province;
		this.city = city;
		this.district = district;
		this.postcode = postcode;
		this.shortcode = shortcode;
		this.citycode = citycode;
	}

	public Set getSubareas() {
		return subareas;
	}

	public void setSubareas(Set subareas) {
		this.subareas = subareas;
	}

	public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode == null ? null : shortcode.trim();
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode == null ? null : citycode.trim();
    }
}