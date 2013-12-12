package com.upgradingdave.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class UserAccount implements Model<Integer>{

  Integer id;
  String userName;
  String password;
  Date createDate;
  Date lastUpdateDate;

  public UserAccount() {};

  public UserAccount(Integer id, String userName, String password, Date createDate, Date lastUpdateDate) {
    this.id = id;
    this.userName = userName;
    this.password = password;
    this.createDate = createDate;
    this.lastUpdateDate = lastUpdateDate;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getLastUpdateDate() {
    return lastUpdateDate;
  }

  public void setLastUpdateDate(Date lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserAccount that = (UserAccount) o;

    if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (lastUpdateDate != null ? !lastUpdateDate.equals(that.lastUpdateDate) : that.lastUpdateDate != null)
      return false;
    if (password != null ? !password.equals(that.password) : that.password != null) return false;
    if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
    result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "UserAccount{" +
            "id=" + id +
            ", userName='" + userName + '\'' +
            ", password='" + password + '\'' +
            ", createDate=" + createDate +
            ", lastUpdateDate=" + lastUpdateDate +
            '}';
  }
}
