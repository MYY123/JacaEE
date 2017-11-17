package com.atguigu.bos.bean;

public class Subarea {
    

	private String id;

    private Decidedzone decidedzone;//定区

    private Region region;//区域

    private String addresskey;

    private String startnum;

    private String endnum;

    private String single;

    private String position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    
    public Decidedzone getDecidedzone() {
		return decidedzone;
	}

	public void setDecidedzone(Decidedzone decidedzone) {
		this.decidedzone = decidedzone;
	}                
	public String getSubareaid(){
		return id;
	}

  /*  public String getDecidedzoneId() {
        return decidedzoneId;
    }

    public void setDecidedzoneId(String decidedzoneId) {
        this.decidedzoneId = decidedzoneId == null ? null : decidedzoneId.trim();
    }*/

  
    public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getAddresskey() {
        return addresskey;
    }

    public void setAddresskey(String addresskey) {
        this.addresskey = addresskey == null ? null : addresskey.trim();
    }

    public String getStartnum() {
        return startnum;
    }

    public void setStartnum(String startnum) {
        this.startnum = startnum == null ? null : startnum.trim();
    }

    public String getEndnum() {
        return endnum;
    }

    public void setEndnum(String endnum) {
        this.endnum = endnum == null ? null : endnum.trim();
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single == null ? null : single.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }
}