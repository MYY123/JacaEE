package com.atguigu.bos.bean;


public class Decidedzone {
    private String id;
    private String name;
	private Staff staff;
	
	/* public String getStaffId() {
    return staffId;
}        

public void setStaffId(String staffId) {
    this.staffId = staffId == null ? null : staffId.trim();
}*/
	
    public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

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

   
}