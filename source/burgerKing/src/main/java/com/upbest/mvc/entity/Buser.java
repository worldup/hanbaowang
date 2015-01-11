package com.upbest.mvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "bk_user")
public class Buser implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
	
	 @Column(name = "create_date")
	 private Date createdate;
	 
	 @Column(name="role")
	 private String role;
	 
	 @Column(name = "modify_date")
	 private Date modifydate;
	 
	 @Column(name="name")
	 @Size(max=100)
	 private String name;
	 
	 @Column(name="pwd")
	 @Size(max=100)
	 private String pwd;
	 
	 @Column(name="real_name")
	 @Size(max=255)
	 private String realname;
	 
	 @Column(name="telephone")
	 @Size(max=255)
	 private String telephone;
	 
	 @Column(name="is_del")
	 @Size(max=100)
	 private String isdel;
	 
	 @Column(name="pid")
	 private Integer pid;
	 

	 @Column(name="emp")
	 @Size(max=100)
     private String emp;

	 @Column(name="pic")
     private String pic;
	 
	 @Column(name="area_id")
	 private Integer areaId;

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}


    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
    public String getEmp() {
        return emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Buser [id=" + id + ", createdate=" + createdate + ", role=" + role + ", modifydate=" + modifydate + ", name=" + name + ", pwd=" + pwd + ", realname=" + realname + ", telephone="
                + telephone + ", isdel=" + isdel + ", pid=" + pid + ", emp=" + emp + ", pic=" + pic + "]";
    }

}
