package com.xsdzq.mm.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "lcj_employee_info")
@EntityListeners(AuditingEntityListener.class)
public class EmpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
	@SequenceGenerator(name = "employee_sequence", sequenceName = "sequence_employee", allocationSize = 1)
	@Column(name = "id")
	private long id;

	@Column(name = "emp_id", unique = true, nullable = false)
	private int empId;

	@Column(name = "emp_name")
	private String empName;// 人员姓名

	@Column(name = "emp_code")
	private String empCode;// 人员编号

	@Column(name = "emp_type")
	private String empType;// 在编性质

	@Column(name = "emp_category")
	private String empCategory;// 人员类别

	@Column(name = "contract")
	private String contract;// 签约合同

	@Column(name = "entry_time")
	private String entryTime;// 入职时间

	@Column(name = "sales_department")
	private String salesDepartment;// 隶属营业部

	@Column(name = "division")
	private String division;// 隶属赛区

	@Column(name = "departmentCode", insertable = false, updatable = false)
	private String departmentCode;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "departmentCode", referencedColumnName = "code")
	private DepartmentEntity departmentEntity;

	@Column(name = "ticketId", insertable = false, updatable = false)
	private long ticketId;
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ticketId", referencedColumnName = "id")
	private TicketEntity ticketEntity;

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public long getTicketId() {
		return ticketId;
	}

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	public TicketEntity getTicketEntity() {
		return ticketEntity;
	}

	public void setTicketEntity(TicketEntity ticketEntity) {
		this.ticketEntity = ticketEntity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public String getEmpCategory() {
		return empCategory;
	}

	public void setEmpCategory(String empCategory) {
		this.empCategory = empCategory;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public String getSalesDepartment() {
		return salesDepartment;
	}

	public void setSalesDepartment(String salesDepartment) {
		this.salesDepartment = salesDepartment;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public DepartmentEntity getDepartmentEntity() {
		return departmentEntity;
	}

	public void setDepartmentEntity(DepartmentEntity departmentEntity) {
		this.departmentEntity = departmentEntity;
	}

	@Override
	public String toString() {
		return "EmpEntity [id=" + id + ", empName=" + empName + ", empCode=" + empCode + ", empType=" + empType
				+ ", empCategory=" + empCategory + ", contract=" + contract + ", entryTime=" + entryTime
				+ ", salesDepartment=" + salesDepartment + ", division=" + division + "]";
	}

}
