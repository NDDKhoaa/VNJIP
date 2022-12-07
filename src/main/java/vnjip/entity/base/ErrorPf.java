package vnjip.entity.base;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "ErrorPf")
public class ErrorPf {
	@Id
	@Column(name = "Error_Code", nullable = true, length = 4)
	private String errorCode;
	@Column(name = "Error_Desc", nullable = true, length = 200)
	private String errorDesc;

	public ErrorPf() {
		super();
	}

	public ErrorPf(String errorCode, String errorDesc) {
		super();
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

}