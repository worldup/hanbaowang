package com.upbest.mvc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Entity
@Table(name = "bk_question")
public class BQuestion implements Serializable{
	 private static final long serialVersionUID = 1L;

	    @Id
	    @Column(name = "id")
	    @GeneratedValue
	    private Integer id;
	    
	    @Column(name="q_value")
	    private Integer qvalue;
	    
	    @Column(name="q_module")
	    private Integer qmodule;
	    
	    @Column(name="q_content")
	    @Size(max=500)
	    private String qcontent;
	    
	    @Column(name="q_answer")
	    @Size(max=1000)
	    private String qanswer;
	    
	    @Column(name="q_standard")
	    @Size(max=1000)
	    private String qstandard;
	    
	    @Column(name="q_remark")
	    @Size(max=1000)
	    private String qremark;
	    
	    @Column(name="q_type")
	    private Integer qtype;
	    
	    @Column(name="is_upload_pic")
	    private Integer isUploadPic;
	    
	    @Column(name="serial_number")
	    @Size(max=100)
        private String serialNumber;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getQvalue() {
			return qvalue;
		}

		public void setQvalue(Integer qvalue) {
			this.qvalue = qvalue;
		}

		public Integer getQmodule() {
			return qmodule;
		}

		public void setQmodule(Integer qmodule) {
			this.qmodule = qmodule;
		}

		public String getQcontent() {
			return qcontent;
		}

		public void setQcontent(String qcontent) {
			this.qcontent = qcontent;
		}

		public String getQanswer() {
			return qanswer;
		}

		public void setQanswer(String qanswer) {
			this.qanswer = qanswer;
		}

		public String getQstandard() {
			return qstandard;
		}

		public void setQstandard(String qstandard) {
			this.qstandard = qstandard;
		}

		public String getQremark() {
			return qremark;
		}

		public void setQremark(String qremark) {
			this.qremark = qremark;
		}

		public Integer getQtype() {
			return qtype;
		}

		public void setQtype(Integer qtype) {
			this.qtype = qtype;
		}
		

		public Integer getIsUploadPic() {
            return isUploadPic;
        }

        public void setIsUploadPic(Integer isUploadPic) {
            this.isUploadPic = isUploadPic;
        }
        
      

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        @Override
        public String toString() {
            return "BQuestion [id=" + id + ", qvalue=" + qvalue + ", qmodule=" + qmodule + ", qcontent=" + qcontent + ", qanswer=" + qanswer + ", qstandard=" + qstandard + ", qremark=" + qremark
                    + ", qtype=" + qtype + ", isUploadPic=" + isUploadPic + ", serialNumber=" + serialNumber + "]";
        }

}
