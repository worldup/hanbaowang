package com.upbest.mvc.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "bk_area")
@JsonIgnoreProperties({"parent"})
public class BArea implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id; 
    
    @Column(name="area")
    private String area;
    
    @Column(name="sort_num")
    private Integer sortNum;
    
    @ManyToOne
    @JoinColumn(name="pId",referencedColumnName="id")
    private BArea parent;
    
    /**
     * 1:直辖市
     */
    @Column(name="level")
    private Integer level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public BArea getParent() {
		return parent;
	}

	public void setParent(BArea parent) {
		this.parent = parent;
	}
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	

	/*@Override
    public String toString() {
        return "BArea [id=" + id + ", area=" + area + ", pId=" + parent + "]";
    }*/
    
    

}
