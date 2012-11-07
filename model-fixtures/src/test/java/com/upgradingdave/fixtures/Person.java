package com.upgradingdave.fixtures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Person {

    String firstName;
    String lastName;
    String email;
    Date lastUpdated;

    Person() {}

    Person(Date lastUpdated, String firstName, String lastName, String email) {
        this.lastUpdated = lastUpdated;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @XmlElement
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @XmlElement
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement
    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public boolean equals(Object o){

        if(this == o) {
            return true;
        }

        if(!(o instanceof Person)){
            return false;
        }

        Person model = (Person)o;

        return (this.email == null ? model.email == null : email.equals(model.email))
                && (this.firstName == null ? model.firstName == null : firstName.equals(model.firstName))
                && (this.lastName == null ? model.lastName == null : lastName.equals(model.lastName))
                && (this.lastUpdated == null ? model.lastUpdated == null : lastUpdated.equals(model.lastUpdated));
    }

    @Override
    public int hashCode() {

        int hash = 7;
        hash = 31 * hash + (null == email ? 0 : email.hashCode());
        hash = 31 * hash + (null == firstName ? 0 : firstName.hashCode());
        hash = 31 * hash + (null == lastName ? 0 : lastName.hashCode());
        return hash;

    }

    @Override
    public String toString(){

        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(this.email == null ? " Email: null" : " Email: "+email);
        builder.append(this.firstName == null ? " firstName: null" : " firstName: "+firstName);
        builder.append(this.lastName == null ? " lastName: null" : " lastName: "+lastName);
        builder.append(this.lastUpdated == null ? " lastUpdated: null" : " lastUpdated: "+lastUpdated);
        return builder.toString();
    }

}

