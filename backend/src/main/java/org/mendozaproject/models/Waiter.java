package org.mendozaproject.models;

import jakarta.persistence.*;
import org.mendozaproject.enums.Schedule;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Waiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer waiterId;
    private String name;
    private String contactNumber;
    @Enumerated(EnumType.STRING)
    private Schedule schedule;


    public Waiter(Integer waiterId, String name, String contactNumber, Schedule schedule) {
        this.waiterId = waiterId;
        this.name = name;
        this.contactNumber = contactNumber;
        this.schedule = schedule;
    }

    public Waiter() {
    }


    public Integer getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(Integer waiterId) {
        this.waiterId = waiterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
